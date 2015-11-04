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
package org.geomajas.quickstart.gwt2.client.widget.custom;

import java.util.List;
import java.util.Map;

import org.geomajas.gwt2.client.map.attribute.Attribute;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.layer.feature.attribute.ImageUrlAttribute;
import org.geomajas.quickstart.gwt2.client.ApplicationService;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureClickedEvent;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureClickedHandler;
import org.geomajas.quickstart.gwt2.client.i18n.ApplicationMessages;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;

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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The custom panel widget.
 *
 * @author David Debuck
 *
 */
public class CustomPanel implements IsWidget {

	@UiField
	protected PopupPanel customPopupPanel;

	PopupPanel temp = new PopupPanel();

	private int left;

	private int top;

	private HTMLPanel customPopupPanelContent = new HTMLPanel("");

	private final Button closeCustomPopupPanelButton = new Button("");

	private ApplicationMessages msg = GWT.create(ApplicationMessages.class);

	private static final CustomPanelUiBinder UIBINDER = GWT.create(CustomPanelUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface CustomPanelUiBinder extends UiBinder<Widget, CustomPanel> {
	}

	/**
	 * Default constructor.
	 */
	public CustomPanel() {
		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();

		ApplicationService.getInstance().getMapPresenter().getEventBus().addHandler(FeatureClickedHandler.TYPE, new MyFeatureClickedHandler());

		initCustomPanel();

		customPopupPanel.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(false);
			}
		}, MouseOverEvent.getType());

		customPopupPanel.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		}, MouseOutEvent.getType());

	}

	/**
	 * Add the content tp the popup panel.
	 * @param wrapper HTMLPanel
	 */
	public void add(HTMLPanel wrapper) {
		customPopupPanel.add(wrapper);
	}

	/**
	 * Set the popup position.
	 *
	 * @param left int
	 * @param top int
	 */
	public void setPopupPosition(int left, int top) {
		customPopupPanel.setPopupPosition(left, top);
	}

	/**
	 * Hide the customPopupPanel.
	 */
	public void hide() {
		customPopupPanel.hide();
	}

	/**
	 * Hide the customPopupPanel.
	 */
	public void show() {
		customPopupPanel.setModal(false);
		customPopupPanel.show();
	}

	/**
	 *
	 * @param left int
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 *
	 * @param top int
	 */
	public void setTop(int top) {
		this.top = top;
	}

	@Override
	public Widget asWidget() {
		return customPopupPanel;
	}

	/**
	 * Handler that handles FeatureClickedEvent.
	 *
	 * @author David Debuck
	 */
	private class MyFeatureClickedHandler implements FeatureClickedHandler {

		@Override
		public void onFeatureClicked(FeatureClickedEvent event) {

			List<Feature> features = event.getFeatures();

			customPopupPanelContent.clear();

			if (!features.isEmpty()) {

				customPopupPanel.setPopupPosition(left, top);

				String previousLayerId = "";

				for (final Feature feature : features) {

					if (!feature.getLayer().getId().equalsIgnoreCase(previousLayerId)) {
						Label subTitle = new Label(msg.customPanelSubTitle() + " "  + feature.getLayer().getTitle());
						customPopupPanelContent.add(subTitle);
					}

					final Label featureLabel = new Label("- " + feature.getLabel());
					featureLabel.addStyleName(ApplicationResource.INSTANCE.css().featureItem());

					customPopupPanelContent.add(featureLabel);

					previousLayerId = feature.getLayer().getId();

				}

				customPopupPanel.setModal(false);
				if (ApplicationService.getInstance().toggleFeatureClickedListener(true)) {
					customPopupPanel.hide();
				} else {
					customPopupPanel.show();
				}

			} else {
				customPopupPanel.hide();
			}

		}

	}

	/**
	 * Init the custom panel.
	 */
	private void initCustomPanel() {

		HTMLPanel customPopupPanelWrapper = new HTMLPanel("");

		closeCustomPopupPanelButton.addStyleName(ApplicationResource.INSTANCE.css().closePopupPanelButton());
		closeCustomPopupPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				customPopupPanel.hide();
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		});

		HTMLPanel closeCustomButtonContainer = new HTMLPanel("");
		closeCustomButtonContainer.addStyleName(ApplicationResource.INSTANCE.css().popupPanelHeader());
		Label customTitle = new Label(msg.customPanelTitle());
		closeCustomButtonContainer.add(customTitle);
		closeCustomButtonContainer.add(closeCustomPopupPanelButton);
		customPopupPanelWrapper.add(closeCustomButtonContainer);

		customPopupPanelContent = new HTMLPanel("");
		customPopupPanel.add(customPopupPanelWrapper);

		customPopupPanel.hide();
	}

}
