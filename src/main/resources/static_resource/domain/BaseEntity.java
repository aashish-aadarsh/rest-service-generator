package com.devop.aashish.java.myapplication.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Serializable {
    @Id
    public String _id;
    public Boolean isActive = Boolean.TRUE;
    public Map<String, Object> additionalProperties = new HashMap<>();

}
