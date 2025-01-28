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
package de.featjar.formula.io.dimacs;

import de.featjar.base.data.Result;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.io.format.ParseProblem;
import de.featjar.base.io.input.AInputMapper;
import de.featjar.formula.VariableMap;
import de.featjar.formula.assignment.ABooleanAssignmentList;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import java.text.ParseException;
import java.util.Objects;

/**
 * Reads / Writes a list of configuration.
 *
 * @author Sebastian Krieter
 */
public class BooleanAssignmentGroupsDimacsFormat implements IFormat<BooleanAssignmentGroups> {

    @Override
    public Result<String> serialize(BooleanAssignmentGroups assignmentSpace) {
        Objects.requireNonNull(assignmentSpace);

        final StringBuilder sb = new StringBuilder();
        ABooleanAssignmentList<? extends BooleanAssignment> cnf = assignmentSpace.getFirstGroup();
        VariableMap variableMap = assignmentSpace.getVariableMap();

        variableMap.stream().forEach(e -> {
            if (e.getValue() != null) {
                sb.append("c ");
                sb.append(e.getKey());
                sb.append(" ");
                sb.append(e.getValue());
                sb.append(System.lineSeparator());
            }
        });
        // Problem
        sb.append(DimacsConstants.PROBLEM);
        sb.append(' ');
        sb.append(DimacsConstants.CNF);
        sb.append(' ');
        sb.append(variableMap.getVariableCount());
        sb.append(' ');
        sb.append(cnf.size());
        sb.append(System.lineSeparator());

        // Clauses
        for (final BooleanAssignment clause : cnf) {
            for (final int l : clause.get()) {
                sb.append(l);
                sb.append(' ');
            }
            sb.append(DimacsConstants.CLAUSE_END);
            sb.append(System.lineSeparator());
        }

        return Result.of(sb.toString());
    }

    @Override
    public Result<BooleanAssignmentGroups> parse(AInputMapper inputMapper) {
        final BooleanAssignmentGroupsDimacsParser r = new BooleanAssignmentGroupsDimacsParser();
        r.setReadingVariableDirectory(true);
        try {
            return Result.of(r.parse(inputMapper.get().getNonEmptyLineIterator()));
        } catch (final ParseException e) {
            return Result.empty(new ParseProblem(e, e.getErrorOffset()));
        } catch (final Exception e) {
            return Result.empty(e);
        }
    }

    @Override
    public String getFileExtension() {
        return "dimacs";
    }

    @Override
    public boolean supportsSerialize() {
        return true;
    }

    @Override
    public boolean supportsParse() {
        return true;
    }

    @Override
    public String getName() {
        return "BooleanAssignmentDimacs";
    }
}
