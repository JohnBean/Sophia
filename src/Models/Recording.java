package edu.gatech.sophia;

import java.util.*;

/**
 * Contains frames that can be rendered by the 3D view and
 * has playback controls to allow playing, pausing, seeking.
 */
public class Recording {
    private ArrayList<Frame> frames;
    private Cluster cluster;

    public Recording(Cluster c) {
        cluster = c;
        frames = new ArrayList<Frame>();
    }

    public int getNumFrames() {
        return frames.size();
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public Frame getFrame(int index) {
        return frames.get(index);
    }

    public Cluster getCluster() {
        return cluster;
    }
}
