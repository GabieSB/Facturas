package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.ProductoPrecioDTO;

public interface IProductoPrecioService {

    public ProductoPrecioDTO create(ProductoPrecioDTO productoDTO);

    public void delete(Long id);

    public Optional<ProductoPrecioDTO> findById(Long id);

    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO precioDTO, Long id);
}
