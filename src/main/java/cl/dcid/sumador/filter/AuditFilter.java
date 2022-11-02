package cl.dcid.sumador.filter;

import cl.dcid.sumador.interfaces.AuditService;
import cl.dcid.sumador.model.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AuditFilter extends OncePerRequestFilter {

    @Autowired
    private AuditService auditService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(req, resp);

        byte[] requestBody = req.getContentAsByteArray();
        byte[] responseBody = resp.getContentAsByteArray();

        Audit audit = new Audit();
        audit.setPath(request.getRequestURI());
        audit.setIp(request.getRemoteAddr());
        audit.setRequestBody(new String(requestBody, StandardCharsets.UTF_8));

        if (isCorrectResponse(resp.getStatus())) {
            audit.setResponseBody(new String(responseBody, StandardCharsets.UTF_8));
        }

        auditService.writeAudit(audit);
        resp.copyBodyToResponse();
    }

    private boolean isCorrectResponse(int response) {
        return response < 400;
    }

}
