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
package de.featjar.formula.transform;

import static de.featjar.formula.structure.Expressions.*;

import de.featjar.formula.computation.ComputeDNFFormula;

class ComputeDNFFormulaTest {
    // @Test
    public void toDNF() {
        // TODO: currently buggy
        TransformationTest.traverseAndAssertFormulaEquals(
                and(or(literal("a"), literal("b")), or(literal("c"))),
                ComputeDNFFormula::new,
                or(and(literal("c"), literal("b")), and(literal("c"), literal("a"))));
        TransformationTest.traverseAndAssertFormulaEquals(
                and(or(literal("a"), literal("b")), literal("c")),
                ComputeDNFFormula::new,
                or(and(literal("c"), literal("b")), and(literal("a"), literal("c"))));
    }
}
