# Alexa Skills Kit re-prompt sample

This sample shows off an approach how one can have multiple skill utterances requiring some data to operate on. 
As user input is not predictable, any slot value can be missing. Of cause, you can do a re-prompt. But what happens if your skill takes 2 slot values? 
And what happens if your skill have 2 utterances that can take the same values? 
This skill covers following use case: 
- You have number of devices that allows to set or read values. Example: light switches. You should be able to turn on or off any switch, or read current switch operational mode.  
- It must be possible to assign a specific value to a specific device. 
- It must be possible to get current reading of a value on specific device.

How to deploy the skill: 
You need an account at AWS to deploy Lambda function. Also you need an account at developer.amazon.com to configure the skill. 
Read more about registering the skill at https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/registering-and-managing-alexa-skills-in-the-developer-portal 
As soon as you see skill ID (example skill ID: amzn1.ask.skill.dd7ff3a8-1841-4167-aee5-abcd12345 ), enter it in SpeechletStreamHandler APPLICATION_ID field. You can now deploy lambda. Read more about how to deploy your lambda function here: https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/developing-an-alexa-skill-as-a-lambda-function

How to test the skill: 
Make sure you followed steps described here: https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/testing-an-alexa-skill
As soon as you have your skill deployed in testing mode and linked to your alexa account, say "Alexa, open _your_skill_name_".
You should here "Hello". This is the value returned from ca.appspace.ask.RepromptSpeechlet.onLaunch(SpeechletRequestEnvelope<LaunchRequest>) method. 
Say "Alexa, tell _your_skill_name_ for help". You should here "Helping". 
Look into SampleUtterances.txt file for other utterances you can say to your skill.
