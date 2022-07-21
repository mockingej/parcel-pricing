package com.gcash.enums;

public enum VolumeBasedEnum {

    SMALL(1, "< 1500", "small", 0.03),
    MEDIUM(2, "< 2500", "medium", 0.04),
    LARGE(3, "> 2499", "large", 0.05);

    private final Integer priority;
    private final String condition;
    private final String type;
    private final double cost;

    VolumeBasedEnum(Integer priority, String condition, String type, double cost) {
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
