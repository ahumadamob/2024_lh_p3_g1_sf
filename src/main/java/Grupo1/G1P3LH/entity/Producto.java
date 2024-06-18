package Grupo1.G1P3LH.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private Long precio;
private Long stock;
@ManyToOne
@JoinColumn(name = "categoria_id")
private Categoria categoria;

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
public Categoria getCategoria() {
	return categoria;
}
public void setCategoria(Categoria categoria) {
	this.categoria = categoria;
}


}
