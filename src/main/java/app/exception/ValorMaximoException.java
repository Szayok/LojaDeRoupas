package app.exception;

public class ValorMaximoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValorMaximoException(String message) {
        super(message);
    }
}
