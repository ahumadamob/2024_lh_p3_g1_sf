package Grupo1.G1P3LH.service;

import java.util.List;

import Grupo1.G1P3LH.entity.Localidad;

public interface ILocalidadService {

	public Localidad saveLocalidad(Localidad localidad);

	public void deleteLocalidad(Long id);

	public List<Localidad> getAllLocalidad();

	public Localidad getLocalidad(Long id);

	public boolean exists(Long id);

	public Localidad obtenerPorId(Long id);

}
