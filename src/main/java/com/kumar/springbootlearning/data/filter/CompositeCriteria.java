package com.kumar.springbootlearning.data.filter;

import java.util.List;

public record CompositeCriteria(BooleanCondition condition, List<SimpleCriteria> simpleConditions,
                                List<CompositeCriteria> compositeConditions) {
}
