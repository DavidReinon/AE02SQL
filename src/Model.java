import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import clases.EncriptadorContrasenya;
import clases.UserConexioBD;

/**
 * Gestiona tots el metodes relacionats amb la gestio de BD
 * 
 * @author David Reinón
 * @author Alejandro Tos
 */
public class Model {

	private Connection con;
	private String tipusConexio;

	public String getTipusConexio() {
		return tipusConexio;
	}

	/**
	 * Obri la conexio a la BD
	 * 
	 * @param tipus de usuario (admin o client)
	 */
	public void openConexion(String tipusUser) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			UserConexioBD userConexioBD = credencialsConexioBD(tipusUser);
			// System.out.println(userConexioBD.toString());
			con = DriverManager.getConnection(userConexioBD.getUrl(), userConexioBD.getUsuari(),
					userConexioBD.getContrasenya());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tanca la BD
	 */
	public void closeConexion() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Extrau la informacio de un respectiu XML per a conectarse a la BD.
	 * 
	 * @param tipusUser tipus de usuari (admin o client)
	 * @return clase amb la informacio necesaria
	 */
	private UserConexioBD credencialsConexioBD(String tipusUser) {
		UserConexioBD userConexioBD = new UserConexioBD();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			String rutaDirectoriActual = System.getProperty("user.dir");
			if (tipusUser.equals("client")) {
				rutaDirectoriActual += "\\client.xml";
				tipusConexio = "client";
			} else if (tipusUser.equals("admin")) {
				rutaDirectoriActual += "\\admin.xml";
				tipusConexio = "admin";
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

	/**
	 * Comproba si l'usuari existeix en la BD
	 * 
	 * @param nom
	 * @return true si exiteix, false si no.
	 */
	public boolean comprobarUserExisteix(String nom) {
		boolean usuarioExiste = false;
		try {
			String nomString = nom;

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

	/**
	 * Comproba que la contrasenya del usuari siga correcta.
	 * 
	 * @param usuari
	 * @param contrasenya
	 * @return true si es correcta, de lo contrari false.
	 */
	public boolean comprobarContrasenya(String usuari, String contrasenya) {
		boolean contrasenyaCorrecta = false;
		try {
			String query = "SELECT pass FROM users WHERE user = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, usuari);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String contrasenaAlmacenadaEncriptada = rs.getString("pass");
				String contrasenaUsuarioEncriptada = new EncriptadorContrasenya().encryptToMD5(contrasenya);

				if (contrasenaAlmacenadaEncriptada.equals(contrasenaUsuarioEncriptada)) {
					contrasenyaCorrecta = true;
				}
			}

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contrasenyaCorrecta;
	}

	/**
	 * Executa una consulta select y torna la informacio incluint el nom de les
	 * columnes
	 * 
	 * @param consultaString Consulta proprocionada per l'usuarí
	 * @return array de objectes con tota la informacio
	 */
	public ArrayList<Object> executarConsultaSelect(String consulta) {
		ArrayList<Object> resultat = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			if (consulta.trim().toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(consulta);

				ResultSetMetaData metaData = rs.getMetaData();
				int numColumnas = metaData.getColumnCount();

				Object[] columnesObjecte = new Object[numColumnas];
				for (int i = 1; i <= numColumnas; i++) {
					columnesObjecte[i - 1] = metaData.getColumnName(i);

				}
				resultat.add(columnesObjecte);

				while (rs.next()) {
					Object[] rowObjecte = new Object[numColumnas];
					for (int i = 1; i <= numColumnas; i++) {
						rowObjecte[i - 1] = rs.getString(i);
					}
					resultat.add(rowObjecte);
				}
			} else {
				resultat = null;
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return resultat;
	}

	/**
	 * Execute consulta insert, update o delete
	 * 
	 * @param consultaString Consulta de l'usuari
	 * @return true si s'executa correctament, false si no.
	 */
	public boolean executarOtraConsulta(String consulta) {
		boolean consultaCorrecta = true;
		try {
			Statement stmt = con.createStatement();

			if (!consulta.trim().toLowerCase().startsWith("select")) {
				int filasAfectadas = stmt.executeUpdate(consulta);

				if (filasAfectadas == 0)
					consultaCorrecta = false;
			} else
				consultaCorrecta = false;

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return consultaCorrecta;
	}

}
