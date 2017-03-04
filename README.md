# Alexa Skills Kit re-prompt sample

This sample shows off an approach how one can have multiple skill utterances requiring some data to operate on. 
As user input is not predictable, any slot value can be missing. Of cause, you can do a re-prompt. But what happens if your skill takes 2 slot values? 
And what happens if your skill have 2 utterances that can take the same values? 
This skill covers following use case: 
- You have number of devices that allows to set or read values. Example: light switches. You should be able to turn on or off any switch, or read current switch operational mode.  
- It must be possible to assign a specific value to a specific device. 
- It must be possible to get current reading of a value on specific device.

How to deploy the skill: 
