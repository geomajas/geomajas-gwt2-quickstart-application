<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
                       xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
    <NamedLayer>
        <Name>Polygons</Name>
        <UserStyle>
            <Title>Polygons</Title>
            <Abstract>Countries of the world</Abstract>
            <FeatureTypeStyle>
                <Rule>
                    <Name>Large</Name>
                    <Title>More than 4% of world total</Title>
                    <ogc:Filter>
                        <ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyName>BJfdf</ogc:PropertyName>
                            <ogc:Literal>4</ogc:Literal>
                        </ogc:PropertyIsGreaterThanOrEqualTo>
                    </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#FF0000</ogc:Literal>
                            </CssParameter>
                            <CssParameter name="fill-opacity">
                                <ogc:Literal>0.5</ogc:Literal>
                            </CssParameter>
                        </Fill>
                        <Stroke>
                            <CssParameter name="stroke">#000000</CssParameter>
                            <CssParameter name="stroke-width">1</CssParameter>
                        </Stroke>
                    </PolygonSymbolizer>
                     <TextSymbolizer>
                              <Label>
                                <ogc:PropertyName>BJfdf</ogc:PropertyName>
                              </Label>
                     </TextSymbolizer>
                </Rule>
               <Rule>
                    <Name>Less large</Name>
                    <Title>Between 1% and 4% of world</Title>
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsGreaterThanOrEqualTo>
                                <ogc:PropertyName>BJfdf</ogc:PropertyName>
                                <ogc:Literal>1</ogc:Literal>
                            </ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyIsLessThan>
                                <ogc:PropertyName>BJfdf</ogc:PropertyName>
                                <ogc:Literal>4</ogc:Literal>
                            </ogc:PropertyIsLessThan>
                        </ogc:And>
                        </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#FF8C00</ogc:Literal>
                            </CssParameter>
                            <CssParameter name="fill-opacity">
                                <ogc:Literal>0.5</ogc:Literal>
                            </CssParameter>
                        </Fill>
                        <Stroke>
                            <CssParameter name="stroke">#000000</CssParameter>
                            <CssParameter name="stroke-width">1</CssParameter>
                    </Stroke>
                    </PolygonSymbolizer>
                    <TextSymbolizer>
							  <Label>
								<ogc:PropertyName>BJfdf</ogc:PropertyName>
							  </Label>
					 </TextSymbolizer>
                </Rule>
                <Rule>
                    <Name>Medium</Name>
                    <Title>Between 0.1% and 1% of world total</Title>
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsGreaterThanOrEqualTo>
                                <ogc:PropertyName>BJfdf</ogc:PropertyName>
                                <ogc:Literal>0.1</ogc:Literal>
                            </ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyIsLessThan>
                                <ogc:PropertyName>BJfdf</ogc:PropertyName>
                                <ogc:Literal>1</ogc:Literal>
                            </ogc:PropertyIsLessThan>
                        </ogc:And>
                    </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#FFFF00</ogc:Literal>
                            </CssParameter>
                            <CssParameter name="fill-opacity">
                               <ogc:Literal>0.5</ogc:Literal>
                            </CssParameter>
                        </Fill>
                        <Stroke>
                            <CssParameter name="stroke">#000000</CssParameter>
                            <CssParameter name="stroke-width">1</CssParameter>
                        </Stroke>
                    </PolygonSymbolizer>
                </Rule>
        	<Rule>
				<Name>Least</Name>
				<Title>Less than 0.1% of world total</Title>
				<ogc:Filter>
						<ogc:PropertyIsLessThan>
							<ogc:PropertyName>BJfdf</ogc:PropertyName>
							<ogc:Literal>0.1</ogc:Literal>
						</ogc:PropertyIsLessThan>
				</ogc:Filter>
				<PolygonSymbolizer>
					<Fill>
						<CssParameter name="fill">
							<ogc:Literal>#008000</ogc:Literal>
						</CssParameter>
						<CssParameter name="fill-opacity">
							<ogc:Literal>0.5</ogc:Literal>
						</CssParameter>
					</Fill>
					<Stroke>
						<CssParameter name="stroke">#000000</CssParameter>
						<CssParameter name="stroke-width">1</CssParameter>
					</Stroke>
				</PolygonSymbolizer>
			</Rule>

                </FeatureTypeStyle>

        </UserStyle>
    </NamedLayer>
</StyledLayerDescriptor>