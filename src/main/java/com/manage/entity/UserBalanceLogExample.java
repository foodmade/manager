package com.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class UserBalanceLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public UserBalanceLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andLogidIsNull() {
            addCriterion("LOGID is null");
            return (Criteria) this;
        }

        public Criteria andLogidIsNotNull() {
            addCriterion("LOGID is not null");
            return (Criteria) this;
        }

        public Criteria andLogidEqualTo(Long value) {
            addCriterion("LOGID =", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidNotEqualTo(Long value) {
            addCriterion("LOGID <>", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidGreaterThan(Long value) {
            addCriterion("LOGID >", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidGreaterThanOrEqualTo(Long value) {
            addCriterion("LOGID >=", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidLessThan(Long value) {
            addCriterion("LOGID <", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidLessThanOrEqualTo(Long value) {
            addCriterion("LOGID <=", value, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidIn(List<Long> values) {
            addCriterion("LOGID in", values, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidNotIn(List<Long> values) {
            addCriterion("LOGID not in", values, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidBetween(Long value1, Long value2) {
            addCriterion("LOGID between", value1, value2, "logid");
            return (Criteria) this;
        }

        public Criteria andLogidNotBetween(Long value1, Long value2) {
            addCriterion("LOGID not between", value1, value2, "logid");
            return (Criteria) this;
        }

        public Criteria andBalidIsNull() {
            addCriterion("BALID is null");
            return (Criteria) this;
        }

        public Criteria andBalidIsNotNull() {
            addCriterion("BALID is not null");
            return (Criteria) this;
        }

        public Criteria andBalidEqualTo(String value) {
            addCriterion("BALID =", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidNotEqualTo(String value) {
            addCriterion("BALID <>", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidGreaterThan(String value) {
            addCriterion("BALID >", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidGreaterThanOrEqualTo(String value) {
            addCriterion("BALID >=", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidLessThan(String value) {
            addCriterion("BALID <", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidLessThanOrEqualTo(String value) {
            addCriterion("BALID <=", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidLike(String value) {
            addCriterion("BALID like", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidNotLike(String value) {
            addCriterion("BALID not like", value, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidIn(List<String> values) {
            addCriterion("BALID in", values, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidNotIn(List<String> values) {
            addCriterion("BALID not in", values, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidBetween(String value1, String value2) {
            addCriterion("BALID between", value1, value2, "balid");
            return (Criteria) this;
        }

        public Criteria andBalidNotBetween(String value1, String value2) {
            addCriterion("BALID not between", value1, value2, "balid");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Float value) {
            addCriterion("AMOUNT =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Float value) {
            addCriterion("AMOUNT <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Float value) {
            addCriterion("AMOUNT >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Float value) {
            addCriterion("AMOUNT >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Float value) {
            addCriterion("AMOUNT <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Float value) {
            addCriterion("AMOUNT <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Float> values) {
            addCriterion("AMOUNT in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Float> values) {
            addCriterion("AMOUNT not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Float value1, Float value2) {
            addCriterion("AMOUNT between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Float value1, Float value2) {
            addCriterion("AMOUNT not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andOptiontypeIsNull() {
            addCriterion("OPTIONTYPE is null");
            return (Criteria) this;
        }

        public Criteria andOptiontypeIsNotNull() {
            addCriterion("OPTIONTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOptiontypeEqualTo(Integer value) {
            addCriterion("OPTIONTYPE =", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeNotEqualTo(Integer value) {
            addCriterion("OPTIONTYPE <>", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeGreaterThan(Integer value) {
            addCriterion("OPTIONTYPE >", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPTIONTYPE >=", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeLessThan(Integer value) {
            addCriterion("OPTIONTYPE <", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeLessThanOrEqualTo(Integer value) {
            addCriterion("OPTIONTYPE <=", value, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeIn(List<Integer> values) {
            addCriterion("OPTIONTYPE in", values, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeNotIn(List<Integer> values) {
            addCriterion("OPTIONTYPE not in", values, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeBetween(Integer value1, Integer value2) {
            addCriterion("OPTIONTYPE between", value1, value2, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOptiontypeNotBetween(Integer value1, Integer value2) {
            addCriterion("OPTIONTYPE not between", value1, value2, "optiontype");
            return (Criteria) this;
        }

        public Criteria andOldbalanceIsNull() {
            addCriterion("OLDBALANCE is null");
            return (Criteria) this;
        }

        public Criteria andOldbalanceIsNotNull() {
            addCriterion("OLDBALANCE is not null");
            return (Criteria) this;
        }

        public Criteria andOldbalanceEqualTo(Float value) {
            addCriterion("OLDBALANCE =", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceNotEqualTo(Float value) {
            addCriterion("OLDBALANCE <>", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceGreaterThan(Float value) {
            addCriterion("OLDBALANCE >", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceGreaterThanOrEqualTo(Float value) {
            addCriterion("OLDBALANCE >=", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceLessThan(Float value) {
            addCriterion("OLDBALANCE <", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceLessThanOrEqualTo(Float value) {
            addCriterion("OLDBALANCE <=", value, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceIn(List<Float> values) {
            addCriterion("OLDBALANCE in", values, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceNotIn(List<Float> values) {
            addCriterion("OLDBALANCE not in", values, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceBetween(Float value1, Float value2) {
            addCriterion("OLDBALANCE between", value1, value2, "oldbalance");
            return (Criteria) this;
        }

        public Criteria andOldbalanceNotBetween(Float value1, Float value2) {
            addCriterion("OLDBALANCE not between", value1, value2, "oldbalance");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}