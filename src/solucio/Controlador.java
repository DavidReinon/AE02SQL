package solucio;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListener_conectar, actionListener_login, actionListener_logout, actionListener_ejecutaSQL, actionListener_desconectar;

	/**
	 * Método constructor de la clase Controlador, recibe instancias de las clases Modelo y Vista
	 * @param modelo
	 * @param vista
	 */
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		control();
	}

	/**
	 * Método control: inicializa y gestiona todo las acciones asociadas a los eventos de la Vista, comunicandose con el Modelo
	 */
	public void control() {

		actionListener_conectar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				boolean respuesta = modelo.conexion(modelo.ficheroXMLclient);
				if (respuesta) {
					vista.btn_conectar.setBackground(Color.GREEN);
				}
			}
		};
		vista.btn_conectar.addActionListener(actionListener_conectar);

		actionListener_login = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				boolean respuesta = modelo.login(vista.textField_usuario.getText(),	vista.passwordField_pass.getPassword());
				if (respuesta) {
					vista.btn_login.setBackground(Color.GREEN);
				}
			}
		};
		vista.btn_login.addActionListener(actionListener_login);


		actionListener_ejecutaSQL = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String sql = vista.textField_SQL.getText();
				String resultadoSQL = modelo.consultaSQL(sql);
				vista.editorPane_resultadoSQL.setText(resultadoSQL);
			}
		};
		vista.btn_ejecutaSQL.addActionListener(actionListener_ejecutaSQL);

		
		actionListener_logout = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int opcion = JOptionPane.showConfirmDialog(null, "CONFIRM LOGOUT?");
				if (opcion == 0) {
					modelo.logout();
					vista.btn_login.setBackground(null);
					vista.textField_usuario.setText(null);
					vista.passwordField_pass.setText(null);
					vista.textField_SQL.setText(null);
					vista.editorPane_resultadoSQL.setText("");
				}
			}
		};
		vista.btn_logout.addActionListener(actionListener_logout);
		
		actionListener_desconectar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int opcion = JOptionPane.showConfirmDialog(null, "CONFIRM DISCONNECTION?");
				if (opcion == 0) {
					boolean respuesta = modelo.desconexion();
					if (respuesta) {
						vista.btn_conectar.setBackground(null);
						vista.btn_login.setBackground(null);
						vista.textField_usuario.setText(null);
						vista.passwordField_pass.setText(null);
						vista.textField_SQL.setText(null);
						vista.editorPane_resultadoSQL.setText("");
					}
				}
			}
		};
		vista.btn_desconectar.addActionListener(actionListener_desconectar);

	}

}
