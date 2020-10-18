package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ProductoDTO;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoServiceImplementationTest {

    @Autowired
    private IProductoService productoService;

    ProductoDTO productoEjemplo;
    ProductoDTO productoInactivo;

    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
                setEstado(true);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoCorrectamente() {

        productoEjemplo = productoService.create(productoEjemplo);

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            assertEquals(productoEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }
      @Test
    public void sePuedeModificarUnProductoCorrectamente() throws EvitarModificarContenidoInactivoExeption {

        productoEjemplo = productoService.create(productoEjemplo);
        productoEjemplo.setDescripcion("Producto en mal estado");
        productoService.update(productoEjemplo, productoEjemplo.getId());
        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            Assertions.assertEquals(productoEjemplo.getId(), producto.getId());
            Assertions.assertEquals(productoEjemplo.getDescripcion(), producto.getDescripcion());
            Assertions.assertEquals(productoEjemplo.getImpuesto(), producto.getImpuesto());
        } else {
            fail("No se encontro la información en la BD");
        }
    }
     @Test
    public void sePuedeEliminarUnProductoCorrectamente() {
        productoEjemplo = productoService.create(productoEjemplo);
        productoService.delete(productoEjemplo.getId());
        Optional<ProductoDTO> productoEliminar= productoService.findById(productoEjemplo.getId());

        if (productoEliminar != null) {
            fail("El objeto no se ha eliminado de la BD");
        }else{
            productoEjemplo = null;
            Assertions.assertTrue(true);
        }
    }


    @Test
    public void seEvitaModificarUnProductoInactivo() throws EvitarModificarContenidoInactivoExeption {
        initDataForseEvitaModificarUnProductoInactivo();

        assertThrows(EvitarModificarContenidoInactivoExeption.class,
                () -> {
                    productoService.update(productoInactivo, productoInactivo.getId());
                }
        );
    }

    private void initDataForseEvitaModificarUnProductoInactivo()  {
        productoInactivo = new ProductoDTO(){
            {
                setEstado(false);
                setDescripcion("Varita Mágica");
            }
        };

        productoInactivo = productoService.create(productoInactivo);
        System.out.println(productoInactivo.getId());
    }

  /*  @AfterEach
    public void tearDown() {
        if (productoEjemplo != null) {
            productoService.delete(productoEjemplo.getId());
            productoEjemplo = null;
        }

    }*/

}
