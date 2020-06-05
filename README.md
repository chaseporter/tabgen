# Purpose

This is an Android Application to record guitar playing and generate tablature based on the recording.

## Implementation details
Current functionality is a single button that controls the AppState and a TextView with text that reflects the current AppState.
The App can currently be in two different states: READY or RECORDING. 
State is managed with a Singleton AppState that is injected with Dagger wherever needed. This insures all models and views
listen to the same state. When the button is clicked, a Recorder Model which will later be used to record and store audio files
tells the AppState model to change the State. 
I use databinding to have the view listen for changes on the AppState to reflect changes.
Immediate Next Steps are to secure permissions from a user to record and access external storage and then use Recorder class
to implement recording a file, saving the recording, and displaying all recordings to a user in a file system.
For file organization would like an option to start new projects where all recordings belonging to a certain project would exist. 

## Future Feature List
Multi Tracking and then assembling multiple tracks into a single audio file\n
Metronome \n
noise cancelling \n
effect adding \n

