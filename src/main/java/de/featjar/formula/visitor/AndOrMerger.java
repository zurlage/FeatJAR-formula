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
package de.featjar.formula.visitor;

import de.featjar.base.data.Result;
import de.featjar.base.data.Void;
import de.featjar.base.tree.visitor.ITreeVisitor;
import de.featjar.formula.structure.IExpression;
import de.featjar.formula.structure.IFormula;
import de.featjar.formula.structure.connective.And;
import de.featjar.formula.structure.connective.IConnective;
import de.featjar.formula.structure.connective.Or;
import de.featjar.formula.structure.predicate.IPredicate;
import java.util.List;

/**
 * Merges nested {@link And} and {@link Or} connectives.
 *
 * @author Sebastian Krieter
 */
public class AndOrMerger implements ITreeVisitor<IFormula, Void> {
    @Override
    public TraversalAction firstVisit(List<IFormula> path) {
        final IFormula formula = ITreeVisitor.getCurrentNode(path);
        if (formula instanceof IPredicate) {
            return TraversalAction.SKIP_CHILDREN;
        } else if (formula instanceof IConnective) {
            return TraversalAction.CONTINUE;
        } else {
            return TraversalAction.FAIL;
        }
    }

    @Override
    public TraversalAction lastVisit(List<IFormula> path) {
        final IFormula formula = ITreeVisitor.getCurrentNode(path);
        if (formula instanceof And) {
            formula.flatReplaceChildren(this::mergeAnd);
        } else if (formula instanceof Or) {
            formula.flatReplaceChildren(this::mergeOr);
        }
        formula.replaceChildren(
                child -> child.getChildrenCount() == 1 && ((child instanceof And) || (child instanceof Or))
                        ? child.getFirstChild().get()
                        : null);
        return TraversalAction.CONTINUE;
    }

    private List<? extends IExpression> mergeAnd(final IExpression child) {
        return (child instanceof And) || (child instanceof Or && (child.getChildrenCount() == 1))
                ? child.getChildren()
                : null;
    }

    private List<? extends IExpression> mergeOr(final IExpression child) {
        return (child instanceof Or) || (child instanceof And && (child.getChildrenCount() == 1))
                ? child.getChildren()
                : null;
    }

    @Override
    public Result<Void> getResult() {
        return Result.ofVoid();
    }
}
