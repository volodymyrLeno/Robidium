package com.robidium.demo.compiler.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.builder.UIPathActions.visitor.CreateActionVisitor;
import lombok.Data;

@Data
public abstract class Action {

    @CsvBindByPosition(position = 0)
    protected String timeStamp;
    @CsvBindByPosition(position = 1)
    protected String userID;
    @CsvBindByPosition(position = 2)
    protected String targetApp;
    @CsvBindByPosition(position = 3)
    protected String eventType;
    @CsvBindByPosition(position = 4)
    protected String url;
    @CsvBindByPosition(position = 5)
    protected String content;
    @CsvBindByPosition(position = 6)
    protected String workbookName;
    @CsvBindByPosition(position = 7)
    protected String sheetName;
    @CsvBindByPosition(position = 8)
    protected String targetId;
    @CsvBindByPosition(position = 9)
    protected String targetClass;
    @CsvBindByPosition(position = 10)
    protected String targetTagName;
    @CsvBindByPosition(position = 11)
    protected String targetType;
    @CsvBindByPosition(position = 12)
    protected String targetName;
    @CsvBindByPosition(position = 13)
    protected String targetValue;
    @CsvBindByPosition(position = 14)
    protected String targetInnerText;
    @CsvBindByPosition(position = 15)
    protected String targetChecked;
    @CsvBindByPosition(position = 16)
    protected String targetHref;
    @CsvBindByPosition(position = 17)
    protected String targetOption;
    @CsvBindByPosition(position = 18)
    protected String targetTitle;
    @CsvBindByPosition(position = 19)
    protected String targetInnerHTML;

    protected int index;

    public abstract void accept(CreateActionVisitor visitor, ScriptBuilder scriptBuilder);
}
