package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.exceptions.ClienteEstaInactivoExeption;
import org.una.tienda.facturacion.exceptions.ClienteSinDatosEscencialesExeption;
import org.una.tienda.facturacion.exceptions.EModificarContenidoInactivoExeption;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FacturaServiceImplementationTest {

    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private IClienteService clienteService;

    FacturaDTO facturaEjemplo;
    FacturaDTO facturaInactivo;
    ClienteDTO clienteEjemplo;
    ClienteDTO clienteInactivo;


    @BeforeEach
    public void setup() throws ClienteSinDatosEscencialesExeption {
        clienteEjemplo = new ClienteDTO() {
            {
                setEstado(true);
                setNombre("ClienteDeEjemplo");
                setEmail("gallinaperro@catmail.com");
                setDireccion("La Boveda de Gallinas");
                setTelefono("555555");
            }
        };
        clienteService.create(clienteEjemplo);
        facturaEjemplo = new FacturaDTO() {
            {
               setCaja(1);
               setDescuentoGeneral(10);
               setEstado(true);
               setCliente(clienteEjemplo);

            }
        };
    }

    @Test
    public void sePuedeCrearUnFacturaCorrectamente() throws ClienteEstaInactivoExeption {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> FacturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (FacturaEncontrado.isPresent()) {
            FacturaDTO Factura = FacturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), Factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }


    @Test
    public void sePuedeModificarUnFacturaCorrectamente() throws EModificarContenidoInactivoExeption, ClienteEstaInactivoExeption {
        facturaEjemplo = facturaService.create(facturaEjemplo);
        facturaEjemplo.setCaja(2);

        facturaService.update(facturaEjemplo);
        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDTO factura = facturaEncontrado.get();
            Assertions.assertEquals(facturaEjemplo.getId(), factura.getId());
            Assertions.assertEquals(facturaEjemplo.getCaja(), factura.getCaja());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnFacturaCorrectamente() throws ClienteEstaInactivoExeption {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaService.delete(facturaEjemplo.getId());

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado != null) {
            fail("El objeto no se ha eliminado de la BD");
        }else{
            facturaEjemplo = null;
            Assertions.assertTrue(true);
        }


    }

    @Test
    public void seEvitaModificarUnFacturaInactivo() throws ClienteEstaInactivoExeption {
        initDataForseEvitaModificarUnFacturaInactivo();

        assertThrows(EModificarContenidoInactivoExeption.class,
                () -> {
                    facturaService.update(facturaInactivo);
                }
        );
    }

    private void initDataForseEvitaModificarUnFacturaInactivo() throws ClienteEstaInactivoExeption {


        facturaInactivo = new FacturaDTO(){
            {
                setEstado(false);
                setCaja(10);
                setDescuentoGeneral(200);
                setCliente(clienteEjemplo);
            }
        };

        facturaInactivo = facturaService.create(facturaInactivo);

    }

    @Test
    public void seEvitaFacturarConClienteInactivo() throws ClienteEstaInactivoExeption, ClienteSinDatosEscencialesExeption {
        initDataForSeEvitaFacturarConClienteInactivo();

        assertThrows(ClienteEstaInactivoExeption.class,
                () -> {
                    facturaService.create(facturaEjemplo);
                }
        );
    }

    private void initDataForSeEvitaFacturarConClienteInactivo() throws ClienteEstaInactivoExeption, ClienteSinDatosEscencialesExeption {


        clienteInactivo = new ClienteDTO() {
            {
                setEstado(false);
                setNombre("ClienteDeEjemplo");
                setEmail("gallinaperro@catmail.com");
                setDireccion("La Boveda de Gallinas");
                setTelefono("555555");
            }
        };
        clienteInactivo = clienteService.create(clienteInactivo);

        System.out.println("se crea el cliente " + clienteInactivo.getId());

        facturaEjemplo.setCliente(clienteInactivo);


    }


}