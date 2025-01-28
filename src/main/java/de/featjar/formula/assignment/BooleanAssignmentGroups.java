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

import de.featjar.base.io.format.IFormat;
import de.featjar.formula.VariableMap;
import java.util.List;
import java.util.Objects;

/**
 * Stores multiple groups of {@link ABooleanAssignmentList}.
 * The main purposes of this class is to provide an easy to write/read object for a corresponding {@link IFormat format}.
 *
 * @author Sebastian Krieter
 */
public class BooleanAssignmentGroups {

    protected final VariableMap variableMap;
    protected final List<? extends ABooleanAssignmentList<? extends BooleanAssignment>> assignmentGroups;

    public BooleanAssignmentGroups(
            VariableMap variableMap, List<? extends ABooleanAssignmentList<?>> assignmentGroups) {
        this.variableMap = variableMap;
        this.assignmentGroups = assignmentGroups;
    }

    public BooleanAssignmentGroups(
            VariableMap variableMap, ABooleanAssignmentList<? extends BooleanAssignment> assignmentGroup) {
        this.variableMap = variableMap;
        this.assignmentGroups = List.of(assignmentGroup);
    }

    public BooleanAssignmentGroups(ABooleanAssignmentList<? extends BooleanAssignment> assignmentGroup) {
        this.variableMap = assignmentGroup.getVariableMap();
        this.assignmentGroups = List.of(assignmentGroup);
    }

    public BooleanAssignmentGroups(VariableMap variableMap, BooleanAssignment... assignments) {
        this.variableMap = variableMap;
        BooleanAssignmentList firstGroup = new BooleanAssignmentList(variableMap);
        for (BooleanAssignment assignment : assignments) {
            firstGroup.add(assignment);
        }
        this.assignmentGroups = List.of(firstGroup);
    }

    public VariableMap getVariableMap() {
        return variableMap;
    }

    public List<? extends ABooleanAssignmentList<? extends BooleanAssignment>> getGroups() {
        return assignmentGroups;
    }

    public ABooleanAssignmentList<? extends BooleanAssignment> getFirstGroup() {
        return assignmentGroups.get(0);
    }

    public BooleanClauseList toClauseList() {
        return toClauseList(0);
    }

    public BooleanSolutionList toSolutionList() {
        return toSolutionList(0);
    }

    public BooleanClauseList toClauseList(int groupIndex) {
        return assignmentGroups.get(groupIndex).toClauseList();
    }

    public BooleanSolutionList toSolutionList(int groupIndex) {
        return assignmentGroups.get(groupIndex).toSolutionList();
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentGroups, variableMap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BooleanAssignmentGroups other = (BooleanAssignmentGroups) obj;
        return Objects.equals(assignmentGroups, other.assignmentGroups)
                && Objects.equals(variableMap, other.variableMap);
    }

    @Override
    public String toString() {
        return "AssignmentGroup [map=" + variableMap + ", groups=" + assignmentGroups + "]";
    }
}
