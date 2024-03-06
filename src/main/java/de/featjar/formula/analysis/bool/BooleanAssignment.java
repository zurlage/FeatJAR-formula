/*
 * Copyright (C) 2024 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-formula.
 *
 * formula is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * formula is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with formula. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-formula> for further information.
 */
package de.featjar.formula.analysis.bool;

import de.featjar.formula.analysis.VariableMap;
import de.featjar.formula.analysis.value.ValueAssignment;
import java.util.Collection;

/**
 * Primary implementation of {@link ABooleanAssignment}. To be used when neither
 * CNF nor DNF semantics are associated with an assignment.
 *
 * @author Elias Kuiter
 */
public class BooleanAssignment extends ABooleanAssignment {

    public BooleanAssignment(int... integers) {
        super(integers);
    }

    public BooleanAssignment(Collection<Integer> integers) {
        super(integers);
    }

    public BooleanAssignment(BooleanAssignment booleanAssignment) {
        super(booleanAssignment);
    }

    @Override
    public ValueAssignment toValue() {
        return VariableMap.toValue(this);
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
    public BooleanAssignment inverse() {
        return new BooleanAssignment(negate());
    }

    @Override
    public BooleanAssignment addAll(ABooleanAssignment integers) {
        return new BooleanAssignment(addAll(integers.get()));
    }

    @Override
    public BooleanAssignment retainAll(ABooleanAssignment integers) {
        return new BooleanAssignment(retainAll(integers.get()));
    }

    @Override
    public BooleanAssignment retainAllVariables(ABooleanAssignment integers) {
        return new BooleanAssignment(retainAllVariables(integers.get()));
    }

    @Override
    public BooleanAssignment removeAll(ABooleanAssignment integers) {
        return new BooleanAssignment(removeAll(integers.get()));
    }

    @Override
    public BooleanAssignment removeAllVariables(ABooleanAssignment integers) {
        return new BooleanAssignment(removeAllVariables(integers.get()));
    }
}
