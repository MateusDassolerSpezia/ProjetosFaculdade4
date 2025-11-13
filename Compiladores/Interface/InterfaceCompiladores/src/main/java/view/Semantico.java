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
                acao106();
                break;

            case 107:
                acao107();
                break;

            case 108:
                acao108();
                break;

            case 109:
                acao109();
                break;

            case 110:
                acao110();
                break;

            case 111:
                acao111(token);
                break;

            case 112:
                acao112();
                break;

            case 113:
                acao113();
                break;

            case 114:
                acao114();
                break;

            case 115:
                acao115();
                break;

            case 116:
                acao116();
                break;

            case 117:
                acao117();
                break;

            case 118:
                acao118();
                break;

            case 119:
                acao119();
                break;

            case 120:
                acao120(token);
                break;

            case 121:
                acao121(token);
                break;

            case 122:
                acao122();
                break;

            case 123:
                acao123(token);
                break;

            case 124:
                acao124(token);
                break;

            case 125:
                acao125(token);
                break;

            case 126:
                acao126();
                break;

            case 127:
                acao127();
                break;

            case 128:
                acao128();
                break;

            case 129:
                acao129(token);
                break;

            case 130:
                acao130(token);
                break;

        }
        //System.out.println("Ação #"+action+", Token: "+token);  
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
                + "}\n"
                + "}");
    }

    public void acao102() {
        String tipoRetirado = pilhaTipos.pop();
        if (tipoRetirado.equals("int64")) {
            codigo.add("conv.i8\n");
        }
        codigo.add("call void [mscorlib]System.Console::Write(" + tipoRetirado + ")\n");
    }

    public void acao118() {
        codigo.add("ldstr \"\\n\"\n");
        codigo.add("call void [mscorlib]System.Console::Write(string)\n");
        //codigo.add("call void [mscorlib]System.Console::WriteLine()\n");
    }

    public void acao103(Token token) {
        pilhaTipos.push("int64");
        codigo.add("ldc.i8 " + token.getLexeme() + "\n");
        codigo.add("conv.r8\n");
    }

    public void acao104(Token token) {
        pilhaTipos.add("float64");
        codigo.add("ldc.r8 " + token.getLexeme() + "\n");
    }

    public void acao105(Token token) {
        pilhaTipos.add("string");
        codigo.add("ldstr " + token.getLexeme() + "\n");
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

    public void acao120(Token token) {
        tipo = token.getLexeme();
    }

    public void acao121(Token token) {
        listaIdentificadores.add(token.getLexeme());
    }

    public void acao119() {
        for (String id : listaIdentificadores) {
            String tipoIL = "";

            switch (tipo) {
                case "int":
                    tipoIL = "int64";
                    break;

                case "float":
                    tipoIL = "float64";
                    break;

                case "string":
                    tipoIL = "string";
                    break;

                case "bool":
                    tipoIL = "bool";
                    break;
            }
            tabelaSimbolos.put(id, tipoIL);
            codigo.add(".locals(" + tipoIL + " " + id + ")\n");
        }
        listaIdentificadores.clear();
    }

    public void acao122() {
        String tipoRetirado = pilhaTipos.pop();
        if (tipoRetirado.equals("int64")) {
            codigo.add("conv.i8\n");
        }
        String id = listaIdentificadores.get(listaIdentificadores.size() - 1);

        codigo.add("stloc " + id + "\n");

        listaIdentificadores.clear();
    }

    public void acao123(Token token) throws SemanticError { // PERGUNTAR SOBRE ESSA REGRA
        String id = token.getLexeme();

        /*if (!tabelaSimbolos.containsKey(id)) {
            throw new SemanticError("identificador não declarado " + id);
        }*/

        String tipoId = tabelaSimbolos.get(id);

        if (tipoId.equals("bool")) {
            throw new SemanticError(id + " inválido para comando de entrada", token.getPosition());
        }

        codigo.add("call string [mscorlib]System.Console::ReadLine()\n");

        switch (tipoId) {
            case "int64":
                codigo.add("call int64 [mscorlib]System.Int64::Parse(string)\n");
                break;

            case "float64":
                codigo.add("call float64 [mscorlib]System.Double::Parse(string)\n");
                break;

            case "string":
                break;
        }

        codigo.add("stloc " + id + "\n");
    }

    public void acao124(Token token) {
        codigo.add("ldstr " + token.getLexeme() + "\n");
        codigo.add("call void [mscorlib]System.Console::Write(string)\n");
    }

    public void acao130(Token token) {
        String id = token.getLexeme();
        String tipoId = tabelaSimbolos.get(id);

        pilhaTipos.push(tipoId);
        codigo.add("ldloc " + id + "\n");

        if (tipoId.equals("int64")) {
            codigo.add("conv.r8\n");
        }
    }

    public void acao125(Token token) throws SemanticError {
        /*if (pilhaTipos.isEmpty()) {
            throw new SemanticError("expressão vazia em comando de seleção", token.getPosition());
        }*/

        String tipoRetirado = pilhaTipos.pop();

        if (!tipoRetirado.equals("bool")) {
            throw new SemanticError("expressão incompatível em comando de seleção", token.getPosition());
        }

        String novoRotulo1 = gerarRotulo();
        codigo.add("brfalse " + novoRotulo1 + "\n");
        pilhaRotulos.push(novoRotulo1);
    }

    public void acao127() {
        String novoRotulo2 = gerarRotulo();
        codigo.add("br " + novoRotulo2 + "\n");
        String rotuloDesempilhado = pilhaRotulos.pop();
        codigo.add(rotuloDesempilhado + ":\n");
        pilhaRotulos.push(novoRotulo2);
    }

    public void acao126() {
        String rotuloDesempilhado = pilhaRotulos.pop();
        codigo.add(rotuloDesempilhado + ":\n");
    }

    public void acao128() {
        String novoRotulo = gerarRotulo();
        codigo.add(novoRotulo + ":\n");
        pilhaRotulos.push(novoRotulo);
    }

    public void acao129(Token token) throws SemanticError {
        String tipoRetirado = pilhaTipos.pop();
        if (!tipoRetirado.equals("bool")) {
            throw new SemanticError("expressão incompatível em comando de repetição", token.getPosition());
        }
        String rotuloDesempilhado = pilhaRotulos.pop();
        codigo.add("brfalse " + rotuloDesempilhado + "\n");
    }

    private String gerarRotulo() {
        contadorRotulos++;
        return "L" + contadorRotulos;
    }

    private String tipoResultante(String tipo1, String tipo2) {
        if (tipo1.equals("float64") || tipo2.equals("float64")) {
            return "float64";
        }
        return "int64";
    }

    public String getCodigoGerado() {
        return String.join("", codigo);
    }
}
