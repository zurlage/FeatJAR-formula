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

import de.featjar.base.data.Result;
import de.featjar.formula.VariableMap;
import java.util.Collection;

/**
 * Primary implementation of {@link AValueAssignmentList}.
 * To be used when neither CNF nor DNF semantics are associated with an assignment list.
 *
 * @author Elias Kuiter
 */
public class ValueAssignmentList extends AValueAssignmentList<ValueAssignment> {

    public ValueAssignmentList(VariableMap variableMap) {
        super(variableMap);
    }

    public ValueAssignmentList(VariableMap variableMap, int size) {
        super(variableMap, size);
    }

    public ValueAssignmentList(VariableMap variableMap, Collection<? extends ValueAssignment> assignments) {
        super(variableMap, assignments);
    }

    public ValueAssignmentList(ValueAssignmentList other) {
        super(other);
    }

    @Override
    public Result<BooleanAssignmentList> toBoolean() {
        return VariableMap.toBoolean(this);
    }

    @Override
    public String toString() {
        return String.format("ValueAssignmentList[%s]", print());
    }
}
