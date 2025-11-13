package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Cotizacion;
import com.tarea2.ingsoft.model.DetalleCotizacion;
import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.repository.CotizacionRepository;
import com.tarea2.ingsoft.repository.DetalleCotizacionRepository;
import com.tarea2.ingsoft.service.factory.CotizacionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {
    
    @Autowired
    private CotizacionRepository cotizacionRepository;
    
    @Autowired
    private DetalleCotizacionRepository detalleCotizacionRepository;
    
    @Autowired
    private CotizacionFactory cotizacionFactory;
    
    @Autowired
    private MuebleService muebleService;
    
    @Autowired
    private PrecioService precioService;
    
    @Autowired
    private VarianteService varianteService;
    
    /**
     * Crear una cotización vacía usando Factory
     */
    public Cotizacion crearCotizacionVacia() {
        Cotizacion cotizacion = cotizacionFactory.crearCotizacionVacia();
        return cotizacionRepository.save(cotizacion);
    }
    
    /**
     * Agregar un detalle a una cotización existente
     */
    @Transactional
    public Cotizacion agregarDetalleCotizacion(Long idCotizacion, Long idMueble, 
                                                Long idVariante, Integer cantidad) {
        Optional<Cotizacion> cotizacionOpt = cotizacionRepository.findById(idCotizacion);
        
        if (!cotizacionOpt.isPresent()) {
            throw new RuntimeException("Cotización no encontrada con ID: " + idCotizacion);
        }
        
        Cotizacion cotizacion = cotizacionOpt.get();
        
        if (cotizacion.getConfirmada()) {
            throw new RuntimeException("No se puede modificar una cotización ya confirmada");
        }
        
        // Obtener el mueble
        Optional<Mueble> muebleOpt = muebleService.obtenerMueblePorId(idMueble);
        if (!muebleOpt.isPresent()) {
            throw new RuntimeException("Mueble no encontrado con ID: " + idMueble);
        }
        Mueble mueble = muebleOpt.get();
        
        // Obtener la variante (puede ser null para variante "Normal")
        Variante variante = null;
        if (idVariante != null) {
            variante = varianteService.obtenerVariantePorId(idVariante).orElse(null);
        }
        
        // Calcular precio usando el servicio de precios
        BigDecimal precioUnitario = precioService.calcularPrecioNormal(mueble, variante);
        
        // Crear detalle usando Factory
        DetalleCotizacion detalle = cotizacionFactory.crearDetalleCotizacion(
            mueble, variante, cantidad, precioUnitario
        );
        
        cotizacion.agregarDetalle(detalle);
        cotizacion.calcularTotal();
        
        return cotizacionRepository.save(cotizacion);
    }
    
    /**
     * Confirmar una cotización como venta (descuenta stock)
     */
    @Transactional
    public Cotizacion confirmarVenta(Long idCotizacion) {
        Optional<Cotizacion> cotizacionOpt = cotizacionRepository.findById(idCotizacion);
        
        if (!cotizacionOpt.isPresent()) {
            throw new RuntimeException("Cotización no encontrada con ID: " + idCotizacion);
        }
        
        Cotizacion cotizacion = cotizacionOpt.get();
        
        if (cotizacion.getConfirmada()) {
            throw new RuntimeException("La cotización ya está confirmada");
        }
        
        // Verificar stock para todos los detalles
        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            if (!muebleService.verificarStock(detalle.getMueble().getIdMueble(), detalle.getCantidad())) {
                throw new RuntimeException("Stock insuficiente para el mueble: " + 
                    detalle.getMueble().getNombreMueble());
            }
        }
        
        // Decrementar stock de cada mueble
        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            muebleService.decrementarStock(detalle.getMueble().getIdMueble(), detalle.getCantidad());
        }
        
        // Confirmar cotización usando Factory
        cotizacion = cotizacionFactory.confirmarCotizacion(cotizacion);
        
        return cotizacionRepository.save(cotizacion);
    }
    
    /**
     * Listar todas las cotizaciones
     */
    public List<Cotizacion> listarTodasCotizaciones() {
        return cotizacionRepository.findAll();
    }
    
    /**
     * Obtener cotización por ID
     */
    public Optional<Cotizacion> obtenerCotizacionPorId(Long id) {
        return cotizacionRepository.findById(id);
    }
    
    /**
     * Listar cotizaciones pendientes
     */
    public List<Cotizacion> listarCotizacionesPendientes() {
        return cotizacionRepository.findByEstado("PENDIENTE");
    }
    
    /**
     * Listar cotizaciones confirmadas (ventas)
     */
    public List<Cotizacion> listarVentas() {
        return cotizacionRepository.findByConfirmada(true);
    }
}