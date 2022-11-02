package cl.dcid.sumador.controller;

import cl.dcid.sumador.dto.AuditResult;
import cl.dcid.sumador.interfaces.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/audit")
@Tag(name = "audit", description = "Endpoints de auditoría")
public class AuditController {

    @Autowired
    AuditService auditService;

    @Operation(summary = "Obtiene resultados de auditoría de endpoints de la API de forma paginada", tags = "audit")
    @ApiResponse(responseCode = "200", description = "",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuditResult.class)) })

    @GetMapping("")
    ResponseEntity<AuditResult> getAudit(
            @Parameter(description = "Número de página a consultar, comenzando desde 1")
            @Valid
            @RequestParam @Min(1) int page,

            @Valid
            @Parameter(description = "Tamaño de la página. Valor entre 1 y 100")
            @Min(1) @Max(100) @RequestParam int pageSize) {
        return new ResponseEntity<>(auditService.getAuditPage(page, pageSize), HttpStatus.OK);
    }
}
