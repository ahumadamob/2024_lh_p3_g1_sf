package Grupo1.G1P3LH.controller;

import Grupo1.G1P3LH.entity.Domicilio;
import Grupo1.G1P3LH.util.DTOResponse;
import Grupo1.G1P3LH.util.ResourceNotFoundException;
import Grupo1.G1P3LH.service.IDomicilioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DomicilioController {

	@Autowired
	private IDomicilioService dService;

	// Alta de domicilio
	@PostMapping("/domicilio")
	public ResponseEntity<DTOResponse<Domicilio>> crearDomicilio(@RequestBody Domicilio domicilio) {
		if (dService.exists(domicilio.getId())) {
			throw new ResourceNotFoundException("El ID ya existe: " + domicilio.getId());
		} else {
			Domicilio savedDomicilio = dService.saveDomicilio(domicilio);
			DTOResponse<Domicilio> dtoResponse = new DTOResponse<>(200, "", savedDomicilio);
			return ResponseEntity.ok(dtoResponse);
		}
	}

	// Actualización de domicilio
	@PutMapping("/domicilio")
	public ResponseEntity<DTOResponse<Domicilio>> actualizarDomicilio(@RequestBody Domicilio domicilio) {
		if (!dService.exists(domicilio.getId())) {
			throw new ResourceNotFoundException("El ID no existe: " + domicilio.getId());
		} else {
			Domicilio updatedDomicilio = dService.saveDomicilio(domicilio);
			DTOResponse<Domicilio> dto = new DTOResponse<>(200, "", updatedDomicilio);
			return ResponseEntity.ok(dto);
		}
	}

	// Lectura de todos los domicilios
	@GetMapping("/domicilios")
	public ResponseEntity<DTOResponse<List<Domicilio>>> buscarDomicilios() {
		List<Domicilio> listadoDomicilio = dService.getAllDomicilio();
		DTOResponse<List<Domicilio>> dtoResponse = new DTOResponse<>(200, "", listadoDomicilio);
		return ResponseEntity.ok(dtoResponse);
	}

	// Obtener domicilio por ID
	@GetMapping("/domicilio/{id}")
	public ResponseEntity<DTOResponse<Domicilio>> buscarDomicilioPorId(@PathVariable("id") Long id) {
		if (!dService.exists(id)) {
			throw new ResourceNotFoundException("No existe este domicilio con ID: " + id);
		}
		Domicilio domicilio = dService.obtenerPorId(id);
		DTOResponse<Domicilio> dto = new DTOResponse<>(200, "", domicilio);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/domicilio/{id}")
	public ResponseEntity<DTOResponse<?>> eliminarDomicilio(@PathVariable("id") Long id) {
		if (!dService.exists(id)) {
			throw new ResourceNotFoundException("El domicilio con ID " + id + " no existe.");
		}
		dService.deleteDomicilio(id);
		DTOResponse<?> dtoSi = new DTOResponse<>(200, "El domicilio se eliminó con éxito", null);
		return ResponseEntity.ok(dtoSi);
	}

	@GetMapping("/domicilios/porCiudad/{ciudad}")
	public ResponseEntity<DTOResponse<List<Domicilio>>> buscarDomiciliosPorCiudad(@PathVariable("ciudad") Long ciudad) {
		if (!dService.ciudadExists(ciudad)) {
			throw new ResourceNotFoundException("No existe la ciudad proporcionada: " + ciudad);
		} else{
			List<Domicilio> listadoDomicilio = dService.obtenerDomiciliosPorCiudad(ciudad);
			DTOResponse<List<Domicilio>> dtoResponse = new DTOResponse<>(200, "", listadoDomicilio);
			return ResponseEntity.ok(dtoResponse);
		}

	}
}