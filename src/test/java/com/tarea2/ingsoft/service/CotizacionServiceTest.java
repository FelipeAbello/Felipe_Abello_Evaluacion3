package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Cotizacion;
import com.tarea2.ingsoft.model.DetalleCotizacion;
import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.repository.CotizacionRepository;
import com.tarea2.ingsoft.repository.DetalleCotizacionRepository;
import com.tarea2.ingsoft.service.factory.CotizacionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizacionServiceTest {
    
    @Mock
    private CotizacionRepository cotizacionRepository;
    
    @Mock
    private DetalleCotizacionRepository detalleCotizacionRepository;
    
    @Mock
    private MuebleService muebleService;
    
    @Mock
    private VarianteService varianteService;
    
    @Mock
    private PrecioService precioService;
    
    @Mock
    private CotizacionFactory cotizacionFactory;
    
    @InjectMocks
    private CotizacionService cotizacionService;
    
    private Cotizacion cotizacion;
    private Mueble mueble;
    private DetalleCotizacion detalle;
    
    @BeforeEach
    void setUp() {
        // Crear mueble de prueba
        mueble = new Mueble();
        mueble.setIdMueble(1L);
        mueble.setNombreMueble("Silla de Oficina");
        mueble.setStock(10);
        
        // Crear cotización de prueba
        cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(1L);
        cotizacion.setEstado("PENDIENTE");
        cotizacion.setConfirmada(false);
        cotizacion.setDetalles(new ArrayList<>());
        
        // Crear detalle de cotización
        detalle = new DetalleCotizacion();
        detalle.setMueble(mueble);
        detalle.setCantidad(5);
        detalle.setPrecioUnitario(new BigDecimal("50000.00"));
        detalle.setSubtotal(new BigDecimal("250000.00"));
        
        cotizacion.agregarDetalle(detalle);
    }
    
    @Test
    @DisplayName("Test: Confirmar venta con stock suficiente")
    void testConfirmarVentaConStockSuficiente() {
        // Given
        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        when(muebleService.verificarStock(1L, 5)).thenReturn(true);
        when(cotizacionFactory.confirmarCotizacion(any(Cotizacion.class))).thenReturn(cotizacion);
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        
        // When
        Cotizacion resultado = cotizacionService.confirmarVenta(1L);
        
        // Then
        verify(muebleService, times(1)).decrementarStock(1L, 5);
        System.out.println("✓ Venta confirmada exitosamente con stock suficiente");
    }
    
    @Test
    @DisplayName("Test: Error al confirmar venta con stock insuficiente")
    void testConfirmarVentaConStockInsuficiente() {
        // Given
        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        when(muebleService.verificarStock(1L, 5)).thenReturn(false);
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cotizacionService.confirmarVenta(1L);
        });
        
        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        verify(muebleService, never()).decrementarStock(any(), any());
        System.out.println("✓ Excepción lanzada por stock insuficiente: " + exception.getMessage());
    }
    
    @Test
    @DisplayName("Test: Error al confirmar cotización ya confirmada")
    void testConfirmarCotizacionYaConfirmada() {
        // Given
        cotizacion.setConfirmada(true);
        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cotizacionService.confirmarVenta(1L);
        });
        
        assertTrue(exception.getMessage().contains("ya está confirmada"));
        System.out.println("✓ Excepción lanzada: cotización ya confirmada");
    }
}