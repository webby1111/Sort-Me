package com.vengestudios.sortme.security;

/**
 * Holds the information for the user for the security message layer
 *
 * Responsible for:
 *  - Generating the user's RSA Public and Private keys, DES key, and nonce
 *  - Encryption and Decryption using the user's RSA private key
 *  - Encryption and Decryption using the user's DES key
 *  - Holding the choice of security protocol the user choose
 */
public class OwnSecurityData {

	private SecurityProtocolType securityProtocolType;
    private DESCipher            desCipher;
    private RSAPrivateCipher     rsaPrivateCipher;
    private byte[]               nonce;

    /**
     * Constructor
     * Creates an instance of OwnSecurityData
     */
    public OwnSecurityData() throws Exception {
        securityProtocolType = SecurityDefaults.SECURITY_PROTOCOL_TYPE;
        rsaPrivateCipher     = new RSAPrivateCipher();
        regenerateForNewSession();
    }

    /**
     * Regenerates a new DES key and nonce
     */
    public void regenerateForNewSession() throws Exception {
        regenerateDESKey();
        regenerateNounce();
    }

    /**
     * Sets the user's choice of SecurityProtocolType
     * @param securityProtocolType
     */
    public void setSecurityProtocolType(SecurityProtocolType securityProtocolType) {
        this.securityProtocolType = securityProtocolType;
    }

    /**
     * @return The user's choice of SecurityProtocolType
     */
    public SecurityProtocolType getSecurityProtocolType() {
        return securityProtocolType;
    }

    /**
     * @return The nonce generated by the user
     */
    public byte[] getNonce() {
        return nonce;
    }

    /**
     * Regenerates the user's nonce
     */
    public void regenerateNounce() throws Exception {
        nonce = SecurityHelper.generateNonce(SecurityDefaults.NONCE_LENGTH);
    }

    /**
     * Regenerates the user's RSA Public and Private keys
     */
    public void regenerateRSAKeys() throws Exception {
        rsaPrivateCipher = new RSAPrivateCipher();
    }

    /**
     * Regenerates the user's DES key
     */
    public void regenerateDESKey() throws Exception {
        desCipher = new DESCipher();
    }

    /**
     * @return The user's DES keys encoded in bytes
     */
    public byte[] getDESKeyBytes() {
        return desCipher.getkey().getEncoded();
    }

    /**
     * @return The user's RSA Public key encoded in bytes
     */
    public byte[] getRSAPublicKeyBytes() {
        return rsaPrivateCipher.getPublicKey().getEncoded();
    }

    /**
     * Get the cipher text encrypted using the user's DES key
     * @param plainText  The text to encrypt
     * @return           The encrypted text
     */
    public byte[] getCipherTextWithDES(byte[] plainText) throws Exception {
        return desCipher.getCipherText(plainText);
    }

    /**
     * Get the plain text decrypted using the user's DES key
     * @param cipherText  The text to decrypt
     * @return            The decrypted text
     */
    public byte[] getPlainTextWithDES(byte[] cipherText) throws Exception {
        return desCipher.getPlainText(cipherText);
    }

    /**
     * Get the cipher text encrypted using the user's RSA private key
     * @param plainText  The text to encrypt
     * @return           The encrypted text
     */
    public byte[] getCipherTextWithRSA(byte[] plainText) throws Exception {
        return rsaPrivateCipher.getCipherText(plainText);
    }

    /**
     * Get the plain text decrypted using the user's RSA private key
     * @param cipherText  The text to decrypt
     * @return            The decrypted text
     */
    public byte[] getPlainTextWithRSA(byte[] cipherText) throws Exception {
        return rsaPrivateCipher.getPlainText(cipherText);
    }

}
