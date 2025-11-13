package service;

import models.Producto;
import java.util.List;

/*
 *Autor: Christian Zumárraga
 * Fecha: 7/11/2025
 * Descripción: Esta Interface, es una plantilla para luego implementar un objeto y modificar el desarrollador
 */
public interface ProductoService {

    /* Método que devuelve lista de productos */
    List<Producto> listar();
}