package recovery;

import parser.LangGoTwoConstants;
import recovery.RecoverySet;

public class First {
  static public final RecoverySet program = new RecoverySet();
  static public final RecoverySet funcList = new RecoverySet();
  static public final RecoverySet paramList = new RecoverySet();
  static public final RecoverySet varDecl = new RecoverySet();
  static public final RecoverySet constDecl = new RecoverySet();
  static public final RecoverySet statement = new RecoverySet();
  static public final RecoverySet statList = new RecoverySet();
  static public final RecoverySet funcDecl = new RecoverySet();
  static public final RecoverySet returnList = new RecoverySet();
  static public final RecoverySet type = new RecoverySet();
  static public final RecoverySet importSpec = new RecoverySet();
  static public final RecoverySet expression = new RecoverySet();
  static public final RecoverySet funcBody = new RecoverySet();
  static public final RecoverySet paramLType = new RecoverySet();


  static {
    program.add(new Integer(LangGoTwoConstants.PACKAGE));

    funcList.add(new Integer(LangGoTwoConstants.FUNC));
    
    paramList.add(new Integer(LangGoTwoConstants.IDENT));

    funcDecl.add(new Integer(LangGoTwoConstants.FUNC));

    varDecl.add(new Integer(LangGoTwoConstants.VAR));

    constDecl.add(new Integer(LangGoTwoConstants.CONST));
  
    statement.add(new Integer(LangGoTwoConstants.LBRACE));
    statement.add(new Integer(LangGoTwoConstants.BREAK));
    statement.add(new Integer(LangGoTwoConstants.SEMICOLON));
    statement.addAll(funcDecl);
    statement.addAll(varDecl);
    statement.addAll(constDecl);
    //lvalue and atribStat
    statement.add(new Integer(LangGoTwoConstants.IDENT));
    //returnstat
    statement.add(new Integer(LangGoTwoConstants.RETURN));
    //ifstat
    statement.add(new Integer(LangGoTwoConstants.IF));
    //forstat
    statement.add(new Integer(LangGoTwoConstants.FOR));

    statList.addAll(statement);

    type.add(new Integer(LangGoTwoConstants.INT));
    type.add(new Integer(LangGoTwoConstants.STRING));
    type.add(new Integer(LangGoTwoConstants.BOOL));
    type.add(new Integer(LangGoTwoConstants.FLOAT));
    type.add(new Integer(LangGoTwoConstants.BYTE));
    type.add(new Integer(LangGoTwoConstants.RUNE));

    returnList.addAll(type);
    returnList.add(new Integer(LangGoTwoConstants.IDENT));

    importSpec.add(new Integer(LangGoTwoConstants.IDENT));
    importSpec.add(new Integer(LangGoTwoConstants.DOT));
    importSpec.add(new Integer(LangGoTwoConstants.STRING_LITERAL));

    expression.add(new Integer(LangGoTwoConstants.PLUS));
    expression.add(new Integer(LangGoTwoConstants.MINUS));
    expression.add(new Integer(LangGoTwoConstants.INTEGER_LITERAL));
    expression.add(new Integer(LangGoTwoConstants.STRING_LITERAL));
    expression.add(new Integer(LangGoTwoConstants.NULL_LITERAL));
    expression.add(new Integer(LangGoTwoConstants.FLOATING_POINT_LITERAL));
    expression.add(new Integer(LangGoTwoConstants.LPAREN));
    expression.add(new Integer(LangGoTwoConstants.IDENT));

    funcBody.add(new Integer(LangGoTwoConstants.LPAREN));

    paramLType.addAll(type);
    paramLType.add(new Integer(LangGoTwoConstants.COMMA));

  }
}