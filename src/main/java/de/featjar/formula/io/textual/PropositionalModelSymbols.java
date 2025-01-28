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

import de.featjar.formula.structure.connective.And;
import de.featjar.formula.structure.connective.BiImplies;
import de.featjar.formula.structure.connective.Implies;
import de.featjar.formula.structure.connective.Not;
import de.featjar.formula.structure.connective.Or;

/**
 * Symbols for a representation like in Java. These are inherently incomplete
 * and should only be used if absolutely necessary.
 *
 * @author Sebastian Krieter
 */
public class PropositionalModelSymbols extends Symbols {

    public static final Symbols INSTANCE = new PropositionalModelSymbols();

    private PropositionalModelSymbols() {
        super(false);
        setSymbol(Not.class, "!");
        setSymbol(And.class, "&");
        setSymbol(Or.class, "|");
        setSymbol(Implies.class, "=>");
        setSymbol(BiImplies.class, "==");
    }
}
