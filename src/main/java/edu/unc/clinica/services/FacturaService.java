package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import edu.unc.clinica.domain.Factura;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;

public interface FacturaService {
	List<Factura> listarFacturas();
	Factura buscarFacturabyId(Long IdFactura) throws EntityNotFoundException;
	Factura grabarFactura (Factura factura) throws IllegalOperationException ;
	Factura actualizarFactura(Long id, Factura factura) throws EntityNotFoundException, IllegalOperationException;
	void eliminarFactura(Long IdFactura) throws EntityNotFoundException, IllegalOperationException;
}
