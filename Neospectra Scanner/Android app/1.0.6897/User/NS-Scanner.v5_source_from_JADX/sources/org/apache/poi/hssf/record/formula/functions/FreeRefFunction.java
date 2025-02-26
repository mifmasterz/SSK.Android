package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.p009ss.formula.OperationEvaluationContext;

public interface FreeRefFunction {
    ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext);
}
