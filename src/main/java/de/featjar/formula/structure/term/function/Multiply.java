/*
 * Copyright (C) 2022 Sebastian Krieter, Elias Kuiter
 *
 * This file is part of formula.
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
package de.featjar.formula.structure.term.function;

import de.featjar.formula.structure.BinaryExpression;
import de.featjar.formula.structure.NonTerminalExpression;
import de.featjar.formula.structure.term.Term;

import java.util.List;

/**
 * Multiplies the values of two terms.
 *
 * @author Sebastian Krieter
 * @deprecated currently not supported by any meaningful operations
 */
@Deprecated
public abstract class Multiply extends NonTerminalExpression implements Function, BinaryExpression {
    protected Multiply() {
    }

    protected Multiply(Term leftTerm, Term rightTerm) {
        super(leftTerm, rightTerm);
    }

    protected Multiply(List<Term> arguments) {
        super(arguments);
    }

    @Override
    public String getName() {
        return "*";
    }
}
