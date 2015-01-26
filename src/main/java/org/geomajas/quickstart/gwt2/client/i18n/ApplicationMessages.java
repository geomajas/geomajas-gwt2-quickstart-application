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
package org.geomajas.quickstart.gwt2.client.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * I18n interface for the application.
 *
 * @author David Debuck.
 */
public interface ApplicationMessages extends Messages {

	/***********************
	 ApplicationLayout.class
	 ***********************/
	String tooManyFeaturesToShow();

	/****************
	 InfoButton.class
	 ****************/
	String activateFeatureClickedListener();
	String deActivateFeatureClickedListener();

	/*****************
	 LayerLegend.class
	 *****************/
	String layerLegendPanelTitle();

	/***************
	 InfoPanel.class
	 ***************/
	String infoPanelTitle();
	String infoPanelSubTitle();

}