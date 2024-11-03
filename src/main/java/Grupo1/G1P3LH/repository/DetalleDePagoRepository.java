package Grupo1.G1P3LH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Grupo1.G1P3LH.entity.DetalleDePago;

public interface DetalleDePagoRepository extends JpaRepository<DetalleDePago,Long>{
	List<DetalleDePago> findByEstadoPago(String estadoPago);

}
