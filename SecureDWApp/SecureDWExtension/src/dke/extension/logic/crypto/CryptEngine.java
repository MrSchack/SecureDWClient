package dke.extension.logic.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.crypto.CryptoException;

public interface CryptEngine {

    /**
     * @param data
     * @param iv
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public byte[] encryptString(String data, byte[] iv) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * @param data
     * @param iv
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public synchronized byte[] encrypt(byte[] data, byte[] iv) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * @param data
     * @param iv
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public synchronized byte[] decrypt(byte[] data, byte[] iv) 
      throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * @param data
     * @param iv
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public String decryptString(byte[] data, byte[] iv) 
      throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException;
}
