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
import edu.unc.clinica.domain.Especialidad;
import edu.unc.clinica.dto.CitaDTO;
import edu.unc.clinica.dto.EspecialidadDTO;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.services.EspecialidadService;
import edu.unc.clinica.util.ApiResponse;

@RestController
@RequestMapping(value = "api/v1/especialidades", headers = "Api-Version=1")
public class EspecialidadController {

	@Autowired
	private EspecialidadService especS;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<?> obtenerTodasEspecialidades() {

		List<Especialidad> especialidad = especS.listarEspecialidades();
		if (especialidad == null || especialidad.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<EspecialidadDTO> espDto = especialidad.stream()
					.map(espec -> modelMapper.map(espec, EspecialidadDTO.class)).collect(Collectors.toList());
			ApiResponse<List<EspecialidadDTO>> response = new ApiResponse<>(true, "Lista de citas", espDto);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerEspecialidadPorId(@PathVariable Long id) throws EntityNotFoundException {

		Especialidad especialidad = especS.buscarEspecialidadbyId(id);

		EspecialidadDTO especialidadDto = modelMapper.map(especialidad, EspecialidadDTO.class);
		ApiResponse<EspecialidadDTO> response = new ApiResponse<>(true, "Lista de facturas", especialidadDto);
		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<?> guardarEspecialidad(@Valid @RequestBody Especialidad especDto, BindingResult result)
			throws IllegalOperationException {

		if (result.hasErrors()) {
			return validar(result);
		}
		Especialidad nuevaEsp = modelMapper.map(especDto, Especialidad.class);
		especS.grabarEspecilidad(nuevaEsp);
		EspecialidadDTO saveEspDto = modelMapper.map(nuevaEsp, EspecialidadDTO.class);
		ApiResponse<EspecialidadDTO> response = new ApiResponse<>(true, "Cita guardada", saveEspDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EspecialidadDTO>> actualizarEspecialidad(@Valid @RequestBody CitaDTO citaDto,
			BindingResult result, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {

		Especialidad espActualizada = modelMapper.map(citaDto, Especialidad.class);
		especS.actualizarEspecilidad(id, espActualizada);
		EspecialidadDTO updateEsp = modelMapper.map(espActualizada, EspecialidadDTO.class);
		ApiResponse<EspecialidadDTO> response = new ApiResponse<>(true, "Cita actualizada", updateEsp);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarEspecialidad(@PathVariable Long id)
			throws EntityNotFoundException, IllegalOperationException {
		especS.eliminarEspecialidad(id);
		ApiResponse<?> response = new ApiResponse<>(true, "Especialidad eliminada con exito", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping(value = "/{IdEsp}/{IdMedic}")
	public ResponseEntity<?> asignarMedicos( @PathVariable Long IdEsp,
			@PathVariable Long IdMedic) throws EntityNotFoundException, IllegalOperationException {
		
		Especialidad especialidad = especS.asignarMedicos(IdEsp, IdMedic);
		return ResponseEntity.ok(especialidad);

	}

	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
