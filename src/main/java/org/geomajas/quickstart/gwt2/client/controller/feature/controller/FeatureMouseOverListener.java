/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2015 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.geomajas.quickstart.gwt2.client.controller.feature.controller;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.user.client.Timer;
import org.geomajas.geometry.Coordinate;
import org.geomajas.geometry.Geometry;
import org.geomajas.gwt.client.map.RenderSpace;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.controller.AbstractMapController;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.gwt2.client.map.feature.FeatureMapFunction;
import org.geomajas.gwt2.client.map.feature.ServerFeatureService;
import org.geomajas.gwt2.client.map.layer.FeaturesSupported;
import org.geomajas.quickstart.gwt2.client.ApplicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controller that provides a list of features for the position
 * where the mouse pointer currently resides on the map.
 *
 * @author David Debuck
 *
 */
public class FeatureMouseOverListener extends AbstractMapController {

	private final static Logger LOGGER = Logger.getLogger(FeatureMouseOverListener.class.getName());

	private Map<String, Feature> clickedFeatures = new HashMap<String, Feature>();

	private int pixelBuffer = 10;

	private int delay = 500; // 0.5s

	private Timer timer;

	private Coordinate hoverPosition;

	private Coordinate worldCoordinate;

	/**
	 * Default constructor.
	 */
	public FeatureMouseOverListener() {
		super(false);
	}

	/**
	 * Set a buffer in pixels. Default is 10 pixels.
	 * This is to include all features within this range on the map.
	 *
	 * @param pixelBuffer buffer in pixels.
	 */
	private void setPixelBuffer(int pixelBuffer) {
		this.pixelBuffer = pixelBuffer;
	}

	/**
	 * Set the the delay for searching features on the current position.
	 *
	 * @param delay how long to wait for another search on the position.
	 */
	private void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public void onActivate(MapPresenter mapPresenter) {
		super.onActivate(mapPresenter);
		this.mapPresenter = mapPresenter;
	}

	@Override
	public void onDeactivate(MapPresenter mapPresenter) {
		super.onDeactivate(mapPresenter);
	}

	@Override
	public void onMouseMove(final MouseMoveEvent event) {

		hoverPosition = getLocation(event, RenderSpace.SCREEN);

		// This will make sure that even when we are dragging we get some feedback.
		mapPresenter.getEventBus().fireEvent(
				new FeatureMouseOverEvent(hoverPosition, null));

		if (!isDragging()) {

			worldCoordinate = getLocation(event, RenderSpace.WORLD);

			// Only execute a search after a certain time.
			// Save some server power here.
			if (timer == null) {
				timer = new Timer() {

					public void run() {

						if (ApplicationService.getInstance().isTooltipShowingAllowed()) {

							ApplicationService.getInstance().getMapPresenter().setCursor("progress");

							Geometry point = new Geometry(Geometry.POINT, 0, -1);
							point.setCoordinates(new Coordinate[] { worldCoordinate });

							GeomajasServerExtension
									.getInstance()
									.getServerFeatureService()
									.search(mapPresenter, point, calculateBufferFromPixelTolerance(),
											ServerFeatureService.QueryType.INTERSECTS,
											ServerFeatureService.SearchLayerType.SEARCH_ALL_LAYERS, -1,
											new SelectionCallback()
									);

						} else {
							timer.cancel();
							ApplicationService.getInstance().getMapPresenter().setCursor("default");
						}

					}
				};
				timer.schedule(delay);

			} else {
				ApplicationService.getInstance().getMapPresenter().setCursor("default");
				timer.cancel();
				timer.schedule(delay);
			}

		}

	}

	/**
	 * Callback for feature searches.
	 *
	 * @author David Debuck
	 */
	private class SelectionCallback implements FeatureMapFunction {

		@Override
		public void execute(Map<FeaturesSupported, List<Feature>> featureMap) {

			clickedFeatures.clear();

			for (FeaturesSupported layer : featureMap.keySet()) {
				List<Feature> features = featureMap.get(layer);

				if (layer.isMarkedAsVisible()) {
					if (features != null) {

						// Don't bother adding when features for this layer are greater than 10
						if (features.size() > 10) {
							clickedFeatures.put(layer.getTitle(), null);
						} else {
							for (Feature f : features) {

								clickedFeatures.put(f.getLabel(), f);
							}
						}

					}
				}

			}

			mapPresenter.getEventBus().fireEvent(
					new FeatureMouseOverEvent(hoverPosition, new ArrayList<Feature>(clickedFeatures.values())));

			ApplicationService.getInstance().getMapPresenter().setCursor("default");
		}

	}

	/**
	 * Calculate a buffer in which the listener may include the features from the map.
	 *
	 * @return double buffer
	 */
	private double calculateBufferFromPixelTolerance() {

		Coordinate c1 = mapPresenter.getViewPort().getTransformationService()
				.transform(new Coordinate(0, 0), RenderSpace.SCREEN, RenderSpace.WORLD);
		Coordinate c2 = mapPresenter.getViewPort().getTransformationService()
				.transform(new Coordinate(pixelBuffer, 0), RenderSpace.SCREEN, RenderSpace.WORLD);
		return c1.distance(c2);

	}

}