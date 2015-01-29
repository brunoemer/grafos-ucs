package trabalho;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class Window {

	private JFrame frame;
	private Import imp;
	private Algoritmos alg;
	private Object[] vertices;

	/**
	 * Create the application.
	 */
	public Window() {
		imp = new Import();
		alg = new Algoritmos();
		
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setTitle("Grafos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setAutoscrolls(true);
		splitPane.setToolTipText("");
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		final JTextPane textEntrada = new JTextPane() {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return (getSize().width < getParent().getSize().width);
			}
			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		textEntrada.setEditable(false);
		JScrollPane scrollEntrada = new JScrollPane(textEntrada);
		scrollEntrada.setAutoscrolls(true);
		splitPane.setLeftComponent(scrollEntrada);
		
		final JTextPane textSaida = new JTextPane() {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return (getSize().width < getParent().getSize().width);
			}
			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		textSaida.setEditable(false);
		JScrollPane scrollSaida = new JScrollPane(textSaida);
		scrollSaida.setAutoscrolls(true);
		splitPane.setRightComponent(scrollSaida);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("Dialog", Font.BOLD, 10));
		toolBar.setRollover(true);
		toolBar.setInheritsPopupMenu(true);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		frame.getContentPane().add(toolBar, BorderLayout.WEST);
		
		JButton btnImportar = new JButton("Importar arquivo");
		btnImportar.setMargin(new Insets(2, 2, 2, 2));
		btnImportar.setFont(new Font("Dialog", Font.BOLD, 10));
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					JFileChooser abrir = new JFileChooser();  
					int retorno = abrir.showOpenDialog(null);  
					if(retorno == JFileChooser.APPROVE_OPTION){
						String caminho = abrir.getSelectedFile().getAbsolutePath();
						imp.loadFile(caminho);
						
						if(imp.isValorado()){
							alg.setMValores(imp.getMat());
						}else{
							alg.setMArestas(imp.getMat());
						}
						alg.setN(imp.getN());
						alg.initVars();
	
						// Vertices disponiveis
						List<Integer> verticesAux = new ArrayList<Integer>();
						int[][] m = imp.getMat();
						for(int i = 0; i < m.length; i++){
							int tem = 0;
							for(int j = 0; j < m[i].length; j++){
								if(m[i][j] > 0 || m[j][i] > 0){
									tem = 1;
									break;
								}
							}
							if(tem == 1){
								verticesAux.add(i + 1); // Inicia em 1
							}
						}
						vertices = new Integer[verticesAux.size()];
						vertices = verticesAux.toArray();
						
						// Adiciona arquivo 
						appendPane(textEntrada, "Arquivo:");
						for (String row : imp.getOutput()) {
							appendPane(textEntrada, row);
						}
						// Adiciona matriz
						appendPane(textEntrada, "Matriz:");
						m = imp.getMat();
						for(int i = 0; i < m.length; i++){
							String row = "";
							for(int j = 0; j < m[i].length; j++){
								row = row.concat(String.valueOf(m[i][j])+" ");
							}
							appendPane(textEntrada, row);
						}
						appendPane(textEntrada, "");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao importar grafo");
				}
			}
		});
		toolBar.add(btnImportar);
		
		JButton btnResetar = new JButton("Resetar");
		btnResetar.setMargin(new Insets(2, 2, 2, 2));
		btnResetar.setFont(new Font("Dialog", Font.BOLD, 10));
		btnResetar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alg.initVars();
				textEntrada.setText("");
				textSaida.setText("");
			}
		});
		toolBar.add(btnResetar);
		
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(1, 1));
		separator.setMaximumSize(new Dimension(32767, 2));
		separator.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		separator.setPreferredSize(new Dimension(0, 1));
		toolBar.add(separator);
		
		JButton btnNmeroCromatico = new JButton("Número cromatico");
		btnNmeroCromatico.setMargin(new Insets(2, 2, 2, 2));
		btnNmeroCromatico.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNmeroCromatico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.numeroCromatico();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnNmeroCromatico);
		
		JButton btnBuscaAmplitude = new JButton("Busca amplitude");
		btnBuscaAmplitude.setMargin(new Insets(2, 2, 2, 2));
		btnBuscaAmplitude.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBuscaAmplitude.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Object input = JOptionPane.showInputDialog(null, "Escolha o vértice inicial", "", 
						JOptionPane.PLAIN_MESSAGE, null, vertices, vertices[0]);
					if(input != null){
						int v = (Integer) input;
						alg.buscaAmplitude(v);
						for (String row : alg.getOutput()) {
							appendPane(textSaida, row);
						}
						appendPane(textSaida, "");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnBuscaAmplitude);
		
		JButton btnBuscaProfundidade = new JButton("Busca profundidade");
		btnBuscaProfundidade.setMargin(new Insets(2, 2, 2, 2));
		btnBuscaProfundidade.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBuscaProfundidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Object input = JOptionPane.showInputDialog(null, "Escolha o vértice inicial", "", 
						JOptionPane.PLAIN_MESSAGE, null, vertices, vertices[0]);
					if(input != null){
						int v = (Integer) input;
						alg.buscaProfundidade(v);
						for (String row : alg.getOutput()) {
							appendPane(textSaida, row);
						}
						appendPane(textSaida, "");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			
			}
		});
		toolBar.add(btnBuscaProfundidade);
		
		JButton btnComponentesConexas = new JButton("Componentes conexas");
		btnComponentesConexas.setMargin(new Insets(2, 2, 2, 2));
		btnComponentesConexas.setFont(new Font("Dialog", Font.BOLD, 10));
		btnComponentesConexas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.componentesConexas();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnComponentesConexas);
		
		JButton btnComponentesBiconexas = new JButton("Componentes biconexas");
		btnComponentesBiconexas.setMargin(new Insets(2, 2, 2, 2));
		btnComponentesBiconexas.setFont(new Font("Dialog", Font.BOLD, 10));
		btnComponentesBiconexas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.componentesBiconexas();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnComponentesBiconexas);
		
		JButton btnCicloEuleriano = new JButton("Ciclo Euleriano");
		btnCicloEuleriano.setMargin(new Insets(2, 2, 2, 2));
		btnCicloEuleriano.setFont(new Font("Dialog", Font.BOLD, 10));
		btnCicloEuleriano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.cicloEuleriano();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnCicloEuleriano);
		
		JButton btnCicloHamiltoniano = new JButton("Ciclo hamiltoniano");
		btnCicloHamiltoniano.setMargin(new Insets(2, 2, 2, 2));
		btnCicloHamiltoniano.setFont(new Font("Dialog", Font.BOLD, 10));
		btnCicloHamiltoniano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.cicloHamiltoniano();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnCicloHamiltoniano);
		
		JButton btnArvoreGeradoraMinima = new JButton("Árvore geradora mínima");
		btnArvoreGeradoraMinima.setMargin(new Insets(2, 2, 2, 2));
		btnArvoreGeradoraMinima.setFont(new Font("Dialog", Font.BOLD, 10));
		btnArvoreGeradoraMinima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.arvoreGeradoraMinima();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnArvoreGeradoraMinima);
		
		JButton btnDistanciaVertices = new JButton("Distância vertices");
		btnDistanciaVertices.setMargin(new Insets(2, 2, 2, 2));
		btnDistanciaVertices.setFont(new Font("Dialog", Font.BOLD, 10));
		btnDistanciaVertices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					alg.distanciaTodosVertices();
					for (String row : alg.getOutput()) {
						appendPane(textSaida, row);
					}
					appendPane(textSaida, "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro grafo inválido");
				}
			}
		});
		toolBar.add(btnDistanciaVertices);
		
	}
	
	public void appendPane(JTextPane textPane, String text){
		textPane.setText(textPane.getText()+text+"\n");
	}
}
