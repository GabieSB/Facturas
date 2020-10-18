package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.ClienteDTO;

import java.util.Optional;

public interface IClienteService {

    public ClienteDTO create(ClienteDTO clienteDTO);

    public  Optional<ClienteDTO> update(ClienteDTO clienteDTO);

    public void delete(Long id);

    public Optional<ClienteDTO> findById(Long id);


}
