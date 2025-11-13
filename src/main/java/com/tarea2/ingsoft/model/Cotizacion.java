package com.tarea2.ingsoft.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Long idCotizacion;
    
    @Column(name = "fecha_cotizacion")
    private LocalDateTime fechaCotizacion;
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE";
    
    @Column(name = "confirmada")
    private Boolean confirmada = false;
    
    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;
    
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCotizacion> detalles = new ArrayList<>();
    
    // Constructor vacío
    public Cotizacion() {
    }
    
    // Constructor con parámetros
    public Cotizacion(BigDecimal total) {
        this.total = total;
        this.estado = "PENDIENTE";
        this.confirmada = false;
    }
    
    @PrePersist
    protected void onCreate() {
        fechaCotizacion = LocalDateTime.now();
    }
    
    // Métodos de ayuda
    public void agregarDetalle(DetalleCotizacion detalle) {
        detalles.add(detalle);
        detalle.setCotizacion(this);
    }
    
    public void calcularTotal() {
        this.total = detalles.stream()
            .map(DetalleCotizacion::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Getters y Setters
    public Long getIdCotizacion() {
        return idCotizacion;
    }
    
    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }
    
    public LocalDateTime getFechaCotizacion() {
        return fechaCotizacion;
    }
    
    public void setFechaCotizacion(LocalDateTime fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Boolean getConfirmada() {
        return confirmada;
    }
    
    public void setConfirmada(Boolean confirmada) {
        this.confirmada = confirmada;
    }
    
    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }
    
    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }
    
    public List<DetalleCotizacion> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles = detalles;
    }
}