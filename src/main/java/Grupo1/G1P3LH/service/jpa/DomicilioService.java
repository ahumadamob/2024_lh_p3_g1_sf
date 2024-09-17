package Grupo1.G1P3LH.service.jpa;

import Grupo1.G1P3LH.entity.Domicilio;
import Grupo1.G1P3LH.repository.IDomicilioRepository;
import Grupo1.G1P3LH.service.IDomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService implements IDomicilioService {
    @Autowired
    private IDomicilioRepository dRepo;


    @Override
    public Domicilio saveDomicilio(Domicilio domicilio) {
        return dRepo.save(domicilio);
    }

    @Override
    public void deleteDomicilio(Long id) {
        dRepo.deleteById(id);
    }

    @Override
    public List<Domicilio> getAllDomicilio() {
        return dRepo.findAll();
    }

    @Override
    public Domicilio getDomicilio(Long id) {
        return dRepo.findById(id).orElse(null);
    }

    @Override
    public boolean exist(Long id) {
        if(id==null){
            return false;
        }else{
            return dRepo.existsById(id);
        }
    }

}
