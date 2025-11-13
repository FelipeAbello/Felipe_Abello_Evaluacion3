package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.service.strategy.PrecioStrategy;
import com.tarea2.ingsoft.service.strategy.PrecioNormalStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrecioService {
    
    @Autowired
    private PrecioNormalStrategy precioNormalStrategy;
    
    private PrecioStrategy estrategiaActual;
    
    public PrecioService() {
        // Por defecto usa la estrategia normal
    }
    
    @Autowired
    public void init(PrecioNormalStrategy precioNormalStrategy) {
        this.precioNormalStrategy = precioNormalStrategy;
        this.estrategiaActual = precioNormalStrategy;
    }
    
    /**
     * Cambia la estrategia de cálculo de precio
     */
    public void setEstrategia(PrecioStrategy estrategia) {
        this.estrategiaActual = estrategia;
    }
    
    /**
     * Calcula el precio usando la estrategia actual
     */
    public BigDecimal calcularPrecioFinal(Mueble mueble, Variante variante) {
        return estrategiaActual.calcularPrecio(mueble, variante);
    }
    
    /**
     * Calcula el precio con la estrategia normal (sin descuentos)
     */
    public BigDecimal calcularPrecioNormal(Mueble mueble, Variante variante) {
        return precioNormalStrategy.calcularPrecio(mueble, variante);
    }
}