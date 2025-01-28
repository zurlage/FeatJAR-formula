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
package de.featjar.formula.io;

import de.featjar.base.FeatJAR;
import de.featjar.base.io.format.AFormats;
import de.featjar.formula.assignment.BooleanAssignmentGroups;

/**
 * Extension point for {@link AFormats formats} for {@link BooleanAssignmentGroups}.
 *
 * @author Sebastian Krieter
 */
public class BooleanAssignmentGroupsFormats extends AFormats<BooleanAssignmentGroups> {
    public static BooleanAssignmentGroupsFormats getInstance() {
        return FeatJAR.extensionPoint(BooleanAssignmentGroupsFormats.class);
    }
}
