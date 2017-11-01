package ar.edu.itba.events;


import java.io.File;

/**
 * Created by Luis on 29/10/2017.
 */
public class FindObjectInVideo {
    private File videoDir;

    public FindObjectInVideo(File videoDir) {
        this.videoDir = videoDir;
    }

    public File getVideoDir() {
        return videoDir;
    }
}
