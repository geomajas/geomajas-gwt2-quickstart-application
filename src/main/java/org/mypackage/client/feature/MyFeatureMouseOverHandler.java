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
package org.mypackage.client.feature;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;
import org.mypackage.client.widget.ToolTip;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for feature mouse over events.
 *
 * @author Youri Flement
 */
public class MyFeatureMouseOverHandler implements FeatureMouseOverHandler {

	private ToolTip toolTip;

	private MapLayoutPanel mapLayoutPanel;

	/**
	 * Create a new feature mouse over handler.
	 *
	 * @param mapLayoutPanel The map layout panel.
	 * @param toolTip The tooltip we will show.
	 */
	public MyFeatureMouseOverHandler(MapLayoutPanel mapLayoutPanel, ToolTip toolTip) {
		this.mapLayoutPanel = mapLayoutPanel;
		this.toolTip = toolTip;
	}

	@Override
	public void onFeatureMouseOver(FeatureMouseOverEvent event) {
		if (event.getFeatures() == null) {
			toolTip.hide();
			return;
		}

		List<Feature> features = event.getFeatures();
		if (!features.isEmpty()) {
			toolTip.clearContent();
			List<Label> content = new ArrayList<Label>();

			for (Feature feature : features) {
				final Label label = new Label(feature.getLabel());
				content.add(label);
			}

			// Calculate a position for where to show the tooltip.
			int left = RootPanel.get().getAbsoluteLeft() + mapLayoutPanel.getAbsoluteLeft();
			int top = RootPanel.get().getAbsoluteTop() + mapLayoutPanel.getAbsoluteTop();

			// Add some extra pixels to the tooltip so we can still drag the map.
			toolTip.addContentAndShow(
					content,
					left + (int) event.getCoordinate().getX() + 5,
					top + (int) event.getCoordinate().getY() + 5
			);
		}
	}
}
