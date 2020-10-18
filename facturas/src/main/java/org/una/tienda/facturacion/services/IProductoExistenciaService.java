package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.ProductoExistenciaDTO;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;

public interface IProductoExistenciaService {

    public ProductoExistenciaDTO create(ProductoExistenciaDTO productoExistenciaDTO);

    public void delete(Long id);

    public Optional<ProductoExistenciaDTO> findById(Long id);

    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO productoExistenciaDTO, Long id) throws EvitarModificarContenidoInactivoExeption;
}
