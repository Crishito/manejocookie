package service;

/*
 *Autor: Christian Zumárraga
 * Fecha: 7/11/2025
 * Descripción: Esta clase Producto va a instanciar todos nuestros modelos y atributos
 */

import models.Producto;

import java.util.Arrays;
import java.util.List;

public class ProductoServiceImplement implements ProductoService {

    /* Sobreescribimos el método */
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop", "Computación", 250.25),
                new Producto(2L, "Refrigeradora", "Cocina", 745.13),
                new Producto(3L, "Cama", "Dormitorio", 350.12)
        );
    }
}
