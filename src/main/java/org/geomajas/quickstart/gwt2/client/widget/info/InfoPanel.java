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
package org.geomajas.quickstart.gwt2.client.widget.info;

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
import org.geomajas.gwt2.client.map.attribute.Attribute;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.layer.feature.attribute.ImageUrlAttribute;
import org.geomajas.quickstart.gwt2.client.ApplicationService;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureClickedEvent;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureClickedHandler;
import org.geomajas.quickstart.gwt2.client.i18n.ApplicationMessages;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;

import java.util.List;
import java.util.Map;

/**
 * The info panel widget.
 *
 * @author David Debuck
 *
 */
public class InfoPanel implements IsWidget {

	@UiField
	protected PopupPanel infoPopupPanel;

	PopupPanel temp = new PopupPanel();

	private int left;

	private int top;

	private HTMLPanel infoPopupPanelContent = new HTMLPanel("");

	private final Button closeInfoPopupPanelButton = new Button("");

	private ApplicationMessages msg = GWT.create(ApplicationMessages.class);

	private static final InfoPanelUiBinder UIBINDER = GWT.create(InfoPanelUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface InfoPanelUiBinder extends UiBinder<Widget, InfoPanel> {
	}

	/**
	 * Default constructor.
	 */
	public InfoPanel() {
		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();

		ApplicationService.getInstance().getMapPresenter().getEventBus().addHandler(FeatureClickedHandler.TYPE, new MyFeatureClickedHandler());

		initInfoPanel();

		infoPopupPanel.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(false);
			}
		}, MouseOverEvent.getType());

		infoPopupPanel.addDomHandler(new MouseOutHandler() {
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
		infoPopupPanel.add(wrapper);
	}

	/**
	 * Set the popup position.
	 *
	 * @param left int
	 * @param top int
	 */
	public void setPopupPosition(int left, int top) {
		infoPopupPanel.setPopupPosition(left, top);
	}

	/**
	 * Hide the infoPopupPanel.
	 */
	public void hide() {
		infoPopupPanel.hide();
	}

	/**
	 * Hide the infoPopupPanel.
	 */
	public void show() {
		infoPopupPanel.setModal(false);
		infoPopupPanel.show();
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
		return infoPopupPanel;
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

			infoPopupPanelContent.clear();

			if (!features.isEmpty()) {

				infoPopupPanel.setPopupPosition(left, top);

				String previousLayerId = "";

				for (final Feature feature : features) {

					if (!feature.getLayer().getId().equalsIgnoreCase(previousLayerId)) {
						Label subTitle = new Label(msg.infoPanelSubTitle() + " "  + feature.getLayer().getTitle());
						subTitle.addStyleName(ApplicationResource.INSTANCE.css().infoPopupPanelSubTitle());

						infoPopupPanelContent.add(subTitle);
					}

					final Label featureLabel = new Label("- " + feature.getLabel());
					featureLabel.addStyleName(ApplicationResource.INSTANCE.css().featureItem());
					featureLabel.addMouseOverHandler(new MouseOverHandler() {
						@Override
						public void onMouseOver(MouseOverEvent event) {

							temp.clear();
							temp.addStyleName(ApplicationResource.INSTANCE.css().detailPopupPanel());
							HTMLPanel content = new HTMLPanel("");
							temp.add(content);
							for (Object o : feature.getAttributes().entrySet()) {
								Map.Entry pairs = (Map.Entry) o;
								Attribute at = (Attribute) pairs.getValue();
								if (at.getValue() instanceof ImageUrlAttribute) {
									Image image = new Image("images/" + at.getValue().toString());
									image.setStyleName(ApplicationResource.INSTANCE.css().countryImage());
									content.add(image);
								} else {
									content.add(new Label(pairs.getKey() + ": " + at.getValue()));
								}
							}

							temp.setPopupPosition(
									infoPopupPanel.getAbsoluteLeft() + 205,
									featureLabel.getAbsoluteTop()
							);

							temp.setAutoHideEnabled(true);
							temp.setModal(false);
							temp.show();

						}
					});

					featureLabel.addMouseOutHandler(new MouseOutHandler() {
						@Override
						public void onMouseOut(MouseOutEvent event) {
							temp.hide();
						}
					});

					infoPopupPanelContent.add(featureLabel);

					previousLayerId = feature.getLayer().getId();

				}

				infoPopupPanel.setModal(false);
				if (ApplicationService.getInstance().toggleFeatureClickedListener(true)) {
					infoPopupPanel.hide();
				} else {
					infoPopupPanel.show();
				}

			} else {
				infoPopupPanel.hide();
			}

		}

	}

	/**
	 * Init the info panel.
	 */
	private void initInfoPanel() {

		infoPopupPanel.addStyleName(ApplicationResource.INSTANCE.css().infoPopupPanel());

		HTMLPanel infoPopupPanelWrapper = new HTMLPanel("");

		closeInfoPopupPanelButton.addStyleName(ApplicationResource.INSTANCE.css().closePopupPanelButton());
		closeInfoPopupPanelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				infoPopupPanel.hide();
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		});

		HTMLPanel closeInfoButtonContainer = new HTMLPanel("");
		closeInfoButtonContainer.addStyleName(ApplicationResource.INSTANCE.css().popupPanelHeader());
		Label infoTitle = new Label(msg.infoPanelTitle());
		closeInfoButtonContainer.add(infoTitle);
		closeInfoButtonContainer.add(closeInfoPopupPanelButton);
		infoPopupPanelWrapper.add(closeInfoButtonContainer);

		infoPopupPanelContent = new HTMLPanel("");
		infoPopupPanelContent.addStyleName(ApplicationResource.INSTANCE.css().infoPopupPanelContent());

		ScrollPanel infoPopupPanelScroll = new ScrollPanel();
		infoPopupPanelScroll.addStyleName(ApplicationResource.INSTANCE.css().infoPopupPanelScroll());
		infoPopupPanelScroll.add(infoPopupPanelContent);

		infoPopupPanelWrapper.add(infoPopupPanelScroll);

		infoPopupPanel.add(infoPopupPanelWrapper);

		infoPopupPanel.hide();
	}

}