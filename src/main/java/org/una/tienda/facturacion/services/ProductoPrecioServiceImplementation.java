package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.ProductoPrecio;
import org.una.tienda.facturacion.repositories.IProductoPrecioRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

@Service
public class ProductoPrecioServiceImplementation implements IProductoPrecioService {

    @Autowired
    private IProductoPrecioRepository productoRepository;

    private Optional<ProductoPrecioDTO> oneToDto(Optional<ProductoPrecio> one) {
        if (one.isPresent()) {
            ProductoPrecioDTO productoPrecioDTO = MapperUtils.DtoFromEntity(one.get(), ProductoPrecioDTO.class);
            return Optional.ofNullable(productoPrecioDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ProductoPrecioDTO create(ProductoPrecioDTO productoPrecioDTO) {
        ProductoPrecio productoPrecio = MapperUtils.EntityFromDto(productoPrecioDTO, ProductoPrecio.class);
        productoPrecio = productoRepository.save(productoPrecio);
        return MapperUtils.DtoFromEntity(productoPrecio, ProductoPrecioDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<ProductoPrecioDTO> findById(Long id) {
        return oneToDto(productoRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO precioDTO, Long id) {
        if (productoRepository.findById(id).isPresent()) {
            ProductoPrecio productoPrecio = MapperUtils.EntityFromDto(precioDTO, ProductoPrecio.class);
            productoPrecio = productoRepository.save(productoPrecio);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(productoPrecio, ProductoPrecioDTO.class));
        } else {
            return null;
        }
    }
}
