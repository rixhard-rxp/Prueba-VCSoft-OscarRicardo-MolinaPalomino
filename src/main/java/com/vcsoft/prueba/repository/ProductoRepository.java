package com.vcsoft.prueba.repository;

import com.vcsoft.prueba.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
