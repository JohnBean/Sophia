package edu.gatech.sophia;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class VisualizationController {
    private Recording recording;

    private PlaybackView pbView;
    private PlaybackSettingsView psView;
    private PlaybackControlsView pbcView;

    private int currentFrame;

    /**
     * Playback rate in frames per second
     */
    private int playbackRate = 30;

    /**
     * The current playback state
     */
    private boolean playing = false;

    public VisualizationController() {

    }

    public void setRecording(Recording recording) {
        this.recording = recording;
        currentFrame = 0;
        pbView.showInitialFrame(recording.getFrame(0), recording.getCluster());
        pbcView.setNumFrames(recording.getNumFrames());
        pbcView.setCurrentFrame(0);
        recording.writeRecord();
    }

    public void setPlaybackView(PlaybackView pbView) {
        this.pbView = pbView;
    }

    public void setSettingsView(PlaybackSettingsView psView) {
        this.psView = psView;
    }

    public void setPlaybackControlsView(PlaybackControlsView pbcView) {
        this.pbcView = pbcView;
    }

    public void nextFrame() {
        if(recording != null) {
            currentFrame++;
            if(currentFrame == recording.getNumFrames())
                currentFrame = 0;

            pbView.showFrame(recording.getFrame(currentFrame));
            pbcView.setCurrentFrame(currentFrame);
        }
    }

    public void prevFrame() {
        if(recording != null) {
            currentFrame--;
            if(currentFrame < 0)
                currentFrame = recording.getNumFrames() - 1;

            pbView.showFrame(recording.getFrame(currentFrame));
            pbcView.setCurrentFrame(currentFrame);
        }
    }

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

    private void autoAdvanceFrame() {
        if(playing) {
            currentFrame++;

            if(currentFrame == recording.getNumFrames()) {
                currentFrame = 0;
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
     * Action called when apply button is pushed in playback settings view
     */
    public void applySettings() {
        //TODO: Apply settings from view

    }
}
