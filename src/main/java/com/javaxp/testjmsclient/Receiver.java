package com.javaxp.testjmsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	@Value("${last.msg.key.lookup}")
	private String lookupValue;

	@Autowired
	ShutdownManager shutdownManager;
	private int messageCount = 0;
	@JmsListener(destination = "myQueue")
	public void readMessage(String message) {
		System.out.println("Received message: " + message + " lookup value=" + lookupValue);
		messageCount++;
		if(messageCount >= 10 || message.contains(lookupValue)) {
			System.out.println("Its time to shut down");
			shutdownManager.initiateShutdown(0);
		}
	}

}
