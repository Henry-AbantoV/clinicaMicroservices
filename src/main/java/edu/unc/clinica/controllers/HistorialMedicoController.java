package edu.unc.clinica.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import edu.unc.clinica.domain.HistorialMedico;
import edu.unc.clinica.dto.HistorialDTO;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.services.HistorialService;
import edu.unc.clinica.util.ApiResponse;

@RestController
@RequestMapping(value = "api/v1/historial", headers = "Api-Version=1")
public class HistorialMedicoController {
	@Autowired
	private HistorialService historialS;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity <?> obtenerTodosHistoriales() {
	     
        List<HistorialMedico> historial = historialS.listarHistorial();
        if(historial==null || historial.isEmpty()) {
        	return ResponseEntity.noContent().build();
        }
        else {
        	List<HistorialDTO> historialDto=historial.stream()
        			.map(historiales->modelMapper.map(historiales, HistorialDTO.class))
        			.collect(Collectors.toList());
        	ApiResponse<List<HistorialDTO>> response=new ApiResponse<>(true, "Lista de historiales",historialDto);
        	return ResponseEntity.ok(response);
        }   
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> obtenerHistorialesPorId(@PathVariable Long id) throws EntityNotFoundException {
      
            Optional<HistorialMedico> historial = historialS.buscarPorIdHistorial(id);
            
            HistorialDTO historialDto=modelMapper.map(historial, HistorialDTO.class);
            ApiResponse<HistorialDTO> response=new ApiResponse<>(true, "Lista de Historiales",historialDto);
            return ResponseEntity.ok(response);            
    }
	
	@PostMapping
    public ResponseEntity<?> guardarHistorial(@Valid @RequestBody HistorialDTO historialDto, BindingResult result) throws IllegalOperationException {
        
		if(result.hasErrors()) {
			return validar(result);
		}
            HistorialMedico nuevoHistorial = modelMapper.map(historialDto, HistorialMedico.class);
            historialS.grabarHistorial(nuevoHistorial);
            HistorialDTO saveHistorialDto=modelMapper.map(nuevoHistorial, HistorialDTO.class);
            ApiResponse<HistorialDTO> response=new ApiResponse<>(true, "Historial médico guardado", saveHistorialDto);
             
           return ResponseEntity.status(HttpStatus.CREATED).body(response);       
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HistorialDTO>> actualizarHistorial(@PathVariable Long id, @RequestBody HistorialDTO historialDto) throws EntityNotFoundException, IllegalOperationException {
      
            HistorialMedico historialActualizado = modelMapper.map(historialDto, HistorialMedico.class);
            historialS.actualizarHistorial(id, historialActualizado);
            HistorialDTO updateHistorial=modelMapper.map(historialActualizado, HistorialDTO.class);
            ApiResponse<HistorialDTO> response=new ApiResponse<>(true, "Factura actualizada", updateHistorial);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);        
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHistorial(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
    	historialS.eliminarHistorial(id);
    	ApiResponse<?> response=new ApiResponse<>(true, "Historial médico eliminado con exito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	 private ResponseEntity<Map<String, String>> validar(BindingResult result) {
	        Map<String, String> errores = new HashMap<>();
	        result.getFieldErrors().forEach(err -> {
	            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
	        });
	        return ResponseEntity.badRequest().body(errores);
	    }
}
