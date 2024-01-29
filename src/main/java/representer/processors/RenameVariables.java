package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.CtRenameGenericVariableRefactoring;
import spoon.refactoring.RefactoringException;
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
        LOGGER.debug("Renaming variable {}", ctVariable.getSimpleName());
        var refactor = new CtRenameGenericVariableRefactoring();
        refactor.setTarget(ctVariable);
        refactor.setNewName(this.placeholderGenerator.getPlaceholder(ctVariable.getSimpleName()));
        try {
            refactor.refactor();
        } catch (RefactoringException ex) {
            LOGGER.warn("Failed to rename variable {}", ctVariable.getSimpleName(), ex);
        }
    }
}