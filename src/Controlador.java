import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Gestiona els events de les interfazes.
 * 
 * @author David Reinón
 * @author Alejandro Tos
 */

public class Controlador {

	private Model Model;
	private Vista Vista = new Vista();
	private IniciSesio IniciSesio;
	private ActionListener actionListenerBtnInici;
	private ActionListener actionListenerExecutarConsulta;
	private ActionListener actionListenerTancarSessio;
	private ActionListener actionListenerTancarConexioBD;

	Controlador(IniciSesio IniciSesio, Model Model) {
		this.IniciSesio = IniciSesio;
		this.Model = Model;
		Control();
	}

	public void Control() {

		actionListenerBtnInici = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Model.openConexion("client");
				String nomString = IniciSesio.getNom().getText();

				char[] password = IniciSesio.getContrasenya().getPassword();
				String contrasenyaString = new String(password);

				if (nomString.isEmpty() || contrasenyaString.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Els apartats no poden estar buits.", "Error",
							JOptionPane.ERROR_MESSAGE);
					Model.closeConexion();

				} else if (!Model.comprobarUserExisteix(nomString)) {
					JOptionPane.showMessageDialog(null, "L'usuari especificat no existeix.", "Error",
							JOptionPane.ERROR_MESSAGE);
					Model.closeConexion();

				} else if (!Model.comprobarContrasenya(nomString, contrasenyaString)) {
					JOptionPane.showMessageDialog(null, "La contrasenya no es correcta.", "Error",
							JOptionPane.ERROR_MESSAGE);
					Model.closeConexion();
				} else {
					// Comprobacions correctes
					if (nomString.equals("administrador1")) {
						Model.closeConexion();
						Model.openConexion("admin");
					}
					IniciSesio.setVisible(false);
					IniciSesio.vaciarCamps();
					Vista.setVisible(true);
				}
			}
		};
		IniciSesio.getIniciSessi().addActionListener(actionListenerBtnInici);

		actionListenerExecutarConsulta = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String consultaString = Vista.getConsulta().getText();

				if (consultaString.trim().toLowerCase().startsWith("select")) {
					ArrayList<Object> resultatConsulta = Model.executarConsultaSelect(consultaString);
					if (resultatConsulta == null) {
						JOptionPane.showMessageDialog(null,
								"La consulta dona error y no se ha pogut executar correctament.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Vista.carregarSelectEnTaula(resultatConsulta);
					JOptionPane.showMessageDialog(null, "La consulta se ha executat correctament.", "Aceptada",
							JOptionPane.INFORMATION_MESSAGE);
					return;

				} else if (consultaString.trim().toLowerCase().startsWith("insert")
						|| consultaString.trim().toLowerCase().startsWith("delete")
						|| consultaString.trim().toLowerCase().startsWith("update")) {
					if (Model.getTipusConexio().equals("client")) {
						JOptionPane.showMessageDialog(null, "No tens permisos per a executar este tipus de consultes.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Insert, delete o update
					int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás segur de executar esta consulta?",
							"Confirmación", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.NO_OPTION || confirmacion == JOptionPane.CLOSED_OPTION) {
						return;
					}

					boolean resultatConsulta = Model.executarOtraConsulta(consultaString);
					if (!resultatConsulta) {
						JOptionPane.showMessageDialog(null,
								"La consulta dona error y no se ha pogut executar correctament.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null, "La consulta se ha executat correctament.", "Aceptada",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(null, "No se ha reconegut ninguna consulta.", "Error",
						JOptionPane.ERROR_MESSAGE);
			};
		};
		Vista.getBtnExecutar().addActionListener(actionListenerExecutarConsulta);

		actionListenerTancarSessio = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás segur de que vols tancar la sessio?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.NO_OPTION || confirmacion == JOptionPane.CLOSED_OPTION) {
					return;
				}
				Model.closeConexion();
				Vista.setVisible(false);
				Vista.vaciarCamps();
				IniciSesio.setVisible(true);
			};
		};
		Vista.getBtnTancarSessio().addActionListener(actionListenerTancarSessio);

		actionListenerTancarConexioBD = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null,
						"¿Estás segur de que vols tancar la conexio amb la BD?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.NO_OPTION || confirmacion == JOptionPane.CLOSED_OPTION) {
					return;
				}
				Model.closeConexion();
				IniciSesio.dispose();
				Vista.dispose();
			};
		};
		Vista.getBtnTancarConexioBD().addActionListener(actionListenerTancarConexioBD);
	}

}
