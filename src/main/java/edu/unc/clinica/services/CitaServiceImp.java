package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.Factura;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.CitaRepository;
import edu.unc.clinica.repositories.FacturaRepository;

@Service
public class CitaServiceImp implements CitaService {

	@Autowired
	private CitaRepository citaR;
	
	@Autowired
	private FacturaRepository facturaR;
	
	@Override
	public List<Cita> listarCitas() {
		return (List<Cita>)citaR.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Cita buscarCitabyId(Long IdCita) throws EntityNotFoundException  {
		Optional<Cita> cita=citaR.findById(IdCita);
		return cita.get();
		}

	@Override
	@Transactional
	public Cita grabarCita(Cita cita) throws IllegalOperationException  {
		return citaR.save(cita);
	}

	@Override
	public void eliminarCita(Long IdCita) throws EntityNotFoundException, IllegalOperationException {
		citaR.deleteById(IdCita);
		
	}
	
	@Override
	@Transactional
	public Cita actualizarCita(Long id, Cita cita) throws EntityNotFoundException, IllegalOperationException {
		Optional<Cita> citaEntity = citaR.findById(id);
		if(citaEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.CITA_NOT_FOUND);
			
		cita.setIdCita(id);		
		return citaR.save(cita);
	}

	
	@Override
	@Transactional
	public Cita asignarFactura(Long IdCita, Long IdFactura) throws EntityNotFoundException, IllegalOperationException {
		try {
		Factura FacturaEntity =  facturaR.findById(IdFactura).orElseThrow(
				()->new EntityNotFoundException(ErrorMessage.FACTURA_NOT_FOUND)
				);
		Cita citaEntity = citaR.findById(IdCita).orElseThrow(
				()->new EntityNotFoundException(ErrorMessage.CITA_NOT_FOUND)
				);
		if (citaEntity.getFactura()== null) {
			citaEntity.setFactura(FacturaEntity);
            return citaR.save(citaEntity);
        } else {
            throw new IllegalOperationException("La cita ya tiene asignada una factura");
        }
		}catch (Exception e) {
	        throw new IllegalOperationException("Error durante la asignación de factura");
	    }
		
	}
	
}
