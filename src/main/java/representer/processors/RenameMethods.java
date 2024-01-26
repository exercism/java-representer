package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtMethod;

public final class RenameMethods extends AbstractProcessor<CtMethod<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameMethods.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameMethods(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtMethod<?> ctMethod) {
        LOGGER.debug("Renaming method {}", ctMethod.getSimpleName());
        Refactoring.changeMethodName(ctMethod, this.placeholderGenerator.getPlaceholder(ctMethod.getSimpleName()));
    }
}
