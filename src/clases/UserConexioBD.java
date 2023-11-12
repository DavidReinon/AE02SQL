package clases;

/**
 * Clase per guardar la informacio per conectarse a una BD
 * 
 * @author David Reinón
 * @author Alejandro Tos
 */
public class UserConexioBD {
	private String url;
	private String usuari;
	private String contrasenya;

	public String getUrl() {
		return url;
	}

	public String getUsuari() {
		return usuari;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setUrl(String ur) {
		url = ur;
	}

	public void setUsuari(String usu) {
		usuari = usu;
	}

	public void setContrasenya(String contra) {
		contrasenya = contra;
	}

	public UserConexioBD(String ur, String usu, String contra) {
		url = ur;
		usuari = usu;
		contrasenya = contra;
	}

	public UserConexioBD() {

	}

	public String toString() {
		return "Url: " + url + " Usuari: " + usuari + " Contraseña: " + contrasenya;
	}
}
