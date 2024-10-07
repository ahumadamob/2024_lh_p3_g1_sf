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

import Grupo1.G1P3LH.entity.Categoria;
import Grupo1.G1P3LH.service.ICategoriaService;
import Grupo1.G1P3LH.util.ApiResponse;

@RestController
public class CategoriaController {

	@Autowired
	ICategoriaService servi;
	
	
	@GetMapping("/categorias")
	ApiResponse<List<Categoria>> mostrarTodasLasCategorias (){
		ApiResponse<List<Categoria>> response = new ApiResponse<>();
		List<Categoria> lista = servi.mostrarTodos();
		
		if (lista.isEmpty()) {
			response.setError("No existen categorias");
		}else {
			response.setData(lista);
		}
		
		return response;
	}
	
	
	@GetMapping("categorias{id}")
	ApiResponse<Categoria> mostrarCategoriaPorId(@PathVariable("id") Long id){
		ApiResponse<Categoria> response = new ApiResponse<>();
		Categoria categoria = servi.mostrarPorId(id);
		
		if (categoria == null) {
			
			response.setError("El ID de esta categoria no existe");
		}else {
			response.setData(categoria);
		}
		return response;
	}
	
	
	@PostMapping("/categorias")
	ApiResponse<Categoria> crearCategoria(@RequestBody Categoria categoria){
		ApiResponse<Categoria> response = new ApiResponse<>();	
		if (servi.existe(categoria.getId())) {
			response.setError("La Categoria ya a sido creada");
		}else {
			Categoria categoriaNueva = servi.guardar(categoria);
			response.setData(categoriaNueva);
		}
		
		return response;
	}
	
	@PutMapping("/categorias")
	ApiResponse<Categoria> modificarCategoria(@RequestBody Categoria categoria){
		ApiResponse<Categoria> response = new ApiResponse<>();
		
		if (servi.existe(categoria.getId())) {
			Categoria categoriaModificada = servi.guardar(categoria);
			response.setData(categoriaModificada);
		}else {
			response.setError("La Categoria no existe");
		}
		
		return response;
	}
	
	@DeleteMapping("categorias{id}")
	String eliminarCategoria(@PathVariable("id") Long id) {
		if (servi.existe(id)) {
			servi.eliminar(id);
			return "Categoria Eliminada";
		}else {
			return "El id es incorrecto";
		}
	}
}
