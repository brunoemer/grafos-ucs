Universidade de Caxias do Sul - Centro de Computação e Tecnologia da Informação

Trabalho de Teoria de Grafos – 26/09/12 – Prof. Ricardo
Faça um programa que receba como entrada um grafo e aplique alguns algoritmos sobre ele. O(s) grafo(s) deve
ser lido de um arquivo de entrada ou fornecido pelo usuário através da interface do programa (1 ponto). O programa
deve permitir a entrada de grafos valorados e não-valorados, dirigidos e não-dirigidos.
o programa deve implementar as seguintes operações:
a) Identificação do número cromático através de alteração estrutural (1 ponto).
b) Buscas em amplitude (1 ponto) e profundidade (1 ponto) a partir de um dado vértice inicial. Usar
como critério de escolha, o vértice adjacente de menor valor. Listar a ordem de visitação dos vértices.
c) Identificação de componentes conexas (1 ponto).
d) Identificação de componentes biconexas (1 ponto).
e) Identificação de ciclo euleriano (1 ponto).
f) Identificação de ciclo hamiltoniano (1 ponto).
g) Identificação de árvore geradora mínima (1 ponto).
h) Cálculo das distâncias entre todos os pares de vértices (1 ponto).
O trabalho é individual. Devem ser colocados no webfólio os arquivos-fonte e uma versão executável do
programa até as 23:55 da data da avaliação da segunda área, no dia 30/11.
O formato do arquivo de entrada é o seguinte:
<número de vértices do grafo>
para cada vértice, uma linha no arquivo contendo:
<número de vértices adjacentes><lista de vértices adjacentes>
Obs: A numeração dos vértices inicia em 1.
Obs1: O separador entre os elementos no arquivo é obrigatoriamente o espaço em branco.
Ex de um arquivo contendo K3:
3 // número de vértices de K3
2 2 3 // o vértice 1 tem 2 vértices adjacentes (primeira coluna), que são os vértices 2 e 3
2 1 3
2 1 2
Se o grafo for valorado, em cada lista de vértices adjacentes deve aparecer o número do vértice adjacente e o
peso da aresta.
