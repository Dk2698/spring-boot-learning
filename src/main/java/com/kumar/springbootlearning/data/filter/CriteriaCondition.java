package com.kumar.springbootlearning.data.filter;

import java.util.HashMap;
import java.util.Map;

public enum CriteriaCondition {
    EQUALS("eq"), NOT_EQUALS("ne"), LESS_THAN("lt"), GREATER_THAN("gt"), LESS_THAN_EQUAL("lte"),
    GREATER_THAN_EQUAL("gte"), IN("in"), NOT_IN("nin"), CONTAINS("contains"), NOT_CONTAINS("ncontains"),
    CONTAINS_SENSITIVE("containss"), NOT_CONTAINS_SENSITIVE("ncontainss"), BETWEEN("between"), NOT_BETWEEN("nbetween"),
    NULL("null"), NOT_NULL("nnull"), STARTS_WITH("startswith"), NOT_STARTS_WITH("nstartswith"),
    STARTS_WITH_SENSITIVE("startswiths"), NOT_STARTS_WITH_SENSITIVE("nstartswiths"), ENDS_WITH("endswith"),
    NOT_ENDS_WITH("nendswith"), ENDS_WITH_SENSITIVE("endswiths"), NOT_ENDS_WITH_SENSITIVE("nendswiths"),
    IN_ARRAY("inarray"),NULL_OR_EQUALS("nulleq");

    private static final Map<String, CriteriaCondition> BY_LABEL = new HashMap<>();

    static {
        for (CriteriaCondition e : values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public final String label;

    CriteriaCondition(String label) {
        this.label = label;
    }

    public static CriteriaCondition valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
