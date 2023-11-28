package solucio;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;

public class Vista {

	JFrame frmMysqlExplorer;
	JTextField textField_usuario, textField_SQL;
	JPasswordField passwordField_pass;
	JButton btn_conectar, btn_login, btn_ejecutaSQL, btn_desconectar, btn_logout;
	JEditorPane editorPane_resultadoSQL;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Vista window = new Vista();
//					window.frmMysqlExplorer.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	/**
	 * Método constructor de la clase Vista que llama a la inicialización de la interfaz gráfica.
	 */
	public Vista() {
		initialize();
		this.frmMysqlExplorer.setVisible(true);
	}


	/**
	 * Método que iniciliza la interfaz gráfica con todos los componentes y sus características.
	 */
	private void initialize() {
		frmMysqlExplorer = new JFrame();
		frmMysqlExplorer.setTitle("MySQL Explorer");
		frmMysqlExplorer.setBounds(100, 100, 914, 431);
		frmMysqlExplorer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMysqlExplorer.getContentPane().setLayout(null);
		
		btn_conectar = new JButton("Connect to DB");
		btn_conectar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_conectar.setBounds(21, 20, 194, 21);
		frmMysqlExplorer.getContentPane().add(btn_conectar);
		
		textField_usuario = new JTextField();
		textField_usuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_usuario.setBounds(62, 68, 129, 21);
		frmMysqlExplorer.getContentPane().add(textField_usuario);
		textField_usuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(21, 72, 45, 13);
		frmMysqlExplorer.getContentPane().add(lblNewLabel);
		
		JLabel lblPass = new JLabel("Pass:");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPass.setBounds(208, 72, 45, 13);
		frmMysqlExplorer.getContentPane().add(lblPass);
		
		btn_login = new JButton("Login");
		btn_login.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_login.setBounds(402, 68, 100, 21);
		frmMysqlExplorer.getContentPane().add(btn_login);
		
		JLabel lblSql = new JLabel("SQL:");
		lblSql.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSql.setBounds(10, 129, 39, 13);
		frmMysqlExplorer.getContentPane().add(lblSql);
		
		textField_SQL = new JTextField();
		textField_SQL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_SQL.setBounds(45, 123, 762, 25);
		frmMysqlExplorer.getContentPane().add(textField_SQL);
		textField_SQL.setColumns(10);
		
		btn_ejecutaSQL = new JButton("Run");
		btn_ejecutaSQL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_ejecutaSQL.setBounds(818, 124, 67, 21);
		frmMysqlExplorer.getContentPane().add(btn_ejecutaSQL);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 172, 877, 215);
		frmMysqlExplorer.getContentPane().add(scrollPane_1);
		
		editorPane_resultadoSQL = new JEditorPane();
		editorPane_resultadoSQL.setContentType("text/html");
		scrollPane_1.setViewportView(editorPane_resultadoSQL);
		editorPane_resultadoSQL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		passwordField_pass = new JPasswordField();
		passwordField_pass.setBounds(250, 68, 129, 19);
		frmMysqlExplorer.getContentPane().add(passwordField_pass);
		
		btn_desconectar = new JButton("Disconnect from DB");
		btn_desconectar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_desconectar.setBounds(225, 20, 194, 21);
		frmMysqlExplorer.getContentPane().add(btn_desconectar);
		
		btn_logout = new JButton("Logout");
		btn_logout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_logout.setBounds(522, 68, 100, 21);
		frmMysqlExplorer.getContentPane().add(btn_logout);
	}
}
