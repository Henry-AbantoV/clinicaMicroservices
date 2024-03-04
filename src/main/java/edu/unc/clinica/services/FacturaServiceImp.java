package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Factura;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.FacturaRepository;

@Service
public class FacturaServiceImp implements FacturaService {

	@Autowired
	private FacturaRepository facturaR;
	
	@Override
	public List<Factura> listarFacturas() {
		return (List<Factura>) facturaR.findAll();
		}

	@Override
	@Transactional(readOnly=true)
	public Factura buscarFacturabyId(Long IdFactura) throws EntityNotFoundException  {
		Optional<Factura> factura=facturaR.findById(IdFactura);
		
		return factura.get();
				
	}

	@Override
	public Factura grabarFactura(Factura factura) throws IllegalOperationException  {
		return facturaR.save(factura);
	}

	@Override
	public void eliminarFactura(Long IdFactura)  throws EntityNotFoundException, IllegalOperationException {
	 facturaR.deleteById(IdFactura);
	}
	
	@Override
	@Transactional
	public Factura actualizarFactura(Long id, Factura factura) throws EntityNotFoundException, IllegalOperationException {
	Optional<Factura> facEntity = facturaR.findById(id);
	if(facEntity.isEmpty())
		throw new EntityNotFoundException(ErrorMessage.FACTURA_NOT_FOUND);
	/*if(!facturaR.findByCosto(factura.getCosto()).isEmpty()) {
		throw new IllegalOperationException("El costo total de la factura ya existe");
	}	*/
	factura.setIdFactura(id);;		
	return facturaR.save(factura);
	}

}

