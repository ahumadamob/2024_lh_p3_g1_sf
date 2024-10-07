package Grupo1.G1P3LH.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.G1P3LH.entity.Cliente;
import Grupo1.G1P3LH.service.IClienteService;
import Grupo1.G1P3LH.util.ApiResponse;

@RestController
public class ClienteController {
	
	@Autowired
	IClienteService service;

	@GetMapping("/clientes")
	ApiResponse<List<Cliente>> mostrarTodosLosClientes() {
		ApiResponse<List<Cliente>> response = new ApiResponse<>();
		List<Cliente> lista = service.mostrarTodos();
		if (lista.isEmpty()) {
			response.setError("No existen clientes registrados");
		} else {
			response.setData(lista);
		}
		return response;
	}
	
	@GetMapping("/clientes/{id}")
	ApiResponse<Cliente> mostrarClientesPorId(@PathVariable("id") Long id) {
		ApiResponse<Cliente> response = new ApiResponse<>();
		Cliente cliente = service.mostrarPorId(id);
		if (cliente == null) {
			response.setError("No existe este ID " + id.toString());
		} else {
			response.setData(cliente);
		}
		return response;
	}
	
	@PostMapping("/clientes")
	ApiResponse<Cliente> crearRegistro(@RequestBody Cliente cliente) {
		ApiResponse<Cliente> response = new ApiResponse<>();
		if (service.existe(cliente.getId_cliente())) {
			response.setError("Ya existe este cliente");
		} else {
			Cliente clienteGuardado = service.guardar(cliente);
			response.setData(clienteGuardado);
		}
		return response;
	}
	
	@PutMapping("/clientes")
	ApiResponse<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
		ApiResponse<Cliente> response = new ApiResponse<>();
		if (service.existe(cliente.getId_cliente())) {
			Cliente clienteGuardado = service.guardar(cliente);
			response.setData(clienteGuardado);
		} else {
			response.setError("El producto no existe");
		}
		return response;
	}
	
	@DeleteMapping("/clientes/{id}")
	String eliminarCliente(@PathVariable("id") Long id) {
		if (service.existe(id)) {
			service.eliminar(id);
			return "El cliente se eliminó con exito";
		} else {
			return "No existe el ID " + id.toString();
		}
	}
	
}
