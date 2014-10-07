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
package org.mypackage.client.controller.feature.controller;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

/**
 * Interface for handling {@link FeatureMouseOverEvent}s.
 *
 * @author David Debuck
 * @since 2.1.0
 */
public interface FeatureMouseOverHandler extends EventHandler {
	/**
	 * The type of the handler.
	 */
	Event.Type<FeatureMouseOverHandler> TYPE = new Event.Type<FeatureMouseOverHandler>();

	/**
	 * Called when feature is found where the mouse is hovering.
	 *
	 * This event returns a coordinate with all features on the position.
	 * There are 3 return reslts possible from getFeatures:
	 * features == null => with this we get an instant result response while moving our mouse.
	 *                     This is returned while dragging and moving our mouse.
	 * features == 0    => the coordinate doesn't have any features available.
	 * features == > 0  => the coordinate has 1 or more features available.
	 *
	 * @param event {@link FeatureMouseOverEvent}
	 */
	void onFeatureMouseOver(FeatureMouseOverEvent event);

}
