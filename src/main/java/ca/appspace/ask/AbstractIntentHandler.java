/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * An abstract intent handler that specifies app-specific values.
 * 
 * @author Eugene
 */
abstract class AbstractIntentHandler {

	protected static final String DEVICE_KEY = "DeviceName";
	protected static final String VALUE_KEY = "Value";
	protected static final String INTENT_KEY = "Intent";

	protected abstract SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> requestEnvelope);
	
	public SpeechletResponse handleIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		SpeechletResponse resp = handle(requestEnvelope);
		//This is required for RouterIntent to find out appropriate intent later
		if (!RouterIntentHandler.INTENT_NAME.equals(requestEnvelope.getRequest().getIntent().getName())) {
			if (resp.getReprompt()!=null) {
				requestEnvelope.getSession().setAttribute(INTENT_KEY, requestEnvelope.getRequest().getIntent().getName());
			} else {
				requestEnvelope.getSession().removeAttribute(INTENT_KEY);
			}
		}
		return resp;
	}
	
	protected static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}
	
}
