package com.devop.aashish.java.myapplication.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  This is the base entity, and every document or sub-document will have three properties
 *
 * <b> _id </b> : is the identity field for a document
 * <b> isActive </b> : field used to store if the document is valid or not in case of SOFT delete
 * <b> additionalProperties :field used to store key-value pair for any document, say meta-data </b>
 *
 */

public class BaseEntity implements Serializable {
    @Id
    public String _id;
    public Boolean isActive = Boolean.TRUE;
    public Map<String, Object> additionalProperties = new HashMap<>();

}
