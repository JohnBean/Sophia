package edu.gatech.sophia;

import java.awt.event.*;

public class VisualizationController {
    private Recording recording;

    private PlaybackView pbView;

    public VisualizationController() {

    }

    public void setRecording(Recording recording) {
        this.recording = recording;
        pbView.showInitialFrame(recording.getFrame(0));
    }

    public void setPlaybackView(PlaybackView pbView) {
        this.pbView = pbView;
    }
}
