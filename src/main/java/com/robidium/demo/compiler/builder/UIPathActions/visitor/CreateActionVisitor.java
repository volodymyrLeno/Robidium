package com.robidium.demo.compiler.builder.UIPathActions.visitor;


import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.builder.UIPathActions.AddToDictionary;
import com.robidium.demo.compiler.builder.UIPathActions.browser.*;
import com.robidium.demo.compiler.builder.UIPathActions.browser.navigation.NavigateTo;
import com.robidium.demo.compiler.builder.UIPathActions.browser.navigation.NewTab;
import com.robidium.demo.compiler.builder.UIPathActions.browser.navigation.SelectTab;
import com.robidium.demo.compiler.builder.UIPathActions.excel.*;
import com.robidium.demo.compiler.builder.UIPathActions.imperative.ImperativeCopyCell;
import com.robidium.demo.compiler.builder.UIPathActions.imperative.ImperativePaste;
import com.robidium.demo.compiler.builder.Variables;
import com.robidium.demo.compiler.builder.variables.ValuePerIndex;
import com.robidium.demo.compiler.entity.Action;
import com.robidium.demo.compiler.entity.ImperativeAction;
import com.robidium.demo.compiler.transformation.TransformationMap;
import com.robidium.demo.compiler.transformation.semantic.Dependency;
import com.robidium.demo.compiler.transformation.semantic.DependencyService;
import com.robidium.demo.compiler.transformation.synthetic.TransformationService;

import java.util.List;
import java.util.stream.Collectors;

public class CreateActionVisitor implements Visitor {

    public void visit(ImperativeAction action, ScriptBuilder scriptBuilder) {
        if (action.getEventType().equalsIgnoreCase("copyCell")) {
            new ImperativeCopyCell(action);
            new AssignString(action.getTargetId(), scriptBuilder.getSource());
            return;
        }

        if (action.getEventType().equalsIgnoreCase("editField")) {
            if (TransformationMap.getInstance().getActionTransformations().containsKey(String.valueOf(action.getIndex()))) {
                if (TransformationMap.getInstance().getActionTransformations().get(String.valueOf(action.getIndex())).equals("synthetic")) {
                    new AssignString(TransformationService.getInstance().getTransformationsByAction(action)
                            .get(0).getTarget(), scriptBuilder.getTarget());
                    scriptBuilder.getSourceSwitchController().createSourceSwitch(action);
                    new ImperativePaste(action.getTargetName(), Variables.TRANSFORMED_VALUE);
                    new AddToDictionary(ValuePerIndex.NAME, String.valueOf(action.getIndex()), Variables.TRANSFORMED_VALUE);
                } else if (TransformationMap.getInstance().getActionTransformations().get(String.valueOf(action.getIndex())).equals("semantic")) {
                    List<String> dependee = DependencyService.getInstance().getDependeeByDepender(String.valueOf(action.getIndex()));
                    if (dependee.isEmpty()) {
                        Dependency dependency = DependencyService.getInstance().getDependencyByDependerIndex(action.getIndex());
                        new ImperativePaste(action.getTargetName(), String.format("&quot;%s&quot;", dependency.getDependerPerDependee().get("")));
                        new AddToDictionary(ValuePerIndex.NAME, String.valueOf(action.getIndex()),
                                String.format("&quot;%s&quot;", dependency.getDependerPerDependee().get("")));
                    } else {
                        String text = dependee.stream().map(d -> String.format("valuePerIndex.Item(&quot;%s&quot;)", d))
                                .collect(Collectors.joining("+ &quot;,&quot; +"));
                        new ImperativePaste(action.getTargetName(), String.format("FD.Item(%s)", text));
                        new AddToDictionary(ValuePerIndex.NAME, String.valueOf(action.getIndex()), String.format("FD.Item(%s)", text));
                    }
                }
                return;
            }
        }

        createCommonAction(action, scriptBuilder);
    }

    private void createCommonAction(Action action, ScriptBuilder scriptBuilder) {
        String eventType = action.getEventType();

        if (eventType.equalsIgnoreCase("paste")) {
            new EditField(action);
            return;
        }

        if (eventType.equalsIgnoreCase("editCell")) {
            new EditCell(action);
            return;
        }

        if (eventType.equalsIgnoreCase("editRange")) {
            new EditRange(action);
            return;

        }

        if (eventType.equalsIgnoreCase("copy")) {
            new Copy(action);
            return;
        }

        if (eventType.equalsIgnoreCase("copyRange")) {
            new CopyRange(action);
            return;
        }

        if (eventType.equalsIgnoreCase("pasteIntoCell")) {
            new PasteIntoCell(action);
            return;
        }

        if (eventType.equalsIgnoreCase("pasteIntoRange")) {
            new PasteIntoRange(action);
            return;
        }

        if (eventType.equalsIgnoreCase("clickLink")) {
            new ClickLink(action);
            return;
        }


        if (eventType.equalsIgnoreCase("clickButton")) {
            new ClickButton(action);
            return;
        }

        if (eventType.equalsIgnoreCase("clickCheckbox")) {
            new ClickCheckbox(action);
            return;
        }

        if (eventType.equalsIgnoreCase("navigate_to")) {
            new NavigateTo(action);
            return;
        }

        if (eventType.equalsIgnoreCase("createNewTab")) {
            NewTab newTab = new NewTab(scriptBuilder);
            scriptBuilder.getMainSequence().appendChild(newTab);
        }

        if (eventType.equalsIgnoreCase("selectTab")) {
            SelectTab selectTab = new SelectTab(action, scriptBuilder);
            scriptBuilder.getMainSequence().appendChild(selectTab);
        }
    }
}
