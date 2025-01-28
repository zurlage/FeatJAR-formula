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
import de.featjar.formula.VariableMap;
import java.util.Arrays;
import java.util.Collection;

/**
 * A Boolean clause; that is, a disjunction of literals. Implemented as a sorted
 * list of indices. Often used as input to a SAT {@link ISolver}. Indices are
 * ordered naturally; that is, in ascending order, so negative indices come
 * before positive indices. The same index may occur multiple times, but no
 * element may be 0.
 *
 * @author Sebastian Krieter
 * @author Elias Kuiter
 */
public class BooleanClause extends BooleanAssignment implements IClause<Integer, Boolean> {

    private static final long serialVersionUID = -684031217097515442L;

    public BooleanClause(int... integers) {
        this(integers, true);
        assert Arrays.stream(integers).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(integers);
    }

    public BooleanClause(int[] integers, boolean sort) {
        super(integers);
        assert Arrays.stream(integers).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(integers);
        assert sort
                        || Arrays.stream(integers)
                                        .reduce((a, b) -> a != 0 && a < b ? b : 0)
                                        .orElse(1)
                                != 0
                : "unsorted: " + Arrays.toString(integers);
        if (sort) sort();
    }

    public BooleanClause(Collection<Integer> integers) {
        super(integers);
        assert integers.stream().noneMatch(a -> a == 0) : "contains zero: " + integers.toString();
        sort();
    }

    public BooleanClause(BooleanClause booleanClause) {
        super(booleanClause);
    }

    protected void sort() {
        hashCodeValid = false;
        Arrays.sort(elements);
    }

    @Override
    public int[] getNonZeroValues() {
        assert Arrays.stream(elements).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(elements);
        return copy();
    }

    @Override
    public int countNonZero() {
        assert Arrays.stream(elements).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(elements);
        return size();
    }

    @Override
    public int countNegatives() {
        return -(Arrays.binarySearch(elements, 0) + 1);
    }

    @Override
    public int countPositives() {
        return elements.length + (Arrays.binarySearch(elements, 0) + 1);
    }

    @Override
    public int[] getPositiveValues() {
        return Arrays.copyOfRange(elements, elements.length - countPositives(), elements.length);
    }

    @Override
    public int[] getNegativeValues() {
        return Arrays.copyOfRange(elements, 0, countNegatives());
    }

    @Override
    public int indexOf(int integer) {
        return Arrays.binarySearch(elements, integer);
    }

    @Override
    public int[] indicesOf(int integer) {
        return new int[] {Arrays.binarySearch(elements, integer)};
    }

    @Override
    public BooleanClause inverse() {
        final int[] inverse = new int[elements.length];
        final int highestIndex = inverse.length - 1;
        for (int i = 0; i < inverse.length; i++) {
            inverse[highestIndex - i] = -elements[i];
        }
        return new BooleanClause(inverse, false);
    }

    @Override
    public ValueClause toValue() {
        return VariableMap.toValue(this);
    }

    @Override
    public String toString() {
        return String.format("BooleanClause[%s]", print());
    }

    @Override
    public BooleanAssignment toAssignment() {
        return this;
    }

    @Override
    public BooleanClause toClause() {
        return this;
    }

    @Override
    public BooleanClause addAll(BooleanAssignment integers) {
        return new BooleanClause(addAll(integers.get()));
    }

    @Override
    public BooleanClause retainAll(BooleanAssignment integers) {
        return new BooleanClause(retainAll(integers.get()));
    }

    @Override
    public BooleanClause retainAllVariables(BooleanAssignment integers) {
        return new BooleanClause(retainAllVariables(integers.get()));
    }

    @Override
    public BooleanClause removeAll(BooleanAssignment integers) {
        return new BooleanClause(removeAll(integers.get()));
    }

    @Override
    public BooleanClause removeAllVariables(BooleanAssignment integers) {
        return new BooleanClause(removeAllVariables(integers.get()));
    }
}
