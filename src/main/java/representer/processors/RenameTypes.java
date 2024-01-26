package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtType;

public final class RenameTypes extends AbstractProcessor<CtType<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenameTypes.class);
    private final PlaceholderGenerator placeholderGenerator;

    public RenameTypes(PlaceholderGenerator placeholderGenerator) {
        this.placeholderGenerator = placeholderGenerator;
    }

    @Override
    public void process(CtType<?> ctType) {
        LOGGER.debug("Renaming type {}", ctType.getSimpleName());
        Refactoring.changeTypeName(ctType, this.placeholderGenerator.getPlaceholder(ctType.getSimpleName()));
    }
}
