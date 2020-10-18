package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.entities.Factura;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.repositories.IFacturaRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FacturaServiceImplementation implements IFacturaService{

    @Autowired
    private IFacturaRepository facturaRepository;
    @Autowired
    private IProductoPrecioService productoPrecioService;

    private Optional<FacturaDTO> oneToDto(Optional<Factura> one) {
        if (one.isPresent()) {
            FacturaDTO FacturaDTO = MapperUtils.DtoFromEntity(one.get(),   FacturaDTO.class);
            return Optional.ofNullable(FacturaDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDTO> findById(Long id) {
        return oneToDto(facturaRepository.findById(id));
    }

    @Override
    @Transactional
    public FacturaDTO create(FacturaDTO facturaDTO) {
        AtomicReference<Double> suma = new AtomicReference<>((double) 0);
        List<FacturaDetalleDTO> facturaDetalleList = facturaDTO.getFacturaDetalleList();

      
        Factura usuario = MapperUtils.EntityFromDto(facturaDTO, Factura.class);
        usuario = facturaRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, FacturaDTO.class);
    }



    @Override
    @Transactional
    public void delete(Long id) {
        if (facturaRepository.findById(id).isPresent()) {
            facturaRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Optional<FacturaDTO> update(FacturaDTO facturaDTO) throws EvitarModificarContenidoInactivoExeption {
        if (facturaRepository.findById(facturaDTO.getId()).isPresent()) {
            if(!facturaDTO.getEstado()) throw new EvitarModificarContenidoInactivoExeption("No se puede modificar una factura inactivo");
            Factura factura = MapperUtils.EntityFromDto(facturaDTO, Factura.class);
            factura = facturaRepository.save(factura);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(factura, FacturaDTO.class));
        } else {
            return null;
        }
    }
}
