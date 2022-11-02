package cl.dcid.sumador.repository;

import cl.dcid.sumador.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}