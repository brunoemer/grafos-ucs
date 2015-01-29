package trabalho;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Trabalho Teoria dos Grafos
 * @author Bruno Emer
 * 
 * a) Identificação do número cromático através de alteração estrutural (1 ponto).
 * b) Buscas em amplitude (1 ponto) e profundidade (1 ponto) a partir de um dado vértice inicial. Usar como critério de escolha, o vértice adjacente de menor valor. Listar a ordem de visitação dos vértices.
 * c) Identificação de componentes conexas (1 ponto).
 * d) Identificação de componentes biconexas (1 ponto).
 * e) Identificação de ciclo euleriano (1 ponto).
 * f) Identificação de ciclo hamiltoniano (1 ponto).
 * g) Identificação de árvore geradora mínima (1 ponto).
 * h) Cálculo das distâncias entre todos os pares de vértices (1 ponto).
 */

/*
 * -- Fazer --
 * Confirmar quais algoritmos usam grafos dirigidos e valorados
 * Ciclo hamiltoniano, qual algoritmo? pg 261 OK
 * Entrada de numeros infinitos? todos sem valor com infinito, exceto diagonal principal OK
 * Bug arvore geradora minima, vertice 0 0 esta retornando com infinito OK
 */

public class Algoritmos {
	private static final int INF = 999; // Constante infinita
	
	private int n; // Numero de vertices
	private int[][] mArestas; // Ligacao arestas com vertices
	private int[][] mValores; // Valor das arestas
	private int[] nivel; // Busca amplitude - nivel da arvore
	private int[] jaFoi; // Buscas profundidade - flag de visitacao de vertices
	private int[] componentes; // Componentes conexas - componentes
	int[] num; // Componentes biconexas - ordem visitacao dos vertices
	int[] pai; // Componentes biconexas - pai de cada vertice
	int[] lowpt; // Componentes biconexas - low point
	int numCount; // Componentes biconexas - contador para visitacao
	Stack<int[]> pilha; // Componentes biconexas - pilha para auxiliar busca de componentes
	private List<String> output;
	
	public Algoritmos() {
		// TESTES
//		n = 8;
//		n = 5;
//		n = 10;
		
//		mArestas = new int[][]{ // TESTE BUSCAS
//			{0, 1, 0, 1, 0, 0, 0, 0},
//			{1, 0, 1, 0, 1, 0, 0, 0},
//			{0, 1, 0, 0, 0, 1, 0, 0},
//			{1, 0, 0, 0, 1, 0, 1, 0},
//			{0, 1, 0, 1, 0, 1, 0, 1},
//			{0, 0, 1, 0, 1, 0, 0, 0},
//			{0, 0, 0, 1, 0, 0, 0, 1},
//			{0, 0, 0, 0, 1, 0, 1, 0}};
//		mArestas = new int[][]{ // TESTE COMPONENTES CONEXOS
//			{0, 1, 0, 1, 0, 0, 0, 0},
//			{0, 0, 1, 0, 1, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 1, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 1, 0},
//			{0, 0, 0, 0, 0, 0, 0, 1},
//			{0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0}};
//		mArestas = new int[][]{ // TESTE CICLO EULERIANO
//			{0, 1, 1, 0, 0, 0, 0},
//			{1, 0, 1, 1, 1, 0, 0},
//			{1, 1, 0, 1, 1, 0, 0},
//			{0, 1, 1, 0, 1, 1, 0},
//			{0, 1, 1, 1, 0, 0, 1},
//			{0, 0, 0, 1, 0, 0, 1},
//			{0, 0, 0, 0, 1, 1, 0}};
//		mValores = new int[][]{ // TESTE ARVORE GERADORA MINIMA
//			{0, 15, 0, 0, 0, 0, 0, 0, 0, 10},
//			{15, 0, 4, 0, 0, 0, 0, 0, 8, 0},
//			{0, 4, 0, 8, 0, 0, 0, 6, 0, 0},
//			{0, 0, 8, 0, 5, 0, 8, 0, 0, 0},
//			{0, 0, 0, 5, 0, 4, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 9, 0, 0, 0},
//			{0, 0, 0, 4, 0, 9, 0, 7, 0, 0},
//			{0, 0, 6, 0, 0, 0, 7, 0, 7, 0},
//			{0, 8, 0, 0, 0, 0, 0, 7, 0, 9},
//			{10, 0, 0, 0, 0, 0, 0, 0, 9, 0}};
//		mValores = new int[][]{ // TESTE DISTANCIA DE TODOS VERTICES
//			{0, 8, 3, 6, INF},
//			{8, 0, 3, INF, 6},
//			{3, 3, 0, 2, 3},
//			{6, INF, 2, 0, 8},
//			{INF, 6, 3, 8, 0}};
		initVars();
	}
	
	public void initVars(){
		// Nivel dos vertices para busca por amplitude
		nivel = new int[n];
		
		// Busca por profundidade
		jaFoi = new int[n];
		
		// Componentes conexas
		componentes = new int[n];
		
		// Componentes biconexas
		num = new int[n];
		pai = new int[n];
		lowpt = new int[n];
		numCount = 1;
		pilha = new Stack<int[]>();
		
		for(int i = 0; i < n; i++){
			nivel[i] = -1;
			jaFoi[i] = 0;
			componentes[i] = -1;
			num[i] = 0;
			pai[i] = 0;
			lowpt[i] = 0;
		}
		
		output = new ArrayList<String>();
	}
	
	/**
	 * Questao a
	 * Nao valorado, nao dirigido
	 */
	public void numeroCromatico(){
		final TreeMap<Integer,Integer> graus = new TreeMap<Integer,Integer>();
		int i, j, grau;
		// Calcula graus dos vertices
		for(i = 0; i < mArestas.length; i++){
			grau = 0;
			for(j = 0; j < mArestas[i].length; j++){
				if(mArestas[i][j] == 1){
					grau++;
				}
			}
			graus.put(i, grau);
		}
        // Ordena vertices por ordem decrescente de grau
		final TreeMap<Integer,Integer> grausOrdemDesc = new TreeMap<Integer,Integer>(new Comparator<Integer>(){
		    public int compare(Integer o1, Integer o2){
		        int compare = graus.get(o1).compareTo(graus.get(o2));
		        if (compare == 0){
		        	return 1;
		        } else if(compare > 0) {
		        	return -1;
		        } else {
		        	return 1;
		        }
		    }
		});
		grausOrdemDesc.putAll(graus);
		int[] v = new int[n];
		i = 0;
		for ( Entry<Integer, Integer> e : grausOrdemDesc.entrySet() ){
		    v[i] = e.getKey();
		    i++;
		}
		// Faz coloracao dos vertices
		int[] cores = new int[n];
		int tem, k, cor = 1;
		cores[v[0]] = cor; // Cor 1
		for(j = 1; j < n; j++){
			tem = 0;
			for(k = 0; k < mArestas[v[j]].length; k++){
				if(mArestas[v[j]][k] > 0 && cor == cores[k]){
					tem = 1;
					break;
				}
			}
			if(tem == 1){
				cor++;
			}
			cores[v[j]] = cor;
		}
		
		// Escreve
		output.clear();
		output.add("Número cromático:");
		int c = 0;
		for(i = 0; i < cores.length; i++){
			output.add((i+1)+") "+cores[i]);
			if(cores[i] > c){
				c = cores[i];
			}
		}
		output.add("Número cromático aproximado = "+c);
	}
	
	/**
	 * Questao b1
	 * Nao valorado, nao dirigido
	 */
	public void buscaAmplitude(int v){
		output.clear();
		output.add("Busca amplitude");
		String row = "Visitação: "+v+" ";
		int nivela = 0;
		int tem;
		v = v - 1;
		nivel[v] = 0;
		do{
			tem = 0;
			for(int i = v; i < n; i++){
				if(nivel[i] == nivela){
					for(int j = 0; j < n; j++){
						if(mArestas[i][j] == 1 && nivel[j] == -1){
							nivel[j] = nivela + 1;
							tem = 1;
							row = row.concat(String.valueOf(j + 1)+" ");
						}
					}
				}
			}
			nivela = nivela + 1;
			v = 0;
		}while(tem != 0);
		output.add(row);
	}

	/**
	 * Questao b2
	 * Nao valorado, nao dirigido
	 */
	public void buscaProfundidade(int v){
		output.clear();
		output.add("Busca profundidade");
		output.add("Visitação: ");
		nivel[0] = 0;
		buscaProfundidadeRec(v - 1);
	}
	
	/**
	 * Questao b2
	 */
	public void buscaProfundidadeRec(int v){
		jaFoi[v] = 1;
		int index = output.size() - 1;
		String row = output.get(index);
		output.set(index, row+(v + 1)+" ");
		for(int i = 0; i < n; i++){
			if(mArestas[v][i] == 1 && jaFoi[i] == 0){
				nivel[i] = nivel[v] + 1;
				buscaProfundidadeRec(i);
			}
		}
	}
	
	/**
	 * Questao c
	 * Nao valorado, nao dirigido
	 */
	public void componentesConexas(){
		int num_comp = 1;
		for(int v = 0; v < n; v++){
			if(componentes[v] == -1){
				componentesConexasBusca(v, num_comp++);
			}
		}
		
		// Escreve
		int[] aux;
		int k;
		output.clear();
		output.add("Componentes conexas");
		output.add("Número de componentes: "+(num_comp - 1));
		for(int i = 1; i <= componentes.length; i++){
			aux = new int[componentes.length];
			k = 0;
			for(int j = 0; j < componentes.length; j++){
				if(componentes[j] == i){
					aux[k] = j + 1;
					k++;
				}
			}
			if(k > 0){
				String row = "Componente "+i+": ";
				for (int c : aux) {
					if(c > 0){
						row = row.concat(String.valueOf(c)+" ");
					}
				}
				output.add(row);
			}
		}
	}
	
	/**
	 * Questao c
	 */
	public void componentesConexasBusca(int v, int num_comp){
		componentes[v] = num_comp;
		for(int i = 0; i < mArestas[v].length; i++){
			if(mArestas[v][i] == 1 && componentes[i] == -1){
				componentesConexasBusca(i, num_comp);
			}
		}
	}

	/**
	 * Questao d
	 * Nao valorado, nao dirigido
	 */
	public void componentesBiconexas(){
		// Escrever
		output.clear();
		output.add("Componentes biconexas");
		
		componentesBiconexasBusca(0);
		
	}
	
	public void componentesBiconexasBusca(int v){
		int[] aresta;
		Set<Integer> componente;
		lowpt[v] = num[v] = numCount++;
		for(int w = 0; w < mArestas[v].length; w++){
			if(mArestas[v][w] == 1){
				if(num[w] == 0){
					pai[w] = v;
					pilha.push(new int[]{v, w}); // inserir na pilha, v w
					componentesBiconexasBusca(w);
					if(lowpt[w] >= num[v]){
						// v é uma articulação e w é um demarcador;
						// Tw = elementos da pilha até (v,w)
						// Remover elementos de Tw da pilha P
						componente = new HashSet();
						while(!pilha.empty()){
							aresta = pilha.pop();
							componente.add(aresta[0]);
							componente.add(aresta[1]);
							if(aresta[0] == v && aresta[1] == w){
								break;
							}
						}
						String row = "Componente: ";
						for(Integer c : componente){
							row = row.concat(String.valueOf(c+1)+" ");
						}
						output.add(row);
					}
					lowpt[v] = Math.min(lowpt[v], lowpt[w]);
				}else{
					if(pai[v] != w){ // aresta de retorno
						lowpt[v] = Math.min(lowpt[v], num[w]);
					}
				}
			}
		}
	}

	/**
	 * Questao e
	 * Nao valorado, nao dirigido
	 */
	public void cicloEuleriano(){
		int[] ciclo = new int[((n*(n-1))/2)];
		int[] grau = new int[n];  // grau de cada vértice
		int[] cicloAux = new int[n]; // guarda ciclo auxiliar
		int i, j, hasCiclo = 1;
		for(i = 0; i < n; i++){
			grau[i] = 0;
			for(j = 0; j < n; j++) 
				grau[i] += mArestas[i][j];
			if(grau[i] % 2 != 0)
				hasCiclo = 0;
		}
		int v = 0, pos = 0, grauTotal = 0;
		if(hasCiclo == 1){
			for(i = 0; i < n; i++) 
				grauTotal += grau[i];
			
			while(grau[v] > 0){ // encontra o ciclo inicial
				ciclo[pos++] = v;        // acrescenta vértice no primeiro ciclo
				j = 0;
				for (j = 0; mArestas[v][j] == 0; j++){} // encontra próxima aresta não percorrida
				grau[v]--;
				grau[j]--;
				grauTotal -= 2;   // atualiza graus
				mArestas[v][j] = mArestas[j][v] = 0;     // elimina aresta
				v = j;
			}
			while(grauTotal > 0){ // enquanto há arestas não percorridas...
				j = 0;
				for(j = 0; grau[ciclo[j]] == 0; j++){} // procura primeiro vértice do ciclo 
				// já criado que ainda tem arestas a 
				// percorrer
				v = ciclo[j];
				int posAux = 0;
				int inicAux = j;
				while(grau[v] > 0){ // encontra ciclo auxiliar
					cicloAux[posAux++] = v;        // acrescenta vértice no primeiro ciclo
					j = 0;
					for(j = 0; mArestas[v][j] == 0; j++){} // encontra próxima aresta não percorrida
					grau[v]--;
					grau[j]--;
					grauTotal -= 2;   // atualiza graus
					mArestas[v][j] = mArestas[j][v] = 0;     // elimina aresta
					v = j;
				}
				// acrescenta ciclo auxiliar no ciclo original
				for(i = pos-1; i >= inicAux; i--) 
					ciclo[i+posAux] = ciclo[i]; // desloca vetor à direita
				for(i = 0;i < posAux; i++) 
					ciclo[inicAux+i] = cicloAux[i];
				pos += posAux;
			}
		}
		
		// Escreve
		output.clear();
		output.add("Ciclo euleriano");
		if(pos > 0){
			String row = "Ciclo: ";
			for(i = 0; i < pos; i++){
				row = row.concat(String.valueOf((ciclo[i] + 1)+" "));
			}
			output.add(row);
		}else{
			output.add("Sem ciclo");
		}
	}

	/**
	 * Questao f
	 * Valorado, dirigido ou nao
	 * Algoritmo guloso
	 * caixeiro viajante
	 */
	public void cicloHamiltoniano(){
		int[] ciclo = new int[n];
		int[] visitado = new int[n];
		int v = 0, i = 0, min, minV = 0; // vertice inicial
		for(i = 0; i < n; i++){
			ciclo[i] = v;
			visitado[v] = 1;
			
			// procura aresta de menor valor
			min = INF;
			for(int j = 0; j < n; j++){
				if(mValores[v][j] < min && visitado[j] == 0){
					min = mValores[v][j];
					minV = j;
				}
			}
			
			v = minV;
		}
		
		int visitados = 1;
		for(i = 0; i < n; i++){
			if(visitado[i] == 0){
				visitados = 0;
				break;
			}
		}
		
		// Escrever
		output.clear();
		output.add("Ciclo hamiltoniano");
		// se ultimo vertice visitado tiver ligacao com o primeiro
		// e se todos vertices foram visitados
		if(mValores[ciclo[n-1]][0] > 0 && visitados == 1){
			String row = "Ciclo: ";
			for(i = 0; i < ciclo.length; i++){
				row = row.concat(String.valueOf((ciclo[i] + 1)+" "));
			}
			output.add(row);
		}else{
			output.add("Sem ciclo");
		}
	}
	
	/**
	 * Questao g
	 * Algoritmo prim
	 * Valorado, nao dirigido
	 */
	public void arvoreGeradoraMinima(){
		int[][] t = new int[n][n];
		int[] vu = new int[n],
			vuu = new int[n],
			vv = new int[n],
			l = new int[n];
		int i, j, n = 0, min, v = 0, u = 0, fim;
		v = 0; // Vertice inicial
		vu[0] = 1;
		for(i = 0; i < mValores[v].length; i++){
			if(mValores[v][i] > 0){
				l[i] = mValores[v][i];
			}else{
				l[i] = INF;
			}
		}
		l[0] = 0;
		do{
			min = INF;
			for(i = 0; i < l.length; i++){
				if(vu[i] == 0 && l[i] < min){
					min = l[i];
					u = i;
				}
			}
			for(j = 0; j < mValores[u].length; j++){
				if(vu[j] == 0 && mValores[u][j] > 0 && mValores[u][j] < l[j]){
					l[j] = mValores[u][j];
					vv[j] = u;
				}
			}
			t[vv[u]][u] = min;
			t[u][vv[u]] = min;
			vu[u] = 1;
			vuu[n] = u;
			n++;
			
			// verifica se vu tem todos vertices
			fim = 1;
			for(i = 0; i < vu.length; i++){
				if(vu[i] == 0){
					fim = 0;
				}
			}
		}while(fim == 0 && n <= 10);
		
		// Escreve
		output.clear();
		output.add("Árvore geradora mínima");
		output.add("Valores:");
		String row;
		for(i = 0; i < t.length; i++){
			row = "";
			for(j = 0; j < t[i].length; j++){
				row = row.concat(String.valueOf(t[i][j])+" ");
			}
			output.add(row);
		}
	}

	/**
	 * Questao h
	 * Algoritmo Floyd-Warshall
	 * Valorado, nao dirigido
	 */
	public void distanciaTodosVertices(){
		int i, j, k;
		for(k = 0; k < n; ++k){
//			escreve();
			for(i = 0; i < n; ++i)
				for(j = 0; j < n; ++j)
					if((mValores[i][k] * mValores[k][j] != 0) && (i != j)) // se existir caminho entre i k e k j, e se i e j forem diferentes
						if((mValores[i][k] + mValores[k][j] < mValores[i][j]) || (mValores[i][j] == 0)) // ignora aresta com 0
							mValores[i][j] = mValores[i][k] + mValores[k][j];
		}
		
		// Escreve
		output.clear();
		output.add("Distância de todos vertices");
		output.add("Distâncias:");
		for(i = 0; i < mValores.length; i++){
			String row = "";
			for(j = 0; j < mValores[i].length; j++){
				row = row.concat(String.valueOf(mValores[i][j])+" ");
			}
			output.add(row);
		}
	}
	
	/**
	 * Escreve saida
	 */
	public void escreve(){
		for (String row : output) {
			System.out.println(row);
		}
		
//		System.out.println("Matriz");
//		for(int i = 0; i < n; i++){
//			for(int j = 0; j < n; j++){
//				if(mArestas != null){
//					System.out.print(mArestas[i][j]+" ");
//				}else{
//					System.out.print(mValores[i][j]+" ");
//				}
//			}
//			System.out.println("");
//		}
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getMArestas() {
		return mArestas;
	}

	public void setMArestas(int[][] mArestas) {
		this.mArestas = mArestas;
	}

	public int[][] getMValores() {
		return mValores;
	}

	public void setMValores(int[][] mValores) {
		this.mValores = mValores;
	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}

}
