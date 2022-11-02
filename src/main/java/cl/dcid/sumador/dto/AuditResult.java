package cl.dcid.sumador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuditResult {
    private long resultCount;
    private int currentPage;
    private int pageSize;
    private List<AuditItem> pageItems;

    @JsonProperty("hasNextPage")
    public boolean hasNextPage() {
        return resultCount > currentPage * pageSize;
    }
}
