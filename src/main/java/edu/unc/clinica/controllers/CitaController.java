/*
 * @file CitaController.java;
 * @Autor Daniela Torres (c)2024
 * @Created 5 mar 2024,0:08:16
 */
package edu.unc.clinica.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.dto.CitaDTO;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.services.CitaService;
import edu.unc.clinica.util.ApiResponse;


@RestController
@RequestMapping(value ="api/v1/citas", headers = "Api-Version=1")
public class CitaController {

	@Autowired
	private CitaService citaS;
	
	@Autowired
	private ModelMapper modelMapper;
	
	 /**
 	 * Obtener todas citas.
 	 *
 	 * @return the response entity
 	 */
 	@GetMapping
	    public ResponseEntity<?> obtenerTodasCitas() {
	     
	            List<Cita> citas = citaS.listarCitas();
	            if(citas==null || citas.isEmpty()) {
	            	return ResponseEntity.noContent().build();
	            }
	            else {
	            	List<CitaDTO> citaDto=citas.stream()
	            			.map(cita->modelMapper.map(cita, CitaDTO.class))
	            			.collect(Collectors.toList());
	            	ApiResponse<List<CitaDTO>> response=new ApiResponse<>(true, "Lista de citas",citaDto);
	            	return ResponseEntity.ok(response);
	            }   
	    }
	    
	    /**
	     * Maneja las solicitudes GET para obtener una factura por su ID.
	     * @param id El ID de la factura.
	     * @return ResponseEntity con la FacturaDTO correspondiente al ID o un mensaje de error si no se encuentra la factura.
	     * @throws EntityNotFoundException 
	     */
	   
	    @GetMapping("/{id}")
	    public ResponseEntity<?> obtenerCitasPorId(@PathVariable Long id) throws EntityNotFoundException {
	      
	            Cita citas = citaS.buscarCitabyId(id);
	            
	            CitaDTO citaDto=modelMapper.map(citas, CitaDTO.class);
	            ApiResponse<CitaDTO> response=new ApiResponse<>(true, "Lista de facturas",citaDto);
	            return ResponseEntity.ok(response);
	            
	    }
	    /**
	     * Maneja las solicitudes POST para guardar una nueva factura.
	     * @param facturaDto La factura a guardar.
	     * @return ResponseEntity con la FacturaDTO guardada en caso de éxito o un mensaje de error si falla la operación.
	     * @throws IllegalOperationException Si ocurre una operación ilegal.
	     */
	 
	    @PostMapping
	    public ResponseEntity<?> guardarCita(@Valid @RequestBody Cita citaDto, BindingResult result) throws IllegalOperationException  {
	        //citadto clase 
	    	if(result.hasErrors()) {
				return validar(result);
			}
	           Cita nuevaCita = modelMapper.map(citaDto, Cita.class);
	            citaS.grabarCita(nuevaCita);
	            CitaDTO saveCitaDto=modelMapper.map(nuevaCita, CitaDTO.class);
	            ApiResponse<CitaDTO> response=new ApiResponse<>(true, "Cita guardada", saveCitaDto);
	             
	           return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    	
	    	//return ResponseEntity.status(HttpStatus.CREATED).body(citaS.grabarCita(citaDto));
	    }
	    
	    /**
	     * Maneja las solicitudes PUT para actualizar una factura existente.
	     * @param id El ID de la factura a actualizar.
	     * @param facturaDto La factura actualizada.
	     * @return ResponseEntity con la FacturaDTO actualizada en caso de éxito o un mensaje de error si falla la operación.
	     * @throws EntityNotFoundException Si no se encuentra la factura.
	     * @throws IllegalOperationException Si ocurre una operación ilegal.
	     */ 
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<CitaDTO>> actualizarCita(@RequestBody CitaDTO citaDto, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException  {
	       	    Cita citaActualizada = modelMapper.map(citaDto, Cita.class);
	            citaS.actualizarCita(id, citaActualizada);
	            CitaDTO updateCita=modelMapper.map(citaActualizada, CitaDTO.class);
	            ApiResponse<CitaDTO> response=new ApiResponse<>(true, "Cita actualizada", updateCita);	            
	            return ResponseEntity.status(HttpStatus.OK).body(response);	        
	    }
	    
	    
	    /**
	     * Maneja las solicitudes DELETE para eliminar una factura por su ID.
	     * @param id El ID de la factura a eliminar.
	     * @return ResponseEntity con un mensaje de éxito si la factura se elimina correctamente o un mensaje de error si falla la operación.
	     * @throws EntityNotFoundException Si no se encuentra la factura.
	     * @throws IllegalOperationException Si ocurre una operación ilegal.
	     */
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> eliminarCita(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
	    	citaS.eliminarCita(id);
	    	ApiResponse<?> response=new ApiResponse<>(true, "Factura eliminada con exito", null);
	    	return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	    
	    /**
	     * Asigna una factura a una cita existente.
	     * 
	     * @param IdCita     El ID de la cita a la que se va a asignar la factura.
	     * @param IdFactura  El ID de la factura que se va a asignar a la cita.
	     * @return ResponseEntity que contiene la cita actualizada con la factura asignada.
	     * @throws EntityNotFoundException   Si la cita o la factura no se encuentran en la base de datos.
	     * @throws IllegalOperationException Si se produce una operación ilegal durante la asignación de la factura.
	     */
	    
	    @PutMapping(value = "/{IdCita}/{IdFactura}")
	    public ResponseEntity<?> asignarFacturas (@PathVariable Long IdCita, @PathVariable Long IdFactura) throws EntityNotFoundException, IllegalOperationException { 	    	
	    	Cita cita = citaS.asignarFactura(IdCita, IdFactura);
	            return ResponseEntity.ok(cita);   		
	    
	    }
	    
	    /**
	     * Valida los resultados de la validación y devuelve un ResponseEntity con los errores en caso de que haya alguno.
	     * 
	     * @param result El resultado de la validación.
	     * @return ResponseEntity que contiene un mapa de errores.
	     */
	    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
	        Map<String, String> errores = new HashMap<>();
	        result.getFieldErrors().forEach(err -> {
	            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
	        });
	        return ResponseEntity.badRequest().body(errores);
	    }	
}
	
	

