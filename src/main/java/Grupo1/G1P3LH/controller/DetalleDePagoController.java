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

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.service.IDetalleDePagoService;
import Grupo1.G1P3LH.util.ApiResponse;

@RestController
public class DetalleDePagoController {
	@Autowired
	IDetalleDePagoService servi;
	
	@GetMapping("/detalle de pago")
	ApiResponse<List<DetalleDePago>> mostrarTodosLosDetalles (){
		ApiResponse<List<DetalleDePago>> response = new ApiResponse<>();
		List<DetalleDePago> lista = servi.mostrarTodos();

		if (lista.isEmpty()) {
			response.setError("No existen detalles");
		}else {
			response.setData(lista);
		}

		return response;
	}


	@GetMapping("detalle de pago{id}")
	ApiResponse<DetalleDePago> mostrarDetalleDePagoPorId(@PathVariable("id") Long id){
		ApiResponse<DetalleDePago> response = new ApiResponse<>();
		DetalleDePago detalle = servi.mostrarPorId(id);

		if (detalle == null) {

			response.setError("El ID no existe");
		}else {
			response.setData(detalle);
		}
		return response;
	}


	@PostMapping("/detalle de pago")
	ApiResponse<DetalleDePago> crearDetalleDePago(@RequestBody DetalleDePago detalle){
		ApiResponse<DetalleDePago> response = new ApiResponse<>();	
		if (servi.existe(detalle.getId())) {
			response.setError("El detalle ya a sido creado");
		}else {
			DetalleDePago detalleNuevo = servi.guardar(detalle);
			response.setData(detalleNuevo);
		}

		return response;
	}

	@PutMapping("/detalle de pago")
	ApiResponse<DetalleDePago> modificarDetalle(@RequestBody DetalleDePago detalle){
		ApiResponse<DetalleDePago> response = new ApiResponse<>();

		if (servi.existe(detalle.getId())) {
			DetalleDePago detalleModificado = servi.guardar(detalle);
			response.setData(detalleModificado);
		}else {
			response.setError("La Detalle de pago no existe");
		}

		return response;
	}

	@DeleteMapping("detalle de pago{id}")
	String eliminarDetalle(@PathVariable("id") Long id) {
		if (servi.existe(id)) {
			servi.eliminar(id);
			return "Detalle Eliminada";
		}else {
			return "El id es incorrecto";
		}
	}
}
