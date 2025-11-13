package com.tarea2.ingsoft.service;

import com.tarea2.ingsoft.model.Mueble;
import com.tarea2.ingsoft.model.Variante;
import com.tarea2.ingsoft.service.strategy.PrecioNormalStrategy;
import com.tarea2.ingsoft.service.strategy.PrecioConDescuentoStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrecioServiceTest {
    
    private PrecioService precioService;
    private Mueble mueble;
    private Variante varianteNormal;
    private Variante variantePremium;
    
    @BeforeEach
    void setUp() {
        // Inicializar el servicio
        precioService = new PrecioService();
        PrecioNormalStrategy precioNormalStrategy = new PrecioNormalStrategy();
        precioService.init(precioNormalStrategy);
        
        // Crear un mueble de prueba
        mueble = new Mueble();
        mueble.setIdMueble(1L);
        mueble.setNombreMueble("Silla de Prueba");
        mueble.setPrecioBase(new BigDecimal("50000.00"));
        
        // Crear variante normal
        varianteNormal = new Variante();
        varianteNormal.setIdVariante(1L);
        varianteNormal.setNombreVariante("Normal");
        varianteNormal.setPrecioAdicional(BigDecimal.ZERO);
        
        // Crear variante premium
        variantePremium = new Variante();
        variantePremium.setIdVariante(2L);
        variantePremium.setNombreVariante("Barniz Premium");
        variantePremium.setPrecioAdicional(new BigDecimal("15000.00"));
    }
    
    @Test
    @DisplayName("Test: Calcular precio con variante normal (sin recargo)")
    void testCalcularPrecioConVarianteNormal() {
        // When
        BigDecimal precioFinal = precioService.calcularPrecioNormal(mueble, varianteNormal);
        
        // Then
        assertEquals(new BigDecimal("50000.00"), precioFinal);
        System.out.println("✓ Precio con variante normal: " + precioFinal);
    }
    
    @Test
    @DisplayName("Test: Calcular precio sin variante (null)")
    void testCalcularPrecioSinVariante() {
        // When
        BigDecimal precioFinal = precioService.calcularPrecioNormal(mueble, null);
        
        // Then
        assertEquals(new BigDecimal("50000.00"), precioFinal);
        System.out.println("✓ Precio sin variante: " + precioFinal);
    }
    
    @Test
    @DisplayName("Test: Calcular precio con variante premium (con recargo)")
    void testCalcularPrecioConVariantePremium() {
        // When
        BigDecimal precioFinal = precioService.calcularPrecioNormal(mueble, variantePremium);
        
        // Then
        BigDecimal precioEsperado = new BigDecimal("65000.00"); // 50000 + 15000
        assertEquals(precioEsperado, precioFinal);
        System.out.println("✓ Precio con variante premium: " + precioFinal);
    }
    
    @Test
    @DisplayName("Test: Cambiar estrategia de precio a descuento")
    void testCambiarEstrategiaPrecioConDescuento() {
        // Given
        PrecioConDescuentoStrategy estrategiaDescuento = new PrecioConDescuentoStrategy();
        estrategiaDescuento.setPorcentajeDescuento(new BigDecimal("0.10")); // 10% descuento
        
        // When
        precioService.setEstrategia(estrategiaDescuento);
        BigDecimal precioConDescuento = precioService.calcularPrecioFinal(mueble, varianteNormal);
        
        // Then
        BigDecimal precioEsperado = new BigDecimal("45000.00"); // 50000 - 10%
        assertEquals(0, precioEsperado.compareTo(precioConDescuento), 
                    "El precio con descuento debería ser " + precioEsperado);
        System.out.println("✓ Precio con 10% descuento: " + precioConDescuento);
    }
    
    @Test
    @DisplayName("Test: Precio con variante premium y descuento")
    void testPrecioVariantePremiumConDescuento() {
        // Given
        PrecioConDescuentoStrategy estrategiaDescuento = new PrecioConDescuentoStrategy();
        estrategiaDescuento.setPorcentajeDescuento(new BigDecimal("0.15")); // 15% descuento
        
        // When
        precioService.setEstrategia(estrategiaDescuento);
        BigDecimal precioConDescuento = precioService.calcularPrecioFinal(mueble, variantePremium);
        
        // Then
        // Precio base: 50000 + 15000 = 65000
        // Descuento 15%: 65000 * 0.85 = 55250
        BigDecimal precioEsperado = new BigDecimal("55250.00");
        assertEquals(0, precioEsperado.compareTo(precioConDescuento),
                    "El precio con descuento debería ser " + precioEsperado);
        System.out.println("✓ Precio premium con 15% descuento: " + precioConDescuento);
    }
}