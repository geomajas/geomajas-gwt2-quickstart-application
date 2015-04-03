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
 * Event thrown when features are clicked on the map. Contains the (screen) coordinate where was clicked and the
 * list of features.
 *
 * @author Oliver May
 */
public class FeatureClickedEvent extends Event<FeatureClickedHandler> {

	private Coordinate coordinate;
	private List<Feature> features;

	/**
	 * Main constructor.
	 *
	 * @param coordinate world coordinate
	 * @param features list of features
	 */
	public FeatureClickedEvent(Coordinate coordinate, List<Feature> features) {
		this.coordinate = coordinate;
		this.features = features;
	}

	/**
	 * Get the coordinate where was clicked on the map (in screen space).
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
	public Type<FeatureClickedHandler> getAssociatedType() {
		return FeatureClickedHandler.TYPE;
	}

	@Override
	protected void dispatch(FeatureClickedHandler featuresClickHandler) {
		featuresClickHandler.onFeatureClicked(this);
	}
}
