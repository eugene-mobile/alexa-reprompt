/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

public class RouterIntentHandler extends AbstractIntentHandler {

	public static final String INTENT_NAME = "RouterIntent";
	
	private static final Logger log = LoggerFactory.getLogger(RouterIntentHandler.class);
	
	@Override
	protected SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		String originalIntentName = (String) requestEnvelope.getSession().getAttribute(INTENT_KEY);
		if (StringUtils.isEmpty(originalIntentName)) {
			PlainTextOutputSpeech output = new PlainTextOutputSpeech();
			output.setText("Sorry - I don't know how to help you with that.");
			return SpeechletResponse.newTellResponse(output);
		}
		log.info("Re-routing request to "+originalIntentName);
		switch (originalIntentName) {
			case SetValueIntentHandler.INTENT_NAME : {
				return new SetValueIntentHandler().handleIntent(requestEnvelope);
			}
			case GetValueIntentHandler.INTENT_NAME : {
				return new GetValueIntentHandler().handleIntent(requestEnvelope);
			}
		}
		PlainTextOutputSpeech output = new PlainTextOutputSpeech();
		output.setText("Sorry - I don't know how to help you with that.");
		return SpeechletResponse.newTellResponse(output);
	}

}
