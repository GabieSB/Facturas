package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.exceptions.ClienteSinDatosEscencialesExeption;
import org.una.tienda.facturacion.exceptions.EModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.repositories.IClienteRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

import java.util.Optional;
@Service
public class ClienteServiceImplementation implements  IClienteService {


    @Autowired
    private IClienteRepository clienteRepository;

    private Optional<ClienteDTO> oneToDto(Optional<Cliente> one) {
        if (one.isPresent()) {
            ClienteDTO ClienteDTO = MapperUtils.DtoFromEntity(one.get(),   ClienteDTO.class);
            return Optional.ofNullable(ClienteDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findById(Long id) {
        return oneToDto(clienteRepository.findById(id));
    }

    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO clienteDTO) throws ClienteSinDatosEscencialesExeption {
        if(clienteDTO.getTelefono() == null || clienteDTO.getEmail() == null || clienteDTO.getDireccion() ==null ){
            throw new ClienteSinDatosEscencialesExeption("Se requiere correo, número de teléfono y dirección");
        }
        Cliente usuario = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
        usuario = clienteRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, ClienteDTO.class);
    }





    @Override
    @Transactional
    public void delete(Long id) {
        if (clienteRepository.findById(id).isPresent()) {
            clienteRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> update(ClienteDTO clienteDTO) throws EModificarContenidoInactivoExeption {
        if (clienteRepository.findById(clienteDTO.getId()).isPresent()) {

            if(!clienteDTO.getEstado()) throw new EModificarContenidoInactivoExeption("No se puede modificar un cliente inactivo");
            Cliente cliente = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
            cliente = clienteRepository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, ClienteDTO.class));
        } else {
            return null;

        }
    }

}
