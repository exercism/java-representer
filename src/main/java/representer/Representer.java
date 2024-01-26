package representer;

import representer.processors.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

class Representer {

    public static Representation generate(String path) {
        var placeholders = new Placeholders();

        var launcher = new Launcher();
        launcher.addInputResource(path);
        launcher.addProcessor(new RenameTypes(placeholders));
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
        return normalized.toString();
    }
}
