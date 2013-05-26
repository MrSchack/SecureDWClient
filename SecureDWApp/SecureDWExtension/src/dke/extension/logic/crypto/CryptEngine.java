package dke.extension.logic.crypto;

public interface CryptEngine {

    /**
     * @param data
     * @param nonce
     * @return
     */
    public byte[] encryptString(String data, byte[] iv);

    /**
     * @param data
     * @param iv
     * @return
     */
    public synchronized byte[] encrypt(byte[] data, byte[] iv); 
}
