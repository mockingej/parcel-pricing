package com.gcash.enums;

public enum WeightBasedEnum {
    REJECT(1, "> 50", "rejected", 0),
    HEAVY(2, "> 10", "heavy", 20);

    private final Integer priority;
    private final String condition;
    private final String type;
    private final double cost;

    WeightBasedEnum(Integer priority, String condition, String type, double cost) {
        this.priority = priority;
        this.condition = condition;
        this.type = type;
        this.cost = cost;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getCondition() {
        return condition;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }
}
