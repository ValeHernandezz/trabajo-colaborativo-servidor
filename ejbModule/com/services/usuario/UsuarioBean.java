package com.services.usuario;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.entities.Usuario;
import com.exception.ServiciosException;

/**
 * Session Bean implementation class UsuarioBean
 */
@Stateless
public class UsuarioBean implements UsuarioBeanRemote {

	@PersistenceContext
	EntityManager entityManager;

	public UsuarioBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Usuario login(String nombreUsuario, String clave) throws ServiciosException {
		try {
			// Realizar la consulta para buscar el usuario por nombre de usuario y
			// contraseña
			TypedQuery<Usuario> query = entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.clave = :clave",
							Usuario.class)
					.setParameter("nombreUsuario", nombreUsuario).setParameter("clave", clave);

			ArrayList<Usuario> lista = (ArrayList<Usuario>) query.getResultList();
			Usuario usuario = lista.get(0);

			// Verificar si se encontró un usuario válido
			if (usuario != null) {
				System.out.println("ÉXITO - Usuario encontrado: " + usuario.getNombre());
				return usuario;
			} else {
				throw new ServiciosException("ERROR - Nombre de usuario o contraseña incorrectos.");
			}
		} catch (Exception e) {
			throw new ServiciosException("ERROR - No se pudo realizar el inicio de sesión.");
		}

	}

	@Override
	public Usuario crearUsuario(Usuario oUsuario) throws ServiciosException {
		try {
			entityManager.persist(oUsuario);
			entityManager.flush();
			return oUsuario;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Usuario actualizarUsuario(Usuario oUsuario) throws ServiciosException {
		try {
			entityManager.merge(oUsuario);
			entityManager.flush();
			return oUsuario;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean eliminarUsuario(Long idUsuario) throws ServiciosException {
		try {
			Usuario oUsuario = entityManager.find(Usuario.class, idUsuario);
			entityManager.remove(oUsuario);
			entityManager.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ArrayList<Usuario> listarUsuarios() throws ServiciosException {
		try {
			TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u JOIN FETCH u.rol",
					Usuario.class);
			ArrayList<Usuario> lista = (ArrayList<Usuario>) query.getResultList();
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Usuario> listarUsuariosFiltro(String filtro) throws ServiciosException {
		try {
			TypedQuery<Usuario> query = entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.nombre LIKE :filtro", Usuario.class)
					.setParameter("filtro", "%" + filtro + "%");

			return (ArrayList<Usuario>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Usuario> listarUsuariosFiltroPersonalizado(String filtro, String campo) throws ServiciosException {
		try {

			TypedQuery<Usuario> query = entityManager
					.createQuery("SELECT u FROM Usuario u WHERE " + campo + " LIKE :filtro", Usuario.class)
					.setParameter("filtro", "%" + filtro.toUpperCase() + "%");

			return (ArrayList<Usuario>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean tienePermiso(Long idRol, Long idFuncionalidad) throws ServiciosException {
		try {
			String query = "SELECT COUNT(r) FROM Rol r JOIN r.funcionalidades f WHERE r.idRol = :idRol AND f.idFuncionalidad = :idFuncionalidad";
			Long count = entityManager.createQuery(query, Long.class).setParameter("idRol", idRol)
					.setParameter("idFuncionalidad", idFuncionalidad).getSingleResult();

			return count > 0;
		} catch (Exception e) {
			return false;
		}
	}

}
