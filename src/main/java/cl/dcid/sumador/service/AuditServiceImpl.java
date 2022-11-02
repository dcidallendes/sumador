package cl.dcid.sumador.service;

import cl.dcid.sumador.dto.AuditItem;
import cl.dcid.sumador.dto.AuditResult;
import cl.dcid.sumador.interfaces.AuditService;
import cl.dcid.sumador.model.Audit;
import cl.dcid.sumador.repository.AuditRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AuditResult getAuditPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("timestamp"));
        Page<Audit> pageResult = auditRepository.findAll(pageable);

        List<AuditItem> pageItems = pageResult.get()
                .map(e -> modelMapper.map(e, AuditItem.class))
                .collect(Collectors.toList());

        AuditResult auditResult = new AuditResult();
        auditResult.setCurrentPage(page);
        auditResult.setResultCount(pageResult.getTotalElements());
        auditResult.setPageSize(pageSize);
        auditResult.setPageItems(pageItems);

        return auditResult;
    }

    @Override
    public void writeAudit(Audit audit) {
        auditRepository.save(audit);
    }
}
