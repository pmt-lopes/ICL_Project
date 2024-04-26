/* Generated By:JavaCC: Do not edit this line. Parser.java */
package parser;
import ast.*;
import java.util.ArrayList;
import java.util.List;

public class Parser implements ParserConstants {

  final public Exp Start() throws ParseException {
  Exp e;
    e = decl();
    jj_consume_token(EL);
                      {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp decl() throws ParseException {
  List<Exp> bindings = new ArrayList<Exp>();Token id; Exp e, e1, e2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LET:
      jj_consume_token(LET);
      label_1:
      while (true) {
        id = jj_consume_token(Id);
        jj_consume_token(EQ);
        e1 = bexp();
                                 bindings.add(new ASTId(id.image, e1));
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case Id:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
      }
      jj_consume_token(IN);
      e2 = bexp();
    ASTLet letExpr = new ASTLet();
    letExpr.setBindings(bindings);
    letExpr.setBody(e2);
    e = letExpr;
    {if (true) return e;}
      break;
    case Num:
    case MINUS:
    case LPAR:
    case NEG:
    case TRUE:
    case FALSE:
    case DEREF:
    case Id:
      e = bexp();
    {if (true) return e;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//not tested from here
//makes more sense for these expressions to be inside declarations then the opposite
  final public Exp cycle() throws ParseException {
  Exp e1, e2, e3;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
    case MINUS:
    case LPAR:
    case NEG:
    case TRUE:
    case FALSE:
    case DEREF:
    case Id:
      e1 = seq();
               {if (true) return e1;}
      break;
    case WHILE:
      jj_consume_token(WHILE);
      e1 = bexp();
      jj_consume_token(DO);
      e2 = seq();
      jj_consume_token(END);
                                                           {if (true) return new ASTWhile(e1, e2);}
      break;
    case IF:
      jj_consume_token(IF);
      e1 = bexp();
      jj_consume_token(THEN);
      e2 = seq();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
        e3 = seq();
                                                                       {if (true) return new ASTIf(e1, e2, e3);}
        break;
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      jj_consume_token(END);
                                                                                                                 {if (true) return new ASTIf(e1, e2);}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//Sequential instructions should be added around here i think
//Referencing too
  final public Exp seq() throws ParseException {
  Exp e1, e2;
    e1 = ref();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
      e2 = seq();
                                   e1 = new ASTSeq(e1, e2);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
          {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp ref() throws ParseException {
  Exp e1; Token x;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Id:
      x = jj_consume_token(Id);
      jj_consume_token(REF);
      e1 = bexp();
                               {if (true) return new ASTRef(x.image, e1);}
      break;
    case Num:
    case MINUS:
    case LPAR:
    case NEG:
    case TRUE:
    case FALSE:
    case DEREF:
      e1 = bexp();
                {if (true) return e1;}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// Not tested until here

// describes boolean expressions
  final public Exp bexp() throws ParseException {
  Exp e1, e2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NEG:
      jj_consume_token(NEG);
      e2 = bexp();
                      e1 = new ASTNeg(e2);
      break;
    case Num:
    case MINUS:
    case LPAR:
    case TRUE:
    case FALSE:
    case DEREF:
    case Id:
      e1 = cmp();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
        case OR:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_2;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
          jj_consume_token(AND);
          e2 = bexp();
                            e1 = new ASTAnd(e1, e2);
          break;
        case OR:
          jj_consume_token(OR);
          e2 = bexp();
                             e1 = new ASTOr(e1, e2);
          break;
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    {if (true) return e1;}
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp bool() throws ParseException {
 Token x;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
      x = jj_consume_token(TRUE);
                 {if (true) return new ASTBool(true);}
      break;
    case FALSE:
      x = jj_consume_token(FALSE);
                  {if (true) return new ASTBool(false);}
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp cmp() throws ParseException {
  Exp e1, e2;
    e1 = expr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQ:
    case NEQ:
    case GR:
    case LR:
    case GRE:
    case LRE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
        jj_consume_token(EQ);
        e2 = expr();
                       e1 = new ASTEq(e1, e2);
        break;
      case GR:
        jj_consume_token(GR);
        e2 = expr();
                         e1 = new ASTGr(e1, e2);
        break;
      case LR:
        jj_consume_token(LR);
        e2 = expr();
                         e1 = new ASTGr(e2, e1);
        break;
      case GRE:
        jj_consume_token(GRE);
        e2 = expr();
                          e1 = new ASTGrE(e1, e2);
        break;
      case LRE:
        jj_consume_token(LRE);
        e2 = expr();
                          e1 = new ASTGrE(e2, e1);
        break;
      case NEQ:
        jj_consume_token(NEQ);
        e2 = expr();
                          e1 = new ASTNEq(e1, e2);
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp expr() throws ParseException {
  Exp e1, e2;
    e1 = Term();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
        e2 = expr();
                            e1 = new ASTAdd(e1,e2);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        e2 = expr();
                             e1 = new ASTSub(e1,e2);
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Term() throws ParseException {
  Exp e1, e2;
    e1 = Fact();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
      case DIV:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
        jj_consume_token(TIMES);
        e2 = Term();
                             e1 = new ASTMult(e1,e2);
        break;
      case DIV:
        jj_consume_token(DIV);
        e2 = Term();
                           e1 = new ASTDiv(e1,e2);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Fact() throws ParseException {
  Token x; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
      x = jj_consume_token(Num);
                    {if (true) return new ASTInt(Integer.parseInt(x.image));}
      break;
    case MINUS:
      jj_consume_token(MINUS);
      x = jj_consume_token(Num);
                            {if (true) return new ASTInt(-Integer.parseInt(x.image));}
      break;
    case TRUE:
    case FALSE:
      e = bool();
                     System.out.println(((ASTBool)e).value + " THIS IS VALUE"); {if (true) return e;}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case Num:
      case MINUS:
      case LPAR:
      case TRUE:
      case FALSE:
      case DEREF:
      case Id:
        e = expr();
        break;
      case NEG:
      case LET:
        e = decl();
        break;
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(RPAR);
                                                  {if (true) return e;}
      break;
    case Id:
      x = jj_consume_token(Id);
                   {if (true) return new ASTId(x.image, null);}
      break;
    case DEREF:
      jj_consume_token(DEREF);
      x = jj_consume_token(Id);
                               {if (true) return new ASTDeref(x.image);}
      break;
    default:
      jj_la1[17] = jj_gen;
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
  final private int[] jj_la1 = new int[18];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0xe10250,0x8000000,0x12610250,0x80000000,0x610250,0xc000,0xc000,0x610250,0x600000,0x1e3000,0x1e3000,0x60,0x60,0x180,0x180,0xe10250,0x600250,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x20,0x24,0x0,0x24,0x0,0x24,0x0,0x0,0x24,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x24,0x24,};
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
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
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
    boolean[] la1tokens = new boolean[38];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 18; i++) {
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
    for (int i = 0; i < 38; i++) {
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
