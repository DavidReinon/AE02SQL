package clases;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Gestiona tots eles metodes relacionats amb el encriptat de contrasenyes
 * 
 * @author David Reinón
 * @author Alejandro Tos
 */
public class EncriptadorContrasenya {

	/**
	 * Metode per encriptar una cadena usant MD5
	 * 
	 * @param contrasenya sense encriptar
	 * @return
	 */
	public String encryptToMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			StringBuilder hexString = new StringBuilder();

			for (byte b : messageDigest) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
