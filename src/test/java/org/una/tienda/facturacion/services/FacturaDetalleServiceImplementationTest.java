package org.una.tienda.facturacion.services;

import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.*;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FacturaDetalleServiceImplementationTest {

    @Autowired
    private IFacturaDetalleService facturaDetalleService;
    @Autowired
    private IProductoService productoService;
    @Autowired
    private IProductoPrecioService productoPrecio;
    @Autowired
    private IProductoExistenciaService productoExistenciaService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IFacturaService facturaService;

    FacturaDetalleDTO facturaDetalleEjemplo;
    ProductoDTO productoEjemplo;
    ProductoDTO productoPrueba;
    ProductoExistenciaDTO productoExistenciaPrueba;
    ProductoPrecioDTO productoPrecioPrueba;
    ClienteDTO clientePrueba;
    FacturaDTO  facturaPrueba;
    FacturaDetalleDTO facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO();





   /* @Test
    public void sePuedeCrearUnFacturaDetalleCorrectamente()  {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> FacturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (FacturaDetalleEncontrado.isPresent()) {
            FacturaDetalleDTO FacturaDetalle = FacturaDetalleEncontrado.get();
            assertEquals(facturaDetalleEjemplo.getId(), FacturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }*/

    @BeforeEach
    public void setup() {
        facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO() ;

    }
    @AfterEach
    public void tearDown() {
        if (facturaDetalleEjemplo != null) {
            facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            facturaDetalleEjemplo = null;
        }

    }

    @Test
    public void sePuedeModificarUnFacturaDetalleCorrectamente() throws  ProductoConDescuentoMayorAlPermitidoException {
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);
        facturaDetalleEjemplo.setCantidad(10);

        facturaDetalleService.update(facturaDetalleEjemplo);
        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            FacturaDetalleDTO facturaDetalle = facturaDetalleEncontrado.get();
            Assertions.assertEquals(facturaDetalleEjemplo.getId(), facturaDetalle.getId());
            Assertions.assertEquals(facturaDetalleEjemplo.getCantidad(), facturaDetalle.getCantidad());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        facturaDetalleService.delete(facturaDetalleEjemplo.getId());

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado != null) {
            fail("El objeto no se ha eliminado de la BD");
        }else{
            facturaDetalleEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);

        productoExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setProducto(productoPrueba);
                setCantidad(1);
            }
        };

        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);



        productoPrecioPrueba = new ProductoPrecioDTO() {
            {
                setProducto(productoPrueba);
                setPrecioColones((double) 1000);
                setDescuentoMaximo((double) 10);
                setDescuentoPromocional((double) 2);
            }
        };
        productoPrecioPrueba = productoPrecio.create(productoPrecioPrueba);




        clientePrueba = new ClienteDTO() {
            {
                setNombre("ClienteDePrueba");
            }
        };
        clientePrueba = clienteService.create(clientePrueba);



        facturaPrueba = new FacturaDTO() {
            {
                setCaja(991);
                setCliente(clientePrueba);
            }
        };
        facturaPrueba = facturaService.create(facturaPrueba);



        facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO() {
            {
                setCantidad(1);
                setProducto(productoPrueba);
                setFactura(facturaPrueba);
                setDescuentoFinal(productoPrecioPrueba.getDescuentoMaximo() + 1);
            }
        };



    }


    @Test
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();

        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);
                }
        );
    }




}