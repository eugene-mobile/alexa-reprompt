/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RepromptSpeechlet implements SpeechletV2 {

	private static final Logger log = LoggerFactory.getLogger(RepromptSpeechlet.class);
	
	@Override
	public void onSessionStarted(
			SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		log.info("onSessionStarted: "+requestEnvelope.getSession().getSessionId());
	}

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		log.info("onLaunch: "+requestEnvelope.getSession().getSessionId());
		PlainTextOutputSpeech hello = new PlainTextOutputSpeech();
		hello.setText("Hello");
		return SpeechletResponse.newTellResponse(hello);
	}

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		log.info("onIntent "+requestEnvelope.getSession().getSessionId());
		try {
			log.info(getMapper().writeValueAsString(requestEnvelope));
		} catch (Exception e){}
		String intentName = requestEnvelope.getRequest().getIntent().getName();
		
		SpeechletResponse resp = null;
		switch (intentName) {
			case "AMAZON.HelpIntent" : {
				PlainTextOutputSpeech output = new PlainTextOutputSpeech();
				output.setText("Helping");
				resp = SpeechletResponse.newTellResponse(output);
				break;
			}
			case "AMAZON.StopIntent" : {
				PlainTextOutputSpeech output = new PlainTextOutputSpeech();
				output.setText("Stopping");
				resp = SpeechletResponse.newTellResponse(output);
				break;
			}
			case "AMAZON.CancelIntent" : {
				PlainTextOutputSpeech output = new PlainTextOutputSpeech();
				output.setText("Cancelling");
				resp = SpeechletResponse.newTellResponse(output);
				break;
			}
			case SetValueIntentHandler.INTENT_NAME : {
				resp = new SetValueIntentHandler().handleIntent(requestEnvelope);
				break;
			}
			case GetValueIntentHandler.INTENT_NAME : {
				resp = new GetValueIntentHandler().handleIntent(requestEnvelope);
				break;
			}
			case RouterIntentHandler.INTENT_NAME : {
				resp = new RouterIntentHandler().handleIntent(requestEnvelope);
				break;
			}
		}
		try {
			log.info(getMapper().writeValueAsString(resp));
		} catch (JsonProcessingException e) {}

		return resp;
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		log.info("onSessionEnded: "+requestEnvelope.getSession().getSessionId());
	}

	private static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}
	
}
