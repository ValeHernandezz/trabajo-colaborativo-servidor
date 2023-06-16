package com.services.funcionalidad;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.Funcionalidad;
import com.exception.ServiciosException;

/**
 * Session Bean implementation class FuncionalidadBean
 */
@Stateless
public class FuncionalidadBean implements FuncionalidadBeanRemote {

	@PersistenceContext
	EntityManager entityManager;

	public FuncionalidadBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Funcionalidad crearFuncionalidad(Funcionalidad oFuncionalidad) throws ServiciosException {
		try {
			entityManager.persist(oFuncionalidad);
			entityManager.flush();
			return oFuncionalidad;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Funcionalidad actualizarFuncionalidad(Funcionalidad oFuncionalidad) throws ServiciosException {
		try {
			entityManager.merge(oFuncionalidad);
			entityManager.flush();
			return oFuncionalidad;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean eliminarFuncionalidad(Long idFuncionalidad) throws ServiciosException {
		try {
			Funcionalidad oFuncionalidad = entityManager.find(Funcionalidad.class, idFuncionalidad);
			entityManager.remove(oFuncionalidad);
			entityManager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ArrayList<Funcionalidad> listarFuncionalidades() throws ServiciosException {
		try {
			TypedQuery<Funcionalidad> query = entityManager.createQuery("SELECT f FROM Funcionalidad f",
					Funcionalidad.class);
			ArrayList<Funcionalidad> lista = (ArrayList<Funcionalidad>) query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Funcionalidad> listarFuncionalidadesFiltro(String filtro) throws ServiciosException {
		try {
			TypedQuery<Funcionalidad> query = entityManager
					.createQuery("SELECT f FROM Funcionalidad f WHERE f.nombre LIKE :filtro", Funcionalidad.class)
					.setParameter("filtro", "%" + filtro + "%");

			return (ArrayList<Funcionalidad>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public ArrayList<Funcionalidad> listarFuncionalidadesPorRol(Long idRol) throws ServiciosException {
	    try {
	        TypedQuery<Funcionalidad> query = entityManager.createQuery(
	            "SELECT f FROM Funcionalidad f JOIN f.roles r WHERE r.idRol = :idRol",
	            Funcionalidad.class
	        ).setParameter("idRol", idRol);
	        return (ArrayList<Funcionalidad>) query.getResultList();
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	@Override
	public ArrayList<Funcionalidad> listarFuncionalidadesSinAccesoPorRol(Long idRol) throws ServiciosException {
	    try {
	        TypedQuery<Funcionalidad> query = entityManager.createQuery(
	            "SELECT f FROM Funcionalidad f WHERE NOT EXISTS " +
	            "(SELECT r FROM Rol r JOIN r.funcionalidades rf WHERE rf.idFuncionalidad = f.idFuncionalidad AND r.idRol = :idRol)",
	            Funcionalidad.class
	        ).setParameter("idRol", idRol);
	        return (ArrayList<Funcionalidad>) query.getResultList();
	    } catch (Exception e) {
	        return null;
	    }
	}


}
