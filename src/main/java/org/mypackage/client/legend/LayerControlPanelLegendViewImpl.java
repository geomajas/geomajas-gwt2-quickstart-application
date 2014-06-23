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
package org.mypackage.client.legend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.configuration.NamedStyleInfo;
import org.geomajas.gwt2.client.GeomajasServerExtension;
import org.geomajas.gwt2.client.map.layer.Layer;
import org.geomajas.gwt2.client.map.layer.LegendUrlSupported;
import org.geomajas.gwt2.client.map.layer.RasterServerLayerImpl;
import org.geomajas.gwt2.client.map.layer.VectorServerLayerImpl;
import org.geomajas.gwt2.widget.client.map.layercontrolpanel.LayerControlPanelPresenter;
import org.geomajas.gwt2.widget.client.map.layercontrolpanel.LayerControlPanelView;
import org.geomajas.gwt2.widget.client.map.layercontrolpanel.resource.LayerControlPanelResource;
import org.geomajas.sld.FeatureTypeStyleInfo;
import org.geomajas.sld.RuleInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map Legend implementation of {@link org.geomajas.gwt2.widget.client.map.layercontrolpanel.LayerControlPanelView}.
 *
 * @author Dosi Bingov
 */
public class LayerControlPanelLegendViewImpl implements LayerControlPanelView {

	private LayerControlPanelPresenter presenter;

	private HorizontalPanel widget;

	@UiField
	protected FlexTable legendTable;

	@UiField
	protected CheckBox visibilityToggle;

	@UiField
	protected Label title;

	private Layer layer;

	private LayerControlPanelResource resource;

	private static final LayerControlUiBinder UIBINDER = GWT.create(LayerControlUiBinder.class);

	/**
	 * UI binder interface.
	 *
	 * @author Dosi Bingov
	 *
	 */
	interface LayerControlUiBinder extends UiBinder<Widget, LayerControlPanelLegendViewImpl> {
	}

	public LayerControlPanelLegendViewImpl(LayerControlPanelResource layerControlPanelResource) {
		resource = layerControlPanelResource;
		widget = (HorizontalPanel) UIBINDER.createAndBindUi(this);
		widget.setStyleName(layerControlPanelResource.css().layerControlPanel());
		title.setStyleName(layerControlPanelResource.css().layerControlPanelTitle());
		visibilityToggle.addStyleName(layerControlPanelResource.css().layerControlPanelToggle());
		resource.css().ensureInjected();
		bindEvents();
	}

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------

	@Override
	public Widget asWidget() {
		return widget;
	}

	private void setLegendUrl(String url) {
		Image image = new Image(url);
		final int row = legendTable.insertRow(legendTable.getRowCount());
		legendTable.addCell(row);
		legendTable.setWidget(row, 0, image);

	}

	private void addLayerLegendWidget(LayerLegendWidget layerLegendWidget) {
		final int row = legendTable.insertRow(legendTable.getRowCount());
		legendTable.addCell(row);

		legendTable.setWidget(row, 0, layerLegendWidget);
	}

	@Override
	public Layer getLayer() {
		return layer;
	}

	@Override
	public void setLayer(Layer layer) {
		this.layer = layer;
		visibilityToggle.setValue(layer.isMarkedAsVisible());

		// Add the legend if supported
		if (layer instanceof LegendUrlSupported) {
			setLegendUrl(((LegendUrlSupported) layer).getLegendImageUrl());
		} else if (layer instanceof VectorServerLayerImpl) {
			VectorServerLayerImpl serverLayer = (VectorServerLayerImpl) layer;
			String legendUrl = GeomajasServerExtension.getInstance().getEndPointService().getLegendServiceUrl();

			NamedStyleInfo styleInfo = serverLayer.getLayerInfo().getNamedStyleInfo();
			String name = serverLayer.getLayerInfo().getNamedStyleInfo().getName();

			int i = 0;

			for (FeatureTypeStyleInfo sfi : styleInfo.getUserStyle().getFeatureTypeStyleList()) {
				for (RuleInfo rInfo : sfi.getRuleList()) {
					UrlBuilder url = new UrlBuilder(legendUrl);
					url.addPath(serverLayer.getServerLayerId());
					url.addPath(name);
					url.addPath(i + ".png");
					i++;

					addLayerLegendWidget(new LayerLegendWidget(url.toString(), rInfo.getName()));
				}
			}

		} else if (layer instanceof RasterServerLayerImpl) {
			RasterServerLayerImpl serverLayer = (RasterServerLayerImpl) layer;

			String legendUrl = GeomajasServerExtension.getInstance().getEndPointService().getLegendServiceUrl();
			UrlBuilder url = new UrlBuilder(legendUrl);
			url.addPath(serverLayer.getServerLayerId() + ".png");
			addLayerLegendWidget(new LayerLegendWidget(url.toString(), ""));
		}
	}

	@Override
	public void setPresenter(LayerControlPanelPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setLayerVisible(boolean visible) {
		visibilityToggle.setValue(visible);
	}

	@Override
	public void enableVisibilityToggle(boolean enable) {
		visibilityToggle.setEnabled(enable);
	}

	@Override
	public void setLayerTitle(String title) {
		this.title.setText(title);
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void bindEvents() {
		visibilityToggle.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (visibilityToggle.isEnabled()) {
					layer.setMarkedAsVisible(!layer.isMarkedAsVisible());
					visibilityToggle.setEnabled(true); // Works because JavaScript is single threaded...
				}
			}
		});
	}

	/**
	 * Builds parametrized URL from a base URL.
	 *
	 * @author Jan De Moerloose
	 */
	public class UrlBuilder {

		private final Map<String, String> params = new HashMap<String, String>();

		private String baseUrl;

		/**
		 * Constructor using the given base URL.
		 *
		 * @param baseUrl base URL
		 */
		public UrlBuilder(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		/**
		 * Add a parameter.
		 *
		 * @param name
		 *            name of parameter
		 * @param value
		 *            value of parameter
		 * @return this to allow concatenation
		 */
		public UrlBuilder addParameter(String name, String value) {
			if (value == null) {
				value = "";
			}
			params.put(name, value);
			return this;
		}

		/**
		 * Add a path extension.
		 *
		 * @param path
		 *            path
		 * @return this to allow concatenation
		 */
		public UrlBuilder addPath(String path) {
			if (path.startsWith("/") && baseUrl.endsWith("/")) {
				baseUrl = baseUrl + path.substring(1);
			} else if (baseUrl.endsWith("/")) {
				baseUrl = baseUrl + path;
			} else {
				baseUrl = baseUrl + "/" + path;
			}
			return this;
		}

		/**
		 * Build the URL and return it as an encoded string.
		 *
		 * @return the encoded URL string
		 */
		public String toString() {
			StringBuilder url = new StringBuilder(baseUrl);
			if (params.size() > 0) {
				url.append("?");
				for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
					String name = iterator.next();
					url.append(name).append("=").append(params.get(name));
					if (iterator.hasNext()) {
						url.append("&");
					}
				}
			}
			return URL.encode(url.toString());
		}

	}

	/**
	 * @author Dosi Bingov
	 */
	class LayerLegendWidget implements IsWidget {
		private HorizontalPanel layout;

		public LayerLegendWidget(String imageUrl, String label) {
			layout = new HorizontalPanel();
			layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

			layout.add(new Image(imageUrl));
			Label labelUi = new Label(label);
			labelUi.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);

			layout.add(labelUi);


		}

		@Override
		public Widget asWidget() {
			return layout;
		}
	}
}
