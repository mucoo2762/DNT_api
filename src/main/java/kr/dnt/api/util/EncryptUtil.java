
package kr.dnt.api.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EncryptUtil {
	public static String encryptSha512(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return encryptHash("SHA-512", str);
	}
	public static String decryptSha512(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return decryptSha512(str);
	}

	public static String encryptMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return encryptHash("MD5", str);
	}

	// MD5, SHA-256, SHA-512
	private static String encryptHash(String algorithm, String str)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(str.getBytes("UTF-8"));
		byte[] encrypted = md.digest();
		return byteArrayToHex(encrypted);
	}
	
	public static String secretKey = "8WJ56MZ1BL8R9WBF"; // 16bit / 32bit 256
	public static String IV = "ZM3GG0U7H8QQ7GAB"; // 16bit

	// mysql
	// SELECT HEX(AES_ENCRYPT('test01', '1234567890123456'));
	// SELECT CONVERT(AES_DECRYPT(UNHEX(id), '1234567890123456'),CHAR) FROM user WHERE seq = 900;
	public static byte[] generateMySQLAESKey(final String key) throws UnsupportedEncodingException {
		final byte[] keyBytes = new byte[16];
		int i = 0;
		for (byte b : key.getBytes("UTF-8")) {
			keyBytes[i++ % 16] ^= b;
		}
		return keyBytes;
	}

	public static String encryptAes(String str)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		if (str == null) {
			return null;
		}
		if (str.equals("")) {
			return "";
		}
		final Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(generateMySQLAESKey(secretKey), "AES"));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		return byteArrayToHex(encrypted);
	}

	public static String decryptAes(String str)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		if (str == null) {
			return null;
		}
		final Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generateMySQLAESKey(secretKey), "AES"));

		byte[] decrypted = c.doFinal(hexToByteArray(str));
		return new String(decrypted, "UTF-8");
	}

	private static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100d750f631cb04fbd59650fd5701c959fde5c1ab4142cf7276587e371ed19695a413db42fc821f490584a78f08d250996bb00580e65ce696918b591b5fb29a25628c0683406e71b9e4f337c892c340d6a3ff2c980ebb75b947a0d553b6ed31c239a0699dcd174274b634a982125d426bae3e910effc16d7849810f9b315c2aedc50203010001";
	private static final String PRIVATE_KEY = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100d750f631cb04fbd59650fd5701c959fde5c1ab4142cf7276587e371ed19695a413db42fc821f490584a78f08d250996bb00580e65ce696918b591b5fb29a25628c0683406e71b9e4f337c892c340d6a3ff2c980ebb75b947a0d553b6ed31c239a0699dcd174274b634a982125d426bae3e910effc16d7849810f9b315c2aedc502030100010281805c120ab49013b97c43a2ae321e597359d67d5235bdd3e726240114a0e10e7b3e861f242dc5968b2b08e67d8b502396f72d75ad00d488e5a9a6e49b87f85f61cc50f602e76696e5eec8b2ef290d3cc65df412bfb7631c6610cb499446199192c485c6868c1a3b506768be7740244a40e45126ccc67a398d2781b28830c8c69aa1024100f73187c114160af1fac5c0a4e8d54bb71791af16ed36f1faf1d3de23504e9683da3dc6ba8c30eb7e62c0966ac563211f9476f22a3ae208ddbab19436a73d3b53024100defcb3e70a471b4670caf88bbd47eb707ff89072c6194711c4c30d8368a355048cb3cd8c49aac2da7c6ce748565ed78ac5c55bc7f11869a3538a268509ed2787024100ab5d69e83f3d7cbefe0343a6cf46b6e3a9f233f867e0b4662c699d50cbe67f4fcee70eb2da673b8a75795f8d8b634a5e333536727239aabdd4243144a631bbc3024100d5960be403ae346bf662144c1553ac2aa80938a31728e3301bde11358ba6d72a72734f314ee6a7998b90ecc0172ae51e616d370a7edeb960de05f7fb7a94944502405b841f64e162238cad67c84a73d87097c92fc1aa5db754da290a4b4394d227b4d3b9cb75eafb1ad63ef53963a9997b701fdf03cd3c8339155ac47775834b3660";

	public static void generateRsaKey()
			throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
		 //key 생성
		 KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		 keyGen.initialize(1024);
		
		 KeyPair keyPair = keyGen.generateKeyPair();
		 byte[] publicKey=keyPair.getPublic().getEncoded();
		 byte[] privateKey=keyPair.getPrivate().getEncoded();
		
		 System.out.println("public=" + byteArrayToHex(publicKey));
		 System.out.println("private=" + byteArrayToHex(privateKey));
	}
	
	public static String encryptRsa(String str)
			throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {

		KeyFactory fac = KeyFactory.getInstance("RSA");
		PublicKey publicKey = fac.generatePublic(new X509EncodedKeySpec(hexToByteArray(PUBLIC_KEY)));

		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		c.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		return byteArrayToHex(encrypted);
	}

	public static String decryptRsa(String str)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		KeyFactory fac = KeyFactory.getInstance("RSA");
		// PrivateKey privateKey = fac.generatePrivate(new
		// PKCS8EncodedKeySpec(rsaKey.getPrivate_key()));
		PrivateKey privateKey = fac.generatePrivate(new PKCS8EncodedKeySpec(hexToByteArray(PRIVATE_KEY)));

		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		c.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] decrypted = c.doFinal(hexToByteArray(str));
		return new String(decrypted, "UTF-8");
	}

	// byte array 저장을 위한 hex 처리
	public static String byteArrayToHex(byte[] value) {
		StringBuilder sb = new StringBuilder();
		for (final byte b : value)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

	public static byte[] hexToByteArray(String value) {
		int len = value.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4)
					+ Character.digit(value.charAt(i + 1), 16));
		}
		return data;
	}
}
