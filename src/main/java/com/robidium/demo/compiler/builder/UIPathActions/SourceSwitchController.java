package com.robidium.demo.compiler.builder.UIPathActions;

import com.robidium.demo.compiler.builder.UIPathActions.imperative.ImperativeInvokeCode;
import com.robidium.demo.compiler.builder.variables.Clipboard;
import com.robidium.demo.compiler.entity.Action;
import com.robidium.demo.compiler.transformation.synthetic.Transformation;
import com.robidium.demo.compiler.transformation.synthetic.TransformationService;

import java.util.List;

public class SourceSwitchController {

    public SourceSwitchController() {
    }

    public void createSourceSwitch(Action action) {
        TransformationService transformationService = TransformationService.getInstance();
        List<Transformation> actionTransformations = transformationService.getTransformationsByAction(action);
        if (!actionTransformations.isEmpty()) {
            SourceSwitch sourceSwitch = new SourceSwitch();
            actionTransformations.forEach(t -> {
                String code = transformationService.createCode(t);
                sourceSwitch.appendChild(new ImperativeInvokeCode(code, t.getSource(), Clipboard.NAME));
            });
        }
    }
}
