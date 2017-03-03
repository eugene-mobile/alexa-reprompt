/*******************************************************************************
 * Copyright (c) ecobee, Inc. 2017. All rights reserved.
 *******************************************************************************/
package ca.appspace.ask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public class GetValueIntentHandler extends AbstractIntentHandler {

	private static final Logger log = LoggerFactory.getLogger(GetValueIntentHandler.class);

	public static final String INTENT_NAME = "GetValueIntent";
	
	@Override
	protected SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		SpeechletResponse resp = null;
		PlainTextOutputSpeech output = new PlainTextOutputSpeech();

		Slot deviceSlot = requestEnvelope.getRequest().getIntent().getSlot(DEVICE_KEY);
		log.info("device in slot: "+(deviceSlot==null?"null":deviceSlot.getValue()));
		log.info("device in session: "+requestEnvelope.getSession().getAttribute(VALUE_KEY));
		
		String device = null;

		if (deviceSlot==null || StringUtils.isEmpty(deviceSlot.getValue())) {
			device = (String) requestEnvelope.getSession().getAttribute(DEVICE_KEY);
		}
		if (deviceSlot!=null && StringUtils.isNotEmpty(deviceSlot.getValue())) {
			requestEnvelope.getSession().setAttribute(DEVICE_KEY, deviceSlot.getValue());
		}

		if ( (StringUtils.isNotBlank(device) || (deviceSlot!=null && StringUtils.isNotBlank(deviceSlot.getValue())))) {
			output.setText(
					"Reading "+(device!=null?device:deviceSlot.getValue())
					+" value from "
					+(device!=null?"session":"intent"));
			resp = SpeechletResponse.newTellResponse(output);
		} else {
			StringBuilder sb = new StringBuilder("Sorry, I didn't get device name");
			output.setText(sb.toString());
			
			Reprompt reprompt = new Reprompt();
			reprompt.setOutputSpeech(output);
			resp = SpeechletResponse.newAskResponse(output, reprompt);
		}
		return resp;
	}

}
