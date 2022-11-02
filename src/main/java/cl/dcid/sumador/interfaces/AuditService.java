package cl.dcid.sumador.interfaces;

import cl.dcid.sumador.dto.AuditResult;
import cl.dcid.sumador.model.Audit;
import org.springframework.scheduling.annotation.Async;

public interface AuditService {
    AuditResult getAuditPage(int page, int pageSize);

    @Async
    void writeAudit(Audit audit);
}
