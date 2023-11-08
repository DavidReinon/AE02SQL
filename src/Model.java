import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
	public UserConexioBD credencialsConexioBD(String tipusUser) {
		UserConexioBD userConexioBD = new UserConexioBD();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			String rutaDirectoriActual = System.getProperty("user.dir");

			Document document = dBuilder.parse(new File(rutaDirectoriActual));
			// Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("credentials");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i); // Lista de un item (Por eso dentro del bucle)
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node; // Elemento abstracto que hay que crear para llamar las propiedades
														
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
}
