package com.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USUARIOS database table.
 * 
 */
@Entity
@Table(name = "USUARIOS")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USUARIOS_IDUSUARIO_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIOS_IDUSUARIO_GENERATOR")
	@Column(name = "ID_USUARIO")
	private long idUsuario;

	private String apellido;

	private String clave;

	@Column(unique =  true)
	private String documento;

	private String mail;

	private String nombre;

	@Column(name = "NOMBRE_USUARIO")
	private String nombreUsuario;

	// bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name = "ID_ROL")
	private Rol rol;

	public Usuario() {
	}

	public Usuario(String apellido, String clave, String documento, String mail, String nombre, String nombreUsuario,
			Rol role) {
		super();
		this.apellido = apellido;
		this.clave = clave;
		this.documento = documento;
		this.mail = mail;
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.rol = role;
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombreCompleto() {
		return this.nombre + " " + this.apellido;
	}

}