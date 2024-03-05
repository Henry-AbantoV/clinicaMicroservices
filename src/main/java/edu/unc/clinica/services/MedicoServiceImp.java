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

import edu.unc.clinica.domain.Especialidad;
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
	public void eliminarMedico(Long IdMedico) throws EntityNotFoundException, IllegalOperationException {
		Medico especialidad=medicoRep.findById(IdMedico).orElseThrow(
				()->new EntityNotFoundException("El medico con id proporcionado no se elimino"));
		
		medicoRep.deleteById(IdMedico);

	}


	@Override
	public Medico asignarPaciente(Long idMedico, Long idPaciente)
			throws EntityNotFoundException, IllegalOperationException {
		Medico medicoEntity = medicoRep.findById(idMedico)
				.orElseThrow(() -> new EntityNotFoundException("Medico no encontrado con este ID "));

		Paciente paciente = pacienteRep.findById(idPaciente)
				.orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con  este ID " ));

	
		if (!medicoEntity.getPacientes().contains(paciente)) {
			medicoEntity.getPacientes().add(paciente); 
			medicoRep.save(medicoEntity); 
		} else {
			throw new IllegalOperationException("Este paciente ya esta asignado a este medico");
		}

		return medicoEntity;
	}

}
