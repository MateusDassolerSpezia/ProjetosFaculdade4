package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Semantico implements Constants {

    private Stack<String> pilhaTipos;
    private ArrayList<String> codigo;

    private String operadorRelacional = "";
    private String tipo = "";

    private ArrayList<String> listaIdentificadores;
    private HashMap<String, String> tabelaSimbolos;

    private Stack<String> pilhaRotulos;
    private int contadorRotulos = 0;

    public Semantico() {
        pilhaTipos = new Stack<>();
        codigo = new ArrayList<>();
        listaIdentificadores = new ArrayList<>();
        tabelaSimbolos = new HashMap<>();
        pilhaRotulos = new Stack<>();
    }

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

            case 106:
                break;

            case 107:
                break;

            case 108:
                break;

            case 109:
                break;

            case 110:
                break;

            case 111:
                break;

            case 112:
                break;

            case 113:
                break;

            case 114:
                break;

            case 115:
                acao115();
                break;

            case 116:
                acao116();
                break;

            case 117:
                break;

            case 118:
                acao118();
                break;

            case 119:
                break;

            case 120:
                break;

            case 121:
                break;

            case 122:
                break;

            case 123:
                break;

            case 124:
                break;

            case 125:
                break;

            case 126:
                break;

            case 127:
                break;

            case 128:
                break;

            case 129:
                break;

            case 130:
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

    public void acao118() {
        codigo.add("call void [mscorlib]System.Console::WriteLine()\n");
    }

    public void acao115() {
        pilhaTipos.push("bool");
        codigo.add("ldc.i4.1\n");
    }

    public void acao116() {
        pilhaTipos.push("bool");
        codigo.add("ldc.i4.0\n");
    }

    public void acao110() {
        codigo.add("ldc.i8 -1\n");
        codigo.add("conv.r8\n");
        codigo.add("mul\n");
    }

    public void acao106() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();
        pilhaTipos.push(tipoResultante(tipo1, tipo2));
        codigo.add("add\n");
    }

    public void acao107() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();
        pilhaTipos.push(tipoResultante(tipo1, tipo2));
        codigo.add("sub\n");
    }

    public void acao108() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();
        pilhaTipos.push(tipoResultante(tipo1, tipo2));
        codigo.add("mul\n");
    }

    public void acao109() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();
        pilhaTipos.push("float64");
        codigo.add("div\n");
    }

    public void acao111(Token token) {
        operadorRelacional = token.getLexeme();
    }

    public void acao112() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();

        // Sempre resulta em boolean
        pilhaTipos.push("bool");

        // Geração de código conforme operador armazenado
        switch (operadorRelacional) {
            case "==":
                codigo.add("ceq\n");
                break;

            case "~=":  // diferente
                codigo.add("ceq\n");
                codigo.add("ldc.i4.1\n");
                codigo.add("xor\n"); // NOT do resultado
                break;

            case "<":
                codigo.add("clt\n");
                break;

            case ">":
                codigo.add("cgt\n");
                break;
        }
    }

    public void acao117() {
        codigo.add("ldc.i4.1\n");
        codigo.add("xor\n");
    }

    public void acao113() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();

        // Resultado sempre é boolean
        pilhaTipos.push("bool");

        codigo.add("and\n");
    }

    public void acao114() {
        String tipo2 = pilhaTipos.pop();
        String tipo1 = pilhaTipos.pop();

        // Resultado sempre é boolean
        pilhaTipos.push("bool");

        codigo.add("or\n");
    }

    public String tipoResultante(String tipo1, String tipo2) {
        if (tipo1.equals("float64") || tipo2.equals("float64")) {
            return "float64";
        }
        return "int64";
    }

    public String getCodigoGerado() {
        return String.join("", codigo);
    }
}
