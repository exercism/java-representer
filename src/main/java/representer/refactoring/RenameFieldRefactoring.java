package representer.refactoring;

import spoon.refactoring.AbstractRenameRefactoring;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.filter.FieldReferenceFunction;

/**
 * This {@link spoon.refactoring.CtRenameRefactoring} renames a field and all its usages.
 * Works on both class fields and enum values.
 */
public final class RenameFieldRefactoring extends AbstractRenameRefactoring<CtField<?>> {
    public RenameFieldRefactoring() {
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
