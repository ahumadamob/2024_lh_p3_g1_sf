package Grupo1.G1P3LH.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.Localidad;
import Grupo1.G1P3LH.repository.LocalidadRepository;
import Grupo1.G1P3LH.service.ILocalidadService;

@Service
public class LocalidadServiceImpl implements ILocalidadService {

	@Autowired
	private LocalidadRepository lRepo;

	@Override
	public Localidad saveLocalidad(Localidad localidad) {
		return lRepo.save(localidad);
	}

	@Override
	public void deleteLocalidad(Long id) {
		lRepo.deleteById(id);

	}

	@Override
	public List<Localidad> getAllLocalidad() {
		// TODO Auto-generated method stub
		return lRepo.findAll();
	}

	@Override
	public Localidad getLocalidad(Long id) {
		// TODO Auto-generated method stub
		return lRepo.findById(id).orElse(null);
	}

	@Override
	public boolean exists(Long id) {
		if(id==null){
            return false;
        }else{
            return lRepo.existsById(id);
        }
	}

	@Override
	public Localidad obtenerPorId(Long id) {
		Optional<Localidad> optional = lRepo.findById(id);
        return optional.orElse(null);
	}

}
