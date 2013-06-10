package dke.extension.logic.crypto;

import dke.extension.data.preferencesData.AccessPreferences;

import java.io.FileNotFoundException;

import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class AESCryptEngineImpl implements CryptEngine {
    private BlockCipher engine;
    private BlockCipherPadding padding;
    private BufferedBlockCipher cipher;

    public AESCryptEngineImpl() {
        super();
        /*
           * A full list of BlockCiphers can be found at http://www.bouncycastle.org/docs/docs1.6/org/bouncycastle/crypto/BlockCipher.html
           */
        engine = new AESEngine();
        /*
           * Paddings available (http://www.bouncycastle.org/docs/docs1.6/org/bouncycastle/crypto/paddings/BlockCipherPadding.html):
           *   - ISO10126d2Padding
           *   - ISO7816d4Padding
           *   - PKCS7Padding
           *   - TBCPadding
           *   - X923Padding
           *   - ZeroBytePadding
           */
        padding = new PKCS7Padding();
        cipher =
                new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), padding);
    }


    // Encrypts a string.

    public byte[] encryptString(String data, byte[] iv) throws CryptoException,
                                                               NoSuchAlgorithmException,
                                                               InvalidKeySpecException {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return this.encrypt(data.getBytes(), iv);
    }

    // Encrypt arbitrary byte array with a given key

    public synchronized byte[] encrypt(byte[] data,
                                       byte[] iv) throws CryptoException,
                                                         NoSuchAlgorithmException,
                                                         InvalidKeySpecException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        CipherParameters params = null;

        try {
            params = this.getCipherParameters(AccessPreferences.getKey(), iv);
        } catch (FileNotFoundException e) {
            //todo: log meldung für den anfang, später infomeldung für den user
        }
        cipher.reset();
        cipher.init(true, params);

        return this.callCipher(data);
    }

    // Decrypts arbitrary data.

    public synchronized byte[] decrypt(byte[] data,
                                       byte[] iv) throws CryptoException,
                                                         NoSuchAlgorithmException,
                                                         InvalidKeySpecException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        CipherParameters params = null;

        try {
            params = this.getCipherParameters(AccessPreferences.getKey(), iv);
        } catch (FileNotFoundException e) {
            //todo: log meldung für den anfang, später infomeldung für den user
        }
        cipher.reset();
        cipher.init(true, params);

        return this.callCipher(data);
    }

    // Decrypts a string that was previously encoded
    // using encryptString.

    public String decryptString(byte[] data, byte[] iv) throws CryptoException,
                                                               NoSuchAlgorithmException,
                                                               InvalidKeySpecException {
        if (data == null || data.length == 0) {
            return "";
        }

        return new String(this.decrypt(data, iv));
    }

    // Private routine that does the gritty work.

    private byte[] callCipher(byte[] data) throws CryptoException {
        int size = cipher.getOutputSize(data.length);
        byte[] result = new byte[size];

        // de/encrypting
        /*
       * data = input
       * 0 = offset at which input data starts
       * data.length = number of bytes to be de/encrypted
       * result = resultarray
       * 0 = offset from which the output will be copied
       *
       * returns number of output bytes
       */
        int olen = cipher.processBytes(data, 0, data.length, result, 0);

        // process last blocks in the buffer
        olen += cipher.doFinal(result, olen);

        if (olen < size) {
            byte[] tmp = new byte[olen];
            System.arraycopy(result, 0, tmp, 0, olen);
            result = tmp;
        }

        return result;
    }

    private CipherParameters getCipherParameters(SecretKey key,
                                                 byte[] iv) throws NoSuchAlgorithmException {
        // setup cipher parameters with key and IV
        KeyParameter keyParam = new KeyParameter(key.getEncoded());
        return new ParametersWithIV(keyParam, iv);
    }
}
