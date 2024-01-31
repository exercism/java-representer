package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import representer.refactoring.RenameFieldRefactoring;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.CtRenameGenericVariableRefactoring;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtRecord;
import spoon.reflect.declaration.CtRecordComponent;

import java.util.stream.Collectors;

/**
 * This {@link spoon.processing.Processor} renames all record components and their usages to use placeholder names,
 * so that they become name-agnostic.
 * Note: because record components generate a getter method, class field and constructor parameter in the background,
 * these are all renamed as well to remain consistent.
 */
public final class RenameRecordComponents extends AbstractProcessor<CtRecordComponent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameRecordComponents.class);

    private final PlaceholderGenerator placeholderGenerator;

    public RenameRecordComponents(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtRecordComponent ctRecordComponent) {
        var identifier = ctRecordComponent.getSimpleName();

        if (placeholderGenerator.isPlaceholder(identifier)) {
            return;
        }

        var placeholder = placeholderGenerator.getPlaceholder(identifier);
        LOGGER.info("Renaming record component '{}' to '{}'", identifier, placeholder);
        ctRecordComponent.setSimpleName(placeholder);

        var parent = ctRecordComponent.getParent(CtRecord.class);

        var getter = parent.getMethod(ctRecordComponent.getType(), identifier);
        LOGGER.info("Renaming record component getter '{}' to '{}'", getter.getSimpleName(), placeholder);
        Refactoring.changeMethodName(getter, placeholder);

        var field = parent.getField(identifier);
        LOGGER.info("Renaming record component field '{}' to '{}'", field.getSimpleName(), placeholder);
        new RenameFieldRefactoring().setTarget(field).setNewName(placeholder).refactor();

        var constructorParameters = parent.getConstructors()
                .stream()
                .flatMap(ctConstructor -> ctConstructor.getParameters().stream())
                .filter(ctParameter -> ctParameter.getSimpleName().equals(identifier))
                .collect(Collectors.toSet());

        for (CtParameter<?> ctParameter : constructorParameters) {
            LOGGER.info("Renaming record constructor parameter '{}' to '{}'", ctParameter.getSimpleName(), placeholder);
            new CtRenameGenericVariableRefactoring().setTarget(ctParameter).setNewName(placeholder).refactor();
        }
    }
}
