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

import de.featjar.base.data.Result;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.tree.Trees;
import de.featjar.formula.io.textual.ExpressionSerializer.Notation;
import de.featjar.formula.structure.IExpression;

/**
 * Parses and serializes propositional and first-order expressions.
 *
 * @author Sebastian Krieter
 */
@Deprecated
public class ExpressionFormat implements IFormat<IExpression> {
    @Override
    public String getName() {
        return "Expression";
    }

    //    @Override
    //    public boolean supportsParse() {
    //        return true;
    //    }

    @Override
    public boolean supportsSerialize() {
        return true;
    }

    //    @Override
    //    public Result<IExpression> parse(AInputMapper inputMapper) {
    //        return new ExpressionParser().parse(inputMapper.get().read().get());
    //    }

    @Override
    public Result<String> serialize(IExpression expression) {
        ExpressionSerializer serializer = new ExpressionSerializer();
        serializer.setNotation(Notation.POSTFIX);
        return Trees.traverse(expression, serializer);
    }
}
