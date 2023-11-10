import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.lang.model.element.NestingKind;
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
		con = Model.openConexion();
		actionListenerBtnInici = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (IniciSesio.getNom().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "L'apartat nom esta buit.", "Error", JOptionPane.ERROR_MESSAGE);
					
				} else if (IniciSesio.getContrasenya().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "L'apartat contrasenya esta buit.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String nomString = IniciSesio.getNom().getText();
					if (!Model.comprobarUserExisteix(nomString, con)) {
						JOptionPane.showMessageDialog(null, "L'usuari especificat no existeix.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "L'apartat nom no es troba.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		IniciSesio.getIniciSessi().addActionListener(actionListenerBtnInici);
	}

}
