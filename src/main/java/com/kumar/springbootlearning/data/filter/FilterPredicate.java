package com.kumar.springbootlearning.data.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilterPredicate {

    private final Map<String, List<SimpleCriteria>> criteriaMap = new HashMap<>();

    @Getter
    private final List<SimpleCriteria> conditionsList = new ArrayList<>();
    @Getter
    private final List<CompositeCriteria> compositeCondistionList = new ArrayList<>();

    @Getter
    @Setter
    private String wildSearchText;

    public FilterPredicate add(SimpleCriteria criteriaDefinition) {
        log.debug("Adding simple criteria {} ", criteriaDefinition);
        conditionsList.add(criteriaDefinition);
        criteriaMap.computeIfAbsent(criteriaDefinition.field(), (key) -> {
            final ArrayList<SimpleCriteria> simpleCriteria = new ArrayList<>();
            simpleCriteria.add(criteriaDefinition);
            return simpleCriteria;
        });
        return this;
    }

    public FilterPredicate add(CompositeCriteria criteriaDefinition) {
        log.debug("Adding composite criteria {} ", criteriaDefinition);
        compositeCondistionList.add(criteriaDefinition);
        return this;
    }


    public List<SimpleCriteria> getConditionForField(String fieldName) {
        return criteriaMap.get(fieldName);
    }

    /**
     * Replaces the criteria for a given field with the provided list of criteria.
     *
     * @param fieldName    The field name for which criteria will be replaced
     * @param criteriaList The list of criteria to replace the existing criteria
     * @return The updated FilterPredicate object after replacing the criteria
     */
    public FilterPredicate replaceCriteria(String fieldName, List<SimpleCriteria> criteriaList) {
        criteriaMap.remove(fieldName);
        for (int i = conditionsList.size() - 1; i >= 0; i--) {
            if (conditionsList.get(i).field().equals(fieldName)) {
                conditionsList.remove(i);
            }
        }
        conditionsList.addAll(criteriaList);
        criteriaMap.put(fieldName, criteriaList);
        return this;
    }

    /**
     * Remove the criteria for a given field with the provided list of criteria.
     *
     * @param fieldName The field name for which criteria will be replaced
     * @return The updated FilterPredicate object after replacing the criteria
     */
    public FilterPredicate removeCriteria(String fieldName) {
        criteriaMap.remove(fieldName);
        for (int i = conditionsList.size() - 1; i >= 0; i--) {
            if (conditionsList.get(i).field().equals(fieldName)) {
                conditionsList.remove(i);
            }
        }
        return this;
    }


    /**
     * Expands the quick search criteria (i.e. q=xyz in the incoming request) to different fields
     *
     * @param wildSearchFields - list of fields to which the search filter is to be applied
     */
    public void enrichSearchCriteria(List<String> wildSearchFields) {
        if (StringUtils.hasText(wildSearchText)) {
            List<String> searchValue = new ArrayList<>();
            searchValue.add(wildSearchText);
            List<SimpleCriteria> orConditions = new ArrayList<>();
            wildSearchFields.forEach(field -> orConditions.add(new SimpleCriteria(field, CriteriaCondition.CONTAINS, searchValue)));
            this.add(new CompositeCriteria(BooleanCondition.OR, orConditions, null));
        }
    }

    /**
     * Expands the quick search criteria (i.e., q=xyz in the incoming request) to different fields
     *
     * @param wildSearchFields - list of fields to which the search filter is to be applied
     * @return instance of CompositeCriteria with OR condition if the search text is present else null
     */
    public CompositeCriteria getWildSearchCriteria(List<String> wildSearchFields) {
        if (StringUtils.hasText(wildSearchText)) {
            List<String> searchValue = new ArrayList<>();
            searchValue.add(wildSearchText);
            List<SimpleCriteria> orConditions = new ArrayList<>();
            wildSearchFields.forEach(field -> orConditions.add(new SimpleCriteria(field, CriteriaCondition.CONTAINS, searchValue)));
            return new CompositeCriteria(BooleanCondition.OR, orConditions, null);
        }
        return null;
    }
}