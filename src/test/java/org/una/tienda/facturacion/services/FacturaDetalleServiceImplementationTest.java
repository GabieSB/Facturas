package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.*;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.exceptions.*;
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
    ProductoExistenciaDTO productoExistenciaEjemplo;
    ProductoPrecioDTO productoPrecioEjemplo;
    ClienteDTO clienteEjemplo;
    FacturaDTO  facturaEjemplo;
    FacturaDetalleDTO facturaDetalleEjemploConExtraDescuento = new FacturaDetalleDTO();
    FacturaDetalleDTO facturaDetalleInactivo;
    ProductoDTO productoPrueba;
    ProductoExistenciaDTO productoExistenciaPrueba;
    ProductoExistenciaDTO productoSinExistenciaPrueba;
    ProductoPrecioDTO productoPrecioPrueba;
    ClienteDTO clientePrueba;
    FacturaDTO  facturaPrueba;
    FacturaDetalleDTO facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO();

   @Test
    public void sePuedeCrearUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ProductoSinExistencia {

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> FacturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (FacturaDetalleEncontrado.isPresent()) {
            FacturaDetalleDTO FacturaDetalle = FacturaDetalleEncontrado.get();
            assertEquals(facturaDetalleEjemplo.getId(), FacturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @BeforeEach
    public void setup() throws ClienteSinDatosEscencialesExeption, ClienteEstaInactivoExeption {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);

        productoExistenciaEjemplo = new ProductoExistenciaDTO() {
            {
                setProductoId(productoEjemplo);
                setCantidad(1);
            }
        };

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setProductoId(productoEjemplo);
                setPrecioColones((double) 1000);
                setDescuentoMaximo((double) 100);
                setDescuentoPromocional((double) 2);
            }
        };
        productoPrecioEjemplo = productoPrecio.create(productoPrecioEjemplo);

        System.out.println(productoPrecioEjemplo.getId());

        clienteEjemplo = new ClienteDTO() {
            {
                setEstado(true);
                setNombre("ClienteDeEjemplo");
                setEmail("gallinaperro@catmail.com");
                setDireccion("La Boveda de Gallinas");
                setTelefono("555555");
            }
        };
        clienteEjemplo = clienteService.create(clienteEjemplo);

        facturaEjemplo = new FacturaDTO() {
            {
                setCaja(991);
                setCliente(clienteEjemplo);
            }
        };
        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setCantidad(1);
                setProducto(productoEjemplo);
                setFactura(facturaEjemplo);
                setDescuentoFinal(productoPrecioEjemplo.getDescuentoMaximo() -5);
                setEstado(true);
            }
        };

        System.out.println("esta sakiendo de before eahc");

    }
  /*  @AfterEach
    public void tearDown() {
        if (facturaDetalleEjemplo != null) {
            facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            facturaDetalleEjemplo = null;
        }

    }*/

    @Test
    public void sePuedeModificarUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, EvitarModificarContenidoInactivoExeption, FacturaCantidadCero, ProductoSinExistencia {
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
    public void sePuedeEliminarUnFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ProductoSinExistencia {

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

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws ClienteSinDatosEscencialesExeption, ClienteEstaInactivoExeption {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);

        productoExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setProductoId(productoPrueba);
                setCantidad(1);
            }
        };

        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);

        productoPrecioPrueba = new ProductoPrecioDTO() {
            {
                setProductoId(productoPrueba);
                setPrecioColones((double) 1000);
                setDescuentoMaximo((double) 10);
                setDescuentoPromocional((double) 2);
            }
        };
        productoPrecioPrueba = productoPrecio.create(productoPrecioPrueba);

        clientePrueba = new ClienteDTO() {
            {
                setEstado(true);
                setNombre("ClienteDePrueba");
                setEmail("gallinaperro@catmail.com");
                setDireccion("La Boveda de Gallinas");
                setTelefono("555555");
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
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws ClienteSinDatosEscencialesExeption, ClienteEstaInactivoExeption {
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();

        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);
                }
        );
    }

    @Test
    public void seEvitaFacturarCantidadCero() throws FacturaCantidadCero, ProductoConDescuentoMayorAlPermitidoException {
        initDataForSeEvitaFacturarCero();

        assertThrows(FacturaCantidadCero.class,
                () -> {
                    facturaDetalleService.create(facturaDetalleEjemplo);
                }
        );
    }

    private void initDataForSeEvitaFacturarCero() throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero {

        facturaDetalleEjemplo.setCantidad(0);

    }

    @Test
    public void seEvitaFacturarProductoCantidadCero() throws FacturaCantidadCero, ProductoConDescuentoMayorAlPermitidoException, ClienteSinDatosEscencialesExeption {
        initDataForSeEvitaFacturarProductoCantidadCero();

        assertThrows(ProductoSinExistencia.class,
                () -> {
                    facturaDetalleService.create(facturaDetalleEjemplo);
                }
        );
    }

    private void initDataForSeEvitaFacturarProductoCantidadCero() throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ClienteSinDatosEscencialesExeption {

        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);

        System.out.println(productoEjemplo.getId());

        productoSinExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setProductoId(productoEjemplo);
                setCantidad(0);
                setEstado(true);
            }
        };

        productoSinExistenciaPrueba = productoExistenciaService.create(productoSinExistenciaPrueba);


        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setProductoId(productoEjemplo);
                setPrecioColones((double) 1000);
                setDescuentoMaximo((double) 100);
                setDescuentoPromocional((double) 2);
            }
        };
        productoPrecioEjemplo = productoPrecio.create(productoPrecioEjemplo);

        System.out.println(productoPrecioEjemplo.getId());


        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setCantidad(1);
                setProducto(productoEjemplo);
                setFactura(facturaEjemplo);
                setDescuentoFinal(productoPrecioEjemplo.getDescuentoMaximo() -5);
                setEstado(true);
            }
        };

    }

    @Test
    public void seEvitaModificarUnaFacturaDetalleInactivo() throws EvitarModificarContenidoInactivoExeption, ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ProductoSinExistencia {
        initDataForseEvitaModificarUnaFacturaDetalleInactivo();

        assertThrows(EvitarModificarContenidoInactivoExeption.class,
                () -> {
                    facturaDetalleService.update(facturaDetalleInactivo);
                }
        );
    }

    private void initDataForseEvitaModificarUnaFacturaDetalleInactivo() throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ProductoSinExistencia {

        facturaDetalleInactivo = new FacturaDetalleDTO() {
            {
                setCantidad(1);
                setProducto(productoEjemplo);
                setFactura(facturaEjemplo);
                setDescuentoFinal(productoPrecioEjemplo.getDescuentoMaximo() -5);
                setEstado(false);
            }
        };
        facturaDetalleInactivo = facturaDetalleService.create(facturaDetalleInactivo);

    }




}