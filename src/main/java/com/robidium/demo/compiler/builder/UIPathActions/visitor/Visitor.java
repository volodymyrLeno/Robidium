package com.robidium.demo.compiler.builder.UIPathActions.visitor;


import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.entity.ImperativeAction;

public interface Visitor {

    void visit(ImperativeAction action, ScriptBuilder scriptBuilder);
}
