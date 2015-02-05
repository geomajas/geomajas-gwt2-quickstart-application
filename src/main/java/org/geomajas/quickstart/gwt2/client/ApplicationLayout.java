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
package org.geomajas.quickstart.gwt2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt2.client.GeomajasImpl;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.event.MapInitializationEvent;
import org.geomajas.gwt2.client.event.MapInitializationHandler;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.gwt2.client.map.layer.tile.TileConfiguration;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;
import org.geomajas.gwt2.plugin.tilebasedlayer.client.TileBasedLayerClient;
import org.geomajas.gwt2.plugin.tilebasedlayer.client.layer.OsmLayer;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureMouseOverEvent;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureMouseOverHandler;
import org.geomajas.quickstart.gwt2.client.i18n.ApplicationMessages;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;

import java.util.ArrayList;
import java.util.List;

/**
 * General Application layout for this application.
 */
public class ApplicationLayout extends ResizeComposite {

	private static final int TILE_DIMENSION = 256;

	private static final int MAX_ZOOM_LEVELS = 19;

	private static final double EQUATOR_IN_METERS = 40075016.686;

	private static final double HALF_EQUATOR_IN_METERS = 40075016.686 / 2;

	private final ApplicationService appService;

	private final MapPresenter mapPresenter;

	private final MapLayoutPanel mapLayoutPanel;

	private List<Double> resolutions;

	/**
	 * UI binder interface for this layout.
	 */
	interface MyUiBinder extends UiBinder<Widget, ApplicationLayout> {

	}

	private ApplicationMessages msg = GWT.create(ApplicationMessages.class);

	private static final MyUiBinder UIBINDER = GWT.create(MyUiBinder.class);

	@UiField
	protected SimpleLayoutPanel mapPanel;

	/**
	 * Constructor.
	 */
	public ApplicationLayout() {
		initWidget(UIBINDER.createAndBindUi(this));
		ApplicationResource.INSTANCE.css().ensureInjected();

		mapPresenter = GeomajasImpl.getInstance().createMapPresenter();
		mapPresenter.getEventBus().addMapInitializationHandler(new MyMapInitializationHandler());

		GeomajasServerExtension.getInstance().initializeMap(mapPresenter, "app", "mapMain");

		mapLayoutPanel = new MapLayoutPanel();
		mapLayoutPanel.setPresenter(mapPresenter);
		mapPanel.add(mapLayoutPanel);

		appService = ApplicationService.getInstance();
		appService.setMapPresenter(mapPresenter);
		appService.setMapLayoutPanel(mapLayoutPanel);

		appService.getMapPresenter().getEventBus().addHandler(FeatureMouseOverHandler.TYPE, new MyFeatureMouseOverHandler());

	}

	/**
	 * Handler which will initialize commands given after the map has been initialized.
	 * This handler was added to the event bus of the map presenter.
	 */
	private class MyMapInitializationHandler implements MapInitializationHandler {

		@Override
		public void onMapInitialized(MapInitializationEvent mapInitializationEvent) {
			initializeLayer();

			ApplicationService.getInstance().addFeatureMouseOverListener();
			ApplicationService.getInstance().setTooltipShowingAllowed(true);

			// Add widgets to the map.
			appService.getMapPresenter().getWidgetPane().add(
					appService.getInfoButton().asWidget()
			);

			appService.getMapPresenter().getWidgetPane().add(
					appService.getLayerButton().asWidget()
			);

		}
	}

	/**
	 * Initialize the OSM layer by creating a new {@link org.geomajas.gwt2.client.map.layer.tile.TileBasedLayer}
	 * and a {@link org.geomajas.gwt2.client.map.layer.tile.TileConfiguration}.
	 * <p/>
	 * Values such as {@code {z}, {x}, {y}} are optional and will be used to substitute the tile level, X-ordinate and
	 * Y-ordinate.
	 * <p/>
	 * The tile based layer service can be given different URLs in which case Round-robin will be performed to
	 * determine the next URL to load tiles from.
	 */
	private void initializeLayer() {
		// Set the URL to the service and the file extension:
		String[] domains = new String[] { "a", "b", "c" };
		List<String> urls = new ArrayList<String>();
		for (String domain : domains) {
			urls.add("http://" + domain + ".tile.openstreetmap.org/{z}/{x}/{y}.png");
		}

		// Create the configuration for the tiles:
		Coordinate tileOrigin = new Coordinate(-HALF_EQUATOR_IN_METERS, -HALF_EQUATOR_IN_METERS);
		initializeResolutions();
		TileConfiguration tileConfig = new TileConfiguration(TILE_DIMENSION, TILE_DIMENSION, tileOrigin, resolutions);

		// Create a new layer with the configurations and add it to the maps:
		OsmLayer osmLayer = TileBasedLayerClient.getInstance().createOsmLayer("osmCountries", tileConfig, urls);
		mapPresenter.getLayersModel().addLayer(osmLayer);
		mapPresenter.getLayersModel().moveLayer(osmLayer, 0);
	}

	/**
	 * Generate a list of resolutions for the available zoom levels.
	 */
	private void initializeResolutions() {
		resolutions = new ArrayList<Double>();
		for (int i = 0; i < MAX_ZOOM_LEVELS; i++) {
			resolutions.add(EQUATOR_IN_METERS / (TILE_DIMENSION * Math.pow(2, i)));
		}
	}

	/**
	 * Handler that handles FeatureMouseOverEvent.
	 *
	 * @author David Debuck
	 */
	private class MyFeatureMouseOverHandler implements FeatureMouseOverHandler {

		@Override
		public void onFeatureMouseOver(FeatureMouseOverEvent event) {

			///////////////////////////////////////////////////////////////////////////////////////////
			// Hide the tooltip when we receive a null value.
			// This means that the mouse is not hovering over a feature.
			///////////////////////////////////////////////////////////////////////////////////////////

			if (event.getFeatures() == null) {
				ApplicationService.getInstance().getToolTip().hide();
				return;
			}

			List<Feature> features = event.getFeatures();

			///////////////////////////////////////////////////////////////////////////////////////////
			// Show the tooltip when there are features found.
			///////////////////////////////////////////////////////////////////////////////////////////

			if (!features.isEmpty()) {

				ApplicationService.getInstance().getToolTip().clearContent();

				List<Label> content = new ArrayList<Label>();

				for (Feature feature : features) {
					final Label label;
					if (feature == null) {
						label = new Label(msg.tooManyFeaturesToShow());
					} else {
						label = new Label(feature.getLabel());
					}
					label.addStyleName(ApplicationResource.INSTANCE.css().toolTipLine());
					content.add(label);
				}

				// Calculate a position for where to show the tooltip.
				int left = RootPanel.get().getAbsoluteLeft() + mapLayoutPanel.getAbsoluteLeft();
				int top = RootPanel.get().getAbsoluteTop() + mapLayoutPanel.getAbsoluteTop();

				// Add some extra pixels to the position of the tooltip so we still can drag the map.
				ApplicationService.getInstance().getToolTip().addContentAndShow(
						content,
						left + (int) event.getCoordinate().getX() + 5,
						top + (int) event.getCoordinate().getY() + 5
				);

			}

		}

	}

}
