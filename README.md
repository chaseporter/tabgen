# Purpose #

This is an Android Application to record guitar playing and generate tablature based on the recording. Current functionality is a single button that starts and stops recordings and saves them to the Apps Data directory.

## Implementation details ## 
State is managed with a Singleton AppState that is injected with Dagger wherever needed. This insures all models and views
listen to the same state. When the button is clicked, a Recorder Model tells the AppState model to change the State and uses 
a MediaRecorder to start recoriding a .3gp audio file. Audio file is also time stamped to uniquely identify.  
I use databinding to have the view listen for changes on the AppState to reflect changes.  

A Recording Files model populates a list of recordings on start-up and also receives additional recordings generated in the 
apps lifecycle.


## Future Feature List ##
Show recordings generated in a listview and allow for playing and pausing recordings, editing names, and deleting recordings.  

change recording format to a WAV file, implement a FFT with NDK to do signal processing in C++.   

For file organization would like an option to start new projects where all recordings belonging to a certain project would exist. 

## Wish List ##
Multi Tracking and then assembling multiple tracks into a single audio file  
Metronome  
noise cancelling  
effect adding  

