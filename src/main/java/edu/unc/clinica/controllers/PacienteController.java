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


import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.dto.PacienteDTO;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.services.PacienteService;
import edu.unc.clinica.util.ApiResponse;

@RestController
@RequestMapping(value = "api/v1/pacientes", headers = "Api-Version=1")
public class PacienteController {

	@Autowired
	private PacienteService pacienteS;

	@Autowired
	private ModelMapper modelMapper;

	
	  @GetMapping 
	 public ResponseEntity<?> obtenerTodosPacientes() {
	  
	  List<Paciente> pacientes = pacienteS.listarPacientes(); if(pacientes==null ||
	  pacientes.isEmpty()) { return ResponseEntity.noContent().build(); } else {
	  List<PacienteDTO> pacienteDto=pacientes.stream()
	  .map(paciente->modelMapper.map(paciente, PacienteDTO.class))
	 .collect(Collectors.toList()); ApiResponse<List<PacienteDTO>> response=new
	  ApiResponse<>(true, "Lista de pacientes",pacienteDto); return
	  ResponseEntity.ok(response); }
	
	 }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPacientesPorId(@PathVariable Long id) throws EntityNotFoundException {

		Paciente pacientes = pacienteS.buscarPacienteById(id);

		PacienteDTO pacienteDto = modelMapper.map(pacientes, PacienteDTO.class);
		ApiResponse<PacienteDTO> response = new ApiResponse<>(true, "Paciente", pacienteDto);
		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<?> guardarPaciente(@Valid @RequestBody PacienteDTO pacienteDto, BindingResult result)
			throws IllegalOperationException {

		if (result.hasErrors()) {
			return validar(result);
		}
		Paciente nuevoPaciente = modelMapper.map(pacienteDto, Paciente.class);
		pacienteS.grabarPaciente(nuevoPaciente);
		PacienteDTO savePacienteDto = modelMapper.map(nuevoPaciente, PacienteDTO.class);
		ApiResponse<PacienteDTO> response = new ApiResponse<>(true, "Paciente guardado en la BD", savePacienteDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PacienteDTO>> actualizarPaciente(@PathVariable Long id,
			@RequestBody PacienteDTO pacienteDto) throws EntityNotFoundException, IllegalOperationException {

		Paciente pacienteActualizado = modelMapper.map(pacienteDto, Paciente.class);
		pacienteS.actualizarPaciente(id, pacienteActualizado);
		PacienteDTO updateFactura = modelMapper.map(pacienteActualizado, PacienteDTO.class);
		ApiResponse<PacienteDTO> response = new ApiResponse<>(true, "Datos actualizados del paciente", updateFactura);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPaciente(@PathVariable Long id)
			throws EntityNotFoundException, IllegalOperationException {
		pacienteS.eliminarPaciente(id);
		ApiResponse<?> response = new ApiResponse<>(true, "Paciente eliminado con exito", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(value = "/{IdPaciente}/{IdCita}")
	public ResponseEntity<?> asignarCita(@PathVariable Long IdPaciente, @PathVariable Long IdCita)
			throws EntityNotFoundException, IllegalOperationException {
		Paciente paciente = pacienteS.asignarCita(IdPaciente, IdCita);
		return ResponseEntity.ok(paciente);

	}

	@PutMapping(value = "/ph/{idPaciente}/{idHistorial}")
	public ResponseEntity<?> asignarHistorial(@PathVariable Long idPaciente, @PathVariable Long idHistorial)
			throws EntityNotFoundException, IllegalOperationException {
		Paciente paciente = pacienteS.asignarHistorial(idPaciente, idHistorial);
		return ResponseEntity.ok(paciente);

	}

	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
