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

import com.google.web.bindery.event.shared.Event;
import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt2.client.map.feature.Feature;

import java.util.List;

/**
 * Event thrown when a feature is found when where the mouse if hovering.
 * Contains the (screen) coordinate where was hovered and the list of features.
 *
 * @author David Debuck
 */
public class FeatureMouseOverEvent extends Event<FeatureMouseOverHandler> {

	private Coordinate coordinate;
	private List<Feature> features;

	/**
	 * Main constructor.
	 *
	 * @param coordinate world coordinate
	 * @param features list of features
	 */
	public FeatureMouseOverEvent(Coordinate coordinate, List<Feature> features) {
		this.coordinate = coordinate;
		this.features = features;
	}

	/**
	 * Get the coordinate where was hovered on the map (in screen space).
	 *
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * Get the list of features at this location.
	 *
	 * @return the list of features
	 */
	public List<Feature> getFeatures() {
		return features;
	}


	@Override
	public Type<FeatureMouseOverHandler> getAssociatedType() {
		return FeatureMouseOverHandler.TYPE;
	}

	@Override
	protected void dispatch(FeatureMouseOverHandler featureMouseOverHandler) {
		featureMouseOverHandler.onFeatureMouseOver(this);
	}
}
