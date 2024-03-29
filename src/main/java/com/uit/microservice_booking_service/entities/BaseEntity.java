package com.uit.microservice_booking_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    //    @Id
    ////	id match csdl
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @Column(updatable = false,unique = true)
    //
    //    protected Long id;
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @JsonIgnore
    @CreatedDate
    @DateTimeFormat
    //	@JsonFormat

    protected LocalDateTime createAt;
    @JsonIgnore
    @LastModifiedDate
    @DateTimeFormat
    //	@JsonFormat

    protected LocalDateTime updateAt;

    //    public Long getId() {
    //        return id;
    //    }
    //
    //    public void setId(Long id) {
    //        this.id = id;
    //    }
    //
    //    public LocalDateTime getCreateAt() {
    //        return createAt;
    //    }
    //
    //    public void setCreateAt(LocalDateTime createAt) {
    //        this.createAt = createAt;
    //    }
    //
    //    public LocalDateTime getUpdateAt() {
    //        return updateAt;
    //    }
    //
    //    public void setUpdateAt(LocalDateTime updateAt) {
    //        this.updateAt = updateAt;
    //    }
}
