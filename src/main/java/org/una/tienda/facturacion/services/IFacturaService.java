package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.FacturaDTO;

import java.util.Optional;

public interface IFacturaService {
    public FacturaDTO create(FacturaDTO facturaDTO);

    public Optional<FacturaDTO> update(FacturaDTO facturaDTO);

    public void delete(Long id);

    public Optional<FacturaDTO> findById(Long id);
}
