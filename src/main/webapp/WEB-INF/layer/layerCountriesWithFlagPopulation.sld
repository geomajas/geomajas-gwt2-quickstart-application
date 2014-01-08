<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
                       xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
    <NamedLayer>
        <Name>Countries</Name>
        <UserStyle>
            <Title>Countries</Title>
            <Abstract>Countries of the world</Abstract>
            <FeatureTypeStyle>
                <Rule>
                    <Name>Tiny</Name>
                    <Title>Less than 500K</Title>
                    <ogc:Filter>
                        <ogc:PropertyIsLessThan>
                            <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                            <ogc:Literal>500000</ogc:Literal>
                        </ogc:PropertyIsLessThan>
                    </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#C2F7C7</ogc:Literal>
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
                    <Name>Small</Name>
                    <Title>Between 500K and 5M</Title>
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsGreaterThanOrEqualTo>
                                <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                                <ogc:Literal>500000</ogc:Literal>
                            </ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyIsLessThan>
                                <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                                <ogc:Literal>5000000</ogc:Literal>
                            </ogc:PropertyIsLessThan>
                        </ogc:And>
                        </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#7AC2A3</ogc:Literal>
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
                    <Name>Medium</Name>
                    <Title>Between 5M and 50M</Title>
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsGreaterThanOrEqualTo>
                                <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                                <ogc:Literal>5000000</ogc:Literal>
                            </ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyIsLessThan>
                                <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                                <ogc:Literal>50000000</ogc:Literal>
                            </ogc:PropertyIsLessThan>
                        </ogc:And>
                    </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#3D9485</ogc:Literal>
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
            <Name>Large</Name>
            <Title>Between 50M and 500M</Title>
            <ogc:Filter>
                <ogc:And>
                    <ogc:PropertyIsGreaterThanOrEqualTo>
                        <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                        <ogc:Literal>50000000</ogc:Literal>
                    </ogc:PropertyIsGreaterThanOrEqualTo>
                    <ogc:PropertyIsLessThan>
                        <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                        <ogc:Literal>500000000</ogc:Literal>
                    </ogc:PropertyIsLessThan>
                </ogc:And>
            </ogc:Filter>
            <PolygonSymbolizer>
                <Fill>
                    <CssParameter name="fill">
                        <ogc:Literal>#006666</ogc:Literal>
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
                    <Name>Huge</Name>
                    <Title>More than 500M</Title>
                    <ogc:Filter>
                        <ogc:PropertyIsGreaterThanOrEqualTo>
                                <ogc:PropertyName>POP_WIKI</ogc:PropertyName>
                                <ogc:Literal>500000000</ogc:Literal>
                            </ogc:PropertyIsGreaterThanOrEqualTo>
                    </ogc:Filter>
                    <PolygonSymbolizer>
                        <Fill>
                            <CssParameter name="fill">
                                <ogc:Literal>#003D3D</ogc:Literal>
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
					<Name>Label</Name>
					<Title>Label for all countries</Title>
					<TextSymbolizer>
							  <Label>
								<ogc:PropertyName>ADMIN</ogc:PropertyName>
							  </Label>
					 </TextSymbolizer>

			  </Rule>
           </FeatureTypeStyle>

        </UserStyle>
    </NamedLayer>
</StyledLayerDescriptor>