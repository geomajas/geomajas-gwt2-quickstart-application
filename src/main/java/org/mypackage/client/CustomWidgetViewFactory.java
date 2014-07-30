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

import org.geomajas.gwt2.widget.client.CoreWidgetViewFactory;
import org.geomajas.gwt2.widget.client.map.layercontrolpanel.LayerControlPanelView;
import org.geomajas.gwt2.widget.client.map.layercontrolpanel.resource.LayerControlPanelResource;
import org.mypackage.client.legend.LayerControlPanelLegendViewImpl;

/**
 * MVP view factory for the quickstart application.
 *
 * @author Dosi Bingov
 *
 */
public class CustomWidgetViewFactory extends CoreWidgetViewFactory {

	@Override
	public LayerControlPanelView createLayerControlPanel(LayerControlPanelResource resource) {
		// Change the view for LayerControl panel
		return new LayerControlPanelLegendViewImpl(resource);
	}
}
