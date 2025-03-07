package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@MappedSuperclass // annotation qui ne génère pas de table en base - mais qui permet aux classes de récupèrer ses attributs
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private int version;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false, updatable = false) //sinon on aura null en cas de MAJ
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;
}
