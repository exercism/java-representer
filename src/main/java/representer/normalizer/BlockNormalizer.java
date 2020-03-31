package representer.normalizer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public class BlockNormalizer extends ModifierVisitor<String> {


    @Override
    public Node visit(IfStmt n, String arg) {
        if (!n.hasThenBlock()) {
                        n.setThenStmt(wrapWithBlock(n.getThenStmt()));
        }
        
        n.getElseStmt().ifPresent(stmt -> {
            if(!stmt.isBlockStmt()) {
                n.setElseStmt(wrapWithBlock(stmt));
            }
        });

        return n;
    }

    private Statement wrapWithBlock(Statement stmt) {
        BlockStmt wrapStmt = new BlockStmt();
        wrapStmt.addAndGetStatement(stmt);
        return wrapStmt;

    }

}
