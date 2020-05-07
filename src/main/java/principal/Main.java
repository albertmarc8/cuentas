package principal;

import controlador.Controlador;
import controlador.ControladorCuentas;
import modelo.Modelo;
import modelo.ModeloCuentas;
import vista.Vista;
import vista.VistaCuentas;

public class Main {
    public static void main(String[] args) {
        Vista vista = new VistaCuentas();
        Modelo modelo = new ModeloCuentas();
        Controlador controlador = new ControladorCuentas();

        vista.setControlador(controlador);
        vista.setModelo(modelo);

        modelo.setVista(vista);

        controlador.setModelo(modelo);
        controlador.setVista(vista);

        vista.crearGUI();
    }
}
