/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Roberth :)
 */
@RestController
@RequestMapping("/tipo-cambio")
public class MoneyConverterController {

    private final double conversionDolaresAColones = 610;

    @GetMapping("a-dolares/{value}")
    @ResponseBody
    public ResponseEntity<?> convertCRToUDS(@PathVariable(value = "value") float value) {
        return new ResponseEntity<>(value / conversionDolaresAColones, HttpStatus.OK);
    }

}
