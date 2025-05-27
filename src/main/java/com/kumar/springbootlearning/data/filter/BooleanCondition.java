package com.kumar.springbootlearning.data.filter;

import java.util.HashMap;
import java.util.Map;

public enum BooleanCondition {
    OR("or"), AND("and"), NOT("not");

    private static final Map<String, BooleanCondition> BY_LABEL = new HashMap<>();

    static {
        for (BooleanCondition e : values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public final String label;

    BooleanCondition(String label) {
        this.label = label;
    }

    public static BooleanCondition valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
