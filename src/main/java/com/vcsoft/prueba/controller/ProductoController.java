package com.vcsoft.prueba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vcsoft.prueba.entity.Producto;
import com.vcsoft.prueba.service.ProductoService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController 
@RequestMapping("api/v1/productos")
public class ProductoController {
    
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @GetMapping 
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.ListarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
        .map (producto -> ResponseEntity.ok(producto))
        .orElse (ResponseEntity.notFound().build());
    }
    

    @PostMapping 
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevProducto = productoService.guardarProducto(producto);
            return new ResponseEntity<>(nuevProducto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        return productoService.obtenerPorId(id)
        .map (productoExistente -> {
            productoExistente.setNombreProducto(productoDetalles.getNombreProducto());
            productoExistente.setDescripcionProducto(productoDetalles.getDescripcionProducto());
            productoExistente.setPrecioProducto(productoDetalles.getPrecioProducto());
            productoExistente.setCantidad(productoDetalles.getCantidad());

            Producto actualizado = productoService.guardarProducto(productoExistente);
            return ResponseEntity.ok(actualizado);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/menor-consonantes")
    public ResponseEntity<Producto> obtenerMenorConsonantes() {
        return ResponseEntity.ok(productoService.obtenerProductoConMenosConsonantes());
    }
    
}
