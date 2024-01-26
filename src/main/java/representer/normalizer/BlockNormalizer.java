package representer.normalizer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public class BlockNormalizer extends ModifierVisitor<Void> {

    @Override
    public Node visit(IfStmt node, Void arg) {
        if (!node.hasThenBlock()) {
            node.setThenStmt(wrapWithBlock(node.getThenStmt()));
        }

        node.getElseStmt().ifPresent(stmt -> {
            if (!stmt.isBlockStmt()) {
                node.setElseStmt(wrapWithBlock(stmt));
            }
        });

        return node;
    }

    private Statement wrapWithBlock(Statement stmt) {
        return new BlockStmt().addAndGetStatement(stmt);
    }
}
