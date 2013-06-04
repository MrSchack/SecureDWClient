package dke.extension.logic.validation;

public interface Validate {

    /**
     * @param hostName
     * @param port
     * @param serviceName
     * @return
     */
    public boolean validateConnectionData(String hostName, String port,
                                          String serviceName, String username,
                                          String password);


    public boolean validateFact();
}
