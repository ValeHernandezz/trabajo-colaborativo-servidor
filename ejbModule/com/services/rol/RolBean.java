package com.services.rol;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.Funcionalidad;
import com.entities.Rol;
import com.entities.Usuario;
import com.exception.ServiciosException;

/**
 * Session Bean implementation class RolBean
 */
@Stateless
public class RolBean implements RolBeanRemote {

	@PersistenceContext
	EntityManager entityManager;

	public RolBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rol crearRol(Rol oRol) throws ServiciosException {
		try {
			entityManager.persist(oRol);
			entityManager.flush();
			return oRol;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Rol actualizarRol(Rol oRol) throws ServiciosException {
		try {
			entityManager.merge(oRol);
			entityManager.flush();
			return oRol;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean eliminarRol(Long idRol) throws ServiciosException {
		try {
			Rol oRol = entityManager.find(Rol.class, idRol);
			entityManager.remove(oRol);
			entityManager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ArrayList<Rol> listarRoles() throws ServiciosException {
		try {
			TypedQuery<Rol> query = entityManager.createQuery("SELECT r FROM Rol r", Rol.class);
			ArrayList<Rol> lista = (ArrayList<Rol>) query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Rol> listarRolesFiltro(String filtro) throws ServiciosException {
		try {
			TypedQuery<Rol> query = entityManager
					.createQuery("SELECT r FROM Rol r WHERE r.nombre LIKE :filtro", Rol.class)
					.setParameter("filtro", "%" + filtro + "%");

			return (ArrayList<Rol>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean asignarFuncionalidadAUnRol(Rol oRol, Funcionalidad oFuncionalidad) throws ServiciosException {
		try {
			// Conseguir la instancia real de la BD
			Rol oRolDeLaBD = entityManager.find(Rol.class, oRol.getIdRol());

			oRolDeLaBD.getFuncionalidades().add(oFuncionalidad); // Agrega la funcionalidad al rol

			entityManager.merge(oRolDeLaBD); // Actualiza el rol en la base de datos

			entityManager.flush(); // Guarda los cambios en la base de datos
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
