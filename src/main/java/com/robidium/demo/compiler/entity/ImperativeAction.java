package com.robidium.demo.compiler.entity;


import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.builder.UIPathActions.visitor.CreateActionVisitor;

public class ImperativeAction extends Action {

    public static Action of(String[] data, int index) {
        Action action = new ImperativeAction();
        action.index = index;

        action.timeStamp = data[0];
        action.userID = data[1];
        action.targetApp = data[2];
        action.eventType = data[3];
        action.url = data[4];
        action.content = data[5];
        action.workbookName = data[6];
        action.sheetName = data[7];
        action.targetId = data[8];
        action.targetClass = data[9];
        action.targetTagName = data[10];
        action.targetType = data[11];
        action.targetName = data[12];
        action.targetValue = data[13];
        action.targetInnerText = data[14];
        action.targetChecked = data[15];
        action.targetHref = data[16];
        action.targetOption = data[17];
        action.targetTitle = data[18];
        action.targetInnerHTML = data[19];

        return action;
    }

    @Override
    public void accept(CreateActionVisitor visitor, ScriptBuilder scriptBuilder) {
        visitor.visit(this, scriptBuilder);
    }
}
