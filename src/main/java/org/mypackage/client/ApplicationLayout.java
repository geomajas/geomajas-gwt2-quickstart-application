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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.gwt2.client.GeomajasImpl;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.event.MapInitializationEvent;
import org.geomajas.gwt2.client.event.MapInitializationHandler;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;

public class ApplicationLayout extends Composite {

	private final MapPresenter mapPresenter;

	/**
	 * UI binder interface for this layout.
	 */
	interface MyUiBinder extends UiBinder<Widget, ApplicationLayout> {

	}

	private static final MyUiBinder UIBINDER = GWT.create(MyUiBinder.class);

	@UiField
	protected SimplePanel mapPanel;

	public ApplicationLayout() {
		initWidget(UIBINDER.createAndBindUi(this));


		mapPresenter = GeomajasImpl.getInstance().createMapPresenter();
		mapPresenter.getEventBus().addMapInitializationHandler(new MyMapInitializationHandler());

		GeomajasServerExtension.getInstance().initializeMap(mapPresenter, "app", "mapMain");

		MapLayoutPanel mapLayoutPanel = new MapLayoutPanel();
		mapLayoutPanel.setPresenter(mapPresenter);
		mapPanel.add(mapLayoutPanel);

	}


	private class MyMapInitializationHandler implements MapInitializationHandler {

		@Override
		public void onMapInitialized(MapInitializationEvent mapInitializationEvent) {

		}
	}

}
