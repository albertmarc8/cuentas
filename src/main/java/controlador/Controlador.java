package controlador;

import modelo.Modelo;
import vista.Vista;

public interface Controlador {
    void setModelo(Modelo modelo);
    void setVista(Vista vista);
    void insertarPago();
}
