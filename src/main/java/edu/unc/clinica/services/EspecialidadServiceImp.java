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

@Service
public class EspecialidadServiceImp  implements EspecialidadService{

	@Autowired
	EspecialidadRepository EspR;
	
	@Autowired
	MedicoRepository medicoR;
	
	@Override
	public List<Especialidad> listarEspecialidades() {
		return EspR.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Especialidad buscarEspecialidadbyId(Long IdEsp) throws EntityNotFoundException {
		Optional<Especialidad> espec=EspR.findById(IdEsp);
		return espec.get();
	}

	@Override
	public Especialidad grabarEspecilidad(Especialidad espec) throws IllegalOperationException {
		return EspR.save(espec);
	}

	@Override
	public Especialidad actualizarEspecilidad(Long id, Especialidad espec) throws EntityNotFoundException, IllegalOperationException{
	Optional<Especialidad> espEntity = EspR.findById(id);
	if(espEntity.isEmpty())
		throw new EntityNotFoundException(ErrorMessage.ESPECIALIDAD_NOT_FOUND);
		
	espec.setIdEspecialidad(id);		
	return EspR.save(espec);
	}

	@Override
	public void eliminarEspecialidad(Long IdEsp) throws EntityNotFoundException, IllegalOperationException {
		EspR.deleteById(IdEsp);
		
	}
	
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
