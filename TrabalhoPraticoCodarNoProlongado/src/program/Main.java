/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package program;

/**
 *
 * @author hp
 */
import java.util.*;
import entities.*;

public class Main {
    public static void main(String[] args) {
        // Criação do scanner para entrada de dados
        Scanner input = new Scanner(System.in);
        int escolha, tempoPorProduto, qtdCaixas;

        // Instancia o centro de dados para carregar/salvar informações
        CentroDeDados centroDados = new CentroDeDados();

        System.out.println("-------------------PROGRAMA---INICIADO--------------------------");

        // Tenta carregar o supermercado salvo anteriormente
        SuperMercado superMercado = centroDados.carregarDados();

        // Caso não exista dados salvos, solicita informações iniciais ao usuário
        if (superMercado == null) {
            System.out.print("Determine o tempo de atendimento de um produto: ");
            tempoPorProduto = input.nextInt();

            System.out.print("Numero de caixas de atendimento para inicio da simulacao? ");
            qtdCaixas = input.nextInt();

            // Cria o supermercado com o tempo definido
            superMercado = new SuperMercado(tempoPorProduto);

            // Adiciona o número inicial de caixas
            for (int i = 0; i < qtdCaixas; i++) superMercado.adicionarCaixa();
        }

        // Loop principal do programa
        do {
            System.out.println("-------------------MENU--PRINCIPAL--------------------");
            System.out.println("1.MANUAL;");
            System.out.println("2.AUTOMATICO;");
            System.out.println("3.ENCERRAR O PROGRAMA.");

            // Garante que o usuário insira uma opção válida
            do {
                System.out.print("SELECIONE UMA DAS OPCOES ACIMA:");
                escolha = input.nextInt();
            } while (escolha < 1 || escolha > 3);

            // Escolha do usuário no menu principal
            switch (escolha) {

                case 1 -> { // MODO MANUAL
                    System.out.println("--------------------MODO-MANUAL--------------------");

                    // Loop do menu manual
                    do {
                        System.out.println("\n------------MENU--------------");
                        System.out.println("1.MOSTRAR AS FILAS NOS CAIXAS;");
                        System.out.println("2.CRIAR CLIENTE;");
                        System.out.println("3.ADICIONAR CAIXA;");
                        System.out.println("4.RETIRAR CAIXA DE ATENDIMENTO;");
                        System.out.println("5.ATENDER T TEMPO;");
                        System.out.println("6.VOLTAR AO MENU PRINCIPAL.");

                        // Garante opção válida
                        do {
                            System.out.print("SELECIONE UMA DAS OPCOES ACIMA");
                            escolha = input.nextInt();
                        } while (escolha < 1 || escolha > 6);

                        // Ações do menu manual
                        switch (escolha) {
                            // Mostrar filas
                            case 1 -> { 
                                try {
                                    superMercado.mostrarFilasNosCaixas();
                                } catch (IllegalStateException e) {
                                    System.out.println("Erro: " + e.getLocalizedMessage());
                                }
                            }
                            // Criar cliente
                            case 2 -> { 
                                try {
                                    superMercado.criarCliente();
                                } catch (IllegalStateException e) {
                                    System.out.println("Erro: " + e.getLocalizedMessage());
                                }
                            }
                            // Adicionar caixa
                            case 3 -> superMercado.adicionarCaixa(); 
                            // Retirar caixa
                            case 4 -> { 
                                try {
                                    superMercado.retirarCaixa();
                                } catch (IllegalStateException | NoSuchElementException e) {
                                    System.out.println("Erro: " + e.getLocalizedMessage());
                                }
                            }
                            // Atender por tempo informado
                            case 5 -> { 
                                try {
                                    System.out.print("Digite um valor de tempo de atendimento: ");
                                    int tempo = input.nextInt();
                                    superMercado.atenderTodos(tempo);
                                } catch (IllegalStateException e) {
                                    System.out.println("Erro: " + e.getLocalizedMessage());
                                }
                            }

                            default -> System.out.println("\nOK! INDO AO MENU PRINCIPAL...\n");
                        }

                    } while (escolha != 6); // Voltar ao menu principal
                }

                case 2 -> { // MODO AUTOMÁTICO
                    System.out.println("--------------------MODO-AUTOMATICO--------------------");

                    
                    System.out.print("Intervalo de tempo maximo entre clientes? ");
                    int intervaloTempo = input.nextInt();

                    // Loop de execução automática
                    do {
                        try {
                            superMercado.mostrarFilasNosCaixas();
                            System.out.println();

                            // Gera tempo aleatório de atendimento
                            int x = (int) (Math.random() * (intervaloTempo)) + 1;

                            // Cria um cliente automaticamente
                            superMercado.criarCliente();

                            System.out.println("\nATENDE " + x + "s.");

                            // Atende todos por x segundos
                            superMercado.atenderTodos(x);

                        } catch (NoSuchElementException | IllegalStateException e) {
                            System.out.println("Erro: " + e.getLocalizedMessage());
                        }

                        // Verifica se o usuário quer parar ou continuar
                        do {
                            System.out.println("Para parar a execucao em modo automatico, digite: 0, para continuar digite: 1");
                            escolha = input.nextInt();
                        } while (escolha != 0 && escolha != 1);

                    } while (escolha != 0); // Continua até o usuário parar
                }
                // ENCERRAR PROGRAMA
                default -> {
                    System.out.println("OK! ENCERRANDO O PROGRAMA...");
                    centroDados.guardarDados(superMercado); // Salva dados
                }
            }

        } while (escolha == 6 || escolha == 0); // Repetição de menus
    }
}
