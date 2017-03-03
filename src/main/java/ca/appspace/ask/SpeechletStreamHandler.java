/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import java.util.Collections;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class SpeechletStreamHandler extends SpeechletRequestStreamHandler implements RequestStreamHandler {

	public SpeechletStreamHandler() {
		super(new RepromptSpeechlet(), Collections.singleton("amzn1.ask.skill.dd7ff3a8-1841-4167-aee5-af32a40ce10b"));

	}

}
