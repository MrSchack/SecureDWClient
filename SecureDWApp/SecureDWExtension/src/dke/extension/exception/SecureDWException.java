package dke.extension.exception;

public class SecureDWException extends Exception {
    private boolean forceInit = false;
    
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

    public void setForceInit(boolean b) {
        this.forceInit = b;
    }
    
    public boolean forceInit() {
        return this.forceInit;
    }
}
