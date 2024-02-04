package representer;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.processors.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

class Representer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Representer.class);

    public static Representation generate(String path) {
        var placeholders = new Placeholders();

        var launcher = new Launcher();
        launcher.getEnvironment().setComplianceLevel(19);
        launcher.addInputResource(path);
        launcher.addProcessor(new RenameTypes(placeholders));
        launcher.addProcessor(new RenameRecordComponents(placeholders));
        launcher.addProcessor(new RenameMethods(placeholders));
        launcher.addProcessor(new RenameFields(placeholders));
        launcher.addProcessor(new RenameVariables(placeholders));
        launcher.addProcessor(new RemoveComments());
        launcher.buildModel();
        launcher.process();

        return new Representation(getRepresentationString(launcher.getModel()), placeholders.getPlaceholders());
    }

    private static String getRepresentationString(CtModel model) {
        var normalized = new StringBuilder();
        for (CtType<?> type : model.getAllTypes()) {
            normalized.append(type.toString());
            normalized.append("\n");
        }
        return format(normalized.toString());
    }

    private static String format(String representation) {
        try {
            return new Formatter().formatSource(representation);
        } catch (FormatterException e) {
            LOGGER.warn("Caught exception while attempting to format representation, " +
                    "using unformatted representation instead", e);
            return representation;
        }
    }
}
