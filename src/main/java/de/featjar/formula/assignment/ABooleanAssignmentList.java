/*
 * Copyright (C) 2025 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-FeatJAR-formula.
 *
 * FeatJAR-formula is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * FeatJAR-formula is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatJAR-formula. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-formula> for further information.
 */
package de.featjar.formula.assignment;

import de.featjar.formula.VariableMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A list of Boolean assignments.
 *
 * @param <T> the type of the literal list
 * @author Sebastian Krieter
 * @author Elias Kuiter
 */
public abstract class ABooleanAssignmentList<T extends BooleanAssignment>
        implements IAssignmentList<T>, IBooleanRepresentation {
    protected final List<T> assignments;

    protected final VariableMap variableMap;

    public ABooleanAssignmentList(VariableMap variableMap) {
        this.variableMap = variableMap;
        assignments = new ArrayList<>();
    }

    public ABooleanAssignmentList(VariableMap variableMap, int size) {
        this.variableMap = variableMap;
        assignments = new ArrayList<>(size);
    }

    public ABooleanAssignmentList(VariableMap variableMap, Collection<? extends T> assignments) {
        this.variableMap = variableMap;
        this.assignments = new ArrayList<>(assignments);
    }

    public ABooleanAssignmentList(VariableMap variableMap, Stream<? extends T> assignments) {
        this.variableMap = variableMap;
        this.assignments = assignments.collect(Collectors.toCollection(ArrayList::new));
    }

    public ABooleanAssignmentList(ABooleanAssignmentList<T> other) {
        this(other.variableMap, other.getAll());
    }

    @Override
    public List<T> getAll() {
        return assignments;
    }

    @Override
    public BooleanAssignmentList toAssignmentList() {
        return new BooleanAssignmentList(
                variableMap,
                assignments.stream().map(BooleanAssignment::toAssignment).collect(Collectors.toList()));
    }

    @Override
    public BooleanClauseList toClauseList() {
        return new BooleanClauseList(
                variableMap,
                assignments.stream().map(BooleanAssignment::toClause).collect(Collectors.toList()));
    }

    @Override
    public BooleanSolutionList toSolutionList() {
        return new BooleanSolutionList(
                variableMap,
                assignments.stream().map(BooleanAssignment::toSolution).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ABooleanAssignmentList<?> that = (ABooleanAssignmentList<?>) o;
        return Objects.equals(assignments, that.assignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignments);
    }

    /**
     * Compares list of clauses by the number of literals.
     */
    public static class AscendingLengthComparator implements Comparator<ABooleanAssignmentList<?>> {
        @Override
        public int compare(ABooleanAssignmentList<?> o1, ABooleanAssignmentList<?> o2) {
            return addLengths(o1) - addLengths(o2);
        }

        protected int addLengths(ABooleanAssignmentList<?> o) {
            int count = 0;
            for (final BooleanAssignment literalSet : o.assignments) {
                count += literalSet.get().length;
            }
            return count;
        }
    }

    /**
     * Compares list of clauses by the number of literals.
     */
    public static class DescendingClauseListLengthComparator implements Comparator<ABooleanAssignmentList<?>> {
        @Override
        public int compare(ABooleanAssignmentList<?> o1, ABooleanAssignmentList<?> o2) {
            return addLengths(o2) - addLengths(o1);
        }

        protected int addLengths(ABooleanAssignmentList<?> o) {
            int count = 0;
            for (final BooleanAssignment literalSet : o.assignments) {
                count += literalSet.get().length;
            }
            return count;
        }
    }

    public abstract AValueAssignmentList<? extends ValueAssignment> toValue();

    public String print() {
        return toValue().print();
    }

    public VariableMap getVariableMap() {
        return variableMap;
    }
}
