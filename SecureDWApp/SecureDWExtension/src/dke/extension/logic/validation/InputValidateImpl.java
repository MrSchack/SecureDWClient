package dke.extension.logic.validation;

public class InputValidateImpl implements Validate {
    public InputValidateImpl() {
        super();
    }


    public boolean validateConnectionData(String hostName, int port,
                                          String serviceName, String username,
                                          String password) {
        return false;
    }

    public boolean validateFact() {
        return false;
    }
}
