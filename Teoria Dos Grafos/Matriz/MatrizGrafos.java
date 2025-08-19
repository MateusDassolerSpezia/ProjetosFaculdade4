package Matriz;

import java.util.Scanner;

public class MatrizGrafos {
    public MatrizGrafos() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Qual o tipo de matriz? \n1- Adjacente \n2- Incidente ");
        int tipoMatriz = sc.nextInt();

        switch (tipoMatriz) {
            case 1:

                System.out.println("Tamanho da matriz: ");
                int x = sc.nextInt();
                int matriz[][] = new int[x][x];

                for (int linha = 0; linha < matriz.length; linha++) {
                    for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                        System.out.print("Número[" + linha + "][" + coluna + "]: \n");
                        for (int i = 0; i < matriz.length; i++) { // Linhas
                            for (int j = 0; j < matriz[i /* ou 0 */].length; j++) { // Colunas
                                System.out.print(matriz[i][j] + " ");
                            }
                            System.out.println("");
                        }
                        matriz[linha][coluna] = sc.nextInt();
                    }
                }

                System.out.println("Matriz Final: ");
                for (int i = 0; i < matriz.length; i++) { // Linhas
                            for (int j = 0; j < matriz[i /* ou 0 */].length; j++) { // Colunas
                                System.out.print(matriz[i][j] + " ");
                            }
                            System.out.println("");
                        }
                String tipoGrafo = "Grafo simples";
                for (int i = 0; i < matriz.length; i++) { // Linhas
                            for (int j = 0; j < matriz[i /* ou 0 */].length; j++) { // Colunas
                                if (i == j && matriz[i][j] != 0 || matriz[i][j] > 1) {
                                    tipoGrafo = "Multigrafo";
                                }
                            }
                        }
                        System.out.println(tipoGrafo);
                break;

            case 2:

                System.out.println("Vertices: ");
                int y = sc.nextInt();
                System.out.println("Arestas: ");
                int z = sc.nextInt();
                int matrizIncidente[][] = new int[y][z];

                for (int linha = 0; linha < matrizIncidente.length; linha++) {
                    for (int coluna = 0; coluna < matrizIncidente[linha].length; coluna++) {
                        System.out.print("Número[" + linha + "][" + coluna + "]: \n");
                        for (int i = 0; i < matrizIncidente.length; i++) { // Linhas
                            for (int j = 0; j < matrizIncidente[i /* ou 0 */].length; j++) { // Colunas
                                System.out.print(matrizIncidente[i][j] + " ");
                            }
                            System.out.println("");
                        }
                        matrizIncidente[linha][coluna] = sc.nextInt();
                    }
                }

                System.out.println("Matriz Final: ");
                for (int i = 0; i < matrizIncidente.length; i++) { // Linhas
                            for (int j = 0; j < matrizIncidente[i /* ou 0 */].length; j++) { // Colunas
                                System.out.print(matrizIncidente[i][j] + " ");
                            }
                            System.out.println("");
                        }
                tipoGrafo = "Grafo simples";
                for (int i = 0; i < matrizIncidente.length; i++) { // Linhas
                            for (int j = 0; j < matrizIncidente[i /* ou 0 */].length; j++) { // Colunas
                                if (matrizIncidente[i][j] > 1) {
                                    tipoGrafo = "Multigrafo";
                                } else if (matrizIncidente[i][j] < 0) {
                                    tipoGrafo = "Grafo dirigido";
                                }
                            }
                        }
                        System.out.println(tipoGrafo);

                break;
        }

        /*int x = 3;
        int y = 3;

        int matriz[][] = new int[x][y];

        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                System.out.print("Número[" + linha + "][" + coluna + "]: \n");
                for (int i = 0; i < matriz.length; i++) { // Linhas
                    for (int j = 0; j < matriz[i].length; j++) { // Colunas
                        System.out.print(matriz[i][j] + " ");
                    }
                    System.out.println("");
                }
                matriz[linha][coluna] = sc.nextInt();
            }
        }

        for (int i = 0; i < matriz.length; i++) { // Linhas
            for (int j = 0; j < matriz[i].length; j++) { // Colunas

            }

        }*/

        sc.close();
    }

    public static void main(String[] args) {
        new MatrizGrafos();
    }
}
