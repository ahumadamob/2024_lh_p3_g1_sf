package Grupo1.G1P3LH.controller;

import java.util.ArrayList;
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
import Grupo1.G1P3LH.util.DTOResponse;
import Grupo1.G1P3LH.util.ResourceNotFoundException;
import jakarta.validation.Valid;

@RestController
public class ClienteController {

	@Autowired
	IClienteService service;

	@GetMapping("/clientes")
	ResponseEntity<DTOResponse<List<Cliente>>> mostrarTodosLosClientes() {
		List<Cliente> lista = service.mostrarTodos();
		DTOResponse<List<Cliente>> dto = new DTOResponse<>(200, "", lista);

		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

	@GetMapping("/clientes/vip")
	ResponseEntity<DTOResponse<List<Cliente>>> mostrarClientesVIP() {
		List<Cliente> lista = service.obtenerClientesVIP();
		DTOResponse<List<Cliente>> dto = new DTOResponse<>(200, "", lista);

		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

	@GetMapping("/clientes/{id}")
	ResponseEntity<DTOResponse<Cliente>> mostrarClientesPorId(@PathVariable("id") Long id) {

		if (service.existe(id)) {
			DTOResponse<Cliente> dto = new DTOResponse<>(200, "", service.mostrarPorId(id));

			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {

			throw new ResourceNotFoundException("No existe este cliente: " + id);

		}

	}

	@PostMapping("/clientes")
	ResponseEntity<DTOResponse<Cliente>> crearRegistro(@Valid @RequestBody Cliente cliente) {
		if (service.existe(cliente.getId_cliente())) {

			throw new ResourceNotFoundException("Ya existe este cliente: " + cliente.getId_cliente());
		} else {
			// Exito
			DTOResponse<Cliente> dto = new DTOResponse<Cliente>(201, "El cliente se creó con exito",
					service.guardar(cliente));
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}

	}

	@PutMapping("/clientes")
	ResponseEntity<DTOResponse<Cliente>> actualizarCliente(@Valid @RequestBody Cliente cliente) {

		if (service.existe(cliente.getId_cliente())) {
			DTOResponse<Cliente> dto = new DTOResponse<Cliente>(200, "EL cliente se actualizó con exito",
					service.guardar(cliente));
			return ResponseEntity.ok().body(dto);
		} else {

			throw new ResourceNotFoundException("No existe este cliente: " + cliente.getId_cliente());
		}

	}

	@DeleteMapping("/clientes/{id}")
	ResponseEntity<DTOResponse<?>> eliminarCliente(@PathVariable("id") Long id) {
		if (service.existe(id)) {
			service.eliminar(id);
			DTOResponse<?> dtoSi = new DTOResponse<>(200, "El cliente se eliminó", null);
			return ResponseEntity.status(HttpStatus.OK).body(dtoSi);
		} else {

			throw new ResourceNotFoundException("El cliente con ID " + id + " no existe.");

		}
	}

}
