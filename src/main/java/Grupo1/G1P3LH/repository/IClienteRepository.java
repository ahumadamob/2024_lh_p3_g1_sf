package Grupo1.G1P3LH.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Grupo1.G1P3LH.entity.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByEsVipTrue();

	Optional<Cliente> findByDni(int dni);

	boolean existsByDni(int dni);
	
}
