package representer.normalizer;

import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PlaceholderNormalizer extends VoidVisitorAdapter<String> {
    private Mapper mapper = new Mapper();

    @Override
    public void visit(ClassOrInterfaceDeclaration n, String arg) {
        final String name = n.getNameAsString();
        n.setName(mapper.getPlaceholder(name));
        super.visit(n, arg);
    }


    @Override
    public void visit(MethodDeclaration n, String arg) {
        final String name = n.getNameAsString();
        n.setName(mapper.getPlaceholder(name));
        super.visit(n, arg);
    }


    @Override
    public void visit(Parameter n, String arg) {
        final String name = n.getNameAsString();
        n.setName(mapper.getPlaceholder(name));
        super.visit(n, arg);
    }

    @Override
    public void visit(NameExpr n, String arg) {
        final String name = n.getNameAsString();
        n.setName(mapper.getPlaceholder(name));
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarator n, String arg) {
        final String name = n.getNameAsString();
        n.setName(mapper.getPlaceholder(name));
        super.visit(n, arg);
    }

    public Map<String, String> mapping() {
        return mapper.getMapping();
    }
}
