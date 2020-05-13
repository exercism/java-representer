package representer;

import static com.github.javaparser.utils.PositionUtils.sortByBeginPosition;
import static com.github.javaparser.utils.Utils.isNullOrEmpty;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

public class RepresenterPrintVisitor extends PrettyPrintVisitor {

    public RepresenterPrintVisitor(PrettyPrinterConfiguration prettyPrinterConfiguration) {
        super(prettyPrinterConfiguration);
    }

    @Override
    public void visit(BlockStmt node, Void argument) {
        printOrphanCommentsBeforeThisChildNode(node);
        printComment(node.getComment(), argument);
        printer.println();
        printer.println("{");
        if (node.getStatements() != null) {
            printer.indent();
            for (final Statement statament : node.getStatements()) {
                statament.accept(this, argument);
                printer.println();
            }
            printer.unindent();
        }
        printOrphanCommentsEnding(node);
        printer.print("}");
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration node, Void argument) {
        printOrphanCommentsBeforeThisChildNode(node);
        printComment(node.getComment(), argument);
        printMemberAnnotations(node.getAnnotations(), argument);
        printModifiers(node.getModifiers());

        if (node.isInterface()) {
            printer.print("interface ");
        } else {
            printer.print("class ");
        }

        node.getName().accept(this, argument);

        printTypeParameters(node.getTypeParameters(), argument);

        if (!node.getExtendedTypes().isEmpty()) {
            printer.print(" extends ");
            for (final Iterator<ClassOrInterfaceType> classOrInterfaceIterator =
                    node.getExtendedTypes().iterator(); classOrInterfaceIterator
                    .hasNext();) {
                final ClassOrInterfaceType classOrInterface = classOrInterfaceIterator.next();
                classOrInterface.accept(this, argument);
                if (classOrInterfaceIterator.hasNext()) {
                    printer.print(", ");
                }
            }
        }

        if (!node.getImplementedTypes().isEmpty()) {
            printer.print(" implements ");
            for (final Iterator<ClassOrInterfaceType> classOrInterfaceIterator =
                    node.getImplementedTypes().iterator(); classOrInterfaceIterator
                    .hasNext();) {
                final ClassOrInterfaceType classOrInterface = classOrInterfaceIterator.next();
                classOrInterface.accept(this, argument);
                if (classOrInterfaceIterator.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.println();
        printer.println("{");
        printer.indent();
        if (!isNullOrEmpty(node.getMembers())) {
            printMembers(node.getMembers(), argument);
        }

        printOrphanCommentsEnding(node);

        printer.unindent();
        printer.print("}");
    }

    private void printOrphanCommentsEnding(final Node node) {
        if (configuration.isIgnoreComments())
            return;

        List<Node> everything = new ArrayList<>(node.getChildNodes());
        sortByBeginPosition(everything);
        if (everything.isEmpty()) {
            return;
        }

        int commentsAtEnd = 0;
        boolean findingComments = true;
        while (findingComments && commentsAtEnd < everything.size()) {
            Node last = everything.get(everything.size() - 1 - commentsAtEnd);
            findingComments = (last instanceof Comment);
            if (findingComments) {
                commentsAtEnd++;
            }
        }
        for (int i = 0; i < commentsAtEnd; i++) {
            everything.get(everything.size() - commentsAtEnd + i).accept(this, null);
        }
    }

    private void printOrphanCommentsBeforeThisChildNode(final Node node) {
        if (configuration.isIgnoreComments())
            return;
        if (node instanceof Comment)
            return;

        Node parent = node.getParentNode().orElse(null);
        if (parent == null)
            return;
        List<Node> everything = new ArrayList<>(parent.getChildNodes());
        sortByBeginPosition(everything);
        int positionOfTheChild = -1;
        for (int i = 0; i < everything.size(); ++i) { // indexOf is by equality, so this is used to
                                                      // index by identity
            if (everything.get(i) == node) {
                positionOfTheChild = i;
                break;
            }
        }
        if (positionOfTheChild == -1) {
            throw new AssertionError("I am not a child of my parent.");
        }
        int positionOfPreviousChild = -1;
        for (int i = positionOfTheChild - 1; i >= 0 && positionOfPreviousChild == -1; i--) {
            if (!(everything.get(i) instanceof Comment))
                positionOfPreviousChild = i;
        }
        for (int i = positionOfPreviousChild + 1; i < positionOfTheChild; i++) {
            Node nodeToPrint = everything.get(i);
            if (!(nodeToPrint instanceof Comment))
                throw new RuntimeException("Expected comment, instead " + nodeToPrint.getClass()
                        + ". Position of previous child: " + positionOfPreviousChild
                        + ", position of child " + positionOfTheChild);
            nodeToPrint.accept(this, null);
        }
    }

    private void printComment(final Optional<Comment> comment, final Void argument) {
        comment.ifPresent(c -> c.accept(this, argument));
    }

    private void printMemberAnnotations(final NodeList<AnnotationExpr> annotations,
            final Void argument) {
        if (annotations.isEmpty()) {
            return;
        }
        for (final AnnotationExpr annotation : annotations) {
            annotation.accept(this, argument);
            printer.println();
        }
    }

    private void printMembers(final NodeList<BodyDeclaration<?>> members, final Void argument) {
        for (final BodyDeclaration<?> member : members) {
            printer.println();
            member.accept(this, argument);
            printer.println();
        }
    }

    private void printTypeParameters(final NodeList<TypeParameter> args, final Void argument) {
        if (!isNullOrEmpty(args)) {
            printer.print("<");
            for (final Iterator<TypeParameter> typeParameterInterface =
                    args.iterator(); typeParameterInterface.hasNext();) {
                final TypeParameter typeParameter = typeParameterInterface.next();
                typeParameter.accept(this, argument);
                if (typeParameterInterface.hasNext()) {
                    printer.print(", ");
                }
            }
            printer.print(">");
        }
    }

    private void printModifiers(final NodeList<Modifier> modifiers) {
        if (modifiers.size() > 0) {
            printer.print(modifiers.stream().map(Modifier::getKeyword)
                    .map(Modifier.Keyword::asString).collect(joining(" ")) + " ");
        }
    }

}
