package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteSinDatosEscencialesExeption;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

import java.util.Optional;

public interface IClienteService {

    public ClienteDTO create(ClienteDTO clienteDTO) throws ClienteSinDatosEscencialesExeption;

    public  Optional<ClienteDTO> update(ClienteDTO clienteDTO) throws EvitarModificarContenidoInactivoExeption;

    public void delete(Long id);

    public Optional<ClienteDTO> findById(Long id);


}
