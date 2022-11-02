package cl.dcid.sumador.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank(message = "username es requerido")
    private String username;

    @Size(min = 8, max = 50, message = "password debe tener como mínimo 8 caracteres y 50 como máximo")
    private String password;

    @Email(message = "email debe tener un formato válido")
    @NotBlank(message = "email es requerido")
    private String email;
}
