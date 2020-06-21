package com.robidium.demo.compiler;

import java.nio.file.Path;
import java.util.List;

public class Compiler {

    public static void compile(List<Path> args, String patternId) {
        ImperativeCompiler.LOG_FILE_PATH = args.get(0).toString();
        ImperativeCompiler.TRANSFORMATIONS_FILE = args.get(1).toString();
        ImperativeCompiler.DEPENDENCIES_FILE = args.get(2).toString();
        ImperativeCompiler.TRANSFORMATION_MAP_FILE = args.get(3).toString();
        ImperativeCompiler.compile(patternId);
    }
}
