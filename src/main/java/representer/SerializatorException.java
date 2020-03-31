package representer;

public class SerializatorException extends RuntimeException {
    private static final long serialVersionUID = -6741466477072057537L;

    public SerializatorException() {
        super();
    }

    public SerializatorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SerializatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializatorException(String message) {
        super(message);
    }

    public SerializatorException(Throwable cause) {
        super(cause);
    }


}
