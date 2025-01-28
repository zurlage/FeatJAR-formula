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

public class XMLFeatureModelCNFFormatTest {
    //
    //    @Test
    //    public void FeatureIDE_CNF_ABC_nAnBnC() {
    //        test("ABC-nAnBnC");
    //    }
    //
    //    @Test
    //    public void FeatureIDE_CNF_A() {
    //        test("A");
    //    }
    //
    //    @Test
    //    public void FeatureIDE_CNF_SingleGroups() {
    //        test("SingleGroups");
    //    }
    //
    //    @Test
    //    public void FeatureIDE_CNF_faulty() {
    //        test("faulty");
    //    }
    //
    //    private static void test(String name) {
    //        FormatTest.testLoad(getFormula(name), name, new XMLFeatureModelCNFFormat());
    //    }
    //
    //    private static Expression getFormula(String name) {
    //        switch (name) {
    //            case "faulty": {
    //                return null;
    //            }
    //            case "ABC-nAnBnC": {
    //                final TermMap map = new TermMap();
    //                final Literal root = map.createLiteral("Root");
    //                final Literal a = map.createLiteral("A");
    //                final Literal b = map.createLiteral("B");
    //                final Literal c = map.createLiteral("C");
    //                return new And(
    //                        root.cloneNode(),
    //                        new Or(a.invert(), root.cloneNode()),
    //                        new Or(b.invert(), root.cloneNode()),
    //                        new Or(c.invert(), root.cloneNode()),
    //                        new Or(root.invert(), a.cloneNode(), b.cloneNode(), c.cloneNode()),
    //                        new Or(a.invert(), b.invert(), c.invert()));
    //            }
    //            case "SingleGroups": {
    //                final TermMap map = new TermMap();
    //                final Literal root = map.createLiteral("Root");
    //                final Literal a = map.createLiteral("A");
    //                final Literal a1 = map.createLiteral("A1");
    //                final Literal b = map.createLiteral("B");
    //                final Literal b1 = map.createLiteral("B1");
    //                return new And(
    //                        root.cloneNode(),
    //                        new Or(a.invert(), root.cloneNode()),
    //                        new Or(root.invert(), a.cloneNode()),
    //                        new Or(a1.invert(), a.cloneNode()),
    //                        new Or(a.invert(), a1.cloneNode()),
    //                        new Or(b.invert(), root.cloneNode()),
    //                        new Or(root.invert(), b.cloneNode()),
    //                        new Or(b1.invert(), b.cloneNode()),
    //                        new Or(b.invert(), b1.cloneNode()));
    //            }
    //            case "A": {
    //                final TermMap map = new TermMap();
    //                final Literal a = map.createLiteral("A");
    //                return new And(a.cloneNode());
    //            }
    //            default:
    //                fail(name);
    //                return null;
    //        }
    //    }
}
