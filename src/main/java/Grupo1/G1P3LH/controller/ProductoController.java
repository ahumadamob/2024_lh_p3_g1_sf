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

import Grupo1.G1P3LH.entity.Producto;
import Grupo1.G1P3LH.service.IProductoService;
import Grupo1.G1P3LH.util.ApiResponse;

@RestController
public class ProductoController {
	@Autowired
	IProductoService service;

	@GetMapping("/productos")
	ApiResponse<List<Producto>> mostrarTodosLosProductos() {
		ApiResponse<List<Producto>> response = new ApiResponse<>();
		List<Producto> lista = service.mostrarTodos();
		if (lista.isEmpty()) {
			response.setError("No existen productos");
		} else {
			response.setData(lista);
		}
		return response;
	}

	@GetMapping("/productos/{id}")
	ApiResponse<Producto> mostrarProductosPorId(@PathVariable("id") Long id) {
		ApiResponse<Producto> response = new ApiResponse<>();
		Producto producto = service.mostrarPorId(id);
		if (producto == null) {
			response.setError("No existe este ID " + id.toString());
		} else {
			response.setData(producto);
		}
		return response;
	}

	@PostMapping("/productos")
	ApiResponse<Producto> crearRegistro(@RequestBody Producto producto) {
		ApiResponse<Producto> response = new ApiResponse<>();
		if (service.existe(producto.getId())) {
			response.setError("Ya existe este producto");
		} else {
			Producto productoGuardado = service.guardar(producto);
			response.setData(productoGuardado);
		}
		return response;
	}

	@PutMapping("/productos")
	ApiResponse<Producto> actualizarProducto(@RequestBody Producto producto) {
		ApiResponse<Producto> response = new ApiResponse<>();
		if (service.existe(producto.getId())) {
			Producto productoGuardado = service.guardar(producto);
			response.setData(productoGuardado);
		} else {
			response.setError("El producto no existe");
		}
		return response;
	}

	@DeleteMapping("/productos/{id}")
	String eliminarProducto(@PathVariable("id") Long id) {
		if (service.existe(id)) {
			service.eliminar(id);
			return "El producto se eliminó con exito";
		} else {
			return "No existe el ID " + id.toString();
		}
	}
}
