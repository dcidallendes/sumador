package cl.dcid.sumador.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "audit")
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, length = 500)
    private String path;

    @Column(updatable = false, length = 2000)
    private String responseBody;

    @Column(updatable = false, length = 2000)
    private String requestBody;


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date timestamp;

    @NotNull
    @NotEmpty
    @Column(updatable = false)
    @NotBlank
    private String ip;

}