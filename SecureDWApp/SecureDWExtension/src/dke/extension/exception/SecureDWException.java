package dke.extension.exception;

public class SecureDWException extends Exception {
    public SecureDWException(Throwable throwable) {
        super(throwable);
    }

    public SecureDWException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SecureDWException(String string) {
        super(string);
    }

    public SecureDWException() {
        super();
    }
}
