package representer.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtComment;

/**
 * This {@link spoon.processing.Processor} removes all comments from the solution,
 * so that solutions that only differ in comments become equivalent.
 * Removed comments include line comments, block comments, and Javadoc comments.
 */
public final class RemoveComments extends AbstractProcessor<CtComment> {
    @Override
    public void process(CtComment ctComment) {
        ctComment.delete();
    }
}
