package com.tarea2.ingsoft.service.strategy;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PrecioConDescuentoStrategy implements PrecioStrategy {
    
    private BigDecimal porcentajeDescuento;
    
    public PrecioConDescuentoStrategy() {
        this.porcentajeDescuento = new BigDecimal("0.10"); // 10% descuento 
    }
    
    @Override
    public BigDecimal calcularPrecio(Mueble mueble, Variante variante) {
        BigDecimal precioBase = mueble.getPrecioBase();
        
        if (variante != null && !"Normal".equalsIgnoreCase(variante.getNombreVariante())) {
            precioBase = precioBase.add(variante.getPrecioAdicional());
        }
        
        // Aplica descuento
        BigDecimal descuento = precioBase.multiply(porcentajeDescuento);
        return precioBase.subtract(descuento);
    }
    
    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}