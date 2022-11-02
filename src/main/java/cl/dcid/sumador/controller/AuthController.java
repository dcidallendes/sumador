package cl.dcid.sumador.controller;

import cl.dcid.sumador.controller.exceptions.UserAlreadyExistsException;
import cl.dcid.sumador.dto.SigninRequest;
import cl.dcid.sumador.dto.SigninResult;
import cl.dcid.sumador.dto.SignupRequest;
import cl.dcid.sumador.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("/auth")

@Tag(name = "auth", description = "Endpoints de autorización y autenticación")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Permite autenticar usuario y obtener credenciales para las llamadas a la API", tags = "auth")
    @ApiResponse(responseCode = "200",
            description = "Resultado de autenticación correcto, con las credenciales para consumir los otros endpoint de la API",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SigninResult.class)) })
    @PostMapping(value = "/signin", consumes = {"application/json"})
    public ResponseEntity<SigninResult> signin(@Valid @RequestBody SigninRequest signinRequest) {
        return new ResponseEntity<>(authService.signin(signinRequest), HttpStatus.OK);
    }

    @Operation(summary = "Permite la creación de un usuario en el sistema", tags = "auth")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @PostMapping(value = "/signup", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest signupRequest) throws UserAlreadyExistsException {
        authService.signup(signupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
