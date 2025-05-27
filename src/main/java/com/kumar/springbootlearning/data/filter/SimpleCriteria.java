package com.kumar.springbootlearning.data.filter;

import java.util.List;

public record SimpleCriteria(String field, CriteriaCondition condition, List<String> value) {
}

