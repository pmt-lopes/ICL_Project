/* Generated By:JavaCC: Do not edit this line. Parser.java */
package parser;
import ast.*;
import java.util.ArrayList;
import java.util.List;

public class Parser implements ParserConstants {

  final public Exp Start() throws ParseException {
  Exp exp;
    exp = seq();
    jj_consume_token(EL);
                   {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  final public Exp seq() throws ParseException {
  Token op;
  Exp t1, t2;
    t1 = expression();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      op = jj_consume_token(SEMICOLON);
      t2 = expression();
                 t1 = new ASTSeq(t1,t2);
    }
       {if (true) return t1;}
    throw new Error("Missing return statement in function");
  }

//Exp ref() :
//{Token n;
//  Exp q1, q2;}
//{
//     q1 = expression() ( <REF> q2=expression()
//    			 {
//                    q1 = new ASTRef(q1,q2);
//                 }
//                  )?
//     { return q1; }
//
//}
  final public Exp expression() throws ParseException {
  Exp left, right;
  Token op;
    left = term();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
      case EQ:
      case NEQ:
      case AND:
      case OR:
      case GR:
      case LR:
      case GRE:
      case LRE:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        op = jj_consume_token(PLUS);
        break;
      case MINUS:
        op = jj_consume_token(MINUS);
        break;
      case EQ:
        op = jj_consume_token(EQ);
        break;
      case NEQ:
        op = jj_consume_token(NEQ);
        break;
      case GR:
        op = jj_consume_token(GR);
        break;
      case GRE:
        op = jj_consume_token(GRE);
        break;
      case LR:
        op = jj_consume_token(LR);
        break;
      case LRE:
        op = jj_consume_token(LRE);
        break;
      case AND:
        op = jj_consume_token(AND);
        break;
      case OR:
        op = jj_consume_token(OR);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      right = term();
      switch(op.kind) {
        case PLUS: left = new ASTAdd(left, right); break;
        case MINUS: left = new ASTSub(left, right); break;
        case EQ: left = new ASTEq(left, right); break;
        case NEQ: left = new ASTNEq(left, right); break;
        case GR: left = new ASTGr(left, right); break;
        case GRE: left = new ASTGrE(left, right); break;
        case LR: left = new ASTGr(right, left); break;
        case LRE: left = new ASTGrE(right, left); break;
        case AND: left = new ASTAnd(left, right); break;
        case OR: left = new ASTOr(left, right); break;
      }
    }
    {if (true) return left;}
    throw new Error("Missing return statement in function");
  }

  final public Exp term() throws ParseException {
  Exp primary, exp;
  Token op;
    primary = primaryExpression();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
      case DIV:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
        op = jj_consume_token(TIMES);
        break;
      case DIV:
        op = jj_consume_token(DIV);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      exp = primaryExpression();
      switch(op.kind) {
        case TIMES: {if (true) return new ASTMult(primary, exp);} break;
        case DIV: {if (true) return new ASTDiv(primary, exp);} break;
      }
    }
    {if (true) return primary;}
    throw new Error("Missing return statement in function");
  }

//Exp simpleExpression() : {
//  Exp primary, exp; Token op;
//}
//{
//  primary=primaryExpression()
//  (
//    <REF> exp=expression() { return new ASTRef(primary, exp); }
//    |
//    <REREF> exp=expression() { return new ASTReref(primary, exp); }
//    |
//    <DEREF> exp = expression() { return new ASTDeref(exp); }
//  )*
//  { return primary; }
//}
  final public Exp primaryExpression() throws ParseException {
  Token id;
  Exp exp, exp1, exp2;
  Exp exp3 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUM:
      jj_consume_token(NUM);
            {if (true) return new ASTInt(Integer.parseInt(token.image));}
      break;
    case TRUE:
      jj_consume_token(TRUE);
             {if (true) return new ASTBool(true);}
      break;
    case FALSE:
      jj_consume_token(FALSE);
              {if (true) return new ASTBool(false);}
      break;
    case ID:
      id = jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case REF:
        jj_consume_token(REF);
        exp3 = expression();
        break;
      default:
        jj_la1[5] = jj_gen;
        ;
      }
                                           {if (true) return new ASTRef(id.image,exp3);}
      break;
    case DEREF:
      jj_consume_token(DEREF);
      id = jj_consume_token(ID);
                      {if (true) return new ASTDeref(id.image);}
      break;
    case MINUS:
      jj_consume_token(MINUS);
      jj_consume_token(NUM);
                    {if (true) return new ASTInt(-Integer.parseInt(token.image));}
      break;
    case NEG:
      jj_consume_token(NEG);
      exp = primaryExpression();
                                    {if (true) return new ASTNeg(exp);}
      break;
    case LET:
      jj_consume_token(LET);
      id = jj_consume_token(ID);
      jj_consume_token(EQ);
      exp1 = seq();
      jj_consume_token(IN);
      exp2 = seq();
      jj_consume_token(END);
      ASTLet let = new ASTLet(); let.addToken(id.image); let.addBinding(exp1); let.setBody(exp2); {if (true) return let;}
      break;
    case IF:
      jj_consume_token(IF);
      exp = seq();
      jj_consume_token(THEN);
      exp1 = seq();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
        exp3 = seq();
        break;
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      jj_consume_token(END);
                                                                    {if (true) return new ASTIf(exp, exp1, exp3);}
      break;
    case WHILE:
      jj_consume_token(WHILE);
      exp1 = seq();
      jj_consume_token(DO);
      exp2 = seq();
      jj_consume_token(END);
                                               {if (true) return new ASTWhile(exp1, exp2);}
      break;
    case PRINTLN:
      jj_consume_token(PRINTLN);
      exp = expression();
                                 {if (true) return new ASTPrintln(exp);}
      break;
    case PRINT:
      jj_consume_token(PRINT);
      exp = expression();
                               {if (true) return new ASTPrint(exp);}
      break;
    case NEW:
      jj_consume_token(NEW);
      exp = expression();
                             {if (true) return new ASTNew(exp);}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RPAR:
        jj_consume_token(RPAR);
        break;
      case NUM:
      case MINUS:
      case LPAR:
      case NEG:
      case TRUE:
      case FALSE:
      case LET:
      case IF:
      case WHILE:
      case DEREF:
      case NEW:
      case PRINT:
      case PRINTLN:
      case ID:
        exp3 = expression();
        jj_consume_token(RPAR);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                                 if(exp3 != null) {if (true) return exp3;} else {if (true) return new ASTNull();}
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[9];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x10000000,0x3de0c,0x3de0c,0x30,0x30,0x20000000,0x1000000,0x825c20ca,0x825c204a,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x207,0x207,};
   }

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[43];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 9; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 43; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
