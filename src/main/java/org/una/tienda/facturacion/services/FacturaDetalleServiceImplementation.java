package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.FacturaDetalleDTO;
import org.una.tienda.facturacion.dto.ProductoExistenciaDTO;
import org.una.tienda.facturacion.dto.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.exceptions.EvitarModificarContenidoInactivoExeption;
import org.una.tienda.facturacion.exceptions.FacturaCantidadCero;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.exceptions.ProductoSinExistencia;
import org.una.tienda.facturacion.repositories.IFacturaDetalleRepository;
import org.una.tienda.facturacion.repositories.IProductoPrecioRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

import java.util.Optional;
@Service
public class FacturaDetalleServiceImplementation implements IFacturaDetalleService {


    @Autowired
    private IFacturaDetalleRepository facturaDetalleRepository;
    @Autowired
    private IProductoPrecioService productoPrecioService;
    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    private Optional<FacturaDetalleDTO> oneToDto(Optional<FacturaDetalle> one) {
        if (one.isPresent()) {
            FacturaDetalleDTO FacturaDetalleDTO = MapperUtils.DtoFromEntity(one.get(),   FacturaDetalleDTO.class);
            return Optional.ofNullable(FacturaDetalleDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDetalleDTO> findById(Long id) {
        return oneToDto(facturaDetalleRepository.findById(id));
    }

    @Override
    @Transactional
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalle) throws ProductoConDescuentoMayorAlPermitidoException, FacturaCantidadCero, ProductoSinExistencia {

        Optional<ProductoPrecioDTO> productoPrecio = productoPrecioService.findByProductoId(facturaDetalle.getProducto().getId());
        Optional<ProductoExistenciaDTO> productoExistencia = productoExistenciaService.findByProductoId(facturaDetalle.getProducto().getId());
        System.out.println(productoExistencia.get().getCantidad());
        if (productoPrecio.isEmpty()) {
            //TODO:implementar verificar existencia de asignacion de precios
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un sin precio registrado");
        }
        if (facturaDetalle.getDescuentoFinal() > productoPrecio.get().getDescuentoMaximo()) {
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un producto con un descuento mayor al permitido");
        }
        if(facturaDetalle.getCantidad() == 0.0) throw new FacturaCantidadCero("No se puede facturar un producto con cantidad 0");
        if(productoExistencia.isEmpty()){
            throw new ProductoSinExistencia("Se intenta facturar un producto sin exitencia registrada");
        }
        if(productoExistencia.get().getCantidad() <= 0) throw new ProductoSinExistencia("No se puede facturar un producto sin exitencia");

        FacturaDetalle usuario = MapperUtils.EntityFromDto(facturaDetalle, FacturaDetalle.class);
        usuario = facturaDetalleRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, FacturaDetalleDTO.class);
    }




    @Override
    @Transactional
    public void delete(Long id) {
        if (facturaDetalleRepository.findById(id).isPresent()) {
            facturaDetalleRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO) throws EvitarModificarContenidoInactivoExeption {
        if (facturaDetalleRepository.findById(facturaDetalleDTO.getId()).isPresent()) {
            if(!facturaDetalleDTO.isEstado()) throw new EvitarModificarContenidoInactivoExeption("No se puede modificar un factura detalle inactivo");
            FacturaDetalle facturaDetalle = MapperUtils.EntityFromDto(facturaDetalleDTO, FacturaDetalle.class);
            facturaDetalle = facturaDetalleRepository.save(facturaDetalle);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(facturaDetalle, FacturaDetalleDTO.class));
        } else {
            return null;
        }
    }
    
}
