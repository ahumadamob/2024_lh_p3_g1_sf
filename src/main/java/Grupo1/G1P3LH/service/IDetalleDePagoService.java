package Grupo1.G1P3LH.service;

import java.util.List;

import Grupo1.G1P3LH.entity.DetalleDePago;

public interface IDetalleDePagoService {
	public List<DetalleDePago>mostrarTodos();
	public DetalleDePago mostrarPorId(Long id);
	public DetalleDePago guardar(DetalleDePago detalle);
	public void eliminar(Long id);
	public boolean existe(Long id);

}
