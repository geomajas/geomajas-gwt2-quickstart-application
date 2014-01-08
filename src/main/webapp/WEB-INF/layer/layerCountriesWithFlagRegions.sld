<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
                       xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
    <NamedLayer>
        <Name>Countries</Name>
        <UserStyle>
            <Title>Countries</Title>
            <Abstract>Regions of the world</Abstract>
            <FeatureTypeStyle>
            	<Transformation>
            		<ogc:Function>
            		</ogc:Function>
            	</Transformation>
                <Rule>
                    <Name>Europe</Name>
                    <Title>Europe</Title>
                    <ogc:Filter>
						<ogc:PropertyIsLike wildCard="*" singleChar="." escape="!">
							<ogc:PropertyName>continent</ogc:PropertyName>
							<ogc:Literal>Europe*</ogc:Literal>
						</ogc:PropertyIsLike>
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
					<TextSymbolizer>
							  <Label>
								<ogc:PropertyName>name</ogc:PropertyName>
							  </Label>
					 </TextSymbolizer>
                </Rule>
            </FeatureTypeStyle>
         <!--   <FeatureTypeStyle>
				<Rule>
					<Name>Asia</Name>
					<Title>Asia</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>continent</ogc:PropertyName>
							<ogc:Literal>Europe</ogc:Literal>
						</ogc:PropertyIsEqualTo>
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
					</PolygonSymbolizer>
				</Rule>
			</FeatureTypeStyle>            -->

        </UserStyle>
    </NamedLayer>
</StyledLayerDescriptor>