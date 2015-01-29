package trabalho;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Import {
	private int n;
	private int[][] mat;
	private boolean valorado;
	private boolean dirigido;
	private List<String> output;
	
	public Import() {
		dirigido = true;
		output = new ArrayList<String>();
	}
	
	public void loadFile(String path){
//		path = "/home/bruno/workspace/grafos/src/trabalho/files/k3.txt";
		output.clear();
		FileReader reader;
		BufferedReader buffer;
		try {
			reader = new FileReader(path);
			buffer = new BufferedReader(reader);
			String row;
			
			// Primeira linha com o numero de vertices
			row = buffer.readLine();
			n = Integer.parseInt(row);
			mat = new int[n][n];
			output.add(String.valueOf(n));
			
			String[] rowArr;
			int grau, valor = 0, i = 0, j = 0, k = 0;
			while((row = buffer.readLine()) != null && i < n){
				output.add(row);
				rowArr = row.split("\\ ");
				
				// Verifica se eh valorado
				grau = Integer.parseInt(rowArr[0]);
				if(grau == rowArr.length - 1){
					valorado = false;
					valor = 1;
				}else{
					valorado = true;
				}
				
				j = 0;
				for(k = 1; k < rowArr.length; k++){
					j = Integer.parseInt(rowArr[k]) - 1;
					// Verifica se eh valorado, se for pega proximo numero como valor da aresta
					if(valorado){
						k++;
						valor = Integer.parseInt(rowArr[k]);
					}
					
					mat[i][j] = valor;
					if(!dirigido){
						mat[j][i] = valor;
					}
				}
				
				i++;
			}
			if(i > n){
				throw new Exception("numero de vertices invalido");
			}
			
			buffer.close();
			reader.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao importar arquivo: "+e.getMessage());
		}
	}

	public void escreve(){
		System.out.println("Arquivo:");
		for (String row : output) {
			System.out.println(row);
		}
		
		System.out.println("Matriz:");
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				System.out.print(mat[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getMat() {
		return mat;
	}

	public void setMat(int[][] mat) {
		this.mat = mat;
	}

	public boolean isValorado() {
		return valorado;
	}

	public void setValorado(boolean valorado) {
		this.valorado = valorado;
	}

	public boolean isDirigido() {
		return dirigido;
	}

	public void setDirigido(boolean dirigido) {
		this.dirigido = dirigido;
	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}

}
