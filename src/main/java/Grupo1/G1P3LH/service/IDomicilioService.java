package Grupo1.G1P3LH.service;

import Grupo1.G1P3LH.entity.Domicilio;

import java.util.List;

public interface IDomicilioService {
	public Domicilio saveDomicilio(Domicilio domicilio);

	public void deleteDomicilio(Long id);

	public List<Domicilio> getAllDomicilio();

	public Domicilio getDomicilio(Long id);

	public boolean exists(Long id);

	public Domicilio obtenerPorId(Long id);

	public List<Domicilio> obtenerDomiciliosPorCiudad(long ciudad);

	public boolean ciudadExists(Long id);
}
