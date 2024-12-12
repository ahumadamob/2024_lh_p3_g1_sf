package Grupo1.G1P3LH.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.service.jpa.DetalleDePagoService;
import Grupo1.G1P3LH.util.DTOApiResponse;

@RestController
@RequestMapping("/api/detalle")
public class DetalleDePagoController {
	
	 @Autowired
	private DetalleDePagoService detalleDePagoService;
	 
	 
	 @PostMapping
	    public ResponseEntity<DTOApiResponse<DetalleDePago>> createDetalleDePago(@RequestBody DetalleDePago detalle) {
	        
		 DetalleDePago nuevoDetalle = detalleDePagoService.createDetalleDePago(detalle);
	        
		 return ResponseEntity.status(201).body(new DTOApiResponse<>(201, "detalle creado", nuevoDetalle));
	    }
	 
	 
	
	
	
	
}














