package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.repository.MuebleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MuebleServiceTest {
    
    @Mock
    private MuebleRepository muebleRepository;
    
    @InjectMocks
    private MuebleService muebleService;
    
    private Mueble mueble;
    
    @BeforeEach
    void setUp() {
        mueble = new Mueble();
        mueble.setIdMueble(1L);
        mueble.setNombreMueble("Mesa de Comedor");
        mueble.setTipo("MESA");
        mueble.setPrecioBase(new BigDecimal("180000.00"));
        mueble.setStock(10);
        mueble.setEstado("ACTIVO");
        mueble.setTamanio("GRANDE");
        mueble.setMaterial("Madera");
    }
    
    @Test
    @DisplayName("Test: Verificar stock suficiente")
    void testVerificarStockSuficiente() {
      
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        
     
        boolean hayStock = muebleService.verificarStock(1L, 5);
        
       
        assertTrue(hayStock);
        System.out.println("✓ Stock suficiente: Solicitado 5, Disponible 10");
    }
    
    @Test
    @DisplayName("Test: Verificar stock insuficiente")
    void testVerificarStockInsuficiente() {
     
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        
      
        boolean hayStock = muebleService.verificarStock(1L, 15);
        
       
        assertFalse(hayStock);
        System.out.println("✓ Stock insuficiente: Solicitado 15, Disponible 10");
    }
    
    @Test
    @DisplayName("Test: Decrementar stock exitosamente")
    void testDecrementarStockExitoso() {
      
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        when(muebleRepository.save(any(Mueble.class))).thenReturn(mueble);
        
      
        muebleService.decrementarStock(1L, 3);
        
       
        assertEquals(7, mueble.getStock());
        verify(muebleRepository, times(1)).save(mueble);
        System.out.println("✓ Stock decrementado: 10 → 7");
    }
    
    @Test
    @DisplayName("Test: Error al decrementar stock insuficiente")
    void testDecrementarStockInsuficiente() {
      
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(mueble));
        
       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            muebleService.decrementarStock(1L, 15);
        });
        
        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        System.out.println("✓ Excepción lanzada correctamente: " + exception.getMessage());
    }
    
    @Test
    @DisplayName("Test: Error al decrementar stock de mueble inexistente")
    void testDecrementarStockMuebleInexistente() {
       
        when(muebleRepository.findById(999L)).thenReturn(Optional.empty());
        
       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            muebleService.decrementarStock(999L, 5);
        });
        
        assertTrue(exception.getMessage().contains("Mueble no encontrado"));
        System.out.println("✓ Excepción lanzada: " + exception.getMessage());
    }
    
    @Test
    @DisplayName("Test: Crear mueble activa automáticamente")
    void testCrearMuebleActivoAutomaticamente() {
     
        Mueble nuevoMueble = new Mueble();
        nuevoMueble.setNombreMueble("Silla Nueva");
        when(muebleRepository.save(any(Mueble.class))).thenReturn(nuevoMueble);
        
      
        Mueble resultado = muebleService.crearMueble(nuevoMueble);
        
       
        assertEquals("ACTIVO", resultado.getEstado());
        System.out.println("✓ Mueble creado con estado ACTIVO");
    }
}