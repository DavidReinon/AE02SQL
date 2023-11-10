import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mysql.cj.xdevapi.Statement;

public class Controlador {

	private Model Model;
	private Vista Vista;
	private IniciSesio IniciSesio;
	private Connection con;
	private ActionListener actionListenerBtnInici;
	Controlador(IniciSesio IniciSesio, Model Model) {
		this.IniciSesio = IniciSesio;
		this.Model = Model;
		Control();
	}

	public void Control() {
		actionListenerBtnInici = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(IniciSesio.getNom().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "L'apartat nom esta buit.", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(IniciSesio.getContrasenya().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "L'apartat contrasenya esta buit.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					if(comprobarNombre()) {
						
					}else {
						JOptionPane.showMessageDialog(null, "L'apartat nom no es troba.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		IniciSesio.getIniciSessi().addActionListener(actionListenerBtnInici);
	}
	
	public boolean comprobarNombre() {
		UserConexioBD userConexio = Model.credencialsConexioBD("client");
	        try (Connection con = DriverManager.getConnection(userConexio.getUrl(), userConexio.getUsuari(), userConexio.getContrasenya())) {
	            System.out.println("Conexión realizada con éxito");

	            String query = "SELECT * FROM users WHERE user = ?";
	            try (PreparedStatement pstmt = con.prepareStatement(query)) {
	                pstmt.setString(1, IniciSesio.getNom().getText());

	                try (ResultSet rs = pstmt.executeQuery()) {
	                    return rs.next();  // Devuelve true si hay algún resultado, es decir, si el nombre ya existe
	                }
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();  // Manejo básico de excepciones. Considera manejarlo de una manera más robusta en tu aplicación.
	        }
	        return false;  // En caso de error o si el nombre no existe
	    }
}

