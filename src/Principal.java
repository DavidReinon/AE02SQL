
public class Principal {

	public static void main(String[] args) {
		Model Model = new Model();
		IniciSesio IniciSesio = new IniciSesio();
		//Vista es obri desde Cotrolador
		Controlador Controlador = new Controlador(IniciSesio, Model);

	}

}