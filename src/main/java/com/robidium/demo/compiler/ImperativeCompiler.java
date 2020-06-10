package com.robidium.demo.compiler;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.builder.UIPathActions.visitor.CreateActionVisitor;
import com.robidium.demo.compiler.entity.Action;
import com.robidium.demo.compiler.transformation.TransformationMap;
import com.robidium.demo.compiler.transformation.semantic.DependencyService;
import com.robidium.demo.compiler.transformation.synthetic.TransformationService;

import java.util.List;

public class ImperativeCompiler {
    public static String TRANSFORMATIONS_FILE;
    public static String TRANSFORMATION_MAP_FILE;
    public static String DEPENDENCIES_FILE;
    public static String LOG_FILE_PATH;
    private static Action previousAction;
    private static ScriptBuilder scriptBuilder;
    private static boolean newExcelScope = true;
    private static boolean newChromeScope = true;

    static void compile() {
        List<Action> actions = Utils.readLog();
        TransformationService.getInstance().readTransformationsFromJSON(TRANSFORMATIONS_FILE);
        DependencyService.getInstance().parse(DEPENDENCIES_FILE);
        TransformationMap.getInstance().parse(TRANSFORMATION_MAP_FILE);
        scriptBuilder = new ScriptBuilder();
        scriptBuilder.createDoSequence();
        parseActions(actions);
        ScriptBuilder.writeScript();

        previousAction = null;
        newExcelScope = true;
        newChromeScope = true;
    }

    private static void parseActions(List<Action> actions) {
        previousAction = actions.get(0);
        for (Action action : actions) {
            createScope(action);
            if (!addElement(action)) {
                continue;
            }
            previousAction = action;
        }
    }

    private static void createScope(Action action) {
        String currentApp = action.getTargetApp();
        if (newChromeScope && currentApp.equals("Chrome")) {
            scriptBuilder.createOpenBrowser(action.getUrl());
            newChromeScope = false;
        } else if (newExcelScope && currentApp.equals("Excel")) {
            scriptBuilder.createExcelScope(action.getWorkbookName());
            newExcelScope = false;
        } else if (!currentApp.equals(previousAction.getTargetApp())) {
            scriptBuilder.createNewScope(action.getTargetApp(), action.getWorkbookName());
        }
    }

    private static boolean addElement(Action action) {
        String actionName = action.getEventType();
        String previousActionName = previousAction.getEventType();
//        if (actionName.equals("editField") && previousActionName.equals("paste") &&
//                previousAction.getTargetName().equals(action.getTargetName())) {
//            return false;
//        }
        if (actionName.equals("paste")) {
            return false;
        }
        action.accept(new CreateActionVisitor(), scriptBuilder);

        return true;
    }
}
