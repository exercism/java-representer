package representer;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class JavaSymbolTest {

    public static void main(String[] args) throws FileNotFoundException {
        CombinedTypeSolver comb = new CombinedTypeSolver(new ReflectionTypeSolver(),
                new JavaParserTypeSolver("src/test/resources"));
        JavaSymbolSolver solver = new JavaSymbolSolver(comb);
        ParserConfiguration parserConfiguration =
                new ParserConfiguration().setSymbolResolver(solver);
        JavaParser parser = new JavaParser(parserConfiguration);
        CompilationUnit u =
                parser.parse(new File("src/test/resources/TwoferTest.java")).getResult().get();
        u.accept(new MyVisitor(), null);
    }

    public static class MyVisitor extends VoidVisitorAdapter<String> {

        // @Override
        // public void visit(MethodCallExpr n, String arg) {
        // System.out.println("== MethodCallExpr ==");
        // Optional<Expression> scope = n.getScope();
        // scope.ifPresent(s -> {
        // System.out.println(qualifiedName(s.asNameExpr()));
        // });
        // super.visit(n, arg);
        // }

        // @Override
        // public void visit(FieldAccessExpr n, String arg) {
        // System.out.println("== FieldAccessExpr ==");
        // System.out.println(qualifiedName(n.getScope().asNameExpr()));
        // // System.out.println(n.getScope().reso);
        // super.visit(n, arg);
        // }

        @Override
        public void visit(NameExpr n, String arg) {
            System.out.println("== NameExpr ==");
            System.out.println(qualifiedName(n));
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceType n, String arg) {
            System.out.println("== ClassOrInterfaceType ==");
            System.out.println(n.resolve().getQualifiedName());
            n.getScope().ifPresent(scope -> {
                ResolvedReferenceType resolvedType = scope.resolve();
                System.out.println(resolvedType.describe());
            });

            super.visit(n, arg);
            // ResolvedType resolvedType = n.getScope().calculateResolvedType();
            // System.out.println(resolvedType.describe());
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, String arg) {
            System.out.println("== ClassOrInterfaceDeclaration ==");
            System.out.println(n.resolve().getQualifiedName());
            super.visit(n, arg);
        }

        private String qualifiedName(NameExpr nameExpr) {
            return nameExpr.calculateResolvedType().describe();
        }
    }

}
