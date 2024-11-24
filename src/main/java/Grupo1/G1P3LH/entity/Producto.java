package Grupo1.G1P3LH.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Producto {
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

@NotBlank(message = "El nombre no puede ser nulo ni estar vacío")
private String nombreProducto;

@Min(value = 1, message="El precio debe ser mayor a 0")
private Long precio;
private Long stock;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "categoria_id")
@JsonIgnore
private Categoria categoria;

public String getNombreProducto() {
	return nombreProducto;
}
public void setNombreProducto(String nombreProducto) {
	this.nombreProducto = nombreProducto;
}
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
