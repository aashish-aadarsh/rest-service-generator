package com.devop.aashish.java.myapplication.application.utility;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CriteriaUtility {

    private static final String EQUALS = "=";
    private static final String NOT_EQUALS = "!=";
    private static final String GREATER_THAN = ">";
    private static final String LESS_THAN = "<";
    private static final String GREATER_EQUALS = ">=";
    private static final String LESS_EQUALS = "<=";
    private List<Criteria> orCriteria;
    private List<Criteria> andCriteria;
    private Set<ConditionValue> conditionValues;

    public Query getQuery(String criteria, Class<?> entityClass) {
        Query query = new Query();
        orCriteria = new ArrayList<>();
        andCriteria = new ArrayList<>();
        conditionValues = new HashSet<>();

        String[] conditions = criteria.split("(?=\\bAND\\b|\\bOR\\b)");

        for (String condition : conditions) {
            parseCondition(condition, entityClass);
        }

        Set<ConditionValue> conditionValuesStart = conditionValues.stream().filter(conditionValue ->
                StringUtils.isEmpty(conditionValue.logicalOp)).collect(Collectors.toSet());

        Set<ConditionValue> conditionValuesRemain = conditionValues.stream().filter(conditionValue ->
                !StringUtils.isEmpty(conditionValue.logicalOp)).collect(Collectors.toSet());

        conditionValuesStart.forEach(conditionValue -> {
            if (conditionValue.operator.equalsIgnoreCase(EQUALS)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .is(conditionValue.value));
            } else if (conditionValue.operator.equalsIgnoreCase(NOT_EQUALS)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .ne(conditionValue.value));
            } else if (conditionValue.operator.equalsIgnoreCase(GREATER_THAN)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .gt(conditionValue.value));
            } else if (conditionValue.operator.equalsIgnoreCase(LESS_THAN)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .lt(conditionValue.value));
            } else if (conditionValue.operator.equalsIgnoreCase(GREATER_EQUALS)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .gte(conditionValue.value));
            } else if (conditionValue.operator.equalsIgnoreCase(LESS_EQUALS)) {
                query.addCriteria(Criteria.where(conditionValue.fieldName)
                        .lte(conditionValue.value));

            }
        });

        conditionValuesRemain.forEach(conditionValue -> {

            if (conditionValue.operator.equalsIgnoreCase(EQUALS)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).is
                                (conditionValue.value),
                        conditionValue.logicalOp);
            } else if (conditionValue.operator.equalsIgnoreCase(NOT_EQUALS)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).ne
                                (conditionValue.value),
                        conditionValue.logicalOp);
            } else if (conditionValue.operator.equalsIgnoreCase(GREATER_THAN)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).gt
                                (conditionValue.value),
                        conditionValue.logicalOp);
            } else if (conditionValue.operator.equalsIgnoreCase(LESS_THAN)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).lt
                                (conditionValue.value),
                        conditionValue.logicalOp);

            } else if (conditionValue.operator.equalsIgnoreCase(GREATER_EQUALS)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).gte
                                (conditionValue.value),
                        conditionValue.logicalOp);

            } else if (conditionValue.operator.equalsIgnoreCase(LESS_EQUALS)) {
                addToCriteria(Criteria.where(conditionValue.fieldName).lte
                                (conditionValue.value),
                        conditionValue.logicalOp);
            }

        });
        if (andCriteria.size() > 0)
            query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));
        if (orCriteria.size() > 0)
            query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        return query;
    }


    private void addToCriteria(Criteria criteria, String logicalOp) {
        if (logicalOp.equalsIgnoreCase("OR")) {
            orCriteria.add(criteria);
        } else if (logicalOp.equalsIgnoreCase("AND")) {
            andCriteria.add(criteria);
        }

    }

    private Object returnConditionValue(String value, String fieldName, Class<?> entityClass) {
        Map<String, Field> fields = getAllFields(entityClass);
        Field field = fields.get(fieldName);
        if (null != field) {
            Class clazz = field.getType();
            try {
                if (Boolean.class == clazz)
                    return Boolean.parseBoolean(value);
                if (Byte.class == clazz)
                    return Byte.parseByte(value);
                if (Short.class == clazz)
                    return Short.parseShort(value);
                if (Integer.class == clazz)
                    return Integer.parseInt(value);
                if (Long.class == clazz)
                    return Long.parseLong(value);
                if (Float.class == clazz)
                    return Float.parseFloat(value);
                if (Double.class == clazz)
                    return Double.parseDouble(value);
            } catch (Exception ex) {
                return value;
            }
        }
        return value;
    }

    private void parseCondition(String str, Class<?> entityClass) {
        String[] splitVariables = new String[2];
        String operator = "";
        String logicalOp = "";
        String fieldName;
        if (str.contains(NOT_EQUALS)) {
            operator = NOT_EQUALS;
            splitVariables = str.split(NOT_EQUALS);
        } else if (str.contains(GREATER_EQUALS)) {
            operator = GREATER_EQUALS;
            splitVariables = str.split(GREATER_EQUALS);
        } else if (str.contains(LESS_EQUALS)) {
            operator = LESS_EQUALS;
            splitVariables = str.split(LESS_EQUALS);
        } else if (str.contains(EQUALS)) {
            operator = EQUALS;
            splitVariables = str.split(EQUALS);
        } else if (str.contains(GREATER_THAN)) {
            operator = GREATER_THAN;
            splitVariables = str.split(GREATER_THAN);
        } else if (str.contains(LESS_THAN)) {
            operator = LESS_THAN;
            splitVariables = str.split(LESS_THAN);
        }
        fieldName = splitVariables[0];
        if (splitVariables[0].startsWith("OR")) {
            logicalOp = "OR";
            fieldName = splitVariables[0].substring(2);
        } else if (splitVariables[0].startsWith(("AND"))) {
            logicalOp = "AND";
            fieldName = splitVariables[0].substring(3);
        }
        conditionValues.add(new ConditionValue(fieldName.trim(), operator,
                splitVariables[1].trim(), logicalOp, entityClass));
    }

    private Map<String, Field> getAllFields(Class<?> entityClass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(entityClass.getDeclaredFields()));

        Class<?> superClass = entityClass.getSuperclass();
        while (superClass != null) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        Map<String, Field> fieldMap = new HashMap<>();
        for (Field f : fields) {
            fieldMap.put(f.getName(), f);
        }
        return fieldMap;
    }

    private class ConditionValue {
        private String fieldName;
        private String operator;
        private Object value;
        private String logicalOp;

        ConditionValue(String fieldName, String operator, String value, String logicalOp, Class<?> entityClass) {
            this.fieldName = fieldName;
            this.operator = operator;
            this.value = (returnConditionValue(value, fieldName, entityClass));
            this.logicalOp = logicalOp;
        }
    }
}
