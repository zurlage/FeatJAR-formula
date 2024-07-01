package de.featjar.formula.visitor;

import de.featjar.formula.assignment.Assignment;
import de.featjar.formula.structure.IExpression;
import de.featjar.formula.structure.IFormula;
import de.featjar.formula.structure.predicate.False;
import de.featjar.formula.structure.predicate.IPolarPredicate;
import de.featjar.formula.structure.predicate.Literal;
import de.featjar.formula.structure.predicate.True;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.featjar.formula.structure.Expressions.*;
import static de.featjar.formula.structure.Expressions.literal;

public class LiteralReplacerTest {
    @Test
    void testNoReplacement() {
        IFormula oldFormula = or(and(literal("a"), literal("b")), and(literal("c"), literal("d")));
        VisitorTest.traverseAndAssertSameFormula(oldFormula, new LiteralReplacer(new HashMap<>()));
    }

    @Test
    void testReplacement() {
        IFormula oldFormula = or(and(literal("a"), literal("b")), and(literal(false, "c"), literal("d")));
        IFormula newFormula = or(and(literal("a"), literal(false, "b")), and(literal(true, "x"), literal("d")));
        Map<IPolarPredicate, IExpression> map = new HashMap<>();
        map.put(literal(true, "b"), literal(false, "b"));
        map.put(literal(false, "c"), literal(true, "x"));
        map.put(literal(false, "d"), literal(true, "x"));
        VisitorTest.traverseAndAssertFormulaEquals(oldFormula, new LiteralReplacer(map), newFormula);
    }

    @Test
    void testDeepReplacement() {
        IFormula oldFormula = or(
                and(
                        implies(literal("a"), literal(false, "b")),
                        biImplies(literal(false, "c"), literal("d"))),
                and(
                        biImplies(literal(false, "d"), literal("e")),
                        implies(literal("f"), literal(false, "g"))));
        IFormula newFormula = or(
                and(
                        implies(literal("a"), literal(true, "b")),
                        biImplies(literal(true, "x"), literal("d"))),
                and(
                        biImplies(literal(false, "d"), literal(false, "y")),
                        implies(literal("f"), literal(false, "g"))));
        Map<IPolarPredicate, IExpression> map = new HashMap<>();
        map.put(literal(false, "b"), literal(true, "b"));
        map.put(literal(false, "c"), literal(true, "x"));
        map.put(literal(true, "e"), literal(false, "y"));
        map.put(literal(false, "f"), literal(true, "z"));
        VisitorTest.traverseAndAssertFormulaEquals(oldFormula, new LiteralReplacer(map), newFormula);
    }

    @Test
    void testAssignmentConstructor() {
        IFormula oldFormula = or(and(literal("a"), literal("b")), and(literal("c"), literal("d")));
        IFormula newFormula = or(and(literal("a"), False), and(True, literal("d")));
        Assignment assignment = new Assignment("b", False, "c", True);
        VisitorTest.traverseAndAssertFormulaEquals(oldFormula, new LiteralReplacer(assignment), newFormula);
    }

    @Test
    void testBooleanReplacement() {
        IFormula oldFormula = or(and(literal("a"), literal("b")), and(literal("c"), literal("d")));
        IFormula newFormula = or(and(literal("a"), False), and(True, literal("d")));
        Map<IPolarPredicate, IExpression> map = new HashMap<>();
        map.put(literal("b"), False);
        map.put(literal("c"), True);
        VisitorTest.traverseAndAssertFormulaEquals(oldFormula, new LiteralReplacer(map), newFormula);
    }

    @Test
    void testDeepBooleanReplacement() {
        IFormula oldFormula = or(
                and(
                        implies(literal("a"), literal("b")),
                        biImplies(literal("c"), literal("d"))),
                and(
                        biImplies(literal("d"), literal("e")),
                        implies(literal("f"), literal("g"))));
        IFormula newFormula = or(
                and(
                        implies(literal("a"), False),
                        biImplies(True, literal("d"))),
                and(
                        biImplies(literal("d"), literal("e")),
                        implies(literal("f"), True)));
        Map<IPolarPredicate, IExpression> map = new HashMap<>();
        map.put(literal("b"), False);
        map.put(literal("c"), True);
        map.put(literal("g"), True);
        VisitorTest.traverseAndAssertFormulaEquals(oldFormula, new LiteralReplacer(map), newFormula);
    }
}
