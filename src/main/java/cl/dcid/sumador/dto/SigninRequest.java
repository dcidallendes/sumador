package cl.dcid.sumador.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SigninRequest {
    @NotBlank(message = "username es requerido")
    private String username;

    @NotBlank(message = "password es requerido")
    private String password;
}
