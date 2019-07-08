package com.devop.aashish.java.myapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class BaseTransactionalEntity extends BaseEntity implements Serializable {
    @JsonIgnore
    public String createdByUser;
    @JsonIgnore
    public String updatedByUser;
    public Date createdDateTime;
    public Date updatedDateTime;

}
