package Grupo1.G1P3LH.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private Long precio;
private Long stock;
private Long categoria;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Long getPrecio() {
	return precio;
}
public void setPrecio(Long precio) {
	this.precio = precio;
}
public Long getStock() {
	return stock;
}
public void setStock(Long stock) {
	this.stock = stock;
}
public Long getCategoria() {
	return categoria;
}
public void setCategoria(Long categoria) {
	this.categoria = categoria;
}

}
