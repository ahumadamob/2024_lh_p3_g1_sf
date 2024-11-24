package Grupo1.G1P3LH.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.G1P3LH.entity.Producto;
import Grupo1.G1P3LH.service.IProductoService;
import Grupo1.G1P3LH.util.DTOApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
public class ProductoController {

    @Autowired
    IProductoService service;

    @GetMapping("/productos")
    public ResponseEntity<DTOApiResponse<List<Producto>>> mostrarTodosLosProductos() {
        List<Producto> listaProducto = service.mostrarTodos();
        if (listaProducto == null || listaProducto.isEmpty()) {
            DTOApiResponse<List<Producto>> dto = new DTOApiResponse<>(404, "No se han cargado productos", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        } else {
            DTOApiResponse<List<Producto>> dto = new DTOApiResponse<>(200, "Lista actualizada", listaProducto);
            return ResponseEntity.ok().body(dto);
        }
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<DTOApiResponse<Producto>> mostrarProductoPorId(@PathVariable("id") Long id) {
        if (service.existe(id)) {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(200, "Producto encontrado", service.mostrarPorId(id));
            return ResponseEntity.ok().body(dto);
        } else {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(404, "El producto con el ID: " + id + " no está registrado", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    @PostMapping("/productos")
    public ResponseEntity<DTOApiResponse<Producto>> crearProducto(@RequestBody Producto producto) {
        if (service.existe(producto.getId())) {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(404, "El producto con el ID: " + producto.getId().toString() + " ya está creado", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        } else {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(200, "Producto creado", service.guardar(producto));
            return ResponseEntity.ok().body(dto);
        }
    }

    @PutMapping("/productos")
    public ResponseEntity<DTOApiResponse<Producto>> actualizarProducto(@RequestBody Producto producto) {
        if (service.existe(producto.getId())) {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(200, "Producto actualizado", service.guardar(producto));
            return ResponseEntity.ok().body(dto);
        } else {
            DTOApiResponse<Producto> dto = new DTOApiResponse<>(404, "El ID " + producto.getId().toString() + " no existe", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<DTOApiResponse<?>> eliminarProducto(@PathVariable("id") Long id) {
        if (service.existe(id)) {
            service.eliminar(id);
            DTOApiResponse<?> dto = new DTOApiResponse<>(200, "Producto con ID: " + id + " eliminado", null);
            return ResponseEntity.ok().body(dto);
        } else {
            DTOApiResponse<?> dto = new DTOApiResponse<>(404, "El producto con el ID: " + id + " no existe", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    @GetMapping("/productos/categoria/{id}")
    public ResponseEntity<DTOApiResponse<List<Producto>>> mostrarPorCategoria(@PathVariable("id") Long id) {
        if (service.existeCategoria(id)) {
            DTOApiResponse<List<Producto>> dto = new DTOApiResponse<>(200, "Lista de productos por categoría con ID: " + id, service.mostrarPorCategoria(id));
            return ResponseEntity.ok().body(dto);
        } else {
            DTOApiResponse<List<Producto>> dto = new DTOApiResponse<>(404, "No se encontró la categoría con el ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DTOApiResponse<?>> controladorDeExcepciones(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        DTOApiResponse<?> response = new DTOApiResponse<>(400, errors, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
