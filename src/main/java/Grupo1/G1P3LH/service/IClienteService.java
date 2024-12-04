package Grupo1.G1P3LH.service;

import java.util.List;

import Grupo1.G1P3LH.entity.Cliente;

public interface IClienteService {

	public List<Cliente> mostrarTodos();

	public Cliente mostrarPorId(Long id);

	public Cliente guardar(Cliente cliente);

	public void eliminar(Long id);

	public boolean existe(Long id);

	List<Cliente> obtenerClientesVIP();

	Cliente crearCliente(Cliente cliente);

	public boolean findByDni(int dni);

}
