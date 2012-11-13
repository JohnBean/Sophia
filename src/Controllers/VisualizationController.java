package edu.gatech.sophia;

import java.awt.event.*;

public class VisualizationController {
    private Recording recording;

    private PlaybackView pbView;
    private PlaybackSettingsView psView;

    private int currentFrame;

    public VisualizationController() {

    }

    public void setRecording(Recording recording) {
        this.recording = recording;
        currentFrame = 0;
        pbView.showInitialFrame(recording.getFrame(0), recording.getCluster());
    }

    public void setPlaybackView(PlaybackView pbView) {
        this.pbView = pbView;
    }

    public void setSettingsView(PlaybackSettingsView psView) {
        this.psView = psView;
    }

    public void nextFrame() {
        currentFrame++;
        pbView.showFrame(recording.getFrame(currentFrame));
    }

    /**
     * Action called when apply button is pushed in playback settings view
     */
    public void applySettings() {
        //TODO: Apply settings from view

        //TODO: remove
        nextFrame();
    }
}
