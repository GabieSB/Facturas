package org.una.tienda.facturacion.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ProductoExistenciaDTO;
import org.una.tienda.facturacion.exceptions.EModificarContenidoInactivoExeption;

@SpringBootTest
class ProductoExistenciaServiceImplementationTest {

    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    ProductoExistenciaDTO productoExistenciaEjemplo;
    ProductoExistenciaDTO productoExistenciaInactivo;

    @BeforeEach
    public void setup() {
        productoExistenciaEjemplo = new ProductoExistenciaDTO() {
            {
                setCantidad(1000.0);
                setEstado(true);

            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoEnExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoExistenciaDTO producto = productoEncontrado.get();
            assertEquals(productoExistenciaEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoCorrectamente() throws EModificarContenidoInactivoExeption {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoExistenciaEjemplo.setCantidad(8000.0);
        productoExistenciaService.update(productoExistenciaEjemplo, productoExistenciaEjemplo.getId());
        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoExistenciaDTO productoExistenciaDTO = productoEncontrado.get();
            Assertions.assertEquals(productoExistenciaEjemplo.getId(), productoExistenciaDTO.getId());
            Assertions.assertEquals(productoExistenciaEjemplo.getCantidad(), productoExistenciaDTO.getCantidad());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnProductoCorrectamente() {
        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoExistenciaService.delete(productoExistenciaEjemplo.getId());
        Optional<ProductoExistenciaDTO> productoEliminar = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEliminar != null) {
            fail("El objeto no se ha eliminado de la BD");
        } else {
            productoExistenciaEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void seEvitaModificarUnProductoExistenciaInactivo()  {
        initDataForseEvitaModificarUnProductoExistenciaInactivo();

        assertThrows(EModificarContenidoInactivoExeption.class,
                () -> {
                    productoExistenciaService.update(productoExistenciaInactivo, productoExistenciaInactivo.getId());
                }
        );
    }

    private void initDataForseEvitaModificarUnProductoExistenciaInactivo(){
        productoExistenciaInactivo = new ProductoExistenciaDTO(){
            {
               setCantidad(100);
                setEstado(false);

            }
        };

        productoExistenciaInactivo = productoExistenciaService.create(productoExistenciaInactivo);
    }

}
