package Grupo1.G1P3LH.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cliente;
	
	private int dni;
	private String apellido;
	private String nombre;
	private String correo;
	@OneToOne
	private Domicilio domicilio;
	
	@OneToMany(mappedBy = "cliente")
	private List<Venta> ventas;
	
	
	
	public List<Venta> getVentas() {
		return ventas;
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public Long getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
