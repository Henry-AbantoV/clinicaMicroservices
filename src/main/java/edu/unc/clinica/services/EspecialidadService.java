package edu.unc.clinica.services;

import java.util.List;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.Especialidad;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;

public interface EspecialidadService {

   List<Especialidad> listarEspecialidades();
	
   Especialidad buscarEspecialidadbyId(Long IdEsp) throws EntityNotFoundException;
   Especialidad grabarEspecilidad (Especialidad espec) throws IllegalOperationException;
   Especialidad actualizarEspecilidad(Long id, Especialidad espec) throws EntityNotFoundException, IllegalOperationException;
   void eliminarEspecialidad(Long IdEsp) throws EntityNotFoundException, IllegalOperationException;
	
   Especialidad asignarMedicos(Long IdEsp, Long IdMedico) throws EntityNotFoundException, IllegalOperationException;
   
}
