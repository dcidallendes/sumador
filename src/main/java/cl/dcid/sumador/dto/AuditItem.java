package cl.dcid.sumador.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuditItem {
    private String path;
    private String responseBody;
    private String requestBody;
    private Date timestamp;
    private String ip;
}
