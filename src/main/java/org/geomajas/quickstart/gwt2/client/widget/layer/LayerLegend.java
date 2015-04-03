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
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.configuration.NamedStyleInfo;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.map.layer.Layer;
import org.geomajas.gwt2.client.map.layer.LayersModel;
import org.geomajas.gwt2.client.map.layer.RasterServerLayerImpl;
import org.geomajas.gwt2.client.map.layer.VectorServerLayerImpl;
import org.geomajas.sld.FeatureTypeStyleInfo;
import org.geomajas.sld.RuleInfo;
import org.geomajas.quickstart.gwt2.client.ApplicationService;
import org.geomajas.quickstart.gwt2.client.i18n.ApplicationMessages;
import org.geomajas.quickstart.gwt2.client.resource.ApplicationResource;
import org.geomajas.quickstart.gwt2.client.util.UrlBuilder;

/**
 * The layer legend panel widget.
 *
 * @author David Debuck
 *
 */
public class LayerLegend implements IsWidget {

	@UiField
	protected PopupPanel layerLegendPanel;

	private MapPresenter mapPresenter;

	private Button closeLayerPopupPanelButton = new Button();

	private ApplicationMessages msg = GWT.create(ApplicationMessages.class);

	private static final LayerLegendUiBinder UIBINDER = GWT.create(LayerLegendUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author David Debuck
	 *
	 */
	interface LayerLegendUiBinder extends UiBinder<Widget, LayerLegend> {
	}

	/**
	 * Default constructor.
	 */
	public LayerLegend() {
		UIBINDER.createAndBindUi(this);
		ApplicationResource.INSTANCE.css().ensureInjected();

		this.mapPresenter = ApplicationService.getInstance().getMapPresenter();

		layerLegendPanel.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(false);
			}
		}, MouseOverEvent.getType());

		layerLegendPanel.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				ApplicationService.getInstance().setTooltipShowingAllowed(true);
			}
		}, MouseOutEvent.getType());

		Window.addResizeHandler(new BrowserResizedHandler());

		initLayerLegend();
	}

	/**
	 * Handler that listens when the browser gets resized.
	 * This will calculate and rescale the search filter accordingly, we are using a timer here as this will unload the
	 * browser somewhat when handling the events.
	 *
	 * @author David Debuck
	 */
	class BrowserResizedHandler implements ResizeHandler {

		private Timer resizeTimer = new Timer() {
			@Override
			public void run() {
				ApplicationService.getInstance().getLayerLegend().hide();
			}
		};

		@Override
		public void onResize(ResizeEvent event) {
			resizeTimer.cancel();
			resizeTimer.schedule(100);
		}

	}

	/**
	 * Add the content tp the popup panel.
	 * @param wrapper HTMLPanel
	 */
	public void add(HTMLPanel wrapper) {
		layerLegendPanel.add(wrapper);
	}

	/**
	 * Set the popup position.
	 *
	 * @param left int
	 * @param top int
	 */
	public void setPopupPosition(int left, int top) {
		layerLegendPanel.setPopupPosition(left, top);
	}

	/**
	 * Hide the layerLegendPanel.
	 */
	public void hide() {
		layerLegendPanel.hide();
	}

	/**
	 * Hide the layerLegendPanel.
	 */
	public void show() {
		layerLegendPanel.setModal(false);
		layerLegendPanel.setWidth("200px");
		layerLegendPanel.show();
	}

	@Override
	public Widget asWidget() {
		return layerLegendPanel;
	}

	/**
	 * Get a fully build layer legend for a LayersModel.
	 *
	 * @param layerPopupPanelContent Original HTMLPanel
	 * @param layersModel LayersModel of the map
	 * @return HTMLPanel fully build legend.
	 */
	private HTMLPanel getLayersLegend(HTMLPanel layerPopupPanelContent, LayersModel layersModel) {

		for (int i = 0; i < mapPresenter.getLayersModel().getLayerCount(); i++) {

			HTMLPanel layer = new HTMLPanel("");
			CheckBox visible = new CheckBox();

			final int finalI = i;
			visible.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (mapPresenter.getLayersModel().getLayer(finalI).isMarkedAsVisible()) {
						mapPresenter.getLayersModel().getLayer(finalI).setMarkedAsVisible(false);
					} else {
						mapPresenter.getLayersModel().getLayer(finalI).setMarkedAsVisible(true);
					}
				}
			});

			if (mapPresenter.getLayersModel().getLayer(i).isMarkedAsVisible()) {
				visible.setValue(true);
			}

			InlineLabel layerName = new InlineLabel(
					mapPresenter.getLayersModel().getLayer(i).getTitle()
			);

			layer.add(visible);
			layer.add(layerName);

			layerPopupPanelContent.add(layer);

			////////////////////////////////
			// Add legend items
			////////////////////////////////

			Layer legendLayer = mapPresenter.getLayersModel().getLayer(i);

			if (legendLayer instanceof VectorServerLayerImpl) {
				VectorServerLayerImpl serverLayer = (VectorServerLayerImpl) legendLayer;
				String legendUrl = GeomajasServerExtension.getInstance().getEndPointService().getLegendServiceUrl();

				NamedStyleInfo styleInfo = serverLayer.getLayerInfo().getNamedStyleInfo();
				String name = serverLayer.getLayerInfo().getNamedStyleInfo().getName();

				int x = 0;

				for (FeatureTypeStyleInfo sfi : styleInfo.getUserStyle().getFeatureTypeStyleList()) {
					for (RuleInfo rInfo : sfi.getRuleList()) {
						UrlBuilder url = new UrlBuilder(legendUrl);
						url.addPath(serverLayer.getServerLayerId());
						url.addPath(name);
						url.addPath(x + ".png");
						x++;

						HorizontalPanel layout = new HorizontalPanel();
						layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						layout.add(new Image(url.toString()));
						Label labelUi = new Label(rInfo.getName());
						labelUi.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
						layout.add(labelUi);

						layout.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
						layout.getElement().getStyle().setMarginTop(3, Style.Unit.PX);

						layerPopupPanelContent.add(layout);
					}
				}

			} else if (legendLayer instanceof RasterServerLayerImpl) {
				RasterServerLayerImpl serverLayer = (RasterServerLayerImpl) legendLayer;

				String legendUrl = GeomajasServerExtension.getInstance().getEndPointService().getLegendServiceUrl();
				UrlBuilder url = new UrlBuilder(legendUrl);
				url.addPath(serverLayer.getServerLayerId() + ".png");

				HorizontalPanel layout = new HorizontalPanel();
				layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				layout.add(new Image(url.toString()));
				Label labelUi = new Label("");
				labelUi.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
				layout.add(labelUi);

				layout.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
				layout.getElement().getStyle().setMarginTop(3, Style.Unit.PX);

				layerPopupPanelContent.add(layout);
			}

		}

		return layerPopupPanelContent;

	}

	/**
	 * Init the layer legend panel.
	 */
	private void initLayerLegend() {

			HTMLPanel layerPopupPanelWrapper = new HTMLPanel("");

			closeLayerPopupPanelButton.addStyleName(ApplicationResource.INSTANCE.css().closePopupPanelButton());
			closeLayerPopupPanelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					layerLegendPanel.hide();
					ApplicationService.getInstance().setTooltipShowingAllowed(true);
				}
			});

			HTMLPanel closeLayerButtonContainer = new HTMLPanel("");
			closeLayerButtonContainer.addStyleName(ApplicationResource.INSTANCE.css().popupPanelHeader());
			Label layerTitle = new Label(msg.layerLegendPanelTitle());
			closeLayerButtonContainer.add(layerTitle);
			closeLayerButtonContainer.add(closeLayerPopupPanelButton);
			layerPopupPanelWrapper.add(closeLayerButtonContainer);

			HTMLPanel layerPopupPanelContent = new HTMLPanel("");
			layerPopupPanelContent.addStyleName(ApplicationResource.INSTANCE.css().layerPopupPanelContent());

			// Add a generated layers legend.
			layerPopupPanelWrapper.add(
					getLayersLegend(layerPopupPanelContent, mapPresenter.getLayersModel())
			);

			layerLegendPanel.add(layerPopupPanelWrapper);

	}

}