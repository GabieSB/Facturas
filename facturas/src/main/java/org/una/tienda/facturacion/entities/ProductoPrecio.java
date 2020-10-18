/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Roberth :)
 */
@Entity
@Table(name = "ut_productos_precios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoPrecio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "descuento_maximo")
    private Double descuentoMaximo;
    @Column(name = "descuento_promocional")
    private Double descuentoPromocional;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_Registro")
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    private Date fechaRegistro;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    private Date fechaModificacion;
    @Column(name = "precio_colones")
    private Double precioColones;
    @JoinColumn(name = "productos_id", referencedColumnName = "id")
    @ManyToOne
    private Producto productoId;

    @PrePersist
    public void prePersist() {
        fechaRegistro = new Date();
        fechaModificacion = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = new Date();
    }

}
