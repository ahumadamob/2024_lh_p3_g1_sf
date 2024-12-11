package Grupo1.G1P3LH.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.G1P3LH.entity.Localidad;
import Grupo1.G1P3LH.service.ILocalidadService;
import Grupo1.G1P3LH.util.DTOApiResponse;
import Grupo1.G1P3LH.util.ResourceNotFoundException;

@RestController
public class LocalidadController {
	
	@Autowired
	private ILocalidadService lService;

	// Alta de localidad
	@PostMapping("/localidad")
	public ResponseEntity<DTOApiResponse<Localidad>> crearLocalidad(@RequestBody Localidad localidad) {
		if (lService.exists(localidad.getId())) {
			throw new ResourceNotFoundException("El ID ya existe: " + localidad.getId());
		} else {
			Localidad savedLocalidad = lService.saveLocalidad(localidad);
			DTOApiResponse<Localidad> dtoResponse = new DTOApiResponse<>(200, "Localidad creada correctamente", savedLocalidad);
			return ResponseEntity.ok(dtoResponse);
		}
	}

	// Actualización de localidad
	@PutMapping("/localidad")
	public ResponseEntity<DTOApiResponse<Localidad>> actualizarLocalidad(@RequestBody Localidad localidad) {
		if (!lService.exists(localidad.getId())) {
			throw new ResourceNotFoundException("El ID no existe: " + localidad.getId());
		} else {
			Localidad updatedLocalidad = lService.saveLocalidad(localidad);
			DTOApiResponse<Localidad> dtoResponse = new DTOApiResponse<>(200, "Localidad actualizada correctamente", updatedLocalidad);
			return ResponseEntity.ok(dtoResponse);
		}
	}

	// Lectura de todos las localidades
	@GetMapping("/localidades")
	public ResponseEntity<DTOApiResponse<List<Localidad>>> buscarLocalidades() {
		List<Localidad> listadoLocalidad = lService.getAllLocalidad();
		DTOApiResponse<List<Localidad>> dtoResponse = new DTOApiResponse<>(200, "Lista de localidades obtenida correctamente", listadoLocalidad);
		return ResponseEntity.ok(dtoResponse);
	}

	// Obtener localidad por ID
	@GetMapping("/localidad/{id}")
	public ResponseEntity<DTOApiResponse<Localidad>> buscarLocalidadPorId(@PathVariable("id") Long id) {
		if (!lService.exists(id)) {
			throw new ResourceNotFoundException("No existe la localidad con el ID: " + id);
		}
		Localidad localidad = lService.obtenerPorId(id);
		DTOApiResponse<Localidad> dtoResponse = new DTOApiResponse<>(200, "Localidad encontrada", localidad);
		return ResponseEntity.ok(dtoResponse);
	}

	// Eliminar domicilio por ID
	@DeleteMapping("/localidad/{id}")
	public ResponseEntity<DTOApiResponse<?>> eliminarLocalidad(@PathVariable("id") Long id) {
		if (!lService.exists(id)) {
			throw new ResourceNotFoundException("La localidad con ID " + id + " no existe.");
		}
		lService.deleteLocalidad(id);
		DTOApiResponse<?> dtoResponse = new DTOApiResponse<>(200, "La localidad se eliminó con éxito", null);
		return ResponseEntity.ok(dtoResponse);
	}

}
