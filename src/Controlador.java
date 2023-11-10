import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.lang.model.element.NestingKind;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mysql.cj.xdevapi.Statement;

public class Controlador {

	private Model Model;
	private Vista Vista;
	private IniciSesio IniciSesio;
	private ActionListener actionListenerBtnInici;
	private ActionListener actionListenerExecutarConsulta;

	Controlador(IniciSesio IniciSesio, Model Model) {
		this.IniciSesio = IniciSesio;
		this.Model = Model;
		Control();
	}

	public void Control() {

		Model.openConexion("client");
		actionListenerBtnInici = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomString = IniciSesio.getNom().getText();
				String contrasenyaString = IniciSesio.getContrasenya().getText();
				if (nomString.isEmpty() || contrasenyaString.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Els apartats no poden estar buits.", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else if (!Model.comprobarUserExisteix(nomString)) {
					JOptionPane.showMessageDialog(null, "L'usuari especificat no existeix.", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else if (!Model.comprobarContrasenya(nomString, contrasenyaString)) {
					JOptionPane.showMessageDialog(null, "La contrasenya no es correcta.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Comprobacions correctes
					if (nomString.equals("administrador1")) {
						Model.closeConexion();
						Model.openConexion("admin");
					}
					IniciSesio.dispose();
					Vista = new Vista();
				}
			}
		};
		IniciSesio.getIniciSessi().addActionListener(actionListenerBtnInici);

		actionListenerExecutarConsulta = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String consultaString = Vista.getConsulta().getText();
				ArrayList<ArrayList<Object>> resultatConsulta = Model.ejecutarConsulta(consultaString);
				if (resultatConsulta != null) {
					//Select
					
				} else {
					//Update, Insert o Delete
				}
			};
		};
	}
}
