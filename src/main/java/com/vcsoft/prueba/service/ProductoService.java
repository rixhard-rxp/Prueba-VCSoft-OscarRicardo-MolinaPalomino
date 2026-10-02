package com.vcsoft.prueba.service;

import org.springframework.stereotype.Service;
import com.vcsoft.prueba.entity.Producto;
import com.vcsoft.prueba.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service 
public class ProductoService {
    
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    public List<Producto> ListarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId (Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Producto obtenerProductoConMenosConsonantes() {
        List<Producto> productos = productoRepository.findAll();
        if (productos.isEmpty()) return null;

        Producto menorProducto = null;
        int menosConsonantes = Integer.MAX_VALUE;

        for (Producto p : productos) {
            if (p.getNombreProducto() != null) {
                String soloConsonantes = p.getNombreProducto().toLowerCase().replaceAll("[aeiou]", "");
                int cantidadDeConsonantes = soloConsonantes.length();

                if (cantidadDeConsonantes < menosConsonantes) {
                    menosConsonantes = cantidadDeConsonantes;
                    menorProducto = p;
                }
            }
        }
    return menorProducto;
    }

}
