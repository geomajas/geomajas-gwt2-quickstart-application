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
package org.geomajas.quickstart.gwt2.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *  Resource bundle for the application.
 *
 * @author David Debuck
 */
public interface ApplicationResource extends ClientBundle {

	/**
	 * Instance for use outside UIBinder.
	 */
	ApplicationResource INSTANCE = GWT.create(ApplicationResource.class);

	/**
	 * Get the css resource.
	 * @return the css resource
	 */
	@Source("org/geomajas/quickstart/gwt2/client/css/style.css")
	ApplicationCssResource css();

	/**
	 * Get the css resource.
	 * @return the css resource
	 */
	@Source("org/geomajas/quickstart/gwt2/client/image/info.png")
	ImageResource infoIcon();

	/**
	 * Get the css resource.
	 * @return the css resource
	 */
	@Source("org/geomajas/quickstart/gwt2/client/image/layer.png")
	ImageResource layerIcon();

	/**
	 * Get the css resource.
	 * @return the css resource
	 */
	@Source("org/geomajas/quickstart/gwt2/client/image/close.png")
	ImageResource closeIcon();

}
