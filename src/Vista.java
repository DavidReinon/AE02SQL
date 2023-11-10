import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
//Prueba confirmación
	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 529);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 30, 630, 281);
		table.setBackground(Color.BLACK);
		contentPane.add(table);
		
		JLabel lblMenuDeInteracciones = new JLabel("Menu de Interacciones:");
		lblMenuDeInteracciones.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenuDeInteracciones.setBounds(10, 338, 179, 14);
		contentPane.add(lblMenuDeInteracciones);
		
		JButton btnTancarBaseDeDades = new JButton("Tancar Base De Dades");
		btnTancarBaseDeDades.setBackground(Color.WHITE);
		btnTancarBaseDeDades.setBounds(474, 456, 166, 23);
		contentPane.add(btnTancarBaseDeDades);
		
		JButton btnTancarSessio = new JButton("Tancar Sessió");
		btnTancarSessio.setBackground(Color.WHITE);
		btnTancarSessio.setBounds(345, 456, 119, 23);
		contentPane.add(btnTancarSessio);
		
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.setBackground(Color.WHITE);
		btnExecutar.setBounds(10, 456, 89, 23);
		contentPane.add(btnExecutar);
		
		JLabel lblConsultaSql = new JLabel("Consulta SQL:");
		lblConsultaSql.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConsultaSql.setBackground(UIManager.getColor("Button.background"));
		lblConsultaSql.setBounds(10, 396, 89, 14);
		contentPane.add(lblConsultaSql);
		
		JLabel lblAe = new JLabel("Gestió Consultes SQL:");
		lblAe.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAe.setBounds(243, 5, 172, 14);
		contentPane.add(lblAe);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 380, 466, 54);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		setVisible(true);
	}
}