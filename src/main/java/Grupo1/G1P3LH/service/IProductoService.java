package Grupo1.G1P3LH.service;

import java.util.List;

import Grupo1.G1P3LH.entity.Producto;

public interface IProductoService {
	public List<Producto> mostrarTodos();

	public Producto mostrarPorId(Long id);

	public Producto guardar(Producto producto);

	public void eliminar(Long id);

	public boolean existe(Long id);
	
	public List<Producto>mostrarPorCategoria(Long id_Categoria);

	public boolean existeCategoria(Long id);

}
