package representer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.AbstractRenameRefactoring;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.filter.FieldReferenceFunction;

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
        LOGGER.debug("Renaming field {}", ctField.getSimpleName());

        new CtRenameFieldRefactoring()
                .setTarget(ctField)
                .setNewName(this.placeholderGenerator.getPlaceholder(ctField.getSimpleName()))
                .refactor();
    }

    private static class CtRenameFieldRefactoring extends AbstractRenameRefactoring<CtField<?>> {
        protected CtRenameFieldRefactoring() {
            super(javaIdentifierRE);
        }

        @Override
        protected void refactorNoCheck() {
            getTarget()
                    .map(new FieldReferenceFunction())
                    .forEach((CtConsumer<CtReference>) ctReference -> ctReference.setSimpleName(newName));

            target.setSimpleName(newName);
        }
    }
}
