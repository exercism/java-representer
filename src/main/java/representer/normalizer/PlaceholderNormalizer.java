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

import java.util.Map;
import java.util.Optional;

public class PlaceholderNormalizer extends VoidVisitorAdapter<String> {
	private static final Logger logger = LoggerFactory.getLogger(PlaceholderNormalizer.class);

	private Mapper mapper = new Mapper();

	@Override
	public void visit(ClassOrInterfaceDeclaration n, String arg) {
		logger.debug("ClassOrInterfaceDeclaration: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(MethodDeclaration n, String arg) {
		logger.debug("MethodDeclaration: {}", n.getName().asString());
		logger.debug("method return type: {}", n.getTypeAsString());
		mapType(n.getType());
		n.getParameters().forEach(p -> logger.debug("method parameter: {}", p.getType().asString()));
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}
	
		private void mapType(Type t) {
		if(t.isClassOrInterfaceType()) {
			logger.debug("class or interface type");
			ClassOrInterfaceType classOrInterfaceType = t.asClassOrInterfaceType();
	        Optional<NodeList<Type>> typeArguments = classOrInterfaceType.getTypeArguments();
	        typeArguments.ifPresent(types -> {
	        	String genericsClass = types.get(0).asString();
	        	if(genericsClass.length() > 1) { // workaround to avoid substitution of T
	        		types.set(0, new ClassOrInterfaceType(null,mapper.getPlaceholder(genericsClass)));
	        	}
	        });
		}
		if(t.isArrayType()) {
			logger.debug("array type");
			mapType(t.getElementType());
		}
		if(t.isPrimitiveType()) {
			logger.debug("primitive type");
		}
		
		if(t.isTypeParameter()) {
			logger.debug("type parameter");
		}
		if(t.isVoidType()) {
			logger.debug("void type");
		}
	}

	@Override
	public void visit(Parameter n, String arg) {
		logger.debug("Parameter: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(NameExpr n, String arg) {
		logger.debug("Name Expr: {}", n);
		logger.debug("Name Expr: {}", n.getClass());
		if (isUserDefined(qualifiedName(n))) {
			final String name = n.getNameAsString();
			n.setName(mapper.getPlaceholder(name));
		}
		super.visit(n, arg);
	}

	@Override
	public void visit(FieldAccessExpr n, String arg) {
		logger.debug("FieldAccessExpr: {}", n);
		Expression scope = n.getScope();
		if (scope.isNameExpr()) {
			if (isUserDefined(qualifiedName(scope.asNameExpr()))) {
				logger.debug("user defined");
				final String name = n.getNameAsString();
				n.setName(mapper.getPlaceholder(name));
			} else {
				logger.debug("Java language");
			}
		}
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(ReturnStmt n, String arg) {
		logger.debug("ReturnStmt: {}", n);
		Optional<Expression> scope = n.getExpression();
		scope.ifPresent(s -> {
			if (s.isNameExpr()) {
				if (isUserDefined(qualifiedName(s.asNameExpr()))) {
					logger.debug("user defined");
					final String name = s.asNameExpr().getName().asString();
					s.asNameExpr().setName(mapper.getPlaceholder(name));
				} else {
					logger.debug("Java language");
				}
			}
		});
		super.visit(n, arg);
	}

	@Override
	public void visit(MethodCallExpr n, String arg) {
		logger.debug("MethodCallExpr: {}", n);
		Optional<Expression> scope = n.getScope();
		scope.ifPresent(s -> {
			if (s.isNameExpr()) {
				if (isUserDefined(qualifiedName(s.asNameExpr()))) {
					logger.debug("user defined");
					final String name = s.asNameExpr().getName().asString();
					s.asNameExpr().setName(mapper.getPlaceholder(name));
				} else {
					logger.debug("Java language");
				}
			}
		});

		if (!scope.isPresent()) {
			final String name = n.getNameAsString();
			n.setName(mapper.getPlaceholder(name));
			n.getArguments().forEach(a -> {
				if(a.isNameExpr()) {
					String nn = a.asNameExpr().getName().asString();
					a.asNameExpr().setName(mapper.getPlaceholder(nn));
				}
			});
		}
		super.visit(n, arg);
	}

	public void visit(VariableDeclarator n, String arg) {
		logger.debug("VariableDeclarator: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(ConstructorDeclaration n, String arg) {
		logger.debug("ConstructorDeclaration: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(EnumDeclaration n, String arg) {
		logger.debug("EnumDeclaration: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	@Override
	public void visit(EnumConstantDeclaration n, String arg) {
		logger.debug("EnumConstantDeclaration: {}", n.getName().asString());
		final String name = n.getNameAsString();
		n.setName(mapper.getPlaceholder(name));
		super.visit(n, arg);
	}

	public Map<String, String> mapping() {
		return mapper.getMapping();
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
