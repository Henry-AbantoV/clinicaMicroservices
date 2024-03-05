/*
 * @file EspecialidadServiceImp.java;
 * @Autor Henry AV (c)2024
 * @Created 5 mar 2024,0:12:33
 */
package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.Especialidad;
import edu.unc.clinica.domain.Factura;
import edu.unc.clinica.domain.Medico;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.EspecialidadRepository;
import edu.unc.clinica.repositories.MedicoRepository;

// TODO: Auto-generated Javadoc
/**
 * Implementación de la interfaz EspecialidadService que define los servicios relacionados con las especialidades médicas.
 */
@Service
public class EspecialidadServiceImp  implements EspecialidadService{


	@Autowired
	EspecialidadRepository EspR;

	@Autowired
	MedicoRepository medicoR;
	
	 /**
     * Método para listar todas las especialidades médicas.
     *
     * @return Lista de objetos de tipo Especialidad.
     */
	@Override
	public List<Especialidad> listarEspecialidades() {
		return EspR.findAll();
	}

	/**
     * Método para buscar una especialidad médica por su ID.
     *
     * @param IdEsp ID de la especialidad médica a buscar.
     * @return Objeto Especialidad correspondiente al ID especificado.
     * @throws EntityNotFoundException Si no se encuentra la especialidad médica con el ID especificado.
     */
	@Override
	@Transactional(readOnly=true)
	public Especialidad buscarEspecialidadbyId(Long IdEsp) throws EntityNotFoundException {
		Optional<Especialidad> espec=EspR.findById(IdEsp);
		return espec.get();
	}

	/**
     * Método para grabar una nueva especialidad médica.
     *
     * @param espec Objeto de tipo Especialidad a ser grabado.
     * @return La especialidad médica grabada.
     * @throws IllegalOperationException Si se produce una operación ilegal al intentar grabar la especialidad médica.
     */
	@Override
	public Especialidad grabarEspecilidad(Especialidad espec) throws IllegalOperationException {
		return EspR.save(espec);
	}

	 /**
     * Método para actualizar una especialidad médica existente.
     *
     * @param id    ID de la especialidad médica a actualizar.
     * @param espec Objeto de tipo Especialidad con los datos actualizados.
     * @return La especialidad médica actualizada.
     * @throws EntityNotFoundException    Si no se encuentra la especialidad médica con el ID especificado.
     * @throws IllegalOperationException Si se produce una operación ilegal al intentar actualizar la especialidad médica.
     */
	@Override
	public Especialidad actualizarEspecilidad(Long id, Especialidad espec) throws EntityNotFoundException, IllegalOperationException{
	Optional<Especialidad> espEntity = EspR.findById(id);
	if(espEntity.isEmpty())
		throw new EntityNotFoundException(ErrorMessage.ESPECIALIDAD_NOT_FOUND);
		
	espec.setIdEspecialidad(id);		
	return EspR.save(espec);
	}

	/**
     * Método para eliminar una especialidad médica por su ID.
     *
     * @param IdEsp ID de la especialidad médica a eliminar.
     * @throws EntityNotFoundException    Si no se encuentra la especialidad médica con el ID especificado.
     * @throws IllegalOperationException Si se produce una operación ilegal al intentar eliminar la especialidad médica.
     */
	@Override
	public void eliminarEspecialidad(Long IdEsp) throws EntityNotFoundException, IllegalOperationException {
		EspR.deleteById(IdEsp);
		
	}
	
	
	 /**
     * Método para asignar médicos a una especialidad médica.
     *
     * @param IdEsp   ID de la especialidad médica a la que se asignarán los médicos.
     * @param IdMedico ID del médico a asignar a la especialidad médica.
     * @return La especialidad médica actualizada con el médico asignado.
     * @throws EntityNotFoundException    Si no se encuentra la especialidad médica o el médico con los IDs especificados.
     * @throws IllegalOperationException Si se produce una operación ilegal al intentar asignar el médico a la especialidad médica.
     */
	@Override
	@Transactional
	public Especialidad asignarMedicos(Long IdEsp, Long IdMedico) throws EntityNotFoundException, IllegalOperationException {
		try {
			Especialidad EspeciEntity =  EspR.findById(IdEsp).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.ESPECIALIDAD_NOT_FOUND));
			Medico MedicEntity = medicoR.findById(IdMedico).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND));
			if (MedicEntity.getEspecialidad()== null) {
				MedicEntity.setEspecialidad(EspeciEntity);
	            return EspR.save(EspeciEntity);
	        } else {
	            throw new IllegalOperationException("El medico fue asignado a una especialidad");
	        }
			}catch (Exception e) {
		        throw new IllegalOperationException("Error durante la asignación de especialidad");
		    }
			
		}

}
