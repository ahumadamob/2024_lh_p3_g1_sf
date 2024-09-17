package Grupo1.G1P3LH.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Grupo1.G1P3LH.entity.Producto;
import Grupo1.G1P3LH.repository.ProductoRepository;
import Grupo1.G1P3LH.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoRepository repo;

	@Override
	public List<Producto> mostrarTodos() {
		return repo.findAll();
	}

	@Override
	public Producto mostrarPorId(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Producto guardar(Producto producto) {
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
		} else {
			return repo.existsById(id);
		}
	}

}