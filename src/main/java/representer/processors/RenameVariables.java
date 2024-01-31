package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.CtRenameGenericVariableRefactoring;
import spoon.reflect.code.CtCatchVariable;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtVariable;

/**
 * This {@link spoon.processing.Processor} renames all variables to use placeholder names,
 * so that they become name-agnostic.
 * Renamed variables include local variables, scoped variables like in a catch-clause, and method parameters.
 */
public final class RenameVariables extends AbstractProcessor<CtVariable<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameVariables.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameVariables(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public boolean isToBeProcessed(CtVariable<?> candidate) {
        return candidate instanceof CtLocalVariable<?> ||
                candidate instanceof CtCatchVariable<?> ||
                candidate instanceof CtParameter<?>;
    }

    @Override
    public void process(CtVariable<?> ctVariable) {
        var identifier = ctVariable.getSimpleName();

        if (placeholderGenerator.isPlaceholder(identifier)) {
            return;
        }

        var placeholder = this.placeholderGenerator.getPlaceholder(identifier);
        LOGGER.info("Renaming variable '{}' to '{}'", identifier, placeholder);
        new CtRenameGenericVariableRefactoring().setTarget(ctVariable).setNewName(placeholder).refactor();
    }
}