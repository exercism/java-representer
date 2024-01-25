package representer.normalizer;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import representer.PlaceholderGenerator;

import java.util.Optional;

public class PlaceholderNormalizer extends VoidVisitorAdapter<PlaceholderGenerator> {
    private static final Logger logger = LoggerFactory.getLogger(PlaceholderNormalizer.class);

    @Override
    public void visit(ClassOrInterfaceDeclaration n, PlaceholderGenerator generator) {
        logger.debug("ClassOrInterfaceDeclaration: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(MethodDeclaration n, PlaceholderGenerator generator) {
        logger.debug("MethodDeclaration: {}", n.getName().asString());
        logger.debug("method return type: {}", n.getTypeAsString());
        mapType(n.getType(), generator);
        n.getParameters().forEach(p -> logger.debug("method parameter: {}", p.getType().asString()));
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    private void mapType(Type t, PlaceholderGenerator generator) {
        if (t.isClassOrInterfaceType()) {
            logger.debug("class or interface type");
            ClassOrInterfaceType classOrInterfaceType = t.asClassOrInterfaceType();
            Optional<NodeList<Type>> typeArguments = classOrInterfaceType.getTypeArguments();
            typeArguments.ifPresent(types -> {
                String genericsClass = types.get(0).asString();
                if (genericsClass.length() > 1) { // workaround to avoid substitution of T
                    types.set(0, new ClassOrInterfaceType(null, generator.getPlaceholder(genericsClass)));
                }
            });
        }
        if (t.isArrayType()) {
            logger.debug("array type");
            mapType(t.getElementType(), generator);
        }
        if (t.isPrimitiveType()) {
            logger.debug("primitive type");
        }

        if (t.isTypeParameter()) {
            logger.debug("type parameter");
        }
        if (t.isVoidType()) {
            logger.debug("void type");
        }
    }

    @Override
    public void visit(Parameter n, PlaceholderGenerator generator) {
        logger.debug("Parameter: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(NameExpr n, PlaceholderGenerator generator) {
        logger.debug("Name Expr: {}", n);
        logger.debug("Name Expr: {}", n.getClass());
        if (isUserDefined(qualifiedName(n))) {
            final String name = n.getNameAsString();
            n.setName(generator.getPlaceholder(name));
        }
        super.visit(n, generator);
    }

    @Override
    public void visit(FieldAccessExpr n, PlaceholderGenerator generator) {
        logger.debug("FieldAccessExpr: {}", n);
        Expression scope = n.getScope();
        if (scope.isNameExpr()) {
            if (isUserDefined(qualifiedName(scope.asNameExpr()))) {
                logger.debug("user defined");
                final String name = n.getNameAsString();
                n.setName(generator.getPlaceholder(name));
            } else {
                logger.debug("Java language");
            }
        }
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(ReturnStmt n, PlaceholderGenerator generator) {
        logger.debug("ReturnStmt: {}", n);
        Optional<Expression> scope = n.getExpression();
        scope.ifPresent(s -> {
            if (s.isNameExpr()) {
                if (isUserDefined(qualifiedName(s.asNameExpr()))) {
                    logger.debug("user defined");
                    final String name = s.asNameExpr().getName().asString();
                    s.asNameExpr().setName(generator.getPlaceholder(name));
                } else {
                    logger.debug("Java language");
                }
            }
        });
        super.visit(n, generator);
    }

    @Override
    public void visit(MethodCallExpr n, PlaceholderGenerator generator) {
        logger.debug("MethodCallExpr: {}", n);
        Optional<Expression> scope = n.getScope();
        scope.ifPresent(s -> {
            if (s.isNameExpr()) {
                if (isUserDefined(qualifiedName(s.asNameExpr()))) {
                    logger.debug("user defined");
                    final String name = s.asNameExpr().getName().asString();
                    s.asNameExpr().setName(generator.getPlaceholder(name));
                } else {
                    logger.debug("Java language");
                }
            }
        });

        if (!scope.isPresent()) {
            final String name = n.getNameAsString();
            n.setName(generator.getPlaceholder(name));
            n.getArguments().forEach(a -> {
                if (a.isNameExpr()) {
                    String nn = a.asNameExpr().getName().asString();
                    a.asNameExpr().setName(generator.getPlaceholder(nn));
                }
            });
        }
        super.visit(n, generator);
    }

    public void visit(VariableDeclarator n, PlaceholderGenerator generator) {
        logger.debug("VariableDeclarator: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(ConstructorDeclaration n, PlaceholderGenerator generator) {
        logger.debug("ConstructorDeclaration: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(EnumDeclaration n, PlaceholderGenerator generator) {
        logger.debug("EnumDeclaration: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    @Override
    public void visit(EnumConstantDeclaration n, PlaceholderGenerator generator) {
        logger.debug("EnumConstantDeclaration: {}", n.getName().asString());
        final String name = n.getNameAsString();
        n.setName(generator.getPlaceholder(name));
        super.visit(n, generator);
    }

    private boolean isUserDefined(String qualifiedName) {
        return qualifiedName == null || !qualifiedName.startsWith("java.") && !qualifiedName.startsWith("javax.");
    }

    private String qualifiedName(NameExpr nameExpr) {
        try {
            logger.debug("qualified name: {}", nameExpr.getNameAsString());
            if (Character.isUpperCase(nameExpr.getNameAsString().charAt(0))) {
                return nameExpr.calculateResolvedType().describe();
            } else {
                return null;
            }
        } catch (UnsolvedSymbolException e) {
            return null;
        }
    }

}
