package com.tarea2.ingsoft.service.factory;

import com.tarea2.ingsoft.model.Cotizacion;
import com.tarea2.ingsoft.model.DetalleCotizacion;
import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CotizacionFactory {
    

    public Cotizacion crearCotizacionVacia() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(BigDecimal.ZERO);
        cotizacion.setEstado("PENDIENTE");
        cotizacion.setConfirmada(false);
        return cotizacion;
    }
    

    public Cotizacion crearCotizacionSimple(Mueble mueble, Variante variante, 
                                            Integer cantidad, BigDecimal precioUnitario) {
        Cotizacion cotizacion = crearCotizacionVacia();
        
        DetalleCotizacion detalle = new DetalleCotizacion(mueble, variante, cantidad, precioUnitario);
        detalle.calcularSubtotal();
        
        cotizacion.agregarDetalle(detalle);
        cotizacion.calcularTotal();
        
        return cotizacion;
    }
    

    public DetalleCotizacion crearDetalleCotizacion(Mueble mueble, Variante variante, 
                                                     Integer cantidad, BigDecimal precioUnitario) {
        DetalleCotizacion detalle = new DetalleCotizacion(mueble, variante, cantidad, precioUnitario);
        detalle.calcularSubtotal();
        return detalle;
    }
    

    public Cotizacion confirmarCotizacion(Cotizacion cotizacion) {
        cotizacion.setConfirmada(true);
        cotizacion.setEstado("CONFIRMADA");
        cotizacion.setFechaConfirmacion(java.time.LocalDateTime.now());
        return cotizacion;
    }
}