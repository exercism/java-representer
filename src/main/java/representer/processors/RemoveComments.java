package representer.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtComment;

public final class RemoveComments extends AbstractProcessor<CtComment> {
    @Override
    public void process(CtComment ctComment) {
        ctComment.delete();
    }
}
