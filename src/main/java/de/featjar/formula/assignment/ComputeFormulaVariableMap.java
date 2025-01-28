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
package de.featjar.formula.assignment;

import de.featjar.base.computation.AComputation;
import de.featjar.base.computation.Dependency;
import de.featjar.base.computation.IComputation;
import de.featjar.base.computation.Progress;
import de.featjar.base.data.Result;
import de.featjar.formula.VariableMap;
import de.featjar.formula.structure.IFormula;
import de.featjar.formula.structure.connective.Reference;
import java.util.List;

/**
 * Transforms a {@link IFormula} into a formula and a corresponding {@link VariableMap}.
 *
 * @author Sebastian Krieter
 */
public class ComputeFormulaVariableMap extends AComputation<IFormula> {

    protected static final Dependency<IFormula> CNF = Dependency.newDependency(IFormula.class);

    public ComputeFormulaVariableMap(IComputation<IFormula> cnfFormula) {
        super(cnfFormula);
    }

    protected ComputeFormulaVariableMap(ComputeFormulaVariableMap other) {
        super(other);
    }

    @Override
    public Result<IFormula> compute(List<Object> dependencyList, Progress progress) {
        IFormula vp = CNF.get(dependencyList);
        if (vp instanceof Reference) {
            vp = (IFormula) ((Reference) vp).getExpression();
        }
        return Result.of(vp);
    }
}
