package org.mypackage.client.feature;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.geomajas.gwt2.client.map.attribute.AttributeDescriptor;
import org.geomajas.gwt2.client.map.feature.Feature;
import org.geomajas.gwt2.client.widget.MapLayoutPanel;
import org.mypackage.client.widget.ToolTip;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler that handles FeatureClickedEvent.
 *
 * @author Youri Flement
 */
public class MyFeatureClickedHandler implements FeatureClickedHandler {

	private static final String IMAGE_BASE_URL = "images/";

	private ToolTip toolTip;

	private HTMLPanel featurePanel;

	private VerticalPanel featureContent;

	private final MapLayoutPanel layout;

	public MyFeatureClickedHandler(MapLayoutPanel layout, HTMLPanel featurePanel, VerticalPanel featureContent, ToolTip toolTip) {
		this.featurePanel = featurePanel;
		this.featureContent = featureContent;
		this.toolTip = toolTip;
		this.layout = layout;
	}

	@Override
	public void onFeatureClicked(FeatureClickedEvent event) {
		List<Feature> features = event.getFeatures();

		// Hide the feature info panel when the user clicks on a place without features
		if (features.isEmpty() || (features.size() == 1 && !features.get(0).getLayer().isShowing())) {
			featurePanel.setVisible(false);
			toolTip.hide();
		} else {
			// Show the feature info if there is only one feature selected
			if (features.size() == 1 && features.get(0).getLayer().isShowing()) {
				setFeatureInfo(features.get(0));
				toolTip.hide();
			} else {
				// If multiple features were clicked, show a tooltip with the features
				toolTip.clearContent();
				List<Label> content = new ArrayList<Label>();

				for (final Feature feature : features) {
					final Label label = new Label(feature.getLabel());
					label.getElement().getStyle().setCursor(Cursor.POINTER);
					label.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							setFeatureInfo(feature);
						}
					});
					content.add(label);
				}

				// Calculate a position for where to show the tooltip
				int left = RootPanel.get().getAbsoluteLeft() + layout.getAbsoluteLeft();
				int top = RootPanel.get().getAbsoluteTop() + layout.getAbsoluteTop();

				toolTip.addContentAndShow(
						content,
						left + (int) event.getCoordinate().getX(),
						top + (int) event.getCoordinate().getY()
				);
			}
		}
	}

	/**
	 * Clear the feature info panel and make it visible and put the information of the feature in it.
	 *
	 * @param feature The feature to display.
	 */
	private void setFeatureInfo(Feature feature) {
		featureContent.clear();
		featurePanel.setVisible(true);

		// Layout the attributes of the feature in a grid:
		Grid grid = new Grid(feature.getAttributes().size(), 3);
		grid.getElement().getStyle().setPaddingLeft(10, Unit.PX);

		List<AttributeDescriptor> descriptors = feature.getLayer().getAttributeDescriptors();
		for (int i = 0; i < descriptors.size(); i++) {
			AttributeDescriptor descriptor = descriptors.get(i);

			// Put the attribute label in the first column:
			grid.setText(i, 0, descriptor.getName());

			// Put a delimiter in the second column:
			grid.setText(i, 1, ":");

			// Create a widget for the attribute value and put it in the last column:
			String attributeValue = feature.getAttributeValue(descriptor.getName()).toString();
			if (isImage(attributeValue)) {
				grid.setWidget(i, 2, new Image(IMAGE_BASE_URL + attributeValue));
			} else {
				grid.setText(i, 2, attributeValue);
			}
		}

		featureContent.add(grid);
	}

	/**
	 * A (naive) way of testing whether the file at path is an image.
	 *
	 * @param path The path to the file.
	 * @return <code>true</code> if the file is an image.
	 */
	private boolean isImage(String path) {
		return path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".svg");
	}

}
