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
package de.featjar.formula.transformer;

import de.featjar.base.data.Computation;
import de.featjar.base.data.FutureResult;
import de.featjar.base.data.Result;
import de.featjar.base.tree.Trees;
import de.featjar.formula.structure.formula.Formula;
import de.featjar.formula.visitor.*;

/**
 * Transforms a formula into clausal negation normal form.
 *
 * @author Elias Kuiter
 */
public class ToNNF implements Computation<Formula> {
    protected final Computation<Formula> formulaComputation;

    public ToNNF(Computation<Formula> formulaComputation) {
        this.formulaComputation = formulaComputation;
    }

    @Override
    public FutureResult<Formula> compute() {
        return formulaComputation.get().thenCompute((formula, monitor) -> {
            // todo: if already in NNF, do nothing
            formula = (Formula) formula.cloneTree();
            Trees.traverse(formula, new ConnectiveSimplifier());
            Trees.traverse(formula, new DeMorganApplier());
            Trees.traverse(formula, new AndOrSimplifier());
            return formula;
        });
    }
}
