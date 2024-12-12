package Grupo1.G1P3LH.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Grupo1.G1P3LH.entity.DetalleDePago;

public interface DetalleDePagoRepository extends JpaRepository<DetalleDePago,Long>{
	Optional<DetalleDePago> findByVentaIdYMetodoDePagoYEstadoPago(Long ventaId, String metodoDePago, String estadoPago);

}
