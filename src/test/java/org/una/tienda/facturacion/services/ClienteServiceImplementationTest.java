package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteSinDatosEscencialesExeption;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClienteServiceImplementationTest {

    @Autowired
    private IClienteService clienteService;

    ClienteDTO clienteEjemplo;
    ClienteDTO clienteSinDatosEscenciales;
    ClienteDTO clienteInactivo;

    @BeforeEach
    public void setup() {
        clienteEjemplo = new ClienteDTO() {
            {
                setTelefono("87010393");
                setDireccion("DisneyLandia");
                setEmail("unaoveja@catmail.ton");
                setNombre("Mickey Mouse");
                setEstado(true);
            }
        };
    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() throws ClienteSinDatosEscencialesExeption {

        clienteEjemplo = clienteService.create(clienteEjemplo);

        Optional<ClienteDTO> ClienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (ClienteEncontrado.isPresent()) {
            ClienteDTO Cliente = ClienteEncontrado.get();
            assertEquals(clienteEjemplo.getId(), Cliente.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void seEvitaCrearUnClienteSinDatosCompletos() throws ClienteSinDatosEscencialesExeption {
        initDataForseEvitaCrearUnClienteSinDatosCompletos();

        assertThrows(ClienteSinDatosEscencialesExeption.class,
                () -> {
                    clienteService.create(clienteSinDatosEscenciales);
                }
        );
    }

    private void initDataForseEvitaCrearUnClienteSinDatosCompletos() {
        clienteSinDatosEscenciales = new ClienteDTO(){
            {
                setTelefono("87010344");
                setNombre("Aslan");
                setEstado(true);
            }
        };
    }


    @Test
    public void seEvitaModificarUnClienteInactivo() throws EvitarModificarContenidoInactivoExeption, ClienteSinDatosEscencialesExeption {
        initDataForseEvitaModificarUnClienteInactivo();

        assertThrows(EvitarModificarContenidoInactivoExeption.class,
                () -> {
                    clienteService.update(clienteInactivo);
                }
        );
    }

    private void initDataForseEvitaModificarUnClienteInactivo() throws ClienteSinDatosEscencialesExeption {
        clienteInactivo = new ClienteDTO(){
            {
                setDireccion("Marte de Andrómeda");
                setTelefono("87010344");
                setEmail("aslanElRey@narnia.co");
                setNombre("Aslan");
                setEstado(false);
            }
        };

        clienteInactivo = clienteService.create(clienteInactivo);
    }

   /* @AfterEach
    public void tearDown() {
        if (clienteEjemplo != null) {
            clienteService.delete(clienteEjemplo.getId());
            clienteEjemplo = null;
        }

    }*/

    @Test
    public void sePuedeModificarUnClienteCorrectamente() throws ClienteSinDatosEscencialesExeption, EvitarModificarContenidoInactivoExeption {
        clienteEjemplo = clienteService.create(clienteEjemplo);
        clienteEjemplo.setNombre("Monica");

        clienteService.update(clienteEjemplo);
        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (clienteEncontrado.isPresent()) {
            ClienteDTO cliente = clienteEncontrado.get();
            Assertions.assertEquals(clienteEjemplo.getId(), cliente.getId());
            Assertions.assertEquals(clienteEjemplo.getNombre(), cliente.getNombre());
        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() throws ClienteSinDatosEscencialesExeption {

        clienteEjemplo = clienteService.create(clienteEjemplo);

        clienteService.delete(clienteEjemplo.getId());

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (clienteEncontrado != null) {
            fail("El objeto no se ha eliminado de la BD");
        }else{
            clienteEjemplo = null;
            Assertions.assertTrue(true);
        }


    }

}