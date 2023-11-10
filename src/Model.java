import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
	public Connection openConexion() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			UserConexioBD userConexioBD = credencialsConexioBD("client");
			System.out.println(userConexioBD.toString());
			con = DriverManager.getConnection(userConexioBD.getUrl(), userConexioBD.getUsuari(),
					userConexioBD.getContrasenya());
			return con;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return con;
	}

	private UserConexioBD credencialsConexioBD(String tipusUser) {
		UserConexioBD userConexioBD = new UserConexioBD();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			String rutaDirectoriActual = System.getProperty("user.dir");
			if (tipusUser.equals("client")) {
				rutaDirectoriActual += "\\client.xml";
			} else if (tipusUser.equals("admin")) {
				rutaDirectoriActual += "\\admin.xml";
			}

			Document document = dBuilder.parse(new File(rutaDirectoriActual));
			// Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("credentials");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i); // Lista de un item (Por eso dentro del bucle)
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node; // Elemento abstracto que hay que crear para llamar las
														// propiedades

					String url = eElement.getElementsByTagName("url").item(0).getTextContent();
					String usuari = eElement.getElementsByTagName("usuari").item(0).getTextContent();
					String contrasenya = eElement.getElementsByTagName("contrasenya").item(0).getTextContent();
					userConexioBD = new UserConexioBD(url, usuari, contrasenya);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userConexioBD;
	}

	public boolean comprobarUserExisteix(String nom, Connection con) {
		boolean usuarioExiste = false;
		try {
			String nomString = nom;

			// Realiza la consulta preparada para evitar la inyección de SQL
			String query = "SELECT user FROM users WHERE user = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, nomString);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				usuarioExiste = true;
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("No es va poder realitzar el select del user");
		}
		return usuarioExiste;
	}

	public boolean comprobarContrasenyaCorrecta(String nom, Connection con) {
		boolean correcta = false;
		try {
			String nomString = nom;

			// Realiza la consulta preparada para evitar la inyección de SQL
			String query = "SELECT user FROM users WHERE user = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, nomString);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//usuarioExiste = true;
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("No es va poder realitzar el select del user");
		}
		return true;
	}
}
