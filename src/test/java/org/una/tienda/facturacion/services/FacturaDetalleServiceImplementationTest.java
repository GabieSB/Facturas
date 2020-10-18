package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.dto.ProductoDTO;
import org.una.tienda.facturacion.dto.ProductoExistenciaDTO;
import org.una.tienda.facturacion.dto.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

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
    FacturaDTO facturaPrueba;
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
        facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO();
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setCantidad(2);
                setDescuento_final(0.10);

            }
        };
    }

    @Test
    public void sePuedeEliminarUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
 initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);

        facturaDetalleService.delete(facturaDetalleEjemplo.getId());

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado != null) {
            fail("El objeto no se ha eliminado de la BD");
        } else {
            facturaDetalleEjemplo = null;
            Assertions.assertTrue(true);
        }
    }

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.1);
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
                setDescuentoMaximo((double) 20);
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
                setClienteId(clientePrueba);
            }
        };
        facturaPrueba = facturaService.create(facturaPrueba);

        facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO() {
            {
                setCantidad(1);
                setProductoId(productoPrueba);
                setFacturasId(facturaPrueba);
                setDescuento_final(productoPrecioPrueba.getDescuentoMaximo() +1-5);
            }
        };

    }

    @Test
    public void sePuedeModificarUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);
        facturaDetalleEjemplo.setCantidad(74);
//
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
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();

        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);
                }
        );
    }

    @AfterEach
    public void tearDown() {
        if (facturaDetalleEjemplo != null) {
            facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            facturaDetalleEjemplo = null;
        }
    }
}
