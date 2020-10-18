package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.ProductoExistenciaDTO;
import org.una.tienda.facturacion.entities.ProductoExistencia;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.repositories.IProductoExistenciaRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

@Service
public class ProductoExistenciaServiceImplementation implements IProductoExistenciaService {

    @Autowired
    private IProductoExistenciaRepository productoExistenciaRepository;

    private Optional<ProductoExistenciaDTO> oneToDto(Optional<ProductoExistencia> one) {
        if (one.isPresent()) {
            ProductoExistenciaDTO productoExistenciaDTO = MapperUtils.DtoFromEntity(one.get(), ProductoExistenciaDTO.class);
            return Optional.ofNullable(productoExistenciaDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ProductoExistenciaDTO create(ProductoExistenciaDTO productoExistenciaDTO) {
        ProductoExistencia productoExistencia = MapperUtils.EntityFromDto(productoExistenciaDTO, ProductoExistencia.class);
        productoExistencia = productoExistenciaRepository.save(productoExistencia);
        return MapperUtils.DtoFromEntity(productoExistencia, ProductoExistenciaDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productoExistenciaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoExistenciaDTO> findById(Long id) {
        return oneToDto(productoExistenciaRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoExistenciaDTO>findByProductoId(Long id) {
        return oneToDto(productoExistenciaRepository.findByProductoIdId(id));
    }


    @Override
    @Transactional
    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO productoExistenciaDTO, Long id) throws EvitarModificarContenidoInactivoExeption {
        if (productoExistenciaRepository.findById(id).isPresent()) {
            if(!productoExistenciaDTO.isEstado()) throw new EvitarModificarContenidoInactivoExeption("No se puede modificar un producto existente inactivo");
            ProductoExistencia productoExistencia = MapperUtils.EntityFromDto(productoExistenciaDTO, ProductoExistencia.class);
            productoExistencia = productoExistenciaRepository.save(productoExistencia);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(productoExistencia, ProductoExistenciaDTO.class));
        } else {
            return null;
        }
    }
}
