package org.apache.commons.math3.fitting.leastsquares;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.util.Incrementor;

public class LeastSquaresAdapter implements LeastSquaresProblem {
    private final LeastSquaresProblem problem;

    public LeastSquaresAdapter(LeastSquaresProblem problem2) {
        this.problem = problem2;
    }

    public RealVector getStart() {
        return this.problem.getStart();
    }

    public int getObservationSize() {
        return this.problem.getObservationSize();
    }

    public int getParameterSize() {
        return this.problem.getParameterSize();
    }

    public Evaluation evaluate(RealVector point) {
        return this.problem.evaluate(point);
    }

    public Incrementor getEvaluationCounter() {
        return this.problem.getEvaluationCounter();
    }

    public Incrementor getIterationCounter() {
        return this.problem.getIterationCounter();
    }

    public ConvergenceChecker<Evaluation> getConvergenceChecker() {
        return this.problem.getConvergenceChecker();
    }
}
