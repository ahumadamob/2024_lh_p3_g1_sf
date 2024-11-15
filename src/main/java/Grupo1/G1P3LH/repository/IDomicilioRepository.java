package Grupo1.G1P3LH.repository;

import Grupo1.G1P3LH.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Long> {
    List<Domicilio> findDomicilioByCodigoPostal(long codigoPostal);

    boolean existsByCodigoPostal(Long ciudad);
}
