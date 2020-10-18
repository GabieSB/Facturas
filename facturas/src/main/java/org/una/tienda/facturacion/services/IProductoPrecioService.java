package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

public interface IProductoPrecioService {

    public ProductoPrecioDTO create(ProductoPrecioDTO productoDTO);

    public Optional<ProductoPrecioDTO> findByProductoId(Long id);

    public void delete(Long id);

    public Optional<ProductoPrecioDTO> findById(Long id);

    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO precioDTO, Long id) throws EvitarModificarContenidoInactivoExeption;
}
