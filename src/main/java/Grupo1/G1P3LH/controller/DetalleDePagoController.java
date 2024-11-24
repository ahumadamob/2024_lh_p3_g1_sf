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

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.service.IDetalleDePagoService;
import Grupo1.G1P3LH.util.DTOApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
public class DetalleDePagoController {
	@Autowired
	IDetalleDePagoService servi;
	
	@GetMapping("/detalle")
	public ResponseEntity<DTOApiResponse<List<DetalleDePago>>> mostrarTodosLosDetalles (){
		
		List<DetalleDePago> lista = servi.mostrarTodos();
        if(lista.isEmpty()) {
        	DTOApiResponse<List<DetalleDePago>> dto= new DTOApiResponse<>(404, "No hay detalle que mostrar",null);
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        	
        }else {
        	DTOApiResponse<List<DetalleDePago>> dto= new DTOApiResponse<>(200, "",lista);
        	return ResponseEntity.ok().body(dto);
        }
		
	}


	@GetMapping("/detalle/{id}")
	public ResponseEntity<DTOApiResponse<DetalleDePago>> mostrarDetalleDePagoPorId(@PathVariable("id") Long id){
		DetalleDePago detalle = servi.mostrarPorId(id);
		

		if (detalle !=null) {
         DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<>(200, "",detalle);
         return ResponseEntity.ok().body(dto);
		}else {
	     DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<>(400, "El Id " + id + " no  existe", null);	
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
		
	}
	
	// endpoint sugerido
	
	@GetMapping("/detalle/pendiente")
	public ResponseEntity<DTOApiResponse<List<DetalleDePago>>> obtenerPagosPendientes() {
	    List<DetalleDePago> pendientes = servi.obtenerPagosPendientes();
	    
	    // Verificar si la lista de pendientes está vacía
	    if (pendientes.isEmpty()) {
	        // Retornar un mensaje de que no hay pendientes
	        DTOApiResponse<List<DetalleDePago>> dto = new DTOApiResponse<>(404, "No hay detalles de pago pendientes", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
	    }
	    
	    // Si hay detalles pendientes, retornarlos
	    DTOApiResponse<List<DetalleDePago>> dto = new DTOApiResponse<>(200, "", pendientes);
	    return ResponseEntity.ok().body(dto);
	}




	@PostMapping("/detalle")
	public ResponseEntity<DTOApiResponse<DetalleDePago>> crearDetalleDePago(@RequestBody DetalleDePago detalle){
			
		if (servi.existe(detalle.getId())) {
			//Error
			DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<>(404, "El Id " + detalle.getId().toString() + " Si existe", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);	
		}else {
			//Exito
			DetalleDePago nuevoDetalle = servi.guardar(detalle);
			DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<DetalleDePago>(201, "detalle creado",nuevoDetalle);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}

		
	}

	@PutMapping("/detalle")
	public ResponseEntity<DTOApiResponse<DetalleDePago>> modificarDetalle(@RequestBody DetalleDePago detalle){
		if (servi.existe(detalle.getId())) {
			DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<>(200,"",servi.guardar(detalle));
	        return ResponseEntity.ok().body(dto);	
		}else {
			DTOApiResponse<DetalleDePago> dto = new DTOApiResponse<>(404,"EL Id " + detalle.getId().toString()+ "no existe",null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}

	}

	
	@DeleteMapping("/detalle/{id}")
	public ResponseEntity<DTOApiResponse<String>> eliminarDetalle(@PathVariable("id") Long id) {
	 if(servi.existe(id)) {
		 servi.eliminar(id);
		 DTOApiResponse<String> dtoSi = new DTOApiResponse<>(200, "", null);
	     return ResponseEntity.ok().body(dtoSi);
	 }else {
		 DTOApiResponse<String> dtoNo = new DTOApiResponse<>(404, "No existe el Detalle de pago con el ID " +id, null);
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtoNo);
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














