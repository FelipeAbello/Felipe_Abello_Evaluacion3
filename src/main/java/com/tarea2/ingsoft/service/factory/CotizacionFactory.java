package com.tarea2.ingsoft.service.factory;

import com.tarea2.ingsoft.model.Cotizacion;
import com.tarea2.ingsoft.model.DetalleCotizacion;
import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CotizacionFactory {
    
    /**
     * Crea una nueva cotización vacía con estado PENDIENTE
     */
    public Cotizacion crearCotizacionVacia() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(BigDecimal.ZERO);
        cotizacion.setEstado("PENDIENTE");
        cotizacion.setConfirmada(false);
        return cotizacion;
    }
    
    /**
     * Crea una cotización con un mueble y su variante
     */
    public Cotizacion crearCotizacionSimple(Mueble mueble, Variante variante, 
                                            Integer cantidad, BigDecimal precioUnitario) {
        Cotizacion cotizacion = crearCotizacionVacia();
        
        DetalleCotizacion detalle = new DetalleCotizacion(mueble, variante, cantidad, precioUnitario);
        detalle.calcularSubtotal();
        
        cotizacion.agregarDetalle(detalle);
        cotizacion.calcularTotal();
        
        return cotizacion;
    }
    
    /**
     * Crea un detalle de cotización
     */
    public DetalleCotizacion crearDetalleCotizacion(Mueble mueble, Variante variante, 
                                                     Integer cantidad, BigDecimal precioUnitario) {
        DetalleCotizacion detalle = new DetalleCotizacion(mueble, variante, cantidad, precioUnitario);
        detalle.calcularSubtotal();
        return detalle;
    }
    
    /**
     * Convierte una cotización en venta (la confirma)
     */
    public Cotizacion confirmarCotizacion(Cotizacion cotizacion) {
        cotizacion.setConfirmada(true);
        cotizacion.setEstado("CONFIRMADA");
        cotizacion.setFechaConfirmacion(java.time.LocalDateTime.now());
        return cotizacion;
    }
}