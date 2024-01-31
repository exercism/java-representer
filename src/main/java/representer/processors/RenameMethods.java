package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtMethod;

/**
 * This {@link spoon.processing.Processor} renames all methods and their usages to use placeholder names,
 * so that any helper methods added by students become name-agnostic.
 */
public final class RenameMethods extends AbstractProcessor<CtMethod<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameMethods.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameMethods(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtMethod<?> ctMethod) {
        var identifier = ctMethod.getSimpleName();

        if (placeholderGenerator.isPlaceholder(identifier)) {
            return;
        }

        var placeholder = this.placeholderGenerator.getPlaceholder(identifier);
        LOGGER.info("Renaming method '{}' to '{}'", identifier, placeholder);
        Refactoring.changeMethodName(ctMethod, placeholder);
    }
}
