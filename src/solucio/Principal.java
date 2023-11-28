package solucio;

public class Principal {

	/**
	 * Clase principal ejecutable.
	 * @param args No se necesitan argumentos de entrada.
	 */
	public static void main(String[] args) {
		
		Vista vista = new Vista();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador(modelo,vista);
		
	}

}
