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

/**
 * This handler responds to 'set value' intents.
 * It requires device name and value to be specified.
 * If both values are provided from the user intent, it responds with "Setting value to device..." response
 * with information where the value and the device name were taken from (session or intent directly).
 * If any of the required information is missing, it re-prompts the users.
 * @author Eugene
 *
 */
public class SetValueIntentHandler extends AbstractIntentHandler {

	private static final Logger log = LoggerFactory.getLogger(SetValueIntentHandler.class);
	
	public static final String INTENT_NAME = "SetValueIntent";
	
	@Override
	protected SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		SpeechletResponse resp = null;
		PlainTextOutputSpeech output = new PlainTextOutputSpeech();
		
		Slot valueSlot = requestEnvelope.getRequest().getIntent().getSlot(VALUE_KEY);
		Slot deviceSlot = requestEnvelope.getRequest().getIntent().getSlot(DEVICE_KEY);
		log.info("value in slot: "+(valueSlot==null?"null":valueSlot.getValue()));
		log.info("value in session: "+requestEnvelope.getSession().getAttribute(VALUE_KEY));
		log.info("device in slot: "+(deviceSlot==null?"null":deviceSlot.getValue()));
		log.info("device in session: "+requestEnvelope.getSession().getAttribute(DEVICE_KEY));

		String device = null, value = null;
		if (valueSlot==null || StringUtils.isEmpty(valueSlot.getValue())) {
			value = (String) requestEnvelope.getSession().getAttribute(VALUE_KEY);
		}
		if (valueSlot!=null && StringUtils.isNotEmpty(valueSlot.getValue())) {
			requestEnvelope.getSession().setAttribute(VALUE_KEY, valueSlot.getValue());
		}

		if (deviceSlot==null || StringUtils.isEmpty(deviceSlot.getValue())) {
			device = (String) requestEnvelope.getSession().getAttribute(DEVICE_KEY);
		}
		if (deviceSlot!=null && StringUtils.isNotEmpty(deviceSlot.getValue())) {
			requestEnvelope.getSession().setAttribute(DEVICE_KEY, deviceSlot.getValue());
		}

		if ( (StringUtils.isNotBlank(device) || (deviceSlot!=null && StringUtils.isNotBlank(deviceSlot.getValue())))
				&& (StringUtils.isNotBlank(value) || (valueSlot!=null && StringUtils.isNotBlank(valueSlot.getValue())))) {
			output.setText(
					"Setting "+(device!=null?device:deviceSlot.getValue())
					+" to "+(value!=null?value:valueSlot.getValue())
					+". Device name is from "
					+(device!=null?"session":"intent")
					+". Value is from "
					+(value!=null?"session":"intent")
					);
			
			resp = SpeechletResponse.newTellResponse(output);
		} else {
			StringBuilder sb = new StringBuilder("Sorry, I didn't get ");
			boolean andRequired = false;
			if (value==null && valueSlot!=null && StringUtils.isBlank(valueSlot.getValue())) {
				sb.append("value");
				andRequired = true;
			}
			if (device==null && deviceSlot!=null && StringUtils.isBlank(deviceSlot.getValue())) {
				if (andRequired) {
					sb.append(" and");
				}
				sb.append("device name");
			}
			output.setText(sb.toString());
			
			Reprompt reprompt = new Reprompt();
			reprompt.setOutputSpeech(output);
			resp = SpeechletResponse.newAskResponse(output, reprompt);
		}
		return resp;
	}

}
