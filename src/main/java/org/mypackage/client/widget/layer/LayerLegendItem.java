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
package org.mypackage.client.widget.layer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.mypackage.client.resource.ApplicationResource;

/**
 * LayerLegendItem.
 *
 * @author David Debuck
 *
 */
public class LayerLegendItem implements IsWidget {

	@UiField
	protected CheckBox layerVisible;

	@UiField
	protected InlineLabel layerName;

	private static final LayerLegendItemUiBinder UIBINDER = GWT.create(LayerLegendItemUiBinder.class);

	@Override
	public Widget asWidget() {
		return this.asWidget();
	}

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface LayerLegendItemUiBinder extends UiBinder<Widget, LayerLegendItem> {
	}

	/**
	 * Default constructor.
	 */
	public LayerLegendItem() {
		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();
	}

	/**
	 * Check if the selected layer is visible.
	 * @return boolean isVisible.
	 */
	public boolean isLayerVisible() {
		return layerVisible.isVisible();
	}

	/**
	 * Hide or show the selected layer.
	 * @param visible boolean
	 */
	public void setLayerVisible(boolean visible) {
		layerVisible.setEnabled(visible);
	}

	/**
	 * Set the layers name.
	 * @param name String
	 */
	public void setLayerName(String name) {
		layerName.setText(name);
	}

}