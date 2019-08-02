package com.devop.aashish.java.myapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

/**
 * This is the base transactional entity and it extends BaseEntity. This class is extended by all resource(domain)
 * of the service.
 * This class is used for auditing the entity
 *
 * <b>createdByUser</b> : The user who created the resource
 * <b>updatedByUser</b> : The user who last modified the resource
 * <b>createdDateTime</b> : The date-time when resource was created
 * <b>updatedDateTime</b> : The date-time when resource was last modified
 */

public class BaseTransactionalEntity extends BaseEntity implements Serializable {
    @JsonIgnore
    @CreatedBy
    public String createdByUser;

    @JsonIgnore
    @LastModifiedBy
    public String updatedByUser;

    @JsonIgnore
    @CreatedDate
    public Instant createdDateTime;

    @JsonIgnore
    @LastModifiedDate
    public Instant updatedDateTime;

}
