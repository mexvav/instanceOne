package test_core.extentions.conditions;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import test_core.Configuration;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

public class AssumeSpringContextCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
        if(Configuration.isSpringContextTestRun()){
            return ConditionEvaluationResult.enabled("Running  spring context tests!");
        }
        return ConditionEvaluationResult.disabled("Ignore spring context tests!");
    }
}