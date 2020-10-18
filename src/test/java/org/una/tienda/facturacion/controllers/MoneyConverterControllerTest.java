/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.controllers;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author Roberth :)
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MoneyConverterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void seCalculaCorrectamenteTipoDeCambioADolares() {
        String valorEsperado = "10.0";
        String valorEnColones = "6100";
        String resultadoApi = this.restTemplate.getForObject("http://localhost:" + port + "/tipo-cambio/a-dolares/" + valorEnColones, String.class);
        if (resultadoApi.contains("404")) {
            fail("No se ha podido establecer contacto con el controller especificado");
        }
        Assertions.assertEquals(resultadoApi, valorEsperado, "El tipo de cambio no se calcula correctamente");
    }
}
