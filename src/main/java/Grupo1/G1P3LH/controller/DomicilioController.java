package Grupo1.G1P3LH.controller;

import Grupo1.G1P3LH.entity.Domicilio;
import Grupo1.G1P3LH.util.DTOApiResponse;
import Grupo1.G1P3LH.util.ResourceNotFoundException;
import Grupo1.G1P3LH.service.IDomicilioService;
import Grupo1.G1P3LH.service.ILocalidadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DomicilioController {

	@Autowired
	private IDomicilioService dService;
	
	@Autowired
	private ILocalidadService lService;

	// Alta de domicilio
	@PostMapping("/domicilio")
	public ResponseEntity<DTOApiResponse<Domicilio>> crearDomicilio(@RequestBody Domicilio domicilio) {
		if (dService.exists(domicilio.getId())) {
			throw new ResourceNotFoundException("El ID ya existe: " + domicilio.getId());
		} else {
			
			if (lService.exists(domicilio.getLocalidad().getId())) {
				Domicilio savedDomicilio = dService.saveDomicilio(domicilio);
				DTOApiResponse<Domicilio> dtoResponse = new DTOApiResponse<>(200, "Domicilio creado correctamente", savedDomicilio);
				return ResponseEntity.ok(dtoResponse);
			}
			else {
				DTOApiResponse<Domicilio> dtoResponse = new DTOApiResponse<>(400, "La localidad no existe", null);
				return ResponseEntity.ok(dtoResponse);
			}
			
		}
	}

	// Actualización de domicilio
	@PutMapping("/domicilio")
	public ResponseEntity<DTOApiResponse<Domicilio>> actualizarDomicilio(@RequestBody Domicilio domicilio) {
		if (!dService.exists(domicilio.getId())) {
			throw new ResourceNotFoundException("El ID no existe: " + domicilio.getId());
		} else {
			Domicilio updatedDomicilio = dService.saveDomicilio(domicilio);
			DTOApiResponse<Domicilio> dtoResponse = new DTOApiResponse<>(200, "Domicilio actualizado correctamente", updatedDomicilio);
			return ResponseEntity.ok(dtoResponse);
		}
	}

	// Lectura de todos los domicilios
	@GetMapping("/domicilios")
	public ResponseEntity<DTOApiResponse<List<Domicilio>>> buscarDomicilios() {
		List<Domicilio> listadoDomicilio = dService.getAllDomicilio();
		DTOApiResponse<List<Domicilio>> dtoResponse = new DTOApiResponse<>(200, "Lista de domicilios obtenida correctamente", listadoDomicilio);
		return ResponseEntity.ok(dtoResponse);
	}

	// Obtener domicilio por ID
	@GetMapping("/domicilio/{id}")
	public ResponseEntity<DTOApiResponse<Domicilio>> buscarDomicilioPorId(@PathVariable("id") Long id) {
		if (!dService.exists(id)) {
			throw new ResourceNotFoundException("No existe este domicilio con ID: " + id);
		}
		Domicilio domicilio = dService.obtenerPorId(id);
		DTOApiResponse<Domicilio> dtoResponse = new DTOApiResponse<>(200, "Domicilio encontrado", domicilio);
		return ResponseEntity.ok(dtoResponse);
	}

	// Eliminar domicilio por ID
	@DeleteMapping("/domicilio/{id}")
	public ResponseEntity<DTOApiResponse<?>> eliminarDomicilio(@PathVariable("id") Long id) {
		if (!dService.exists(id)) {
			throw new ResourceNotFoundException("El domicilio con ID " + id + " no existe.");
		}
		dService.deleteDomicilio(id);
		DTOApiResponse<?> dtoResponse = new DTOApiResponse<>(200, "El domicilio se eliminó con éxito", null);
		return ResponseEntity.ok(dtoResponse);
	}

	// Obtener domicilios por ciudad
	@GetMapping("/domicilios/porCiudad/{ciudad}")
	public ResponseEntity<DTOApiResponse<List<Domicilio>>> buscarDomiciliosPorCiudad(@PathVariable("ciudad") Long ciudad) {
		if (!dService.ciudadExists(ciudad)) {
			throw new ResourceNotFoundException("No existe la ciudad proporcionada: " + ciudad);
		} else {
			List<Domicilio> listadoDomicilio = dService.obtenerDomiciliosPorCiudad(ciudad);
			DTOApiResponse<List<Domicilio>> dtoResponse = new DTOApiResponse<>(200, "Domicilios por ciudad obtenidos correctamente", listadoDomicilio);
			return ResponseEntity.ok(dtoResponse);
		}
	}
}
