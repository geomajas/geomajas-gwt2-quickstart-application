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

import com.google.gwt.event.dom.client.HumanInputEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import org.geomajas.geometry.Coordinate;
import org.geomajas.geometry.Geometry;
import org.geomajas.geometry.service.MathService;
import org.geomajas.gwt.client.map.RenderSpace;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.controller.AbstractMapController;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.gwt2.client.map.feature.FeatureMapFunction;
import org.geomajas.gwt2.client.map.feature.ServerFeatureService;
import org.geomajas.gwt2.client.map.layer.FeaturesSupported;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that provides a feature based on a location clicked on the map.
 *
 * @author Dosi Bingov
 * @author Oliver May
 * @author Jan De Moerloose
 * @author David Debuck
 *
 */
public class FeatureClickedListener extends AbstractMapController {

	private Map<String, Feature> clickedFeatures = new HashMap<String, Feature>();

	private int pixelBuffer = 10;

	private double clickDelta = 2;

	protected Coordinate clickedPosition;

	/**
	 * Default constructor.
	 */
	public FeatureClickedListener() {
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
	public void onMouseUp(MouseUpEvent event) {

		if (isDownPosition(event)) {

			Geometry point = new Geometry(Geometry.POINT, 0, -1);
			Coordinate coordinate = getLocation(event, RenderSpace.WORLD);
			point.setCoordinates(new Coordinate[] { coordinate });

			GeomajasServerExtension
					.getInstance()
					.getServerFeatureService()
					.search(mapPresenter, point, calculateBufferFromPixelTolerance(),
							ServerFeatureService.QueryType.INTERSECTS,
							ServerFeatureService.SearchLayerType.SEARCH_ALL_LAYERS, -1, new SelectionCallback()
					);

		}

	}

	@Override
	public void onMouseDown(MouseDownEvent event) {

		clickedPosition = getLocation(event, RenderSpace.SCREEN);

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
						for (Feature f : features) {

							clickedFeatures.put(f.getLabel(), f);

						}

					}
				}

			}

			mapPresenter.getEventBus().fireEvent(
					new FeatureClickedEvent(clickedPosition, new ArrayList<Feature>(clickedFeatures.values())));

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

	/**
	 * Is the event at the same location as the "down" event?
	 *
	 * @param event The event to check.
	 * @return true or false.
	 */
	private boolean isDownPosition(HumanInputEvent<?> event) {
		if (clickedPosition != null) {
			Coordinate location = getLocation(event, RenderSpace.SCREEN);
			if (MathService.distance(clickedPosition, location) < clickDelta) {
				return true;
			}
		}
		return false;
	}

}