# Alexa Skills Kit re-prompt sample
This sample shows off an approach where you can have multiple skill utterances requiring some data to operate on. 
As user input is not predictable, any slot value can be missing. Of cause, you can do a re-prompt. But what happens if your skill takes 2 slot values? 
And what happens if your skill have 2 utterances that can take the same values? 
This skill covers following use case: 
- You have number of devices that allows to set or read values. Example: thermostats in different rooms that allows you to set a temperature in a room, 
and read the room current temperature. Another example are 3D printer that allows to schedule a job and read a printer current progress.
- It must be possible to assign a specific value to a specific device. 
- It must be possible to get current reading of a value on specific device.
