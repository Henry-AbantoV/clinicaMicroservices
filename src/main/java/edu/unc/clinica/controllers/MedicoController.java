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

import edu.unc.clinica.domain.Medico;
import edu.unc.clinica.dto.MedicoDTO;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.services.MedicoService;
import edu.unc.clinica.util.ApiResponse;

@RestController
@RequestMapping(value="api/v1/medicos", headers = "Api-Version=1")
public class MedicoController {

	@Autowired
	private MedicoService medicoServ;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<?> obtenerMedicos() {
		List<Medico> medicos = medicoServ.listarMedicos();

		if (medicos == null || medicos.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<MedicoDTO> medicoDTO = medicos.stream().map(medico -> modelMapper.map(medico, MedicoDTO.class))
					.collect(Collectors.toList());
			ApiResponse<List<MedicoDTO>> response = new ApiResponse<>(true, "Lista de medicos", medicoDTO);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> ObtenerMedicosPorId(@PathVariable Long id) throws EntityNotFoundException {

		Medico medico = medicoServ.buscarMedicoById(id);

		MedicoDTO medicoDTO = modelMapper.map(medico, MedicoDTO.class);
		ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "Lista de medicos", medicoDTO);
		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<?> guardarMedico(@Valid @RequestBody MedicoDTO medicoDTO, BindingResult result) throws IllegalOperationException {

		if(result.hasErrors()) {
			return validar(result);
		}
		
		Medico nuevoMedico = modelMapper.map(medicoDTO, Medico.class);
		medicoServ.grabarMedico(nuevoMedico);
		MedicoDTO guardarMedicoDTO = modelMapper.map(nuevoMedico, MedicoDTO.class);

		ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "Medico guardado", guardarMedicoDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MedicoDTO>> actualizarMedico(@PathVariable Long id,
			@RequestBody MedicoDTO medicoDTO) throws EntityNotFoundException, IllegalOperationException {

		Medico medicoActualizado = modelMapper.map(medicoDTO, Medico.class);
		medicoServ.actualizarMedico(id, medicoActualizado);

		MedicoDTO actualizarMedicoDTO = modelMapper.map(medicoActualizado, MedicoDTO.class);
		ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "Medico actualizado", actualizarMedicoDTO);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMedico(@PathVariable Long id)
			throws EntityNotFoundException, IllegalOperationException {
		medicoServ.eliminarMedico(id);
		ApiResponse<?> response = new ApiResponse<>(true, "Medico eliminado con exito", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(value = "/{IdMedico}/{IdPaciente}")
	public ResponseEntity<?> asignarPaciente(@PathVariable Long IdMedico, @PathVariable Long IdPaciente)
			throws EntityNotFoundException, IllegalOperationException {
		Medico cita = medicoServ.asignarPaciente(IdMedico, IdPaciente);
		return ResponseEntity.ok(cita);

	}
	
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
