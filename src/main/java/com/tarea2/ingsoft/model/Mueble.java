package com.tarea2.ingsoft.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mueble")
public class Mueble {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mueble")
    private Long idMueble;
    
    @Column(name = "nombre_mueble", nullable = false, length = 100)
    private String nombreMueble;
    
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;
    
    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioBase;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "ACTIVO";
    
    @Column(name = "tamanio", nullable = false, length = 20)
    private String tamanio;
    
    @Column(name = "material", nullable = false, length = 50)
    private String material;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructor vacío (requerido por JPA)
    public Mueble() {
    }
    
    // Constructor con parámetros
    public Mueble(String nombreMueble, String tipo, BigDecimal precioBase, 
                  Integer stock, String tamanio, String material) {
        this.nombreMueble = nombreMueble;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.stock = stock;
        this.tamanio = tamanio;
        this.material = material;
        this.estado = "ACTIVO";
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getIdMueble() {
        return idMueble;
    }
    
    public void setIdMueble(Long idMueble) {
        this.idMueble = idMueble;
    }
    
    public String getNombreMueble() {
        return nombreMueble;
    }
    
    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getPrecioBase() {
        return precioBase;
    }
    
    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getTamanio() {
        return tamanio;
    }
    
    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}