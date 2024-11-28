package Grupo1.G1P3LH.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.G1P3LH.entity.Cliente;
import Grupo1.G1P3LH.service.IClienteService;
import Grupo1.G1P3LH.util.DTOApiResponse;
import Grupo1.G1P3LH.util.ResourceNotFoundException;
import jakarta.validation.Valid;

@RestController
public class ClienteController {

	@Autowired
	IClienteService service;

	@GetMapping("/clientes")
	ResponseEntity<DTOApiResponse<List<Cliente>>> mostrarTodosLosClientes() {
		List<Cliente> lista = service.mostrarTodos();
		DTOApiResponse<List<Cliente>> dto = new DTOApiResponse<>(200, "", lista);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@GetMapping("/clientes/vip")
	ResponseEntity<DTOApiResponse<List<Cliente>>> mostrarClientesVIP() {
		List<Cliente> lista = service.obtenerClientesVIP();
		DTOApiResponse<List<Cliente>> dto = new DTOApiResponse<>(200, "", lista);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@GetMapping("/clientes/{id}")
	ResponseEntity<DTOApiResponse<Cliente>> mostrarClientesPorId(@PathVariable("id") Long id) {

		if (service.existe(id)) {
			DTOApiResponse<Cliente> dto = new DTOApiResponse<>(200, "", service.mostrarPorId(id));
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			throw new ResourceNotFoundException("No existe este cliente: " + id);
		}
	}

	@PostMapping("/clientes")
	ResponseEntity<DTOApiResponse<Cliente>> crearRegistro(@Valid @RequestBody Cliente cliente) {
		if (service.existe(cliente.getId_cliente())) {
			DTOApiResponse<Cliente> dto = new DTOApiResponse<>(HttpStatus.BAD_REQUEST.value(),
					"Ya existe este cliente: " + cliente.getId_cliente());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
		} else {
			DTOApiResponse<Cliente> dto = new DTOApiResponse<>(201, "El cliente se creó con éxito",
					service.guardar(cliente));
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}
	}

	@PostMapping("/api/clientes")

	public ResponseEntity<DTOApiResponse<Cliente>> crearCliente(@Valid @RequestBody Cliente cliente) {

		if (service.findByDni(cliente.getDni())) {

			throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + cliente.getDni());
		}

		Cliente nuevoCliente = service.crearCliente(cliente);

		if (!nuevoCliente.getActivo()) {
			System.out.println("Se ha creado un cliente inactivo: " + nuevoCliente.getApellido() + ", "
					+ nuevoCliente.getNombre());
		}

		DTOApiResponse<Cliente> response = new DTOApiResponse<>(HttpStatus.CREATED.value(), "Cliente creado con éxito",
				nuevoCliente);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/clientes")
	ResponseEntity<DTOApiResponse<Cliente>> actualizarCliente(@Valid @RequestBody Cliente cliente) {

		if (service.existe(cliente.getId_cliente())) {
			DTOApiResponse<Cliente> dto = new DTOApiResponse<>(200, "El cliente se actualizó con éxito",
					service.guardar(cliente));
			return ResponseEntity.ok().body(dto);
		} else {
			throw new ResourceNotFoundException("No existe este cliente: " + cliente.getId_cliente());
		}
	}

	@DeleteMapping("/clientes/{id}")
	ResponseEntity<DTOApiResponse<?>> eliminarCliente(@PathVariable("id") Long id) {
		if (service.existe(id)) {
			service.eliminar(id);
			DTOApiResponse<?> dto = new DTOApiResponse<>(200, "El cliente se eliminó", null);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			throw new ResourceNotFoundException("El cliente con ID " + id + " no existe.");
		}
	}
}
