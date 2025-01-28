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

import de.featjar.Common;
import de.featjar.FormatTest;
import de.featjar.base.computation.Computations;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.ComputeBooleanRepresentation;
import de.featjar.formula.computation.ComputeCNFFormula;
import de.featjar.formula.computation.ComputeNNFFormula;
import de.featjar.formula.io.binary.BooleanAssignmentGroupsBinaryFormat;
import de.featjar.formula.io.textual.ExpressionFormat;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ExpressionFormat Formula} format.
 *
 * @author Sebastian Krieter
 */
public class BinaryFormatTest extends Common {

    @Test
    public void Formula_ABC_nAnBnC() {
        test("ABC-nAnBnC");
    }

    @Test
    public void Formula_nA() {
        test("nA");
    }

    @Test
    public void Formula_nAB() {
        test("nAB");
    }

    private static void test(String name) {
        final BooleanAssignmentGroups assignmentSpace = Computations.of(getFormula(name))
                .map(ComputeNNFFormula::new)
                .map(ComputeCNFFormula::new)
                .map(ComputeBooleanRepresentation::new)
                .compute();

        FormatTest.testParseAndSerialize("binary/" + name, new BooleanAssignmentGroupsBinaryFormat());
        FormatTest.testSerializeAndParse(assignmentSpace, new BooleanAssignmentGroupsBinaryFormat());
    }
}
