/*
 * @file MedicoController.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:35:03
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
import org.springframework.web.bind.annotation.PatchMapping;
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

	/**
     * Obtiene una lista de todos los médicos registrados.
     * 
     * @return ResponseEntity con la lista de médicos o un estado de no contenido si no hay médicos.
     */
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
	 /**
     * Obtiene los detalles de un médico específico por su ID.
     * 
     * @param id Identificador del médico.
     * @return ResponseEntity con la información del médico solicitado.
     * @throws EntityNotFoundException Si el médico no se encuentra.
     */
	@GetMapping("/{id}")
	public ResponseEntity<?> ObtenerMedicosPorId(@PathVariable Long id) throws EntityNotFoundException {

		Medico medico = medicoServ.buscarMedicoById(id);

		MedicoDTO medicoDTO = modelMapper.map(medico, MedicoDTO.class);
		ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "Lista de medicos", medicoDTO);
		return ResponseEntity.ok(response);

	}

	/**
     * Guarda un nuevo médico en la base de datos.
     * 
     * @param medicoDTO DTO del médico a guardar.
     * @param result BindingResult para manejar errores de validación.
     * @return ResponseEntity con la información del médico guardado.
     * @throws IllegalOperationException Si la operación no cumple con las reglas de negocio.
     */
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
	/**
     * Actualiza la información de un médico existente.
     * 
     * @param id Identificador del médico a actualizar.
     * @param medicoDTO DTO con la información actualizada del médico.
     * @return ResponseEntity con la información del médico actualizado.
     * @throws EntityNotFoundException Si el médico no se encuentra.
     * @throws IllegalOperationException Si la operación no cumple con las reglas de negocio.
     */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MedicoDTO>> actualizarMedico(@PathVariable Long id,
			@RequestBody MedicoDTO medicoDTO) throws EntityNotFoundException, IllegalOperationException {

		Medico medicoActualizado = modelMapper.map(medicoDTO, Medico.class);
		medicoServ.actualizarMedico(id, medicoActualizado);

		MedicoDTO actualizarMedicoDTO = modelMapper.map(medicoActualizado, MedicoDTO.class);
		ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "Medico actualizado", actualizarMedicoDTO);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	 /**
     * Elimina un médico por su ID.
     * 
     * @param id Identificador del médico a eliminar.
     * @return ResponseEntity confirmando la eliminación del médico.
     * @throws EntityNotFoundException Si el médico no se encuentra.
     * @throws IllegalOperationException Si la operación no cumple con las reglas de negocio.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMedico(@PathVariable Long id)
			throws EntityNotFoundException, IllegalOperationException {
		medicoServ.eliminarMedico(id);
		ApiResponse<?> response = new ApiResponse<>(true, "Medico eliminado con exito", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Endpoint para asignar un paciente a un médico. Este método permite asociar un paciente con un médico,
	 * actualizando la relación en la base de datos.
	 * 
	 * @param IdMedico El ID del médico al cual se le asignará el paciente.
	 * @param IdPaciente El ID del paciente que será asignado al médico.
	 * @return ResponseEntity con el médico actualizado después de la asignación del paciente.
	 * @throws EntityNotFoundException Si el médico o el paciente con los IDs proporcionados no existen.
	 * @throws IllegalOperationException Si ocurre un error durante la asignación del paciente al médico.
	 */
	@PutMapping(value = "/{IdMedico}/{IdPaciente}")
	public ResponseEntity<?> asignarPaciente(@PathVariable Long IdMedico, @PathVariable Long IdPaciente)
			throws EntityNotFoundException, IllegalOperationException {
		Medico cita = medicoServ.asignarPaciente(IdMedico, IdPaciente);
		return ResponseEntity.ok(cita);

	}
	
	/**
	 * Método para asignar un jefe a un médico.
	 *
	 * @param idMedico  ID del médico al que se le asignará un jefe.
	 * @param IdMedJefe ID del médico que será asignado como jefe.
	 * @return ResponseEntity con el objeto DTO del médico actualizado y un mensaje de éxito.
	 * @throws EntityNotFoundException    Si no se encuentra el médico con el ID especificado.
	 * @throws IllegalOperationException Si se produce una operación ilegal al intentar asignar el jefe al médico.
	 */
	@PatchMapping("/{idMedico}/asignarJefe/{IdMedJefe}")
    public ResponseEntity<?> asignarJefe(@PathVariable Long idMedico, @PathVariable Long IdMedJefe) throws EntityNotFoundException, IllegalOperationException {
        Medico medico = medicoServ.asignarJefe(idMedico, IdMedJefe);
        MedicoDTO medicoDTO = modelMapper.map(medico, MedicoDTO.class);
        ApiResponse<MedicoDTO> response = new ApiResponse<>(true, "A un medico se le asigno un jefe correctamente", medicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	/**
	 * Método privado utilizado para validar los campos de entrada cuando se crea o actualiza una entidad.
	 * Este método se invoca dentro de los endpoints POST y PUT para verificar si existen errores en los datos
	 * proporcionados por el cliente. Si se encuentran errores, se devuelve una respuesta con el detalle de los mismos.
	 * 
	 * @param result Objeto BindingResult que contiene los resultados de la validación de los datos de entrada.
	 * @return ResponseEntity con un mapa de errores si se encuentran errores de validación; cada clave del mapa corresponde
	 *         al nombre del campo con error, y el valor asociado es el mensaje de error.
	 */
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
	
}
