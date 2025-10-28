package view;

import java.util.ArrayList;
import java.util.Stack;
import view.SemanticError;

public class Semantico implements Constants {

    private Stack<String> pilhaTipos; 
    private ArrayList<String> codigo;

    public void executeAction(int action, Token token) throws SemanticError {
        switch (action) {
            case 100:
                acao100();
                break;
            case 101:
                acao101();
                break;
            case 102:
                acao102();
                break;
            case 103:
                acao103(token);
                break;
            case 104:
                acao104(token);
                break;
            case 105:
                acao105(token);
                break;
        }
        //System.out.println("Ação #"+action+", Token: "+token);  
    }

    public void acao105(Token token) {
        pilhaTipos.add("string");
        codigo.add("ldstr " + token.getLexeme() + "\n");
    }
    
    public void acao104(Token token) {
        pilhaTipos.add("float64");
        codigo.add("ldc.r8 " + token.getLexeme() + "\n");
    }
    
    public void acao103(Token token) {
        pilhaTipos.push("int64");
        codigo.add("ldc.i8 " + token.getLexeme() + "\n");
        codigo.add("conv.r8\n");
    }

    public void acao102() {
        String tipoRetirado = pilhaTipos.pop();
        if (tipoRetirado.equals("int64")) {
            codigo.add("conv.i8\n");
        }
        codigo.add("call void [mscorlib]System.Console::Write(" + tipoRetirado + ")\n");
    }
    
    public void acao100() {
        codigo.add(".assembly extern mscorlib {}\n"
                + ".assembly teste_f{}\n"
                + ".module teste_f.exe\n"
                + "\n"
                + ".class public teste_f{\n"
                + "  .method static public void _principal(){\n"
                + "   .entrypoint\n");
    }

    public void acao101() {
        codigo.add("ret\n"
                + "  }\n"
                + "}");
    }
}
