package representer.normalizer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public class PackageNormalizer extends ModifierVisitor<String> {

    /**
     * Remove nodes of type PackageDeclaration
     */
    @Override
    public Node visit(PackageDeclaration n, String arg) {
        return null;
    }

}
