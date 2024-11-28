package Grupo1.G1P3LH.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.Cliente;
import Grupo1.G1P3LH.repository.IClienteRepository;
import Grupo1.G1P3LH.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository repo;

	@Override
	public List<Cliente> mostrarTodos() {
		return repo.findAll();
	}

	@Override
	public Cliente mostrarPorId(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Cliente guardar(Cliente cliente) {
		return repo.save(cliente);
	}

	@Override
	public void eliminar(Long id) {
		repo.deleteById(id);

	}

	@Override
	public boolean existe(Long id) {
		if (id == null) {
			return false;
		} else {
			return repo.existsById(id);
		}
	}

	@Override
	public List<Cliente> obtenerClientesVIP() {
		return repo.findByEsVipTrue();
	}

	public Cliente crearCliente(Cliente cliente) {

		if (repo.findByDni(cliente.getDni()).isPresent()) {
			throw new IllegalArgumentException("El DNI ya está registrado");
		} else {
			if (!cliente.getActivo()) {
				System.out.println(
						"Se ha creado un cliente inactivo: " + cliente.getApellido() + ", " + cliente.getNombre());
			}
			return repo.save(cliente);
		}

	}

}
