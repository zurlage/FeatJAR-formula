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
package de.featjar.formula.structure.predicate;

import de.featjar.formula.structure.ANonTerminalExpression;
import de.featjar.formula.structure.term.ITerm;
import java.util.List;

/**
 * Expresses "A != B" constraints.
 * Evaluates to {@code true} iff both children evaluate to different values.
 *
 * @author Sebastian Krieter
 */
public class NotEquals extends ANonTerminalExpression implements IBinaryPredicate, IInvertiblePredicate {
    protected NotEquals() {}

    public NotEquals(ITerm leftTerm, ITerm rightTerm) {
        super(leftTerm, rightTerm);
    }

    public NotEquals(List<? extends ITerm> terms) {
        super(terms);
    }

    @Override
    public String getName() {
        return "!=";
    }

    @Override
    public NotEquals cloneNode() {
        return new NotEquals();
    }

    @Override
    public Equals invert() {
        return new Equals((ITerm) getLeftExpression(), (ITerm) getRightExpression());
    }

    @Override
    public boolean compareDifference(int difference) {
        return difference != 0;
    }
}
