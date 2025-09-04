import java.util.*;

public class GrafoAnalyzer {
    private int[][] matrizAdjacencia;
    private int numVertices;
    
    public GrafoAnalyzer(int[][] matriz) {
        this.matrizAdjacencia = matriz;
        this.numVertices = matriz.length;
    }
    
    /**
     * Identifica o tipo do grafo
     */
    public String tipoDoGrafo(int[][] matrizAdjacencia) {
        boolean dirigido = false;
        boolean temLacos = false;
        boolean simples = true;
        boolean multigrafo = false;
        boolean completo = true;
        boolean regular = true;
        int primeiroGrau = -1;
        
        // Verifica se é dirigido (matriz não simétrica)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] != matrizAdjacencia[j][i]) {
                    dirigido = true;
                }
                // Verifica laços (diagonal principal)
                if (i == j && matrizAdjacencia[i][j] > 0) {
                    temLacos = true;
                    simples = false;
                    multigrafo = true;
                }
                // Verifica multigrafo (arestas múltiplas)
                if (matrizAdjacencia[i][j] > 1) {
                    multigrafo = true;
                    simples = false;
                }
            }
        }
        
        // Verifica se é completo (todos os vértices conectados entre si)
        for (int i = 0; i < numVertices && completo; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i != j && matrizAdjacencia[i][j] == 0) {
                    completo = false;
                    break;
                }
            }
        }
        
        // Verifica se é regular (todos os vértices têm o mesmo grau)
        for (int i = 0; i < numVertices; i++) {
            int grau = 0;
            for (int j = 0; j < numVertices; j++) {
                if (dirigido) {
                    grau += matrizAdjacencia[i][j]; // grau de saída
                } else {
                    grau += matrizAdjacencia[i][j];
                    if (i == j) grau += matrizAdjacencia[i][j]; // laço conta duplo
                }
            }
            
            if (primeiroGrau == -1) {
                primeiroGrau = grau;
            } else if (primeiroGrau != grau) {
                regular = false;
                break;
            }
        }
        
        // Determina o tipo
        StringBuilder tipo = new StringBuilder();
        
        if (dirigido) {
            tipo.append("dirigido");
        } else {
            tipo.append("não dirigido");
        }
        
        if (multigrafo) {
            tipo.append(", multigrafo");
        } else if (simples && !temLacos) {
            tipo.append(", simples");
        }
        
        if (completo) {
            tipo.append(", completo");
        }
        
        if (regular) {
            tipo.append(", regular");
        }
        
        // Se não tem características especiais
        if (tipo.toString().equals("não dirigido") || tipo.toString().equals("dirigido")) {
            tipo.append(", nulo");
        }
        
        return tipo.toString();
    }
    
    /**
     * Lista o conjunto de arestas do grafo
     */
    public String arestasDoGrafo(int[][] matrizAdjacencia) {
        Set<String> arestas = new HashSet<>();
        boolean dirigido = !isMatrizSimetrica(matrizAdjacencia);
        
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] > 0) {
                    if (dirigido) {
                        // Para grafo dirigido, adiciona aresta direcionada
                        for (int k = 0; k < matrizAdjacencia[i][j]; k++) {
                            arestas.add("(" + i + "," + j + ")");
                        }
                    } else {
                        // Para grafo não dirigido, evita duplicatas
                        if (i <= j) {
                            for (int k = 0; k < matrizAdjacencia[i][j]; k++) {
                                if (i == j) {
                                    arestas.add("(" + i + "," + j + ")"); // laço
                                } else {
                                    arestas.add("{" + i + "," + j + "}");
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return "Quantidade: " + arestas.size() + "\nConjunto de arestas: " + arestas.toString();
    }
    
    /**
     * Calcula o grau do grafo e de cada vértice
     */
    public String grausDoVertice(int[][] matrizAdjacencia) {
        StringBuilder resultado = new StringBuilder();
        boolean dirigido = !isMatrizSimetrica(matrizAdjacencia);
        
        List<Integer> graus = new ArrayList<>();
        
        resultado.append("Grau de cada vértice:\n");
        
        for (int i = 0; i < numVertices; i++) {
            if (dirigido) {
                int grauEntrada = 0;
                int grauSaida = 0;
                
                // Calcula grau de entrada
                for (int j = 0; j < numVertices; j++) {
                    grauEntrada += matrizAdjacencia[j][i];
                }
                
                // Calcula grau de saída
                for (int j = 0; j < numVertices; j++) {
                    grauSaida += matrizAdjacencia[i][j];
                }
                
                resultado.append("Vértice ").append(i)
                         .append(" - Grau entrada: ").append(grauEntrada)
                         .append(", Grau saída: ").append(grauSaida)
                         .append(", Grau total: ").append(grauEntrada + grauSaida).append("\n");
                
                graus.add(grauEntrada + grauSaida);
            } else {
                int grau = 0;
                for (int j = 0; j < numVertices; j++) {
                    grau += matrizAdjacencia[i][j];
                    // Laços contam duplo
                    if (i == j) {
                        grau += matrizAdjacencia[i][j];
                    }
                }
                
                resultado.append("Vértice ").append(i).append(" - Grau: ").append(grau).append("\n");
                graus.add(grau);
            }
        }
        
        // Encontra graus mínimo e máximo
        int grauMinimo = Collections.min(graus);
        int grauMaximo = Collections.max(graus);
        
        resultado.append("\nGrau do grafo: ").append(grauMaximo);
        resultado.append("\nGrau mínimo: ").append(grauMinimo);
        resultado.append("\nGrau máximo: ").append(grauMaximo);
        
        // Sequência de graus
        Collections.sort(graus, null/*Collections.reverseOrder()*/);
        resultado.append("\nSequência de graus: ").append(graus);
        
        return resultado.toString();
    }
    
    // Constantes para as cores dos vértices
    private static final int BRANCO = 0; // não visitado
    private static final int CINZA = 1;  // sendo processado
    private static final int PRETO = 2;  // processamento finalizado
    
    // Variáveis globais para DFS
    private int tempo;
    private int[] cor;
    private int[] d; // tempo de descoberta
    private int[] f; // tempo de finalização
    private List<Integer> ordemExploracao;
    
    /**
     * Realiza busca em profundidade (DFS) seguindo o algoritmo fornecido
     */
    public String buscaEmProfundidade(int[][] matrizAdjacencia) {
        // Inicialização
        cor = new int[numVertices];
        d = new int[numVertices];
        f = new int[numVertices];
        ordemExploracao = new ArrayList<>();
        
        // 01. para cada vértice u ∈ V[G]
        // 02. cor[u] ← BRANCO
        for (int u = 0; u < numVertices; u++) {
            cor[u] = BRANCO;
        }
        
        // 03. tempo ← 0
        tempo = 0;
        
        // 04. para cada vértice u ∈ V[G]
        // 05. se cor[u] = BRANCO
        // 06. DFS-VISIT(u)
        for (int u = 0; u < numVertices; u++) {
            if (cor[u] == BRANCO) {
                dfsVisit(u, matrizAdjacencia);
            }
        }
        
        // Retorna resultado formatado
        StringBuilder resultado = new StringBuilder();
        resultado.append("Ordem de exploração dos vértices: ").append(ordemExploracao).append("\n");
        resultado.append("\nDetalhes da DFS:\n");
        
        for (int i = 0; i < numVertices; i++) {
            resultado.append("Vértice ").append(i)
                     .append(" - Descoberta: ").append(d[i])
                     .append(", Finalização: ").append(f[i]).append("\n");
        }
        
        return resultado.toString();
    }
    
    /**
     * Método DFS-VISIT seguindo o algoritmo fornecido
     */
    private void dfsVisit(int u, int[][] matrizAdjacencia) {
        // 01. cor[u] ← CINZA
        cor[u] = CINZA;
        
        // 02. tempo ← tempo + 1
        tempo = tempo + 1;
        
        // 03. d[u] ← tempo
        d[u] = tempo;
        
        // Adiciona à ordem de exploração quando descobre o vértice
        ordemExploracao.add(u);
        
        // 04. para cada vértice v ∈ Adj(u)
        for (int v = 0; v < numVertices; v++) {
            if (matrizAdjacencia[u][v] > 0) { // v é adjacente a u
                // 05. se cor[v] = BRANCO
                if (cor[v] == BRANCO) {
                    // 06. DFS-VISIT(v)
                    dfsVisit(v, matrizAdjacencia);
                }
            }
        }
        
        // 07. cor[u] ← PRETO
        cor[u] = PRETO;
        
        // 08. f[u] ← tempo ← (tempo + 1)
        tempo = tempo + 1;
        f[u] = tempo;
    }
    
    /**
     * Verifica se a matriz é simétrica (grafo não dirigido)
     */
    private boolean isMatrizSimetrica(int[][] matriz) {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Método principal para demonstração
     */
    public static void main(String[] args) {
        // Exemplo de matriz de adjacência para um grafo não dirigido simples
        int[][] exemploMatriz = {
            {0, 1, 1, 0},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {0, 1, 1, 0}
        };
        
        GrafoAnalyzer analyzer = new GrafoAnalyzer(exemploMatriz);
        
        System.out.println("=== ANÁLISE DO GRAFO ===\n");
        
        System.out.println("1. TIPO DO GRAFO:");
        System.out.println(analyzer.tipoDoGrafo(exemploMatriz));
        System.out.println();
        
        System.out.println("2. ARESTAS:");
        System.out.println(analyzer.arestasDoGrafo(exemploMatriz));
        System.out.println();
        
        System.out.println("3. GRAUS:");
        System.out.println(analyzer.grausDoVertice(exemploMatriz));
        System.out.println();
        
        System.out.println("4. BUSCA EM PROFUNDIDADE:");
        System.out.println(analyzer.buscaEmProfundidade(exemploMatriz));
        
        System.out.println("\n" + "=".repeat(50));
        
        // Exemplo adicional: grafo dirigido com laços
        System.out.println("\nEXEMPLO 2 - Grafo Dirigido:\n");
        int[][] grafoDirigido = {
            {1, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        
        GrafoAnalyzer analyzer2 = new GrafoAnalyzer(grafoDirigido);
        
        /*System.out.println("\nTipo: " + analyzer2.tipoDoGrafo(grafoDirigido));
        System.out.println(analyzer2.arestasDoGrafo(grafoDirigido));*/

        System.out.println("1. TIPO DO GRAFO:");
        System.out.println(analyzer2.tipoDoGrafo(grafoDirigido));
        System.out.println();
        
        System.out.println("2. ARESTAS:");
        System.out.println(analyzer2.arestasDoGrafo(grafoDirigido));
        System.out.println();
        
        System.out.println("3. GRAUS:");
        System.out.println(analyzer2.grausDoVertice(grafoDirigido));
        System.out.println();
        
        System.out.println("4. BUSCA EM PROFUNDIDADE:");
        System.out.println(analyzer2.buscaEmProfundidade(grafoDirigido));
    }
}