package Grupo1.G1P3LH.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import Grupo1.G1P3LH.util.DTOResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@RestController
public class ProductoController {
	@Autowired
	IProductoService service;

	@GetMapping("/productos")
	public ResponseEntity <DTOResponse<List<Producto>>> mostrarTodosLosProductos() {
		
		List<Producto> listaProducto = service.mostrarTodos();
		if (listaProducto == null) {
			
			DTOResponse<List <Producto>> dto = new DTOResponse<>(404,
					"no se han cargado productos",null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}else {
			DTOResponse<List <Producto>> dto = new DTOResponse<>(200,
					"Lista actualizada",service.mostrarTodos());
			return ResponseEntity.ok().body(dto);
		}
	}

	@GetMapping("/productos/{id}")
	public ResponseEntity <DTOResponse<Producto>> mostrarProductosPorId(@PathVariable("id") Long id) {
		
		if (service.existe(id)) {
			//Existe
			DTOResponse<Producto> dto = new DTOResponse<>(200,
					"Producto encontrado",service.mostrarPorId(id));
			return ResponseEntity.ok().body(dto);
		} else {
			//no Existe
			DTOResponse<Producto> dto = new DTOResponse<>(404,
					"El producto con el id: "+id+",no esta registrado",
					null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
	}

	@PostMapping("/productos")
	public ResponseEntity <DTOResponse<Producto>> crearProducto(@RequestBody Producto producto) {
		
		if (service.existe(producto.getId())) {
			//Existe el Producto
			DTOResponse<Producto> dto = new DTOResponse<>(404,
					"El Producto con el id: "+producto.getId().toString()+", ya esta creado",
					null);	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		} else {
			//no Existe el producto
			DTOResponse<Producto> dto = new DTOResponse<>(200,
					"Producto creado",service.guardar(producto));
			return ResponseEntity.ok().body(dto);
		}
	}

	@PutMapping("/productos")
	public ResponseEntity <DTOResponse<Producto>> actualizarProducto(@RequestBody Producto producto) {
		
		if (service.existe(producto.getId())) {
			DTOResponse<Producto> dto = new DTOResponse<>(200,
					"Producto actualizado",service.guardar(producto));
			return ResponseEntity.ok().body(dto);
		} else {
			DTOResponse<Producto> dto = new DTOResponse<>(404,
					"El id "+ producto.getId().toString()+ ", No existe",null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
	}

	@DeleteMapping("/productos/{id}")
	public ResponseEntity <DTOResponse<?>> eliminarProducto(@PathVariable("id") Long id) {
		if (service.existe(id)) {
			service.eliminar(id);
			DTOResponse<Producto> dto = new DTOResponse<>(200,
					"Producto con id: "+id+" eliminado",null);
			return ResponseEntity.ok().body(dto);
		} else {
			DTOResponse<Producto> dto = new DTOResponse<>(404,
					"El producto con el id: "+ id.toString()+ ", No existe",null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
	}
	
	@GetMapping("/productos/categoria/{id}")
	public ResponseEntity<DTOResponse<?>> mostrarPorCategoria(@PathVariable("id") Long id){
	    if (service.existeCategoria(id)) {
	    	DTOResponse<List<Producto>> dto = new DTOResponse<>(200,
	    			"Lista de producto por id: "+id,service.mostrarPorCategoria(id));
	        return ResponseEntity.ok().body(dto);
	    } else {
	    	DTOResponse<List<Producto>> dto = new DTOResponse<>(404,
	    			"No se encontro la categoria con el id: "+id,null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
	    }
	}

	
	@ExceptionHandler(ConstraintViolationException.class)
	ResponseEntity <DTOResponse<Producto>> controladorDeExcepciones(ConstraintViolationException e){
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
		DTOResponse<Producto> response = new DTOResponse<>(404, errors,null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
