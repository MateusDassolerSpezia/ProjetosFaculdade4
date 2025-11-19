// Mateus Dassoler Spezia
// Pedro Alegria
// Vinícius Oneda

import java.util.*;
import java.io.PrintWriter;

public class GrafoAnalyzer {
    private int numVertices;
    boolean nulo;

    public GrafoAnalyzer(int[][] matriz) {
        this.numVertices = matriz.length;
        this.nulo = false;
    }

    // ================
    //     TIPOS
    // ================
    public String tipoDoGrafo(int[][] matrizAdjacencia) {
        boolean dirigido = false;
        boolean temLacos = false;
        boolean simples = true;
        boolean multigrafo = false;
        boolean completo = true;
        boolean regular = true;
        nulo = false;
        int nuloAux = 0;
        int primeiroGrau = -1;

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                nuloAux += matrizAdjacencia[i][j];
            }
        }

        if (nuloAux == 0) {
            nulo = true;
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] != matrizAdjacencia[j][i]) {
                    dirigido = true;
                }
                if (i == j && matrizAdjacencia[i][j] > 0) {
                    temLacos = true;
                    simples = false;
                    multigrafo = true;
                }
                if (matrizAdjacencia[i][j] > 1) {
                    multigrafo = true;
                    simples = false;
                }
            }
        }

        for (int i = 0; i < numVertices && completo; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i != j && matrizAdjacencia[i][j] == 0) {
                    completo = false;
                    break;
                }
            }
        }

        for (int i = 0; i < numVertices; i++) {
            int grau = 0;
            for (int j = 0; j < numVertices; j++) {
                if (dirigido) {
                    grau += matrizAdjacencia[i][j];
                } else {
                    grau += matrizAdjacencia[i][j];
                    if (i == j) grau += matrizAdjacencia[i][j];
                }
            }

            if (primeiroGrau == -1) {
                primeiroGrau = grau;
            } else if (primeiroGrau != grau) {
                regular = false;
                break;
            }
        }

        StringBuilder tipo = new StringBuilder();

        if (nulo) {
            tipo.append("nulo");
            if (numVertices == 1) tipo.append(", completo");
            tipo.append(", regular");
        } else {

            if (dirigido) tipo.append("dirigido");
            else tipo.append("não dirigido");

            if (multigrafo) tipo.append(", multigrafo");
            else if (simples && !temLacos) tipo.append(", simples");

            if (completo) tipo.append(", completo");
            if (regular) tipo.append(", regular");
        }

        return tipo.toString();
    }

    // ================
    //    ARESTAS
    // ================
    public String arestasDoGrafo(int[][] matrizAdjacencia) {
        List<String> arestas = new ArrayList<>();
        boolean dirigido = !isMatrizSimetrica(matrizAdjacencia);

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] > 0) {
                    if (dirigido) {
                        for (int k = 0; k < matrizAdjacencia[i][j]; k++)
                            arestas.add("(" + labelFor(i) + "," + labelFor(j) + ")");
                    } else {
                        if (i <= j) {
                            for (int k = 0; k < matrizAdjacencia[i][j]; k++)
                                arestas.add("(" + labelFor(i) + "," + labelFor(j) + ")");
                        }
                    }
                }
            }
        }

        return "Quantidade: " + arestas.size() + "\n\nConjunto de arestas: " + arestas.toString();
    }

    // ================
    //      GRAUS
    // ================
    public String grausDoVertice(int[][] matrizAdjacencia) {
        StringBuilder resultado = new StringBuilder();
        boolean dirigido = !isMatrizSimetrica(matrizAdjacencia);

        List<Integer> graus = new ArrayList<>();
        int grauTotal = 0;

        for (int i = 0; i < numVertices; i++) {
            if (dirigido) {
                int grauEntrada = 0, grauSaida = 0;

                for (int j = 0; j < numVertices; j++) grauEntrada += matrizAdjacencia[j][i];
                for (int j = 0; j < numVertices; j++) grauSaida += matrizAdjacencia[i][j];

                grauTotal += grauEntrada + grauSaida;
                graus.add(grauEntrada + grauSaida);

            } else {
                int grau = 0;
                for (int j = 0; j < numVertices; j++) {
                    grau += matrizAdjacencia[i][j];
                    if (i == j) grau += matrizAdjacencia[i][j];
                }
                grauTotal += grau;
                graus.add(grau);
            }
        }

        resultado.append("Grau do grafo: ").append(grauTotal).append("\n\n");
        resultado.append("Grau de cada vértice:\n");

        for (int i = 0; i < numVertices; i++) {
            if (dirigido) {
                int grauEntrada = 0, grauSaida = 0;
                for (int j = 0; j < numVertices; j++) grauEntrada += matrizAdjacencia[j][i];
                for (int j = 0; j < numVertices; j++) grauSaida += matrizAdjacencia[i][j];

                resultado.append("Vértice ").append(labelFor(i))
                        .append(" - Grau entrada: ").append(grauEntrada)
                        .append(", Grau saída: ").append(grauSaida)
                        .append(", Grau total do vértice: ").append(grauEntrada + grauSaida).append("\n");
            } else {
                resultado.append("Vértice ").append(labelFor(i))
                        .append(" - Grau: ").append(graus.get(i)).append("\n");
            }
        }

        Collections.sort(graus);
        resultado.append("\nSequência de graus: ").append(graus);

        return resultado.toString();
    }

    // ================
    //      DFS
    // ================
    private static final int BRANCO = 0;
    private static final int CINZA = 1;
    private static final int PRETO = 2;

    private int tempo;
    private int[] cor;
    private int[] d;
    private int[] f;
    private List<Integer> ordemExploracao;

    public String buscaEmProfundidade(int[][] matrizAdjacencia) {
        cor = new int[numVertices];
        d = new int[numVertices];
        f = new int[numVertices];
        ordemExploracao = new ArrayList<>();
        tempo = 0;

        for (int u = 0; u < numVertices; u++) cor[u] = BRANCO;

        for (int u = 0; u < numVertices; u++)
            if (cor[u] == BRANCO) dfsVisit(u, matrizAdjacencia);

        StringBuilder resultado = new StringBuilder();
        resultado.append("Ordem de exploração dos vértices: ").append(mapLabels(ordemExploracao)).append("\n\n")
                 .append("Detalhes da DFS:\n");

        for (int i = 0; i < numVertices; i++)
            resultado.append("Vértice ").append(labelFor(i))
                    .append(" - Descoberta: ").append(d[i])
                    .append(", Finalização: ").append(f[i]).append("\n");

        return resultado.toString();
    }

    private void dfsVisit(int u, int[][] matrizAdjacencia) {
        cor[u] = CINZA;
        tempo++;
        d[u] = tempo;
        ordemExploracao.add(u);

        for (int v = 0; v < numVertices; v++)
            if (matrizAdjacencia[u][v] > 0 && cor[v] == BRANCO)
                dfsVisit(v, matrizAdjacencia);

        cor[u] = PRETO;
        tempo++;
        f[u] = tempo;
    }

    private boolean isMatrizSimetrica(int[][] matriz) {
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                if (matriz[i][j] != matriz[j][i]) return false;
        return true;
    }

    // =========================================================
    //      SPRINT 1 — EXPORTAÇÃO PARA DOT (AGORA COM RÓTULOS)
    // =========================================================
    public void exportarDOT(int[][] matriz, String nomeArquivo) {
        boolean dirigido = !isMatrizSimetrica(matriz);
        StringBuilder sb = new StringBuilder();

        sb.append(dirigido ? "digraph G {\n" : "graph G {\n");

        String ligacao = dirigido ? " -> " : " -- ";

        // declarar nós (opcional, mas claro)
        for (int i = 0; i < numVertices; i++) {
            sb.append("    ").append(labelFor(i)).append(";\n");
        }

        // arestas (evita duplicatas em não dirigido)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matriz[i][j] > 0) {
                    if (!dirigido && j < i) continue; // evita duplicar aresta
                    for (int k = 0; k < matriz[i][j]; k++) {
                        sb.append("    ").append(labelFor(i)).append(ligacao).append(labelFor(j)).append(";\n");
                    }
                }
            }
        }

        sb.append("}");

        // Exibe conteúdo gerado ao usuário (exigência do enunciado)
        System.out.println("5. GRAFO E COLORAÇÃO: \n");
        System.out.println(sb.toString() + "\n");

        try (PrintWriter pw = new PrintWriter(nomeArquivo)) {
            pw.print(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    //     SPRINT 2 — COLORAÇÃO SEQUENCIAL
    // =========================================================
    public int[] coloracaoSequencial(int[][] matriz) {
        int[][] M = matrizComoNaoDirigida(matriz);
        int[] cor = new int[numVertices];

        for (int v = 0; v < numVertices; v++) {

            boolean[] usadas = new boolean[numVertices + 1];

            for (int u = 0; u < numVertices; u++)
                if (M[v][u] > 0 && cor[u] != 0)
                    usadas[cor[u]] = true;

            int c;
            for (c = 1; c <= numVertices; c++)
                if (!usadas[c]) break;

            cor[v] = c;
        }
        return cor;
    }

    // =========================================================
    //     SPRINT 3 — EXPORTAÇÃO COLORIDA (EXIBE CONTEÚDO)
    // =========================================================
    private static final String[] CORES = {
        "white",        // índice 0 => sem cor (fallback)
        "lightblue",    // 1
        "lightgreen",   // 2
        "lightyellow",  // 3
        "orange",       // 4
        "pink",         // 5
        "lightgray",    // 6
        "violet",       // 7
        "gold",         // 8
        "coral"         // 9
    };

    private String getColorName(int cor) {
        if (cor <= 0) return CORES[0];
        // queremos evitar index out of bounds: usar módulo com preserve de 1..n
        int idx = cor % (CORES.length);
        if (idx < 0) idx = 0;
        // se cor é múltiplo do tamanho -> idx == 0 -> mapear para último (para variar)
        if (idx == 0) idx = CORES.length - 1;
        return CORES[idx];
    }

    public void exportarDOTColorido(int[][] matriz, int[] cores, String nomeArquivo) {
        boolean dirigido = !isMatrizSimetrica(matriz);
        StringBuilder sb = new StringBuilder();

        sb.append(dirigido ? "digraph G {\n" : "graph G {\n");

        // Declara vértices com atributos de cor
        for (int i = 0; i < numVertices; i++) {
            String corNome = getColorName(cores[i]);
            sb.append("    ").append(labelFor(i))
              .append(" [style=filled, fillcolor=").append(corNome).append("];\n");
        }

        String ligacao = dirigido ? " -> " : " -- ";

        // Declara arestas (como antes)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (!dirigido && j < i) continue;
                if (matriz[i][j] > 0) {
                    for (int k = 0; k < matriz[i][j]; k++) {
                        sb.append("    ").append(labelFor(i)).append(ligacao).append(labelFor(j)).append(";\n");
                    }
                }
            }
        }

        sb.append("}");

        // Exibe conteúdo gerado ao usuário (exigência do enunciado)
        System.out.println(sb.toString() + "\n");

        try (PrintWriter pw = new PrintWriter(nomeArquivo)) {
            pw.print(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    //    SPRINT 4 — COLORAÇÃO HEURÍSTICA (WELSH–POWELL)
    // =========================================================
    public int[] coloracaoHeuristica(int[][] matriz) {
        int[][] M = matrizComoNaoDirigida(matriz);
        int[] cor = new int[numVertices];

        Integer[] ordem = new Integer[numVertices];
        for (int i = 0; i < numVertices; i++) ordem[i] = i;

        Arrays.sort(ordem, (a, b) -> grau(M, b) - grau(M, a));

        for (int v : ordem) {
            boolean[] usadas = new boolean[numVertices + 1];

            for (int u = 0; u < numVertices; u++)
                if (M[v][u] > 0 && cor[u] != 0)
                    usadas[cor[u]] = true;

            int c;
            for (c = 1; c <= numVertices; c++)
                if (!usadas[c]) break;

            cor[v] = c;
        }

        return cor;
    }

    private int grau(int[][] M, int v) {
        int g = 0;
        for (int i = 0; i < numVertices; i++) g += M[v][i];
        return g;
    }

    // ==============================================================
    //    SPRINT 5 — FUNÇÃO EXTRA: Número Cromático e Grafo Dirigido
    // ==============================================================
    public int numeroCromatico(int[] cores) {
        int max = 0;
        for (int c : cores)
            if (c > max) max = c;
        return max;
    }

    private int[][] matrizComoNaoDirigida(int[][] matriz) {
    int[][] m = new int[numVertices][numVertices];
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            // soma os dois sentidos (garante vizinhança)
            m[i][j] = (matriz[i][j] > 0 || matriz[j][i] > 0) ? 1 : 0;
        }
    }
    return m;
}

    // =========================================================
    //   UTILITÁRIOS: rótulos tipo A,B,...,Z,AA,AB...
    // =========================================================
    private String labelFor(int idx) {
        // Converte índice 0 -> "A", 1 -> "B", ... 25 -> "Z", 26 -> "AA", etc.
        StringBuilder sb = new StringBuilder();
        int n = idx;
        while (n >= 0) {
            int rem = n % 26;
            sb.insert(0, (char) ('A' + rem));
            n = (n / 26) - 1;
        }
        return sb.toString();
    }

    private String mapLabels(List<Integer> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(labelFor(lista.get(i)));
            if (i < lista.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // =========================================================
    //    NOVO: GERAR HTML + SVG (LAYOUT CIRCULAR, CORES, LEGENDA)
    // =========================================================
    public void gerarHTML(int[][] matriz, int[] cores, String nomeArquivo) {
        boolean dirigido = !isMatrizSimetrica(matriz);
        // Canvas SVG
        int width = 800;
        int height = 800;
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - 80; // espaçamento
        double angleStep = 2.0 * Math.PI / numVertices;

        double[] px = new double[numVertices];
        double[] py = new double[numVertices];

        for (int i = 0; i < numVertices; i++) {
            double angle = i * angleStep - Math.PI / 2.0; // começa no topo
            px[i] = cx + radius * Math.cos(angle);
            py[i] = cy + radius * Math.sin(angle);
        }

        StringBuilder svg = new StringBuilder();
        svg.append("<svg width=\"").append(width).append("\" height=\"").append(height)
           .append("\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        // defs (seta para dirigido)
        if (dirigido) {
            svg.append("<defs>\n")
               .append("<marker id=\"arrow\" markerWidth=\"10\" markerHeight=\"10\" refX=\"10\" refY=\"3\" orient=\"auto\">\n")
               .append("<path d=\"M0,0 L0,6 L9,3 z\" fill=\"black\" />\n")
               .append("</marker>\n</defs>\n");
        }

        // Draw edges first (so nodes are on top)
        svg.append("<!-- arestas -->\n");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (!dirigido && j <= i) continue; // evita duplicar em não dirigido
                int count = matriz[i][j];
                if (count <= 0) continue;
                for (int k = 0; k < count; k++) {
                    double x1 = px[i];
                    double y1 = py[i];
                    double x2 = px[j];
                    double y2 = py[j];
                    // pequenas curvas para multiarestas (deslocamento)
                    if (count > 1) {
                        double midx = (x1 + x2) / 2;
                        double midy = (y1 + y2) / 2;
                        double dx = y2 - y1;
                        double dy = x1 - x2;
                        double len = Math.hypot(dx, dy);
                        if (len != 0) {
                            dx = dx / len;
                            dy = dy / len;
                        }
                        double offset = 10 * (k - (count - 1) / 2.0);
                        double cxq = midx + dx * offset;
                        double cyq = midy + dy * offset;
                        // quadratic Bezier via SVG path
                        svg.append(String.format(Locale.US,
                            "<path d=\"M %.2f %.2f Q %.2f %.2f %.2f %.2f\" stroke=\"black\" stroke-width=\"1.5\" fill=\"none\" %s/>\n",
                            x1, y1, cxq, cyq, x2, y2,
                            dirigido ? " marker-end=\"url(#arrow)\"" : ""));
                    } else {
                        svg.append(String.format(Locale.US,
                            "<line x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"black\" stroke-width=\"1.5\" %s />\n",
                            x1, y1, x2, y2,
                            dirigido ? " marker-end=\"url(#arrow)\"" : ""));
                    }
                }
            }
        }

        // loops (i == j)
        for (int i = 0; i < numVertices; i++) {
            int loops = matriz[i][i];
            if (loops > 0) {
                double x = px[i];
                double y = py[i];
                double r = 22;
                svg.append(String.format(Locale.US,
                    "<path d=\"M %.2f %.2f m -%f, 0 a %f,%f 0 1,0 %f,0 a %f,%f 0 1,0 -%f,0\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" />\n",
                    x + 30, y - 30, r, r, r, 2 * r, r, r, 2 * r));
            }
        }

        // Draw nodes
        svg.append("<!-- vértices -->\n");
        int nodeR = 22;
        for (int i = 0; i < numVertices; i++) {
            String corNome = (cores != null && cores.length > i) ? getColorName(cores[i]) : getColorName(0);
            svg.append(String.format(Locale.US,
                "<g>\n" +
                "<circle cx=\"%.2f\" cy=\"%.2f\" r=\"%d\" fill=\"%s\" stroke=\"black\" stroke-width=\"1.5\" />\n" +
                "<text x=\"%.2f\" y=\"%.2f\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Arial\" font-size=\"12\">%s</text>\n" +
                "</g>\n",
                px[i], py[i], nodeR, corNome,
                px[i], py[i] + 1.5, labelFor(i)));
        }

        svg.append("</svg>\n");

        // Build legend: which color -> cor número
        StringBuilder legend = new StringBuilder();
        legend.append("<div style=\"font-family:Arial; margin-top:10px;\">\n");
        legend.append("<strong>Legenda (cor -> número):</strong><br/>\n");
        // find used colors
        Set<Integer> used = new TreeSet<>();
        if (cores != null) {
            for (int c : cores) if (c > 0) used.add(c);
        }
        if (used.isEmpty()) {
            legend.append("Nenhuma coloração aplicada.\n");
        } else {
            for (int c : used) {
                String cname = getColorName(c);
                legend.append(String.format(Locale.US,
                    "<div style=\"display:inline-block; margin-right:10px; align-items:center;\">\n" +
                    "<span style=\"display:inline-block; width:16px; height:16px; background:%s; border:1px solid #000; vertical-align:middle;\"></span>\n" +
                    "<span style=\"margin-left:6px;\">Cor %d</span>\n" +
                    "</div>\n", cname, c));
            }
        }
        legend.append("</div>\n");

        // Full HTML
        StringBuilder html = new StringBuilder();
        String titulo = nomeArquivo.replace(".html", "").replace("_", " ").replace("grafos exportados/", "");

        html.append("<!doctype html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n")
            .append("<title>Grafo - ").append(nomeArquivo).append("</title>\n")
            .append("<style>body{font-family:Arial;margin:10px;} .container{display:flex;gap:20px;}</style>\n")
            .append("</head>\n<body>\n")
            .append("<h1>Visualização do ").append(titulo).append("</h1>\n")
            .append("<div class=\"container\">\n")
            .append(svg.toString())
            .append("<div>\n")
            .append("<h2>Informações:</h2>\n")
            .append("<p>Vértices: ").append(numVertices).append("</p>\n")
            .append("<p><strong> Arestas: </strong> </p>\n")
            .append("<pre>").append(arestasDoGrafo(matriz)).append("</pre>\n")
            .append(legend.toString())
            .append("</div>\n</div>\n")
            .append("<p><em>Dirigido: ").append(dirigido ? "Sim" : "Não").append("</em></p>\n")
            .append("</body>\n</html>\n");

        try (PrintWriter pw = new PrintWriter(nomeArquivo)) {
            pw.print(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    //           MAIN DO TEU PROGRAMA (AJUSTADO PARA RÓTULOS)
    // =========================================================
    public static void main(String[] args) {
        int x = 1;
        Scanner sc = new Scanner(System.in);

        while (x != 0) {

            System.out.print("Tamanho da matriz ou 0 para sair: ");
            x = sc.nextInt();

            if (x == 0) {
                sc.close();
                return;
            }

            while (x < 0) {
                System.out.println("Tamanho da matriz deve ser maior que 0");
                System.out.print("Tamanho da matriz ou 0 para sair: ");
                x = sc.nextInt();
            }

            int matriz[][] = new int[x][x];

            for (int linha = 0; linha < matriz.length; linha++) {
                for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                    System.out.print("Ligação[" + linha + "][" + coluna + "]: ");
                    matriz[linha][coluna] = sc.nextInt();
                }
            }

            System.out.println("\nMATRIZ FINAL: ");
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    System.out.print(matriz[i][j] + " ");
                }
                System.out.println("");
            }

            GrafoAnalyzer analyzer3 = new GrafoAnalyzer(matriz);

            System.out.println("\n1. TIPO DO GRAFO:");
            System.out.println(analyzer3.tipoDoGrafo(matriz));
            System.out.println();

            if (!analyzer3.nulo) {

                System.out.println("2. ARESTAS:");
                System.out.println(analyzer3.arestasDoGrafo(matriz));
                System.out.println();

                System.out.println("3. GRAUS:");
                System.out.println(analyzer3.grausDoVertice(matriz));
                System.out.println();

                System.out.println("4. BUSCA EM PROFUNDIDADE:");
                System.out.println(analyzer3.buscaEmProfundidade(matriz));
                System.out.println();

                // ============================================
                //  CHAMADAS DOS SPRINTS (automáticas; gera arquivos .dot e .html)
                // ============================================

                analyzer3.exportarDOT(matriz, "grafos_exportados/grafo.dot");
                // gera uma visualização HTML simples (sem cor)
                analyzer3.gerarHTML(matriz, null, "grafos_exportados/grafo.html");

                int[] seq = analyzer3.coloracaoSequencial(matriz);
                analyzer3.exportarDOTColorido(matriz, seq, "grafos_exportados/grafo_sequencial.dot");
                analyzer3.gerarHTML(matriz, seq, "grafos_exportados/grafo_sequencial.html");

                int[] heur = analyzer3.coloracaoHeuristica(matriz);
                analyzer3.exportarDOTColorido(matriz, heur, "grafos_exportados/grafo_heuristico.dot");
                analyzer3.gerarHTML(matriz, heur, "grafos_exportados/grafo_heuristico.html");


                System.out.println("Número cromático (sequencial): " + analyzer3.numeroCromatico(seq));
                System.out.println("Número cromático (heurístico): " + analyzer3.numeroCromatico(heur));
            }

            System.out.println("-------------------------------------------\n");
        }
        sc.close();
    }
}
