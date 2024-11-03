package Grupo1.G1P3LH.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.DetalleDePago;
import Grupo1.G1P3LH.repository.DetalleDePagoRepository;
import Grupo1.G1P3LH.service.IDetalleDePagoService;

@Service
public class DetalleDePagoServiceImpl implements IDetalleDePagoService {

	@Autowired
	private DetalleDePagoRepository repo;
	@Override
	public List<DetalleDePago> mostrarTodos() {
		
		return repo.findAll();
	}

	@Override
	public DetalleDePago mostrarPorId(Long id) {
		Optional<DetalleDePago> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;	
		}
		
	}

	@Override
	public DetalleDePago guardar(DetalleDePago detalle) {
		
		return repo.save(detalle);
	}

	@Override
	public void eliminar(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public boolean existe(Long id) {
		if(id==null) {
			return false;
		}else {
			return repo.existsById(id);
		}
		
	}
	@Override
	public List<DetalleDePago> obtenerPagosPendientes() {
	    return repo.findByEstadoPago("pendiente");
	}


}
