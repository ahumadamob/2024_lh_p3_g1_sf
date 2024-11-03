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

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.service.IDetalleDePagoService;
import Grupo1.G1P3LH.util.DTOresponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
public class DetalleDePagoController {
	@Autowired
	IDetalleDePagoService servi;
	
	@GetMapping("/detalle")
	public ResponseEntity<DTOresponse<List<DetalleDePago>>> mostrarTodosLosDetalles (){
		
		List<DetalleDePago> lista = servi.mostrarTodos();
        if(lista.isEmpty()) {
        	DTOresponse<List<DetalleDePago>> dto= new DTOresponse<>(404, "No hay detalle que mostrar",null);
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        	
        }else {
        	DTOresponse<List<DetalleDePago>> dto= new DTOresponse<>(200, "",lista);
        	return ResponseEntity.ok().body(dto);
        }
		
	}


	@GetMapping("/detalle/{id}")
	public ResponseEntity<DTOresponse<DetalleDePago>> mostrarDetalleDePagoPorId(@PathVariable("id") Long id){
		DetalleDePago detalle = servi.mostrarPorId(id);
		

		if (detalle !=null) {
         DTOresponse<DetalleDePago> dto = new DTOresponse<>(200, "",detalle);
         return ResponseEntity.ok().body(dto);
		}else {
	     DTOresponse<DetalleDePago> dto = new DTOresponse<>(400, "El Id " + id + " no  existe", null);	
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
		
	}
	
	// endpoint sugerido
	
	@GetMapping("/detalle/pendiente")
	public ResponseEntity<DTOresponse<List<DetalleDePago>>> obtenerPagosPendientes() {
	    List<DetalleDePago> pendientes = servi.obtenerPagosPendientes();
	    
	    // Verificar si la lista de pendientes está vacía
	    if (pendientes.isEmpty()) {
	        // Retornar un mensaje de que no hay pendientes
	        DTOresponse<List<DetalleDePago>> dto = new DTOresponse<>(404, "No hay detalles de pago pendientes", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
	    }
	    
	    // Si hay detalles pendientes, retornarlos
	    DTOresponse<List<DetalleDePago>> dto = new DTOresponse<>(200, "", pendientes);
	    return ResponseEntity.ok().body(dto);
	}




	@PostMapping("/detalle")
	public ResponseEntity<DTOresponse<DetalleDePago>> crearDetalleDePago(@RequestBody DetalleDePago detalle){
			
		if (servi.existe(detalle.getId())) {
			//Error
			DTOresponse<DetalleDePago> dto = new DTOresponse<>(404, "El Id " + detalle.getId().toString() + " Si existe", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);	
		}else {
			//Exito
			DetalleDePago nuevoDetalle = servi.guardar(detalle);
			DTOresponse<DetalleDePago> dto = new DTOresponse<DetalleDePago>(201, "detalle creado",nuevoDetalle);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}

		
	}

	@PutMapping("/detalle")
	public ResponseEntity<DTOresponse<DetalleDePago>> modificarDetalle(@RequestBody DetalleDePago detalle){
		if (servi.existe(detalle.getId())) {
			DTOresponse<DetalleDePago> dto = new DTOresponse<>(200,"",servi.guardar(detalle));
	        return ResponseEntity.ok().body(dto);	
		}else {
			DTOresponse<DetalleDePago> dto = new DTOresponse<>(404,"EL Id " + detalle.getId().toString()+ "no existe",null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}

	}

	
	@DeleteMapping("/detalle/{id}")
	public ResponseEntity<DTOresponse<String>> eliminarDetalle(@PathVariable("id") Long id) {
	 if(servi.existe(id)) {
		 servi.eliminar(id);
		 DTOresponse<String> dtoSi = new DTOresponse<>(200, "", null);
	     return ResponseEntity.ok().body(dtoSi);
	 }else {
		 DTOresponse<String> dtoNo = new DTOresponse<>(404, "No existe el Detalle de pago con el ID " +id, null);
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtoNo);
	 }
	
	}
	
	
	// Controlador de excepciones
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<DTOresponse<List<String>>> exceptionController(ConstraintViolationException e) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}
		DTOresponse<List<String>> response = new DTOresponse<>(400, errors, null);
		return ResponseEntity.badRequest().body(response);
	}
	
	
}














