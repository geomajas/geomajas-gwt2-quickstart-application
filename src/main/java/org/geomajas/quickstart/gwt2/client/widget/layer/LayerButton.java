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
package org.geomajas.quickstart.gwt2.client.widget.layer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.quickstart.gwt2.client.ApplicationService;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;

/**
 * LayerButton widget.
 *
 * @author David Debuck
 *
 */
public class LayerButton implements IsWidget {

	@UiField
	protected Button layerButton;

	private static final LayerButtonUiBinder UIBINDER = GWT.create(LayerButtonUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface LayerButtonUiBinder extends UiBinder<Widget, LayerButton> {
	}

	/**
	 * Default constructor.
	 */
	public LayerButton() {
		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();

		layerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				int left = layerButton.getAbsoluteLeft() - 200 + 23;
				int top = layerButton.getAbsoluteTop();

				ApplicationService.getInstance().getLayerLegend().setPopupPosition(left, top);
				ApplicationService.getInstance().getLayerLegend().show();

			}
		});

		layerButton.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(false);
			}
		});

		layerButton.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		});

	}

	@Override
	public Widget asWidget() {
		return layerButton;
	}

}