package Grupo1.G1P3LH.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
@Entity
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_venta;
	private String carrito_id;
	
	@ManyToMany
	private List<Producto> productos;
	
	public Long getId_venta() {
		return Id_venta;
	}
	public void setId_venta(Long id_venta) {
		Id_venta = id_venta;
	}
	public String getCarrito_id() {
		return carrito_id;
	}
	public void setCarrito_id(String carrito_id) {
		this.carrito_id = carrito_id;
	}
	
}
