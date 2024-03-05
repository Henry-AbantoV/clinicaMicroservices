/*
 * @file MedicoServiceImp.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:32:42
 */
package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Medico;
import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.MedicoRepository;
import edu.unc.clinica.repositories.PacienteRepository;

@Service
public class MedicoServiceImp implements MedicoService {

	
	@Autowired
	private MedicoRepository medicoRep;


	@Autowired
	private PacienteRepository pacienteRep;

	@Override
	@Transactional
	public List<Medico> listarMedicos() {
		return medicoRep.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Medico buscarMedicoById(Long idMedico) throws EntityNotFoundException {
		Optional<Medico> medico = medicoRep.findById(idMedico);

		if (medico.isEmpty())
			throw new EntityNotFoundException("El medico con el id proporcionado no existe en la BD");
		return medico.get();

	}

	@Override
	@Transactional
	public Medico grabarMedico(Medico medico) throws IllegalOperationException {

		return medicoRep.save(medico);
	}

	@Override
	@Transactional
	public Medico actualizarMedico(Long id, Medico medico) throws EntityNotFoundException, IllegalOperationException {
		Optional<Medico> medicoEntity = medicoRep.findById(id);
		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("El medico con este id no fue encontrado");

		medico.setIdMedico(id);
		return medicoRep.save(medico);

	}

	@Override
	@Transactional
	public void eliminarMedico(Long IdMedico) throws EntityNotFoundException, IllegalOperationException {
		Medico especialidad=medicoRep.findById(IdMedico).orElseThrow(
				()->new EntityNotFoundException("El medico con id proporcionado no se elimino"));
		

		medicoRep.deleteById(IdMedico);
	}

	@Override
	@Transactional
	public Medico asignarPaciente(Long idMedico, Long idPaciente)
			throws EntityNotFoundException, IllegalOperationException {
		try {
			Medico MedicoEntity =  medicoRep.findById(idMedico).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND)
					);
			Paciente pacienteEntity = pacienteRep.findById(idPaciente).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.PACIENTE_NOT_FOUND)
					);
			if (pacienteEntity.getMedicos()== null) {
				pacienteEntity.setMedicos((List<Medico>) MedicoEntity);
	            return medicoRep.save(MedicoEntity);
	        } else {
	            throw new IllegalOperationException("El paciente ya esta asigando a un medico");
	        }
			}catch (Exception e) {
		        throw new IllegalOperationException("Error durante la asignación de paciente");
		    }
			
		}

}
