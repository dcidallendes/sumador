package cl.dcid.sumador.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserAlreadyExistsException extends Exception{
    private Map<String,String> errors;
}
