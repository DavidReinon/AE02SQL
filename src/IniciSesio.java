import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class IniciSesio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldContrasenya;
	private JButton btnIniciSessi;

	/**
	 * Create the frame.
	 */
	public IniciSesio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(117, 11, 251, 221);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblIniciSessio = new JLabel("Inici de sessió");
		lblIniciSessio.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIniciSessio.setBounds(78, 24, 101, 14);
		panel.add(lblIniciSessio);
		
		JLabel lblNom = new JLabel("Nom:");
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNom.setBounds(10, 70, 46, 14);
		panel.add(lblNom);
		
		JLabel lblContrasenya = new JLabel("Contrsenya:");
		lblContrasenya.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasenya.setBounds(10, 119, 93, 14);
		panel.add(lblContrasenya);
		
		textFieldNom = new JTextField();
		textFieldNom.setBounds(66, 69, 175, 20);
		panel.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		textFieldContrasenya = new JTextField();
		textFieldContrasenya.setColumns(10);
		textFieldContrasenya.setBounds(111, 118, 130, 20);
		panel.add(textFieldContrasenya);
		
		btnIniciSessi = new JButton("Inici Sessió");
		btnIniciSessi.setBackground(Color.WHITE);
		btnIniciSessi.setBounds(78, 179, 101, 23);
		panel.add(btnIniciSessi);
		
		setVisible(true);
	}
	public JButton getIniciSessi() {
		return btnIniciSessi;
	}
	
	public JTextField getNom() {
		return textFieldNom;
	}
	
	public JTextField getContrasenya() {
		return textFieldContrasenya;
	}
}
