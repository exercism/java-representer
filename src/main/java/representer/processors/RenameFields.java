package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import representer.refactoring.RenameFieldRefactoring;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtField;

/**
 * This {@link spoon.processing.Processor} renames all fields and their usages to use placeholder names,
 * so that they become name-agnostic.
 * Note that enum values also count towards fields and are therefore also renamed by this processor.
 */
public final class RenameFields extends AbstractProcessor<CtField<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameFields.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameFields(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtField<?> ctField) {
        var identifier = ctField.getSimpleName();

        if (placeholderGenerator.isPlaceholder(identifier)) {
            return;
        }

        var placeholder = this.placeholderGenerator.getPlaceholder(identifier);
        LOGGER.info("Renaming field '{}' to '{}'", identifier, placeholder);
        new RenameFieldRefactoring().setTarget(ctField).setNewName(placeholder).refactor();
    }
}
