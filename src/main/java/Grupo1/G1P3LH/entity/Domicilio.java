package Grupo1.G1P3LH.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Domicilio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "La Dirección no puede estar vacía")
	@Size(min = 10, message = "La Dirección debe tener al menos 10 caracteres")
	private String direccion;

	@NotBlank(message = "La Provincia no puede estar vacía")
	private String provincia;

	@Min(0001)
	@Max(9999)
	@NotNull(message = "El Código Postal no puede estar vacío")
	private long codigoPostal;

	@NotNull(message ="El campo Activo no puede estar vacío")
	private boolean activo;

	@ManyToOne
	@JoinColumn(name = "localidad_id")
	@JsonBackReference
	@NotNull(message="La localidad no puede estar vacía")
	private Localidad localidad;
	
	
	/*
	 * public boolean isDomicilioActivo() { return activo; // Devuelve true si
	 * activo es true }
	 */
	
    @AssertTrue(message = "El domicilio no está activo")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(long codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
