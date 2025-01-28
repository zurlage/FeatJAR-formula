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
package de.featjar.formula.io.textual;

import de.featjar.base.data.Result;
import de.featjar.base.io.format.IFormat;
import de.featjar.formula.assignment.AValueAssignmentList;
import de.featjar.formula.assignment.ValueAssignment;
import java.util.stream.Collectors;

/**
 * Textual format for serializing and parsing a list of {@link ValueAssignment value assignments}.
 *
 * @author Elias Kuiter
 * @author Sebastian Krieter
 */
public class ValueAssignmentListFormat implements IFormat<AValueAssignmentList<?>> {

    @Override
    public Result<String> serialize(AValueAssignmentList<?> valueAssignmentList) {
        return Result.of(valueAssignmentList.getAll().stream()
                .map(ValueAssignment::print)
                .collect(Collectors.joining(";")));
    }

    @Override
    public boolean supportsSerialize() {
        return true;
    }

    @Override
    public String getName() {
        return "ValueAssignmentList";
    }
}
