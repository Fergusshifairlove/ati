package ar.edu.itba.events;

import java.io.File;

/**
 * Created by Luis on 3/11/2017.
 */
public class OpenVideo {
    private File video;

    public OpenVideo(File video) {
        this.video = video;
    }

    public File getVideo() {
        return video;
    }
}
