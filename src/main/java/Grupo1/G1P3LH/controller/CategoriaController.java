package Grupo1.G1P3LH.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Grupo1.G1P3LH.entity.Categoria;
import Grupo1.G1P3LH.service.ICategoriaService;
import Grupo1.G1P3LH.util.DTOResponse;

@RestController
public class CategoriaController {

	@Autowired
	ICategoriaService servi;

	@GetMapping("/categorias")
	public ResponseEntity<DTOResponse<List<Categoria>>> mostrarTodasLasCategorias() {
		List<Categoria> listCategoria = servi.mostrarTodos();

		if (listCategoria.isEmpty()) {
			DTOResponse<List<Categoria>> responseDto = new DTOResponse<>(404, "No hay categorías que mostrar", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
		} else {
			DTOResponse<List<Categoria>> responseDto = new DTOResponse<>(200, "", listCategoria);
			return ResponseEntity.ok().body(responseDto);
		}
	}

	@GetMapping("/categorias/{id}")
	public ResponseEntity<DTOResponse<Categoria>> mostrarCategoriaPorId(@PathVariable("id") Long id) {
		Categoria categoria = servi.mostrarPorId(id);
		if (categoria != null) {
			DTOResponse<Categoria> responseDto = new DTOResponse<>(200, "", categoria);
			return ResponseEntity.ok().body(responseDto);
		} else {
			DTOResponse<Categoria> responseDto = new DTOResponse<>(404, "El id " + id + " no existe", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
		}
	}

	//nuevo endpoint solicitado
	@GetMapping("/categorias/activas/count")
	public ResponseEntity<DTOResponse<Integer>> contarCategoriasActivas() {
		int totalActivas = servi.contarCategoriasActivas();

		if (totalActivas > 0) {
			DTOResponse<Integer> responseDto = new DTOResponse<>(200, "", totalActivas);
			return ResponseEntity.ok().body(responseDto);
		} else {
			DTOResponse<Integer> responseDto = new DTOResponse<>(204, "No hay categorías activas.", null);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDto);
		}
	}

	@PostMapping("/categorias")
	public ResponseEntity<DTOResponse<Categoria>> crearCategoria(@RequestBody Categoria categoria) {
		if (servi.existe(categoria.getId())) {
			DTOResponse<Categoria> responseDto = new DTOResponse<>(404, "El id " + categoria.getId().toString() + " ya existe", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
		} else {
			Categoria nuevaCategoria = servi.guardar(categoria);
			DTOResponse<Categoria> responseDto = new DTOResponse<>(201, "", nuevaCategoria);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		}
	}

	@PutMapping("/categorias")
	public ResponseEntity<DTOResponse<Categoria>> modificarCategoria(@RequestBody Categoria categoria) {
		if (servi.existe(categoria.getId())) {
			DTOResponse<Categoria> responseDto = new DTOResponse<>(200, "", servi.guardar(categoria));
			return ResponseEntity.ok().body(responseDto);
		} else {
			DTOResponse<Categoria> responseDto = new DTOResponse<>(404, "El id " + categoria.getId().toString() + " no existe", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
		}
	}


	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<DTOResponse<String>> eliminarCategoria(@PathVariable("id") Long id) {
		if (servi.existe(id)) {
			servi.eliminar(id);
			DTOResponse<String> responseDto = new DTOResponse<>(200, "", null);
			return ResponseEntity.ok().body(responseDto);
		} else {
			DTOResponse<String> responseDto = new DTOResponse<>(404, "No existe la categoría con el id " + id, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
		}
	}

	// Controlador de excepciones
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<DTOResponse<List<String>>> exceptionController(ConstraintViolationException e) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}
		DTOResponse<List<String>> response = new DTOResponse<>(400, errors, null);
		return ResponseEntity.badRequest().body(response);
	}
}
