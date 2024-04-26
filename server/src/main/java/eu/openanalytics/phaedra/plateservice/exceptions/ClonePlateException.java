package eu.openanalytics.phaedra.plateservice.exceptions;

public class ClonePlateException extends Exception {
    public ClonePlateException(String message) {
        super(message);
    }

    public ClonePlateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClonePlateException(Throwable cause) {
        super(cause);
    }
}
