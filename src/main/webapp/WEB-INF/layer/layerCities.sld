<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
                       xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
    <NamedLayer>
        <Name>Cities</Name>
        <UserStyle>
            <Title>Cities</Title>
            <Abstract>Cities of the world</Abstract>
            <FeatureTypeStyle>
                <Rule>
                    <Name>Capital city</Name>
                    <Title>Capital city</Title>
                    <ogc:Filter>
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>CAPITAL</ogc:PropertyName>
                            <ogc:Literal>Y</ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </ogc:Filter>
                    <PointSymbolizer>
                       <Graphic>
                         <Mark>
                           <WellKnownName>square</WellKnownName>
                           <Fill>
                             <CssParameter name="fill">#D40863</CssParameter>
                           </Fill>
                         </Mark>
                         <Size>8</Size>
                       </Graphic>
                     </PointSymbolizer>
                     <TextSymbolizer>
                              <Label>
                                <ogc:PropertyName>NAME</ogc:PropertyName>
                              </Label>
                     </TextSymbolizer>
                 </Rule>
                <Rule>
                    <Name>Other cities</Name>
                    <Title>Other cities</Title>
                    <ogc:Filter>
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>CAPITAL</ogc:PropertyName>
                            <ogc:Literal>N</ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </ogc:Filter>
                    <PointSymbolizer>
                        <Graphic>
                            <Mark>
                                <WellKnownName>circle</WellKnownName>
                                <Fill>
                                    <CssParameter name="fill">#FFCC33</CssParameter>
                                </Fill>
                            </Mark>
                            <Size>6</Size>
                        </Graphic>
                    </PointSymbolizer>
                    <TextSymbolizer>
                        <Label>
                            <ogc:PropertyName>NAME</ogc:PropertyName>
                        </Label>
                    </TextSymbolizer>
                </Rule>
            </FeatureTypeStyle>
        </UserStyle>
    </NamedLayer>
</StyledLayerDescriptor>