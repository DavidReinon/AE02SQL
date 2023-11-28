package solucio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Modelo {

	String ficheroXMLadmin = "config_admin.xml";
	String ficheroXMLclient = "config_client.xml";
	Connection conexion;
	boolean usuarioAutorizado = false;

	/**
	 * Método que devuelve el contenido del fichero XML de configuración.
	 * @return Devuelve un String con el contenido del fichero XML de configuración.
	 */
	public String contenidoXML(String ficheroXML) {
		String contenidoXML = "";
		File f = new File(ficheroXML);
		FileReader fr;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				contenidoXML = contenidoXML + linea + "\n";
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return contenidoXML;
	}

	/**
	 * Método que realiza la conexión a la base de datos a partir de la información del fichero XML de configuración.
	 * @return Devuelve un booleano a true indicando si la conexión se ha realizado correctamente o false en caso contrario.
	 */
	public boolean conexion(String ficheroXML) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File(ficheroXML));
			NodeList nodeList = document.getElementsByTagName("config1");
			Node node = nodeList.item(0);
			Element eElement = (Element) node;
			String url = eElement.getElementsByTagName("url").item(0).getTextContent();
			String user = eElement.getElementsByTagName("user").item(0).getTextContent();
			String password = eElement.getElementsByTagName("password").item(0).getTextContent();
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			JOptionPane.showMessageDialog(null, "Connection established", "INFO", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (ClassNotFoundException | SQLException | ParserConfigurationException | SAXException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	/**
	 * Método para realizar el login de un usuario en la aplicación y poder realizar operaciones sobre la base de datos.
	 * @param usuario String con el nombre del usuario.
	 * @param charPass Array de caracteres con el password.
	 * @return Devuelve un booleano a true si se ha accedido correctamente o a false en caso contrario.
	 */
	public boolean login(String usuario, char[] charPass) {
		try {
			String strPass = new String(charPass);
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(strPass.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				sb.append(String.format("%02x", b));
			}
			String strPassMD5 = sb.toString();
			Statement stmt = conexion.createStatement();
			String query = "SELECT * FROM users WHERE user LIKE '" + usuario + "' AND pass LIKE '" + strPassMD5 + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				usuarioAutorizado = true;
				String tipoUsuario = rs.getString(4);
				if (tipoUsuario.equals("admin")) { //El usuario es de tipo admin -> reconecta como admin a la BDD
					desconexion();
					conexion(ficheroXMLadmin);
				} else if (tipoUsuario.equals("client")) { //El usuario es de tipo client -> reconecta como client a la BDD
					desconexion();
					conexion(ficheroXMLclient);
				}
				JOptionPane.showMessageDialog(null, "Login successful (logged as " + tipoUsuario + ")", "INFO", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect user/password", "INFO", JOptionPane.INFORMATION_MESSAGE);
			}
			rs.close();
			stmt.close();
		} catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException | NullPointerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return usuarioAutorizado;
	}


	/**
	 * Método que recibe por parámetro un String con una consulta SQL de cualquier tipo y la ejecuta
	 * @param sql String con la consulta SQL
	 * @return Devuelve un string con el resultado de la consulta si es de tipo SELECT o vacio si es de otro tipo
	 */
	public String consultaSQL(String sql) {
		if (usuarioAutorizado) {
			String resultadoSQL = "";
			try {
				if (sql.contains("SELECT")) {
					resultadoSQL = "<table border=\"1\"><tr>";
					Statement stmt = conexion.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int numCampos = rsmd.getColumnCount();
					while (rs.next()) {
						resultadoSQL = resultadoSQL + "<tr>";
						for (int i = 1; i <= numCampos; i++) {
							resultadoSQL = resultadoSQL + "<td>" + rs.getString(i) + "</td>";
						}
						resultadoSQL = resultadoSQL + "</tr>";
					}
					resultadoSQL = resultadoSQL + "</table>";
					rs.close();
					stmt.close();
				} else {
					int opcion = JOptionPane.showConfirmDialog(null, "CONFIRM SQL? >>> " + sql);
					if (opcion == 0) {
						PreparedStatement ps = conexion.prepareStatement(sql);
						int resultado = ps.executeUpdate();
						if (resultado >= 0) {
							JOptionPane.showMessageDialog(null, "SQL EXECUTED CORRECTLY: " + ps.toString(), "INFO",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "ERROR IN SQL EXECUTION", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			return resultadoSQL;
		} else {
			JOptionPane.showMessageDialog(null, "USER NOT LOGGED IN", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Método para cerrar la sesión del usuario activo en la aplicación (no cierra la conexión con la BDD).
	 */
	public void logout() {
		usuarioAutorizado = false;
	}
	
	/**
	 * Método para realizar la desconexión de la base de datos.
	 * @return Devuelve un booleano a true si la desconexión se realiza correctamente o a false en caso contrario.
	 */
	public boolean desconexion() {
		try {
			conexion.close();
			JOptionPane.showMessageDialog(null, "Connection released", "INFO", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR IN SQL EXECUTION", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

}
