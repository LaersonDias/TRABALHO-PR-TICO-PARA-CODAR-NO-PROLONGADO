/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author hp
 */
import java.time.*;
import java.util.*;

public class SuperMercado {
    private int tempoPorProduto;            
    private int proximoIdCliente;          
    private int proximoIdCaixa;             
    private List<Caixa> caixasDeAtendimento; 

    // Construtor completo (usado quando estamos a carregar os dados no centro de dados)
    public SuperMercado(int tempoPorProduto, int proximoIdCliente, int proximoIdCaixa) {
        this.tempoPorProduto = tempoPorProduto;
        this.proximoIdCliente = proximoIdCliente;
        this.proximoIdCaixa = proximoIdCaixa;
        this.caixasDeAtendimento = new LinkedList<>();
    }

    // Construtor que define automático o ID inicial dos clientes com base no ano
    public SuperMercado(int tempoPorProduto) {
        this.tempoPorProduto = tempoPorProduto;
        this.proximoIdCliente = LocalDate.now().getYear() * 10000; // Ex.: 2025xxxx
        this.proximoIdCaixa = 0;
        this.caixasDeAtendimento = new LinkedList<>();
    }
    
    // Mostra a fila de todos os caixas
    public void mostrarFilasNosCaixas() {
        if (caixasDeAtendimento.isEmpty())
            throw new IllegalStateException("Não existe nenhum caixa criado.");
        
        boolean tem = false;
        int i = 0;

        // Percorre todos os caixas para mostrar os clientes na fila
        while (i < caixasDeAtendimento.size()) {
            if (!caixasDeAtendimento.get(i).getFilaClientes().isEmpty()) {

                // Mostra dados gerais do caixa
                System.out.print("\n" + caixasDeAtendimento.get(i).toString());

                // Mostra cada cliente na fila desse caixa
                for (Cliente cliente : caixasDeAtendimento.get(i).getFilaClientes()) {
                    System.out.print("<-" + cliente.toString());
                }

                tem = true;
            }
            i++;
        }
        
        // Caso nenhuma fila tenha clientes
        if (!tem) System.out.println("\nNao ha filas em nenhum dos caixas.");
    }
    
    // Cria um cliente novo e adiciona ao caixa com menor fila
    public void criarCliente() {
        if (caixasDeAtendimento.isEmpty())
            throw new IllegalStateException("Não existe nenhum caixa criado.");
        
        // Cria cliente com ID sequencial e tempo calculado
        Cliente novoCliente = new Cliente(++proximoIdCliente, tempoPorProduto);

        // Encontra o caixa com menor fila
        Caixa caixaMenorFila = caixasDeAtendimento.get(0);
        for (Caixa caixa : caixasDeAtendimento) {
            if (caixa.getFilaClientes().size() < caixaMenorFila.getFilaClientes().size()) {
                caixaMenorFila = caixa;
            }
        }

        // Adiciona o cliente ao caixa selecionado
        caixaMenorFila.adicionarCliente(novoCliente);
        System.out.println("\nCliente criado e adicionado ao caixa com menor fila, com sucesso!");
    }
    
    // Adiciona um novo caixa ao supermercado
    public void adicionarCaixa() {
        Caixa novoCaixa = new Caixa(++proximoIdCaixa);
        caixasDeAtendimento.add(novoCaixa);
    }
    
    // Remove todos os caixas vazios
    public void retirarCaixa() {
        if (caixasDeAtendimento.isEmpty())
           throw new IllegalStateException("Não existe nenhum caixa criado.");
        
        boolean encontrou = false;
        
        Iterator<Caixa> caixas = caixasDeAtendimento.iterator();

        // Percorre removendo caixas sem nenhum cliente
        while (caixas.hasNext()) {
            Caixa caixa = caixas.next();
            if (caixa.getFilaClientes().isEmpty()) {
                caixas.remove();
                encontrou = true;
            }
        }
        
        // Se nenhum caixa vazio foi encontrado
        if (!encontrou)
            throw new NoSuchElementException("Nenhum caixa vazio para remover.");
    }
    
    // Faz todos os caixas atenderem por tempo
    public void atenderTodos(int tempo) {
        if (caixasDeAtendimento.isEmpty())
            throw new IllegalStateException("Não existe nenhum caixa criado.");
        
        if (tempo < 1)
            throw new IllegalStateException("O tempo deve ser maior que zero.");
        
        // Cada caixa atende o tempo indicado
        for (Caixa caixa : caixasDeAtendimento) {
            caixa.atenderTempo(tempo);
        }
    }

    // Getters e setters
    public List<Caixa> getCaixasDeAtendimento() {
        return caixasDeAtendimento;
    }

    public int getTempoPorProduto() {
        return tempoPorProduto;
    }

    public void setTempoPorProduto(int tempoPorProduto) {
        this.tempoPorProduto = tempoPorProduto;
    }

    public int getProximoIdCliente() {
        return proximoIdCliente;
    }

    public void setProximoIdCliente(int proximoIdCliente) {
        this.proximoIdCliente = proximoIdCliente;
    }

    public int getProximoIdCaixa() {
        return proximoIdCaixa;
    }

    public void setProximoIdCaixa(int proximoIdCaixa) {
        this.proximoIdCaixa = proximoIdCaixa;
    }
}
