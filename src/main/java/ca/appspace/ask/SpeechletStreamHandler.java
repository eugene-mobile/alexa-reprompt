/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import java.util.Collections;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

/**
 * This is an entry point for the reprompt skill.
 * APPLICATION_ID value is generated by Amazon when you create your skill at developer.amazon.com in Alexa section.
 * 
 * @author Eugene
 */
public class SpeechletStreamHandler extends SpeechletRequestStreamHandler implements RequestStreamHandler {

	private final static String APPLICATION_ID = "ENTER_YOUR_APPLICATION_ID_HERE";
	
	public SpeechletStreamHandler() {
		super(new RepromptSpeechlet(), Collections.singleton(APPLICATION_ID));

	}

}
