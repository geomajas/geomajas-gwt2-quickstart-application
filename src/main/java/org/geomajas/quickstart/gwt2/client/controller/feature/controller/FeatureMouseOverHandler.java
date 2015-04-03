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
package org.geomajas.quickstart.gwt2.client.controller.feature.controller;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

/**
 * Interface for handling {@link FeatureMouseOverEvent}s.
 *
 * @author David Debuck
 */
public interface FeatureMouseOverHandler extends EventHandler {
	/**
	 * The type of the handler.
	 */
	Event.Type<FeatureMouseOverHandler> TYPE = new Event.Type<FeatureMouseOverHandler>();

	/**
	 * Called when feature(s) are found on the position where is the mouse pointer is located.
	 *
	 * @param event {@link FeatureMouseOverEvent}
	 */
	void onFeatureMouseOver(FeatureMouseOverEvent event);

}
