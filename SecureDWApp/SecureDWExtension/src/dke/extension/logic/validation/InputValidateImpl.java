package dke.extension.logic.validation;

import dke.extension.data.dbConnection.ConnectionManager;

public class InputValidateImpl implements Validate {
    public InputValidateImpl() {
        super();
    }


    public boolean validateConnectionData(String hostName, String port,
                                          String serviceName, String username,
                                          String password) {
        if (hostName != null && port != null && serviceName != null &&
            username != null && password != null && !hostName.isEmpty() &&
            !port.isEmpty() && !serviceName.isEmpty() && !username.isEmpty() &&
            !password.isEmpty()) {
            try {
                /*ConnectionManager connection = ConnectionManager.getInstance();
                connection.connect(hostName, port, serviceName, username,
                                   password);
                connection.disconnect();*/
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean validateFact() {
        return false;
    }
}
