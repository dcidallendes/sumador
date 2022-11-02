package cl.dcid.sumador.interfaces;

import cl.dcid.sumador.controller.exceptions.UserAlreadyExistsException;
import cl.dcid.sumador.dto.SigninRequest;
import cl.dcid.sumador.dto.SigninResult;
import cl.dcid.sumador.dto.SignupRequest;

public interface AuthService {
    SigninResult signin(SigninRequest signinRequest);

    void signup(SignupRequest signupReqßuest) throws UserAlreadyExistsException;
}
