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
package org.geomajas.quickstart.gwt2.client.widget.tooltip;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;

import java.util.List;

/**
 * Simple tooltip with an auto hide implementation.
 *
 * @author David Debuck
 *
 */
public class ToolTip implements IsWidget {

	private PopupPanel toolTip;

	@UiField
	protected HTMLPanel contentPanel;

	private static final ToolTipBoxUiBinder UIBINDER = GWT.create(ToolTipBoxUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface ToolTipBoxUiBinder extends UiBinder<Widget, ToolTip> {
	}

	/**
	 * Default constructor.
	 */
	public ToolTip() {
		toolTip = (PopupPanel) UIBINDER.createAndBindUi(this);

		ApplicationResource.INSTANCE.css().ensureInjected();
		toolTip.addStyleName(ApplicationResource.INSTANCE.css().toolTip());
	}

	/**
	 * Hide the tooltip.
	 */
	public void hide() {
		toolTip.hide();
	}

	/**
	 * Add content to the tooltip and show it with the given parameters.
	 *
	 * @param content a list of Labels
	 * @param left the left position of the tooltip
	 * @param top the top position of the tooltip
	 */
	public void addContentAndShow(List<Label> content, int left, int top) {

		// Add the content to the panel.
		for (Label l : content) {
			contentPanel.add(l);
		}

		// Finally set position of the tooltip and show it.
		toolTip.setPopupPosition(left, top);
		toolTip.show();

	}

	/**
	 * Clear the content of the tooltip.
	 */
	public void clearContent() {
		contentPanel.clear();
	}

	@Override
	public Widget asWidget() {
		return toolTip;
	}

}