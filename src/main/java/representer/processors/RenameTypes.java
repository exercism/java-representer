package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtType;

/**
 * This {@link spoon.processing.Processor} renames all types and their usages to use placeholder names,
 * so that any helper classes or enums in the solution become name-agnostic.
 */
public final class RenameTypes extends AbstractProcessor<CtType<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameTypes.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameTypes(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtType<?> ctType) {
        var identifier = ctType.getSimpleName();

        if (placeholderGenerator.isPlaceholder(identifier)) {
            return;
        }

        var placeholder = this.placeholderGenerator.getPlaceholder(identifier);
        LOGGER.info("Renaming type '{}' to '{}'", identifier, placeholder);
        Refactoring.changeTypeName(ctType, placeholder);
    }
}
