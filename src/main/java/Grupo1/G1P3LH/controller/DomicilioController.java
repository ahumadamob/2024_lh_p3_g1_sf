package Grupo1.G1P3LH.controller;

import Grupo1.G1P3LH.entity.Domicilio;
import Grupo1.G1P3LH.service.IDomicilioService;
import Grupo1.G1P3LH.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DomicilioController {
    @Autowired
    private IDomicilioService dService;

    //alta de domicilio
    @PostMapping("/domicilio")
    ApiResponse<Domicilio> guardarDomicilio(@RequestBody Domicilio domicilio){
        ApiResponse<Domicilio> response = new ApiResponse<>();
        if (dService.exist(domicilio.getId())){
            response.setError("ERROR este id ya existe");
        }else{
            response.setData(dService.saveDomicilio(domicilio));
        }
        return response;
    }

    //actualizacion de domicilio
    @PutMapping("/domicilio")
    ApiResponse<Domicilio> actualizarDomicilio(@RequestBody Domicilio domicilio){
        ApiResponse<Domicilio> response = new ApiResponse<>();
        if (dService.exist(domicilio.getId())){
            response.setData(dService.saveDomicilio(domicilio));
        }else{
            response.setError("ERROR datos no encontrados");
        }
        return response;
    }

    //lectura de todos los domicilio
    @GetMapping("/domicilios")
    ApiResponse<List<Domicilio>> traerTodosLosDomicilios() {
        ApiResponse<List<Domicilio>> response = new ApiResponse<>();
        List<Domicilio> lista = dService.getAllDomicilio();

        if (lista.isEmpty()) {
            response.setError("ERROR Datos no encontrados");
        } else {
            response.setData(lista);
        }
        return response;
    }
    //lectura de domicilio especifico
    @GetMapping("/domicilio/{id}")
    ApiResponse<Domicilio> traerDomicilioPorId(@PathVariable Long id){
        ApiResponse<Domicilio> response = new ApiResponse<>();
        Domicilio domicilio = dService.getDomicilio(id);

        if (domicilio == null){
            response.setError("ERROR no existe el ID solicitado " + id.toString());
        }else{
            response.setData(domicilio);
        }
        return response;
    }


    //eliminacion de domicilio especifico
    @DeleteMapping("/domicilio/{id}")
    String eliminarDomicilio(@PathVariable Long id){
        if (dService.exist(id)){
            dService.deleteDomicilio(id);
            return "Elemento eliminado correctamente";
        }else{
            return "ERROR el id solicitado no existe";
        }

    }
}
