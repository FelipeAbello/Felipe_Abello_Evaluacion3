package com.tarea2.ingsoft.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "variante")
public class Variante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variante")
    private Long idVariante;
    
    @Column(name = "nombre_variante", nullable = false, length = 100)
    private String nombreVariante;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "precio_adicional", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioAdicional = BigDecimal.ZERO;
    
    @Column(name = "tipo_variante", nullable = false, length = 50)
    private String tipoVariante;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructor vacío
    public Variante() {
    }
    
    // Constructor con parámetros
    public Variante(String nombreVariante, String descripcion, 
                    BigDecimal precioAdicional, String tipoVariante) {
        this.nombreVariante = nombreVariante;
        this.descripcion = descripcion;
        this.precioAdicional = precioAdicional;
        this.tipoVariante = tipoVariante;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getIdVariante() {
        return idVariante;
    }
    
    public void setIdVariante(Long idVariante) {
        this.idVariante = idVariante;
    }
    
    public String getNombreVariante() {
        return nombreVariante;
    }
    
    public void setNombreVariante(String nombreVariante) {
        this.nombreVariante = nombreVariante;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecioAdicional() {
        return precioAdicional;
    }
    
    public void setPrecioAdicional(BigDecimal precioAdicional) {
        this.precioAdicional = precioAdicional;
    }
    
    public String getTipoVariante() {
        return tipoVariante;
    }
    
    public void setTipoVariante(String tipoVariante) {
        this.tipoVariante = tipoVariante;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}