package Grupo1.G1P3LH.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.Categoria;
import Grupo1.G1P3LH.repository.CategoriaRepository;
import Grupo1.G1P3LH.service.ICategoriaService;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	@Override
	public List<Categoria> mostrarTodos() {
		
		return repo.findAll();
	}

	@Override
	public Categoria mostrarPorId(Long id) {
		
		return repo.findById(id).orElse(null);
	}

	@Override
	public Categoria guardar(Categoria producto) {
		
		return repo.save(producto);
	}

	@Override
	public void eliminar(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public boolean existe(Long id) {
		if (id == null) {
			return false;
		}else {
		
			return repo.existsById(id);
		}
		
	}

	
}
