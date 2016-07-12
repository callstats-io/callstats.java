package io.callstats.sample.sdk;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.BufferOverflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.ECKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.lang.JoseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.callstats.sdk.ICallStatsTokenGenerator;
/**
 * Example token generator for callstats.io third party authentication tokens
 *
 */
public class LocalTokenGenerator implements ICallStatsTokenGenerator {
	/**
	 * Application ID
	 */
	private String appId;
	/**
	 * User ID
	 */
	private String userId;
	/**
	 * EC private key
	 */
	private PrivateKey eCDSAPrivateKey;
	/**
	 * Key ID
	 */
	private String keyId;
	
	/**
	 * Initialize token generator.
	 * You can use <a href="https://www.npmjs.com/package/pem-to-jwk">pem-to-jwk</a> to convert PEM encoded EC private key to JWK
	 * 
	 * @param appId Application ID
	 * @param userId User ID
	 * @param eCDSAPrivateKeyPath Path to JWK file
	 * to 
	 */
	public LocalTokenGenerator(int appId, String keyId, String userId, String eCDSAPrivateKeyPath) {
		this.keyId = keyId;
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.appId = Integer.toString(appId);
		this.userId = userId;
		
		this.appId = (new Integer(appId)).toString();
		try {
			eCDSAPrivateKey = readEcPrivateKey(eCDSAPrivateKeyPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public String generateToken(boolean forcenew) {
		JwtClaims claims = new JwtClaims();
		claims.setClaim("appID", appId);
		claims.setClaim("userID", userId);
		claims.setClaim("keyID", keyId);
		claims.setExpirationTimeMinutesInTheFuture(10);
		claims.setNotBeforeMinutesInThePast(10);
		
		JsonWebSignature jws = new JsonWebSignature();
		
		jws.setKey(eCDSAPrivateKey);
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
		try {
			return jws.getCompactSerialization();
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*private SecureByteBuffer readPkcs8Key(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		try {
	        FileChannel chan = fis.getChannel();
	        long fileSize = chan.size();
	        MappedByteBuffer mapFile = chan.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
	        boolean reading = false;
	        SecureByteBuffer keyBuffer = new SecureByteBuffer();
	        
	        byte[] chr = new byte[1];
	        byte[] compareLineStart = "-----BEGIN PRIVATE KEY-----\n".getBytes();
	        byte[] compareLineEnd = "-----END PRIVATE KEY-----\n".getBytes();
	        boolean startOfLine = true;
	        boolean valid = false;
	        while (mapFile.hasRemaining()) {
	        	chr[0] = mapFile.get();
	        	if (chr[0] == '\n') {
	        		startOfLine = true;
	        	} else if (reading && chr[0] != '-') {
	        		keyBuffer.write(chr);
	        		startOfLine = false;
	        	} else if (reading && chr[0] == '-') {
	        		reading = false;
	        		int linePos = 0;
		        	while (mapFile.hasRemaining() && linePos < compareLineEnd.length && chr[0] == compareLineEnd[linePos]) {
		        		chr[0] = mapFile.get();
		        		linePos++;
		        	}
		        	// They were equal
		        	if (linePos+1 == compareLineEnd.length) {
		        		valid = true;
		        		break;
		        	}
		        	startOfLine = false;
	        	} else if (startOfLine) {
		        	int linePos = 0;
		        	while (mapFile.hasRemaining() && chr[0] == compareLineStart[linePos] && ++linePos < compareLineStart.length) {
		        		chr[0] = mapFile.get();
		        	}
		        	// They were equal
		        	if (linePos == compareLineStart.length) {
		        		startOfLine = true;
		        		reading = true;
		        	} else if (chr[0] != '\n') {
		        		startOfLine = false;
		        	}
	        	}
	        }
	        chr[0] = 0;
	        if (valid)
	        	return keyBuffer;
	        return null;
		} finally {
			fis.close();
		}
	}*/
	
	
	/**
	 * Reads JWK into PrivateKEy instance
	 * @param fileName Path to the JWK file
	 * @return java.security.PrivateKey instance of JWK
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 */
    private PrivateKey readEcPrivateKey(String fileName) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, NoSuchProviderException
    {
    	StringBuilder sb = new StringBuilder();
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	try {
	    	char[] cbuf = new char[1024];
	    	for (int i=0; i <= 10*1024; i++) {
	    		if (!br.ready())
	    			break;
	    		int len = br.read(cbuf, i*1024, 1024);
	    		sb.append(cbuf, 0, len);
	    	}
	    	if (br.ready()) {
	    		throw new IndexOutOfBoundsException("JWK file larger than 10MB");
	    	}
	    	Type mapType = new TypeToken<Map<String, String>>(){}.getType();
	    	Map<String, String> son = new Gson().fromJson(sb.toString(), mapType);
	    	try {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				EllipticCurveJsonWebKey jwk = new EllipticCurveJsonWebKey((Map<String, Object>)(Map)son);
				Base64Encoder enc = new Base64Encoder();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
	        	enc.encode(jwk.getPrivateKey().getEncoded(), 0, jwk.getPrivateKey().getEncoded().length, os);
				return jwk.getPrivateKey();
			} catch (JoseException e1) {
				e1.printStackTrace();
			}
	        return null;
    	} finally {
    		br.close();
    	}
    }
    /**
     * Byte buffer which allows clearing the backing array
     *
     */
    class SecureByteBuffer  {
    	private byte[] buffer;
    	private int pos;
    	private int size;
    	SecureByteBuffer() {
    		this(10);
    	}
    	SecureByteBuffer(int initialSize) {
    		buffer = new byte[initialSize];
    		pos = 0;
    		size = 0;
    	}
    	
    	void write(byte b) {
    		if (size+1 == buffer.length) {
    			resizeBuffer(buffer.length*2);
    		}
    		buffer[size++] = b;
    	}
    	
    	void write(byte[] bytes) {
    		for (int i=0; i < bytes.length; i++) {
    			write(bytes[i]);
    		}
    	}
    	
    	void resizeBuffer(int newSize) {
    		if (newSize < size) {
    			throw new BufferOverflowException();
    		}
    		byte[] newbuffer = new byte[newSize];
    		for (int i=0; i < size; i++) {
    			newbuffer[i] = buffer[i];
    			buffer[i] = 0;
    		}
    		buffer = newbuffer;
    	}
    	
    	void reset() {
    		pos = 0;
    	}
    	
    	boolean hasRemaining() {
    		return buffer.length != pos+1;
    	}
    	
    	byte read() {
    		return buffer[pos++];
    	}
    	
    	void clear() {
    		for (int i=0; i < size; i++) {
    			buffer[i] = 0;
    		}
    		size = 0;
    		pos = 0;
    	}
    	
    	byte[] getBackingBuffer() {
    		return buffer;
    	}
    	
    	byte[] getByteArray() {
    		byte[] ret = new byte[size];
    		for (int i=0; i < size; i++) {
    			ret[i] = buffer[i];
    		}
    		return ret;
    	}
    }

}