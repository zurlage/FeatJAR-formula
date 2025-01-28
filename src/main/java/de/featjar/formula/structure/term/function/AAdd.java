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
package de.featjar.formula.structure.term.function;

import de.featjar.formula.structure.ANonTerminalExpression;
import de.featjar.formula.structure.IBinaryExpression;
import de.featjar.formula.structure.term.ITerm;
import java.util.List;

/**
 * Adds the values of two terms.
 *
 * @author Sebastian Krieter
 */
public abstract class AAdd extends ANonTerminalExpression implements IFunction, IBinaryExpression {

    protected AAdd() {}

    protected AAdd(ITerm leftTerm, ITerm rightTerm) {
        super(leftTerm, rightTerm);
    }

    protected AAdd(List<ITerm> arguments) {
        super(arguments);
    }

    @Override
    public String getName() {
        return "+";
    }
}
