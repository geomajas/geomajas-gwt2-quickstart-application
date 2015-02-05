package org.geomajas.quickstart.gwt2.client;
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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Application entry point.
 */
public class Application implements EntryPoint {

	@Override
	public void onModuleLoad() {
		ApplicationLayout layout = new ApplicationLayout();
		RootLayoutPanel.get().add(layout);
	}
}
