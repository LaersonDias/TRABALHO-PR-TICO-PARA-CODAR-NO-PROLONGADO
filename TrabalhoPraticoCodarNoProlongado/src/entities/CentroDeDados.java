/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.*;

/**
 *
 * @author hp
 */
import java.util.*;

public class CentroDeDados {

        // Nome do ficheiro onde os dados serão gravados
        private final String nomeFicheiro = "superMercado.txt";
        
        // Guarda no ficheiro todas as informações do objeto SuperMercado
        public void guardarDados (SuperMercado superMercado){
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(nomeFicheiro))) {
                
                // Cabeçalho do ficheiro
                printWriter.println("SUPERMERCADO");
                
                // Dados gerais do supermercado
                printWriter.println("TEMPO_POR_PRODUTO," + superMercado.getTempoPorProduto());
                printWriter.println("PROXIMO_ID_CLIENTE," + superMercado.getProximoIdCliente());
                printWriter.println("PROXIMO_ID_CAIXA," + superMercado.getProximoIdCaixa());
                printWriter.println("CAIXAS," + superMercado.getCaixasDeAtendimento().size());
                
                // Para cada caixa, escreve seus dados
                for (Caixa caixa : superMercado.getCaixasDeAtendimento()) {
                    printWriter.println("CAIXA");
                    printWriter.println("CAIXA_N," + caixa.getN());
                    printWriter.println("CAIXA_ID," + caixa.getIdCaixa());
                    printWriter.println("CAIXA_CLIENTES_ATENDIDOS," + caixa.getClientesAtendidos());
                    printWriter.println("TEMPO_TOTAL_ATENDIMENTO," + caixa.getTempoTotalAtendimento());
                    printWriter.println("TEMPO_MEDIO_ATENDIMENTO_DE_UM_CLIENTE," + caixa.getTempoMedioAtendimentoDeUmCliente());
                    printWriter.println("TEMPO_RESTANTE_CLIENTE_TOPO," + caixa.getTempoRestanteClienteTopo());
                    printWriter.println("CLIENTES," + caixa.getFilaClientes().size());
                    
                    // Para cada cliente presente no caixa, gravar os seus dados
                    for (Cliente cliente : caixa.getFilaClientes()) {
                        printWriter.println("CLIENTE");
                        printWriter.println("CLIENTE_ID," + cliente.getId());
                        printWriter.println("CLIENTE_QTD_PRODUTOS," + cliente.getQtdProdutos());
                        printWriter.println("CLIENTE_TEMPO_TOTAL," + cliente.getTempoTotalDoCliente());
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Erro ao guardar os dados: " + e.getMessage());
            }
        }
        
        // Carrega os dados a partir do ficheiro e devolve um objeto SuperMercado reconstruído
        public SuperMercado carregarDados () {
            
            File file = new File(nomeFicheiro);
            
            // Caso o ficheiro ainda não exista, retornar null
            if (!file.exists()) return null;
            
            try (Scanner input = new Scanner(file)) {
                
                // Ignora a primeira linha "SUPERMERCADO"
                input.nextLine();
                
                // Lê dados gerais do supermercado
                int tempoPorProduto = Integer.parseInt(input.nextLine().split(",")[1]);
                int proximoIdCliente = Integer.parseInt(input.nextLine().split(",")[1]);
                int proximoIdCaixa = Integer.parseInt(input.nextLine().split(",")[1]);
                
                // Cria o supermercado com os dados lidos
                SuperMercado superMercado = new SuperMercado(tempoPorProduto, proximoIdCliente, proximoIdCaixa);
                
                // Lê quantidade de caixas existentes
                int qtdCaixas = Integer.parseInt(input.nextLine().split(",")[1]);
                
                // Carrega cada caixa
                for (int i = 0; i < qtdCaixas; i++) {
                    
                    input.nextLine(); // Linha "CAIXA"
                    
                    // Lê atributos do caixa
                    int n = Integer.parseInt(input.nextLine().split(",")[1]);
                    int idCaixa = Integer.parseInt(input.nextLine().split(",")[1]);
                    int clientesAtendidos = Integer.parseInt(input.nextLine().split(",")[1]);
                    long tempoTotalAtendimento = Integer.parseInt(input.nextLine().split(",")[1]);
                    double tempoMedioAtendimentoDeUmCliente = Integer.parseInt(input.nextLine().split(",")[1]);
                    long tempoRestanteClienteTopo = Integer.parseInt(input.nextLine().split(",")[1]);
                    
                    // Cria o caixa carregado
                    Caixa novoCaixa = new Caixa(
                        n,
                        idCaixa,
                        clientesAtendidos,
                        tempoTotalAtendimento,
                        tempoMedioAtendimentoDeUmCliente,
                        tempoRestanteClienteTopo
                    );
                    
                    // Lê quantidade de clientes na fila
                    int qtdClientes = Integer.parseInt(input.nextLine().split(",")[1]);
                    
                    // Carrega cada cliente
                    for (int k = 0; k < qtdClientes; k++) {
                        input.nextLine(); // Linha "CLIENTE"
                        
                        int idCliente = Integer.parseInt(input.nextLine().split(",")[1]);
                        int qtdProdutos = Integer.parseInt(input.nextLine().split(",")[1]);
                        long tempoTotalDoCliente = Integer.parseInt(input.nextLine().split(",")[1]);
                        
                        // Reconstrói o cliente
                        Cliente novoCliente = new Cliente(idCliente, qtdProdutos, tempoTotalDoCliente);
                        
                        // Adiciona o cliente ao caixa carregado
                        novoCaixa.getFilaClientes().add(novoCliente);
                    }
                    
                    // Adiciona o caixa ao supermercado
                    superMercado.getCaixasDeAtendimento().add(novoCaixa);
                }
                // Dados carregados com sucesso
                return superMercado; 
                
            } catch (Exception e) {
                System.out.println("Erro ao carregar dados: " + e.getMessage());
                return null;
            }
        }
}
