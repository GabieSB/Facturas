package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FacturaServiceImplementationTest {

    @Autowired
    private IFacturaService facturaService;

    FacturaDTO facturaEjemplo;
    FacturaDTO facturaInactivo;

    @BeforeEach
    public void setup() {
        facturaEjemplo = new FacturaDTO() {
            {
               setCaja(1);
               setDescuentoGeneral(10);
               setEstado(true);

            }
        };
    }

    @Test
    public void sePuedeCrearUnFacturaCorrectamente() {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> FacturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (FacturaEncontrado.isPresent()) {
            FacturaDTO Factura = FacturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), Factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

  /*  @AfterEach
    public void tearDown() {
        if (facturaEjemplo != null) {
            facturaService.delete(facturaEjemplo.getId());
            facturaEjemplo = null;
        }

    }*/

    @Test
    public void sePuedeModificarUnFacturaCorrectamente() throws EvitarModificarContenidoInactivoExeption {
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
    public void sePuedeEliminarUnFacturaCorrectamente() {

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
    public void seEvitaModificarUnFacturaInactivo() {
        initDataForseEvitaModificarUnFacturaInactivo();

        assertThrows(EvitarModificarContenidoInactivoExeption.class,
                () -> {
                    facturaService.update(facturaInactivo);
                }
        );
    }

    private void initDataForseEvitaModificarUnFacturaInactivo()  {
        facturaInactivo = new FacturaDTO(){
            {
                setEstado(false);
                setCaja(10);
                setDescuentoGeneral(200);
            }
        };

        facturaInactivo = facturaService.create(facturaInactivo);

    }


}