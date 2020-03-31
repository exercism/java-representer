package representer.normalizer;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public class ImportNormalizer extends ModifierVisitor<String> {

    /**
     * Remove nodes of type ImportDeclaration
     */

    @Override
    public Node visit(ImportDeclaration n, String arg) {
        return null;
    }
}
