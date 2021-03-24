package com.example.camelpost;

import javax.print.DocFlavor.STRING;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteConfig extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("direct:processEsign").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.setProperty("vendor",
						new RulesService().determineVendor(exchange.getIn().getBody(String.class)));
				}
		}).choice().when(simple("${exchangeProperty.vendor} == 'adobe'")).transform(new Expression() {
			
			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				
				return (T)exchange.getIn().getBody(String.class).concat(" transformed body ");
			}
		}).setHeader(Exchange.HTTP_METHOD, constant("POST"))
//        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//        .setHeader("Accept",constant("application/json"))
        .log("Body after transformation is ${body} with headers: ${headers}")
        //need to change url after knowing what the cafe-web url will be
        .to("http://localhost:8080/adobe").
        
        otherwise().setHeader(Exchange.HTTP_METHOD, constant("POST"))
//        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//        .setHeader("Accept",constant("application/json"))
        .log("Body after transformation is ${body} with headers: ${headers}")
        //need to change url after knowing what the cafe-web url will be
        .to("http://localhost:8080/docusign");
		
		
		
		

	}

}
