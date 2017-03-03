package pl.wasper.popularmovies.network;

/**
 * Created by wasper on 03.03.17.
 */

public class YoutubeURLBuilder {
    private static final String IMAGE_HOST = "https://img.youtube.com/vi/";
    private static final String HQ_IMAGE = "/hqdefault.jpg";

    public static String buildTrailerImageUrlString(String trailerId) {
        return IMAGE_HOST + trailerId + HQ_IMAGE;
    }
}
