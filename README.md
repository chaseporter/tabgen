# Purpose #

This is an Android Application to record guitar playing and generate tablature based on the recording. Current functionality is a single button that starts and stops recordings and saves them to the Apps Data directory.

A RecyclerView holds the recordings and shows them to the user on the MainActivity. When user selects a list item, the item expands to show three buttons: Play, Edit, Delete. From here, each recording can be chosen to be played, which sets the AppState to playing which in turn makes recording, editing, and deleting impossible and also changes the play button to a stop button. The recording can also be chosen to be edited, which sets the AppState to EDITING and opens a new activity which will be used to run signal processing code on recordings, or deleted, which will delete the audio file and remove it from the RecyclerView.

## Implementation details ## 
State is managed with a Singleton AppState that is injected with Dagger wherever needed. This insures all models and views
listen to the same state. When the button is clicked, a Recorder Model tells the AppState model to change the State and uses 
a MediaRecorder to start recoriding a .3gp audio file. Audio file is also time stamped to uniquely identify.  
I use databinding to have the view listen for changes on the AppState to reflect changes.  

A RecordingFiles model populates a list of recordings on start-up and also receives additional recordings generated in the 
apps lifecycle. This RecordingFiles model also passes the list of audio recordings to an adapter for a RecyclerView that shows
them in as a list to the user. From the RecyclerView a user can choose to listen to a recording (playing the audio coming soon),
edit the audio file with signal processing (coming soon, current functionality is to open the activity where this will be handled), 
or delete the recording (fully functional). The Adapter also receives a state that is data bound to its recording_listitem views and only
allows for behavior like deleting or playing audio recordings if the state is READY (in other words if its not already playing a recording
or making a recording.)

To open the new activity, the adapter includes an interface that has a method that is called when the edit recording button is clicked. 
This interface is implemented from the MainActivity where it creates the intent and starts the new activity.

## Future Feature List ##
change recording format to a WAV file, implement a FFT with NDK to do signal processing in C++.   

For file organization would like an option to start new projects where all recordings belonging to a certain project would exist. 

## Wish List ##
Multi Tracking and then assembling multiple tracks into a single audio file  
Metronome  
noise cancelling  
effect adding  

