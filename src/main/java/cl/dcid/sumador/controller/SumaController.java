package cl.dcid.sumador.controller;

import cl.dcid.sumador.interfaces.SumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suma")
@Tag(name = "suma", description = "Endpoints de suma")
public class SumaController {

    @Autowired
    SumService sumService;

    @Operation(summary = "Suma dos números pasados como parámetro y añade un porcentaje a esta suma", tags = "suma")
    @ApiResponse(responseCode = "200", description = "Resultado de la suma de los números más un porcentaje",
            content = { @Content(mediaType = "application/json") })
    @GetMapping("")
    ResponseEntity<Double> sumar(
            @Parameter(description = "Sumando", required = true)
            @RequestParam double sumando1,

            @Parameter(description = "Sumando", required = true)
            @RequestParam double sumando2) {
        return new ResponseEntity<>(sumService.sum(sumando1, sumando2), HttpStatus.OK);
    }
}
