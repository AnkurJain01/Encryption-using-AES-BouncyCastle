import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PKCS7Padding;

import net.codejava.crypto.CryptoException;

public class BouncyCastleTest {

	public static void main(String[] args) throws DataLengthException, InvalidCipherTextException,
			NoSuchAlgorithmException, UnsupportedEncodingException, CryptoException {

		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);

		SecretKey sk = new SecretKeySpec("0123456789abcdef0123456789abcdef".getBytes(), "AES");

		String str = new String(sk.getEncoded(), "UTF-8");
		System.out.println(str);

		File inputFile = new File("D:\\Desert.jpg");
		File encryptedFile = new File("D:\\encyptDesert.jpg");
		File decryptedFile = new File("D:\\decyptDesert.jpg");

		fileWrite(inputFile, encryptedFile, true);

		fileWrite(encryptedFile, decryptedFile, false);
	}

	private static void fileWrite(File inputFile, File outputFile, boolean b)
			throws CryptoException, DataLengthException, InvalidCipherTextException {
		try {

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] encr;

			if (b)
				encr = AESBouncyCastle.encrypt(inputBytes);
			else
				encr = AESBouncyCastle.decrypt(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(encr);

			inputStream.close();
			outputStream.close();

		} catch (IOException ex) {
			throw new CryptoException("Error encrypting/decrypting file", ex);
		}
	}

}
