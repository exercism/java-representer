package representer.normalizer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.comments.JavadocComment;

import com.github.javaparser.ast.visitor.ModifierVisitor;

public class CommentNormalizer extends ModifierVisitor<String> {

    /**
     * Remove nodes of type LineComment
     */
    @Override
    public Node visit(LineComment n, String arg) {
        return null;
    }

    /**
     * Remove nodes of type BlockComment
     */
    @Override
    public Node visit(BlockComment n, String arg) {
        return null;
    }

    /**
     * Remove nodes of type JavadocComment
     */
    @Override
    public Node visit(JavadocComment n, String arg) {
        return null;
    }
}
