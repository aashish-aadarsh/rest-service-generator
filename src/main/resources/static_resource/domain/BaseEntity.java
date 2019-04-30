package com.devop.aashish.java.myapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Serializable {
    @Id
    public String _id;
    @JsonIgnore
    public String createdByUser;
    @JsonIgnore
    public String updatedByUser;
    public Boolean isActive = Boolean.TRUE;
    public Date createdDateTime = new Date();
    public Date updatedDateTime;
    public Map<String, Object> additionalProperties = new HashMap<>();

}
