package com.tarea2.ingsoft.service.strategy;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PrecioNormalStrategy implements PrecioStrategy {
    
    @Override
    public BigDecimal calcularPrecio(Mueble mueble, Variante variante) {
        // Si la variante es "Normal" o null, devuelve solo el precio base
        if (variante == null || "Normal".equalsIgnoreCase(variante.getNombreVariante())) {
            return mueble.getPrecioBase();
        }
        // Si tiene variante, suma el precio adicional
        return mueble.getPrecioBase().add(variante.getPrecioAdicional());
    }
}