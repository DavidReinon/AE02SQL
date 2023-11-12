import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Interfaz que gestiona el programa.
 * 
 * @author David Reinón
 * @author Alejandro Tos
 */
public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnExecutar;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextArea textAreaConsulta;
	private JButton btnTancarSessio;
	private JButton btnTancarConexioBD;

	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 529);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableModel = new DefaultTableModel();

		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setBounds(10, 30, 630, 266);
		contentPane.add(scrollPaneTable);

		table = new JTable(tableModel);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		scrollPaneTable.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);

		JLabel lblMenuDeInteraccions = new JLabel("Menu de Interaccions:");
		lblMenuDeInteraccions.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenuDeInteraccions.setBounds(10, 338, 215, 14);
		contentPane.add(lblMenuDeInteraccions);

		btnTancarConexioBD = new JButton("Tancar Conexio BD");
		btnTancarConexioBD.setBackground(Color.WHITE);
		btnTancarConexioBD.setBounds(474, 456, 166, 23);
		contentPane.add(btnTancarConexioBD);

		btnTancarSessio = new JButton("Tancar Sessió");
		btnTancarSessio.setBackground(Color.WHITE);
		btnTancarSessio.setBounds(345, 456, 119, 23);
		contentPane.add(btnTancarSessio);

		btnExecutar = new JButton("Executar");
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

		JScrollPane scrollPaneConsulta = new JScrollPane();
		scrollPaneConsulta.setBounds(109, 380, 466, 54);
		contentPane.add(scrollPaneConsulta);

		textAreaConsulta = new JTextArea();
		textAreaConsulta.setLineWrap(true);
		scrollPaneConsulta.setViewportView(textAreaConsulta);

		JLabel lblBDbooks = new JLabel("BD books");
		lblBDbooks.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBDbooks.setBounds(10, 313, 144, 14);
		contentPane.add(lblBDbooks);
		setVisible(false);
	}

	/**
	 * Vacia la informció que puga haber en els elements de la interfaz.
	 */
	public void vaciarCamps() {
		textAreaConsulta.setText("");
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
	}

	/**
	 * Carrega la informacio en la taula de la interfaz.
	 * 
	 * @param arrayList amb la informació
	 */
	public void carregarSelectEnTaula(ArrayList<Object> arrayList) {
		if (arrayList != null && !arrayList.isEmpty()) {
			tableModel.setColumnCount(0);
			tableModel.setRowCount(0);

			Object[] columnas = (Object[]) arrayList.get(0);
			tableModel.setColumnIdentifiers(columnas);

			for (int i = 1; i < arrayList.size(); i++) {
				Object[] fila = (Object[]) arrayList.get(i);
				tableModel.addRow(fila);
			}
		}
	}

	public JTextArea getConsulta() {
		return textAreaConsulta;
	}

	public JButton getBtnExecutar() {
		return btnExecutar;
	}

	public JButton getBtnTancarSessio() {
		return btnTancarSessio;
	}

	public JButton getBtnTancarConexioBD() {
		return btnTancarConexioBD;
	}
}