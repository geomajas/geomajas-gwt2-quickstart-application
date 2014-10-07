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
package org.mypackage.client.widget.info;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.gwt2.client.widget.AbstractMapWidget;
import org.mypackage.client.ApplicationService;
import org.mypackage.client.resource.ApplicationResource;

/**
 * InfoButton.
 *
 * @author David Debuck
 *
 */
public class InfoButton extends AbstractMapWidget implements IsWidget {

	@UiField
	protected Button infoButton;

	private static final InfoButtonUiBinder UIBINDER = GWT.create(InfoButtonUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface InfoButtonUiBinder extends UiBinder<Widget, InfoButton> {
	}

	/**
	 * Default constructor.
	 */
	public InfoButton() {
		super(ApplicationService.getInstance().getMapPresenter());

		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();

		ApplicationService.getInstance().getInfoPanel();
		ApplicationService.getInstance().getInfoPanel().setLeft(5);
		ApplicationService.getInstance().getInfoPanel().setTop(infoButton.getAbsoluteTop() + 35);

		AbstractMapWidget.StopPropagationHandler preventWeirdBehaviourHandler = new AbstractMapWidget.StopPropagationHandler();
		addDomHandler(preventWeirdBehaviourHandler, MouseDownEvent.getType());
		addDomHandler(preventWeirdBehaviourHandler, MouseUpEvent.getType());
		addDomHandler(preventWeirdBehaviourHandler, ClickEvent.getType());
		addDomHandler(preventWeirdBehaviourHandler, DoubleClickEvent.getType());

		infoButton.setTitle("Activate the feature clicked listener.");

		infoButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {

				boolean active = ApplicationService.getInstance().toggleFeatureClickedListener(false);
				if (active) {
					infoButton.setStyleName(ApplicationResource.INSTANCE.css().infoButtonActive());
					infoButton.setTitle("Deactivate the feature clicked listener.");
				} else {
					infoButton.setStyleName(ApplicationResource.INSTANCE.css().infoButton());
					infoButton.setTitle("Activate the feature clicked listener.");
				}

			}
		});

		infoButton.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(false);
			}
		});

		infoButton.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		});

	}

	@Override
	public Widget asWidget() {
		return infoButton;
	}

}