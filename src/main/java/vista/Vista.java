package vista;

import controlador.Controlador;
import modelo.Modelo;

public interface Vista {
    void datosCambiados();

    void setControlador(Controlador controlador);
    void setModelo(Modelo modelo);

    void crearGUI();

    String getNombre();
    String getConcepto();
    String getCantidad();

    void setConcepto(String s);
    void setCantidad(String s);

    void mensajeError(String mensaje);

    void focoEnNombre();

    void ponerMensaje(String mensaje);
}
