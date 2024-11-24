package Grupo1.G1P3LH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Grupo1.G1P3LH.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> findByCategoria_Id(Long categoriaId);
	
}
