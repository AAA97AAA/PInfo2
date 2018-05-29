package services.security;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.core.io.ClassPathResource;

/**
*
* Utility functions for RSA key
* 
* @author Clasino
* @source https://github.com/jwtk/jjwt/issues/131
* 
*/
public class KeytoolUtility {

	private KeyStore keystore;
	
	KeytoolUtility() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		ClassPathResource resource = new ClassPathResource("jwtkey.jks");
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(resource.getInputStream(), "rz6ob288z26r2PFJrU4njRF4YmE5GS".toCharArray());
	}
	
	public Key getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return keystore.getKey("jwtkey", "938C7k2X88o6ia5SHYyV9GrnoACYus".toCharArray());
	}
	
	public Key getPublicKey() throws KeyStoreException {
		Certificate cert = keystore.getCertificate("jwtkey");
		return cert.getPublicKey();
	}
}
