
public class Principal {

	public static void main(String[] args) {
		Model Model = new Model();
		//Vista Vista = new Vista();
		IniciSesio IniciSesio = new IniciSesio();
		Controlador Controlador = new Controlador(IniciSesio, Model);

	}

}
