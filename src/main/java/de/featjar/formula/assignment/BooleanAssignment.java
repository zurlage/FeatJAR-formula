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

import de.featjar.analysis.ISolver;
import de.featjar.base.data.IntegerList;
import de.featjar.base.data.Maps;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.data.Sets;
import de.featjar.formula.VariableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.IntStream;

/**
 * Assigns Boolean values to integer-identified
 * {@link de.featjar.formula.structure.term.value.Variable variables}. Can be
 * used to represent a set of literals for use in a satisfiability
 * {@link ISolver}. Implemented as an unordered list of indices to variables in
 * some unspecified {@link VariableMap}. An index can be negative, indicating a
 * negated occurrence of its variable, or 0, indicating no occurrence, and it
 * may occur multiple times. For specific use cases, consider using
 * {@link BooleanClause} (a disjunction of literals) or {@link BooleanSolution}
 * (a conjunction of literals).
 *
 * @author Sebastian Krieter
 * @author Elias Kuiter
 */
public class BooleanAssignment extends IntegerList implements IAssignment<Integer, Boolean>, IBooleanRepresentation {

    private static final long serialVersionUID = 1614980283996088122L;

    /**
     * Constructs a new literal array from the given clause with all literals
     * removed that evaluate to {@code false}. Returns {@code null} if any literal
     * evaluate to {@code true} or the clause contains a literal and its complement.
     *
     * @param clause the given clause
     * @param core   the known literals
     * @return a new array containing only unknown literals or {@code null} if the
     *         clause is a tautology
     */
    public static int[] unitPropagation(BooleanClause clause, BooleanAssignment core) {
        final int[] literals = clause.get();
        final LinkedHashSet<Integer> literalSet = new LinkedHashSet<>(literals.length << 1);

        for (int var : literals) {
            if (core.indexOf(var) >= 0) {
                return null;
            } else if (core.indexOf(-var) < 0) {
                if (literalSet.contains(-var)) {
                    return null;
                } else {
                    literalSet.add(var);
                }
            }
        }
        final int[] literalArray = new int[literalSet.size()];
        int i = 0;
        for (final int lit : literalSet) {
            literalArray[i++] = lit;
        }
        return literalArray;
    }

    public static int[] simplify(int[] literals) {
        final LinkedHashSet<Integer> integerSet = Sets.empty();
        for (final int integer : literals) {
            if (integer != 0 && integerSet.contains(-integer)) {
                // If this assignment is a contradiction or tautology, it can be simplified.
                return new int[] {};
            } else {
                integerSet.add(integer);
            }
        }
        if (integerSet.size() == literals.length) {
            return Arrays.copyOf(literals, literals.length);
        }
        int[] newArray = new int[integerSet.size()];
        int i = 0;
        for (final int lit : integerSet) {
            newArray[i++] = lit;
        }
        return newArray;
    }

    public static Result<int[]> adapt(
            int[] oldIntegers, VariableMap oldVariableMap, VariableMap newVariableMap, boolean inPlace) {
        final int[] newIntegers = inPlace ? oldIntegers : new int[oldIntegers.length];
        for (int i = 0; i < oldIntegers.length; i++) {
            final int l = oldIntegers[i];
            final Result<String> name = oldVariableMap.get(Math.abs(l));
            if (name.isPresent()) {
                final Result<Integer> index = newVariableMap.get(name.get());
                if (index.isPresent()) {
                    newIntegers[i] = l < 0 ? -index.get() : index.get();
                } else {
                    return Result.empty(new Problem("No variable named " + name.get(), Problem.Severity.ERROR));
                }
            } else {
                return Result.empty(new Problem("No variable with index " + l, Problem.Severity.ERROR));
            }
        }
        return Result.of(newIntegers);
    }

    public static Result<int[]> adaptAddVariables(
            int[] oldIntegers, VariableMap oldVariableMap, VariableMap newVariableMap) {
        final int[] newIntegers = new int[oldIntegers.length];
        for (int i = 0; i < oldIntegers.length; i++) {
            final int l = oldIntegers[i];
            final Result<String> name = oldVariableMap.get(Math.abs(l));
            if (name.isPresent()) {
                Result<Integer> index = newVariableMap.get(name.get());
                if (index.isEmpty()) {
                    newVariableMap.add(name.get());
                    index = newVariableMap.get(name.get());
                }
                newIntegers[i] = l < 0 ? -index.get() : index.get();
            } else {
                return Result.empty(new Problem("No variable with index " + l, Problem.Severity.ERROR));
            }
        }
        return Result.of(newIntegers);
    }

    public BooleanAssignment(int... integers) {
        super(integers);
    }

    public BooleanAssignment(Collection<Integer> integers) {
        super(integers);
    }

    public BooleanAssignment(BooleanAssignment booleanAssignment) {
        super(booleanAssignment);
    }

    public final int[] simplify() {
        return simplify(elements);
    }

    public final Result<int[]> adapt(VariableMap oldVariableMap, VariableMap newVariableMap) {
        return adapt(elements, oldVariableMap, newVariableMap, false);
    }

    public int indexOfVariable(int variable) {
        if (variable < 0) {
            throw new IllegalArgumentException(String.format("%d is negative", variable));
        }
        return IntStream.range(0, elements.length)
                .filter(i -> Math.abs(elements[i]) == variable)
                .findFirst()
                .orElse(-1);
    }

    public int[] indicesOfVariable(int variable) {
        if (variable < 0) {
            throw new IllegalArgumentException(String.format("%d is negative", variable));
        }
        return IntStream.range(0, elements.length)
                .filter(i -> Math.abs(elements[i]) == variable)
                .toArray();
    }

    public final boolean containsVariable(int integer) {
        return indexOfVariable(integer) >= 0;
    }

    public final boolean containsAnyVariable(int... integers) {
        return Arrays.stream(integers).anyMatch(integer -> containsVariable(integer));
    }

    public final boolean containsAllVariables(int... integers) {
        return Arrays.stream(integers).allMatch(integer -> containsVariable(integer));
    }

    public final boolean containsNoneVariables(int... integers) {
        return Arrays.stream(integers).noneMatch(integer -> containsVariable(integer));
    }

    /**
     * {@return the intersection of this integer list with the given integers}
     *
     * @param integers the integers
     */
    public final int[] retainAllVariables(int... integers) {
        boolean[] intersectionMarker = new boolean[elements.length];
        int count = 0;
        for (int integer : integers) {
            final int[] indices = indicesOfVariable(integer);
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                if (index >= 0 && !intersectionMarker[index]) {
                    count++;
                    intersectionMarker[index] = true;
                }
            }
        }

        int[] newArray = new int[count];
        int j = 0;
        for (int i = 0; i < elements.length; i++) {
            if (intersectionMarker[i]) {
                newArray[j++] = elements[i];
            }
        }
        assert Arrays.stream(elements)
                .allMatch(e -> Arrays.stream(newArray).anyMatch(i -> i == e)
                        == Arrays.stream(integers).anyMatch(i -> i == Math.abs(e)));
        return newArray;
    }

    /**
     * {@return the difference of this integer list and the given integers}
     *
     * @param integers the integers
     */
    public final int[] removeAllVariables(int... integers) {
        boolean[] intersectionMarker = new boolean[elements.length];
        int count = 0;
        for (int integer : integers) {
            final int[] indices = indicesOfVariable(integer);
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                if (index >= 0 && !intersectionMarker[index]) {
                    count++;
                    intersectionMarker[index] = true;
                }
            }
        }

        int[] newArray = new int[elements.length - count];
        int j = 0;
        for (int i = 0; i < elements.length; i++) {
            if (!intersectionMarker[i]) {
                newArray[j++] = elements[i];
            }
        }
        assert Arrays.stream(elements)
                .allMatch(e -> Arrays.stream(newArray).anyMatch(i -> i == e)
                        ^ Arrays.stream(integers).anyMatch(i -> i == Math.abs(e)));
        return newArray;
    }

    @Override
    public ValueAssignment toValue() {
        return VariableMap.toValue(this);
    }

    @Override
    public LinkedHashMap<Integer, Boolean> getAll() {
        LinkedHashMap<Integer, Boolean> map = Maps.empty();
        for (int literal : elements) {
            if (literal != 0) {
                map.put(literal, literal > 0);
            }
        }
        return map;
    }

    @Override
    public Result<Boolean> getValue(Integer variable) {
        int index = indexOfVariable(variable);
        if (index < 0) {
            return Result.empty();
        }
        int value = get(index);
        return value == 0 ? Result.empty() : Result.of(value > 0);
    }

    @Override
    public String print() {
        return toValue().print();
    }

    public BooleanAssignment(Assignment assignment, VariableMap map) {
        super(new int[assignment.size()]);
        int i = 0;
        for (var keyValue : assignment.getAll().entrySet()) {
            String key = keyValue.getKey();

            Result<Integer> index = map.get(key);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("Variable " + key + " does not exist in the variable map.");
            }
            elements[i++] = index.get();
        }
    }

    @Override
    public String toString() {
        return String.format("BooleanAssignment[%s]", print());
    }

    @Override
    public BooleanAssignment toAssignment() {
        return this;
    }

    @Override
    public BooleanClause toClause() {
        return new BooleanClause(Arrays.copyOf(elements, elements.length));
    }

    @Override
    public BooleanSolution toSolution() {
        return new BooleanSolution(IntStream.of(elements).map(Math::abs).max().orElse(0), elements);
    }

    public BooleanAssignment inverse() {
        return new BooleanAssignment(negate());
    }

    public BooleanAssignment addAll(BooleanAssignment integers) {
        return new BooleanAssignment(addAll(integers.get()));
    }

    public BooleanAssignment retainAll(BooleanAssignment integers) {
        return new BooleanAssignment(retainAll(integers.get()));
    }

    public BooleanAssignment retainAllVariables(BooleanAssignment integers) {
        return new BooleanAssignment(retainAllVariables(integers.get()));
    }

    public BooleanAssignment removeAll(BooleanAssignment integers) {
        return new BooleanAssignment(removeAll(integers.get()));
    }

    public BooleanAssignment removeAllVariables(BooleanAssignment integers) {
        return new BooleanAssignment(removeAllVariables(integers.get()));
    }
}
