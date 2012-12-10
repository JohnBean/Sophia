package edu.gatech.sophia;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for the visualization portion of SOPHIA
 * Handles playback and visual representation actions
 */
public class VisualizationController {
    /**
     * The current recording that the user is visualizing
     */
    private Recording recording;

    /**
     * References to view objects to allow the controller to get and send information
     */
    private PlaybackView pbView;
    private PlaybackSettingsView psView;
    private PlaybackControlsView pbcView;

    /**
     * Index of the current frame being visualized
     */
    private int currentFrame;

    /**
     * Playback rate in frames per second
     */
    private int playbackRate = 30;

    /**
     * The current playback state
     */
    private boolean playing = false;

    /**
     * Constructs a new visualization controller
     */
    public VisualizationController() {

    }
    public void loadRecording(String recordFilename){
        Cluster cluster= new Cluster(recordFilename);
        setRecording(new Recording(cluster));
    }
    /**
     * Sets up a new recording in the visualization window by initializing the 3D scenegraph and setting up the GUI
     *
     * @param recording the recording to start visualizing
     */
    public void setRecording(Recording recording) {
        this.recording = recording;
        currentFrame = 0;
        pbView.showInitialFrame(recording.getFrame(0), recording.getCluster());
        pbcView.setNumFrames(recording.getNumFrames());
        pbcView.setCurrentFrame(0);
    }
    public Recording getRecording(){
        return this.recording;
    }
    public int getFrameId(){
        return currentFrame;
    }
    /**
     * Set the playback view that will be visible in the main window
     *
     * @param pbView the playback view
     */
    public void setPlaybackView(PlaybackView pbView) {
        this.pbView = pbView;
    }

    /**
     * Set the visualization settings view that will be visible for playback
     *
     * @param psView the playback settings view
     */
    public void setSettingsView(PlaybackSettingsView psView) {
        this.psView = psView;
    }

    /**
     * Set the playback controls viewer that allows the user to seek and control playback
     *
     * @param pbcView the playback controls view
     */
    public void setPlaybackControlsView(PlaybackControlsView pbcView) {
        this.pbcView = pbcView;
    }

    /**
     * Increments to the next frame and displays it in the visualization. Resets to 0 if at the end.
     */
    public void nextFrame() {
        if(recording != null) {
            currentFrame++;
            if(currentFrame == recording.getNumFrames())
                currentFrame = 0;

            pbView.showFrame(recording.getFrame(currentFrame));
            pbcView.setCurrentFrame(currentFrame);
        }
    }

    /**
     * Decrements to the previous frame and displays it in the visualization
     */
    public void prevFrame() {
        if(recording != null) {
            currentFrame--;
            if(currentFrame < 0)
                currentFrame = recording.getNumFrames() - 1;

            pbView.showFrame(recording.getFrame(currentFrame));
            pbcView.setCurrentFrame(currentFrame);
        }
    }

    /**
     * Sets which frame to display
     *
     * @param frameId the index of the frame to display
     */
    public void setFrame(int frameId) {
        if(recording != null && frameId >= 0 && frameId < recording.getNumFrames()) {
            currentFrame = frameId;

            pbView.showFrame(recording.getFrame(currentFrame));
        }
    }

    /**
     * Starts playback of the recording
     */
    public void play() {
        //Schedule frame change based on playback rate
        new Timer().schedule(new TimerTask() {
            public void run()  {
                autoAdvanceFrame();
            }
        }, 1000 / playbackRate);

        //Signal to continue playing
        playing = true;
    }

    /**
     * Pauses playback of recording
     */
    public void pause() {
        playing = false;
    }

    /**
     * Called to auto advance to the next frame and sets up a timer which calls itself to keep
     * continuous playback
     */
    private void autoAdvanceFrame() {
        if(playing) {
            currentFrame++;

            if(currentFrame == recording.getNumFrames()) {
                currentFrame--;
                playing = false;
            }

            pbView.showFrame(recording.getFrame(currentFrame));
            pbcView.setCurrentFrame(currentFrame);

            //Schedule the next frame change
            new Timer().schedule(new TimerTask() {
                public void run()  {
                    autoAdvanceFrame();
                }
            }, 1000 / playbackRate);
        }
    }

    /**
     * Saves the recording given a filename
     *
     * @param filename the base filename to save as (no extension)
     */
    public void saveRecording(String filename)
    {
        if(recording != null)
            recording.writeRecord(filename);
    }

    /**
     * Action called when apply button is pushed in playback settings view
     */
    public void applySettings() {
        //TODO: Apply settings from view

    }

    /**
     * Change the display mode for molecules. Method controls the representation type
     * 
     * @param method string name of drawing method
     */
    public void changeDrawingMethod(String method) {
        if(method.equals("VDW")) {
            pbView.toggleVDW(true);
        } else if(method.equals("CPK")) {
            pbView.toggleVDW(false);
        }
    }

    /**
     * Update the coloring in the visualization
     */
    public void updateColoring() {
        pbView.updateColors();
    }
}
