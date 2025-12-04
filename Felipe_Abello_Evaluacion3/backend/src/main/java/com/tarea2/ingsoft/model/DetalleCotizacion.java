package com.tarea2.ingsoft.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Cotizacion cotizacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mueble", nullable = false)
    private Mueble mueble;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_variante")
    private Variante variante;
    
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    // Constructor vacío
    public DetalleCotizacion() {
    }
    
    // Constructor con parámetros
    public DetalleCotizacion(Mueble mueble, Variante variante, Integer cantidad, 
                             BigDecimal precioUnitario) {
        this.mueble = mueble;
        this.variante = variante;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
    
    // Método para calcular subtotal
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
    
    // Getters y Setters
    public Long getIdDetalle() {
        return idDetalle;
    }
    
    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }
    
    public Cotizacion getCotizacion() {
        return cotizacion;
    }
    
    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }
    
    public Mueble getMueble() {
        return mueble;
    }
    
    public void setMueble(Mueble mueble) {
        this.mueble = mueble;
    }
    
    public Variante getVariante() {
        return variante;
    }
    
    public void setVariante(Variante variante) {
        this.variante = variante;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}