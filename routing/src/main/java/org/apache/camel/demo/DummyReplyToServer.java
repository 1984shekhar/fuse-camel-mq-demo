package org.apache.camel.demo;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DummyReplyToServer implements Processor {

	public void process(Exchange exchange) throws Exception {
		try {
			String body = exchange.getIn().getBody(String.class);
			exchange.getIn().setBody(body + "Hi From Dummy Reply Server" + new Date());

		} catch (Exception e) {
			exchange.setException(e);
		}
	}

}
