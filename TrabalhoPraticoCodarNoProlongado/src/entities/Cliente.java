/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author hp
 */

public class Cliente {
    private int idCliente;
    private int qtdProdutos;
    private long tempoTotalDoCliente;

    // Construtor completo (usado quando estamos a carregar os dados no centro de dados)
    public Cliente(int idCliente, int qtdProdutos, long tempoTotalDoCliente) {
        this.idCliente = idCliente;                   
        this.qtdProdutos = qtdProdutos;               
        this.tempoTotalDoCliente = tempoTotalDoCliente; 
    }

    // Construtor usado quando queremos gerar a quantidade de produtos aleatoriamente
    public Cliente(int idCliente, int tempoPorProduto) {
        this.idCliente = idCliente;  

        // Gera um número aleatório de produtos entre 2 e 120)
        this.qtdProdutos = (int)(Math.random() * (119)) + 2;

        this.tempoTotalDoCliente = qtdProdutos * tempoPorProduto;
    }

    public long getTempoTotalDoCliente() {
        return tempoTotalDoCliente;
    }

    public int getId() {
        return idCliente;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setQtdProdutos(int qtdProdutos) {
        this.qtdProdutos = qtdProdutos;
    }

    // Altera o tempo total do cliente
    public void setTempoTotalDoCliente(long tempoTotalDoCliente) {
        this.tempoTotalDoCliente = tempoTotalDoCliente;
    }

    // Representação em String do cliente
    @Override
    public String toString() {
        return "Cliente " + idCliente + ", " + qtdProdutos + " Produtos";
    }
}
