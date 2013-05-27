package dke.extension.logic.validation;

public interface Validate {

    /**
     * @param hostName
     * @param port
     * @param serviceName
     * @return
     */
    public boolean validateSettings(String hostName, int port, String serviceName);
}
