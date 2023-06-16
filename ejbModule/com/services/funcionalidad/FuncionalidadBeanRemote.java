package com.services.funcionalidad;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.entities.Funcionalidad;
import com.exception.ServiciosException;

@Remote
public interface FuncionalidadBeanRemote {

	Funcionalidad crearFuncionalidad(Funcionalidad oFuncionalidad) throws ServiciosException;

	Funcionalidad actualizarFuncionalidad(Funcionalidad oFuncionalidad) throws ServiciosException;

	boolean eliminarFuncionalidad(Long idFuncionalidad) throws ServiciosException;

	ArrayList<Funcionalidad> listarFuncionalidades() throws ServiciosException;

	ArrayList<Funcionalidad> listarFuncionalidadesFiltro(String filtro) throws ServiciosException;

	ArrayList<Funcionalidad> listarFuncionalidadesPorRol(Long idRol) throws ServiciosException;
	
	ArrayList<Funcionalidad> listarFuncionalidadesSinAccesoPorRol(Long idRol) throws ServiciosException;

}
