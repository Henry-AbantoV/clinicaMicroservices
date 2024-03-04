package edu.unc.clinica.util;


import lombok.Data;

@Data
public class ApiResponse<T> {


    // Indica si la operación fue exitosa.
    private boolean success;

    // Mensaje asociado con la operación.
    private String message;

    // Datos relacionados con la operación.
    private T data;

    /**
     * Constructor de la clase ApiResponse.
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje asociado con la operación.
     * @param data Datos relacionados con la operación.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
