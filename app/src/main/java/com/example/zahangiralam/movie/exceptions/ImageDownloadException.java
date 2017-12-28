package com.example.zahangiralam.movie.exceptions;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class ImageDownloadException extends Exception {

    public ImageDownloadException() {
    }

    public ImageDownloadException(String message) {
        super(message);
    }

    public ImageDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageDownloadException(Throwable cause) {
        super(cause);
    }
}
