package dke.extension.logic.validation;

public class InputValidateImpl implements Validate {
    public InputValidateImpl() {
        super();
    }

    public void validateSettings(String hostName, int port,
                                    String serviceName) {
        
        testConnection testCon = new testConnection().connect(host, port, sid, name, pwd)
        
        
        
    }
}
