package Grupo1.G1P3LH.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Grupo1.G1P3LH.entity.Localidad;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

}