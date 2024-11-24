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
import Grupo1.G1P3LH.util.DTOApiResponse;

@RestController
public class CategoriaController {

    @Autowired
    ICategoriaService servi;

    @GetMapping("/categorias")
    public ResponseEntity<DTOApiResponse<List<Categoria>>> mostrarTodasLasCategorias() {
        List<Categoria> listCategoria = servi.mostrarTodos();

        if (listCategoria.isEmpty()) {
            DTOApiResponse<List<Categoria>> responseDto = new DTOApiResponse<>(404, "No hay categorías que mostrar", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } else {
            DTOApiResponse<List<Categoria>> responseDto = new DTOApiResponse<>(200, "", listCategoria);
            return ResponseEntity.ok().body(responseDto);
        }
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<DTOApiResponse<Categoria>> mostrarCategoriaPorId(@PathVariable("id") Long id) {
        Categoria categoria = servi.mostrarPorId(id);
        if (categoria != null) {
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(200, "", categoria);
            return ResponseEntity.ok().body(responseDto);
        } else {
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(404, "El id " + id + " no existe", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    // Endpoint sugerido
    @GetMapping("/categorias/activas/count")
    public ResponseEntity<DTOApiResponse<Integer>> contarCategoriasActivas() {
        int cantidadActivas = servi.contarCategoriasActivas();
        DTOApiResponse<Integer> responseDto = new DTOApiResponse<>(200, "Conteo de categorías activas", cantidadActivas);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/categorias")
    public ResponseEntity<DTOApiResponse<Categoria>> crearCategoria(@RequestBody Categoria categoria) {
        if (servi.existe(categoria.getId())) {
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(404, "El id " + categoria.getId().toString() + " ya existe", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } else {
            Categoria nuevaCategoria = servi.guardar(categoria);
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(201, "", nuevaCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }
    }

    @PutMapping("/categorias")
    public ResponseEntity<DTOApiResponse<Categoria>> modificarCategoria(@RequestBody Categoria categoria) {
        if (servi.existe(categoria.getId())) {
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(200, "", servi.guardar(categoria));
            return ResponseEntity.ok().body(responseDto);
        } else {
            DTOApiResponse<Categoria> responseDto = new DTOApiResponse<>(404, "El id " + categoria.getId().toString() + " no existe", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<DTOApiResponse<String>> eliminarCategoria(@PathVariable("id") Long id) {
        if (servi.existe(id)) {
            servi.eliminar(id);
            DTOApiResponse<String> responseDto = new DTOApiResponse<>(200, "Eliminada con éxito", null);
            return ResponseEntity.ok().body(responseDto);
        } else {
            DTOApiResponse<String> responseDto = new DTOApiResponse<>(404, "No existe la categoría con el id " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    // Controlador de excepciones
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DTOApiResponse<List<String>>> exceptionController(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        DTOApiResponse<List<String>> response = new DTOApiResponse<>(400, errors, null);
        return ResponseEntity.badRequest().body(response);
    }
}
