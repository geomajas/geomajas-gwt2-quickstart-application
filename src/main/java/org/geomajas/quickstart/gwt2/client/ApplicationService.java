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
package org.geomajas.quickstart.gwt2.client;

import org.geomajas.gwt2.client.map.MapPresenter;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureClickedListener;
import org.geomajas.quickstart.gwt2.client.controller.feature.controller.FeatureMouseOverListener;
import org.geomajas.quickstart.gwt2.client.widget.info.InfoButton;
import org.geomajas.quickstart.gwt2.client.widget.info.InfoPanel;
import org.geomajas.quickstart.gwt2.client.widget.layer.LayerButton;
import org.geomajas.quickstart.gwt2.client.widget.layer.LayerLegend;
import org.geomajas.quickstart.gwt2.client.widget.tooltip.ToolTip;

/**
 * Application service class for easy access.
 *
 * @author David Debuck.
 */
public class ApplicationService {

	private static ApplicationService INSTANCE;

	private MapPresenter mapPresenter;

	private MapLayoutPanel mapLayoutPanel;

	private ToolTip toolTip;

	private boolean isTooltipShowingAllowed;

	private InfoButton infoButton;

	private InfoPanel infoPanel;

	private LayerButton layerButton;

	private LayerLegend layerLegend;

	private FeatureClickedListener featureClickedListener;

	private FeatureMouseOverListener featureMouseOverListener;

	/**
	 * Constructor.
	 */
	public ApplicationService() {
	}

	/**
	 * Get a singleton instance.
	 *
	 * @return Return ApplicationService
	 */
	public static ApplicationService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ApplicationService();
		}
		return INSTANCE;
	}

	/**
	 * Set the map presenter for this application.
	 *
	 * @param mapPresenter MapPresenter
	 */
	public void setMapPresenter(MapPresenter mapPresenter) {
		this.mapPresenter = mapPresenter;
	}

	/**
	 * Get the map presenter.
	 *
	 * @return mapPresenter MapPresenter
	 */
	public MapPresenter getMapPresenter() {
		return this.mapPresenter;
	}

	/**
	 * Set the map layout panel.
	 *
	 * @param mapLayoutPanel MapLayoutPanel
	 */
	public void setMapLayoutPanel(MapLayoutPanel mapLayoutPanel) {
		this.mapLayoutPanel = mapLayoutPanel;
	}

	/**
	 * Get the map layout panel.
	 *
	 * @return mapLayoutPanel MapLayoutPanel
	 */
	public MapLayoutPanel getMapLayoutPanel() {
		return mapLayoutPanel;
	}


	/**
	 * Get the tooltip.
	 *
	 * @return toolTip ToolTip
	 */
	public ToolTip getToolTip() {
		if (toolTip == null) {
			toolTip = new ToolTip();
		}
		return toolTip;
	}

	/**
	 * Set if the tooltip is allow to show at this time.
	 * @param allowed boolean
	 */
	public void setTooltipShowingAllowed(boolean allowed) {
		this.isTooltipShowingAllowed = allowed;
	}

	/**
	 * Is the tooltip allowed to show?
	 * @return isTooltipShowingAllowed boolean
	 */
	public boolean isTooltipShowingAllowed() {
		return isTooltipShowingAllowed;
	}

	/**
	 * Get the info button.
	 *
	 * @return infoButton InfoButton
	 */
	public InfoButton getInfoButton() {
		if (infoButton == null) {
			infoButton = new InfoButton();
		}
		return infoButton;
	}

	/**
	 * Get the info panel.
	 *
	 * @return infoPanel InfoPanel
	 */
	public InfoPanel getInfoPanel() {
		if (infoPanel == null) {
			infoPanel = new InfoPanel();
		}
		return infoPanel;
	}

	/**
	 * Get the layer button.
	 *
	 * @return layerButton LayerButton
	 */
	public LayerButton getLayerButton() {
		if (layerButton == null) {
			layerButton = new LayerButton();
			getLayerLegend();
		}
		return layerButton;
	}

	/**
	 * Get the layer legend panel.
	 *
	 * @return layerLegend LayerLegend
	 */
	public LayerLegend getLayerLegend() {
		if (layerLegend == null) {
			layerLegend = new LayerLegend();
		}
		return layerLegend;
	}

	/**
	 * Toggle the featureClickedListener on the map.
	 *
	 * @param onlyGetStatus boolean
	 * @return boolean featureClickedListener active
	 */
	public boolean toggleFeatureClickedListener(boolean onlyGetStatus) {
		if (featureClickedListener == null) {
			if (!onlyGetStatus) {
				featureClickedListener = new FeatureClickedListener();
				mapPresenter.addMapListener(featureClickedListener);
			}
			return true;
		} else {
			if (!onlyGetStatus) {
				mapPresenter.removeMapListener(featureClickedListener);
				featureClickedListener = null;
			}
			return false;
		}
	}

	/**
	 * Remove the featureMouseOverListener on the map.
	 */
	public void removeFeatureMouseOverListener() {
		if (featureMouseOverListener != null) {
			mapPresenter.removeMapListener(featureMouseOverListener);
			featureMouseOverListener = null;
		}
	}

	/**
	 * Add the featureMouseOverListener on the map.
	 */
	public void addFeatureMouseOverListener() {
		if (featureMouseOverListener == null) {
			featureMouseOverListener = new FeatureMouseOverListener();
			mapPresenter.addMapListener(featureMouseOverListener);
		}
	}

}
