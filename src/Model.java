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

public class Model {

	private Connection con = null;

	public void openConexion(String tipusUser) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			UserConexioBD userConexioBD = credencialsConexioBD(tipusUser);
			// System.out.println(userConexioBD.toString());
			con = DriverManager.getConnection(userConexioBD.getUrl(), userConexioBD.getUsuari(),
					userConexioBD.getContrasenya());
		} catch (Exception e) {

		}
	}

	public void closeConexion() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
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

	public boolean comprobarUserExisteix(String nom) {
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

	public boolean comprobarContrasenya(String usuari, String contrasenya) {
		boolean contrasenyaCorrecta = false;
		try {
			// Realiza la consulta para obtener la contraseña encriptada almacenada para el
			// usuario
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

			pstmt.close(); // Cierra la declaración preparada
		} catch (SQLException e) {
			e.printStackTrace(); // Manejo básico de excepciones. Considera manejarlas de forma más apropiada.
		}
		return contrasenyaCorrecta;
	}

	public ArrayList<ArrayList<Object>> ejecutarConsulta(String consulta) {
	    ArrayList<ArrayList<Object>> resultado = new ArrayList<>();

	    try {
	        Statement stmt = con.createStatement();

	        if (consulta.trim().toLowerCase().startsWith("select")) {
	            ResultSet rs = stmt.executeQuery(consulta);
	            
	            ResultSetMetaData metaData = rs.getMetaData();
	            int numColumnas = metaData.getColumnCount();

	            for (int i = 1; i <= numColumnas; i++) {
	                ArrayList<Object> columna = new ArrayList<>();
	                columna.add(metaData.getColumnName(i));
	                resultado.add(columna);
	            }

	            while (rs.next()) {
	                for (int i = 1; i <= numColumnas; i++) {
	                    resultado.get(i - 1).add(rs.getObject(i));
	                }
	            }
	        } else {
	            resultado = null;
	        }

	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return resultado;
	}



}
