package representer;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

class ParserConfigurationFactory {
    static ParserConfiguration getParserConfiguration() {
        CombinedTypeSolver comb = new CombinedTypeSolver(new ReflectionTypeSolver());
        JavaSymbolSolver solver = new JavaSymbolSolver(comb);
        return new ParserConfiguration().setSymbolResolver(solver);
    }
}
