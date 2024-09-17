package Grupo1.G1P3LH.service;

import java.util.List;

import Grupo1.G1P3LH.entity.Categoria;

public interface ICategoriaService {
	public List<Categoria>mostrarTodos();
	public Categoria mostrarPorId(Long id);
	public Categoria guardar(Categoria producto);
	public void eliminar(Long id);
	public boolean existe(Long id);
}
