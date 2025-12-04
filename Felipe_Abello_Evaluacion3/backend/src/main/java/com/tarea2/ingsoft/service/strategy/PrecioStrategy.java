package com.tarea2.ingsoft.service.strategy;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import java.math.BigDecimal;

public interface PrecioStrategy {
    BigDecimal calcularPrecio(Mueble mueble, Variante variante);
}