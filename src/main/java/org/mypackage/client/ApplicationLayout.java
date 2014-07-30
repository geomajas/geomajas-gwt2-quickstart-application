/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2014 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.mypackage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.gwt2.client.GeomajasImpl;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.event.MapInitializationEvent;
import org.geomajas.gwt2.client.event.MapInitializationHandler;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;
import org.geomajas.gwt2.widget.client.map.mapcontrolpanel.MapControlPanel;
import org.mypackage.client.feature.FeatureClickedHandler;
import org.mypackage.client.feature.FeatureClickedListener;
import org.mypackage.client.feature.FeatureMouseOverHandler;
import org.mypackage.client.feature.FeatureMouseOverListener;
import org.mypackage.client.feature.MyFeatureClickedHandler;
import org.mypackage.client.feature.MyFeatureMouseOverHandler;
import org.mypackage.client.resource.ApplicationResource;
import org.mypackage.client.widget.ToolTip;

/**
 * Layout for the application. Initializes the map and various controllers, widgets, handlers, ...
 *
 * @author Youri Flement
 */
public class ApplicationLayout extends ResizeComposite {

	/**
	 * UI binder interface for this layout.
	 */
	interface MyUiBinder extends UiBinder<Widget, ApplicationLayout> {
	}

	private MapPresenter mapPresenter;

	private static final MyUiBinder UI_BINDER = GWT.create(MyUiBinder.class);

	private ApplicationResource resources = GWT.create(ApplicationResource.class);

	@UiField
	protected SimpleLayoutPanel mapPanel;

	@UiField
	protected VerticalPanel featureInfoContent;

	@UiField
	protected HTMLPanel featureInfoPanel;

	@UiField
	protected VerticalPanel legendPanel;

	/**
	 * Initialize the map, layout, handlers, controllers, etc.
	 */
	public ApplicationLayout() {
		// Initialize the layout
		initWidget(UI_BINDER.createAndBindUi(this));
		resources.css().ensureInjected();
		ToolTip toolTip = new ToolTip();
		featureInfoPanel.setVisible(false);

		// Initialize the map
		mapPresenter = GeomajasImpl.getInstance().createMapPresenter();
		mapPresenter.getEventBus().addMapInitializationHandler(new MyMapInitializationHandler());

		GeomajasServerExtension.getInstance().initializeMap(mapPresenter, "app", "mapMain");
		MapLayoutPanel mapLayoutPanel = new MapLayoutPanel();
		mapLayoutPanel.setPresenter(mapPresenter);

		// Create the panel for the legend
		MapControlPanel mapControlPanel = new MapControlPanel(mapPresenter);
		legendPanel.add(mapControlPanel);

		// Add a handler for feature mouse over events
		mapPresenter.getEventBus().addHandler(FeatureMouseOverHandler.TYPE,
				new MyFeatureMouseOverHandler(mapLayoutPanel, toolTip));

		// Add a feature clicked listener/handler:
		mapPresenter.getEventBus().addHandler(FeatureClickedHandler.TYPE,
				new MyFeatureClickedHandler(mapLayoutPanel, featureInfoPanel, featureInfoContent, toolTip));
		FeatureClickedListener featureListener = new FeatureClickedListener();
		mapPresenter.addMapListener(featureListener);

		// Add the map to the layout
		mapPanel.add(mapLayoutPanel);
	}

	private class MyMapInitializationHandler implements MapInitializationHandler {
		@Override
		public void onMapInitialized(MapInitializationEvent mapInitializationEvent) {
			// Add a listener for hovering over features
			mapPresenter.addMapListener(new FeatureMouseOverListener());
		}
	}
}
