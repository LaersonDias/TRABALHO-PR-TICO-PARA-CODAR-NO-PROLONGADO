/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author hp
 */
import java.util.*;

public class Caixa {
    private int n;                                      
    private int idCaixa;                                
    private int clientesAtendidos;                      
    private long tempoTotalAtendimento;                 
    private double tempoMedioAtendimentoDeUmCliente;    
    private long tempoRestanteClienteTopo;              
    private Queue<Cliente> filaClientes;                

    // Construtor completo (usado quando estamos a carregar os dados no centro de dados)
    public Caixa(int n, int idCaixa, int clientesAtendidos, long tempoTotalAtendimento,
                 double tempoMedioAtendimentoDeUmCliente, long tempoRestanteClienteTopo) {

        this.n = n;
        this.idCaixa = idCaixa;
        this.clientesAtendidos = clientesAtendidos;
        this.tempoTotalAtendimento = tempoTotalAtendimento;
        this.tempoMedioAtendimentoDeUmCliente = tempoMedioAtendimentoDeUmCliente;
        this.tempoRestanteClienteTopo = tempoRestanteClienteTopo;
        this.filaClientes = new LinkedList<>();
    }

    // Construtor básico (inicia apenas com o id do caixa)
    public Caixa(int idCaixa) {
        this.idCaixa = idCaixa;
        this.filaClientes = new LinkedList<>();
        this.n = 0; // n começa em 0 porque ainda não há médias calculadas
    }

    // Adiciona um cliente à fila
    public void adicionarCliente(Cliente novoCliente) {

        // Se a fila estiver vazia, este será o cliente do topo e definimos o tempo restante
        if (filaClientes.isEmpty())
            this.tempoRestanteClienteTopo = novoCliente.getTempoTotalDoCliente();
        
        filaClientes.add(novoCliente); // Adiciona o cliente ao fim da fila
    } 
    
    // Processa o atendimento por um determinado tempo
    public void atenderTempo(long tempo) {

        if (filaClientes.isEmpty()) return;   // Se não há clientes, nada para fazer
        
        // Caso 1: ainda falta mais tempo do que o passado → só subtrai
        if (tempo < this.tempoRestanteClienteTopo) {
            this.tempoTotalAtendimento += tempo;
            this.tempoRestanteClienteTopo -= tempo;
        }

        // Caso 2: tempo exato para concluir o cliente atual
        else if (tempo == this.tempoRestanteClienteTopo) {

            this.clientesAtendidos++;                 // Cliente concluído
            this.tempoTotalAtendimento += tempo;      // Soma ao total
            
            // Atualiza média de atendimento
            this.tempoMedioAtendimentoDeUmCliente *= n;
            this.tempoMedioAtendimentoDeUmCliente += filaClientes.peek().getTempoTotalDoCliente();
            this.tempoMedioAtendimentoDeUmCliente /= ++n;

            filaClientes.remove(); // Remove cliente concluído
            
            // Se ainda houver clientes, define tempo restante do próximo
            if (!filaClientes.isEmpty())
                this.tempoRestanteClienteTopo = filaClientes.peek().getTempoTotalDoCliente();
        }

        // Caso 3: tempo maior que o necessário → termina cliente e continua recursivamente
        else {
            this.clientesAtendidos++;
            this.tempoTotalAtendimento += tempo;
            
            // Atualiza média
            this.tempoMedioAtendimentoDeUmCliente *= n;
            this.tempoMedioAtendimentoDeUmCliente += filaClientes.peek().getTempoTotalDoCliente();
            this.tempoMedioAtendimentoDeUmCliente /= ++n;

            tempo -= this.tempoRestanteClienteTopo;  // Remove o tempo usado para terminar este cliente
            filaClientes.remove();                   // Cliente saiu

            if (!filaClientes.isEmpty())
                this.tempoRestanteClienteTopo = filaClientes.peek().getTempoTotalDoCliente();
            
            // Chama de novo para gastar o tempo restante
            atenderTempo(tempo);
        }
    }
    // Getters e setters
    public int getN() {
        return n;
    }

    public int getIdCaixa() {
        return idCaixa;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public long getTempoTotalAtendimento() {
        return tempoTotalAtendimento;
    }

    public double getTempoMedioAtendimentoDeUmCliente() {
        return tempoMedioAtendimentoDeUmCliente;
    }

    public long getTempoRestanteClienteTopo() {
        return tempoRestanteClienteTopo;
    }

    public Queue<Cliente> getFilaClientes() {
        return filaClientes;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setIdCaixa(int idCaixa) {
        this.idCaixa = idCaixa;
    }

    public void setClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }

    public void setTempoTotalAtendimento(long tempoTotalAtendimento) {
        this.tempoTotalAtendimento = tempoTotalAtendimento;
    }

    public void setTempoMedioAtendimentoDeUmCliente(double tempoMedioAtendimentoDeUmCliente) {
        this.tempoMedioAtendimentoDeUmCliente = tempoMedioAtendimentoDeUmCliente;
    }

    public void setTempoRestanteClienteTopo(long tempoRestanteClienteTopo) {
        this.tempoRestanteClienteTopo = tempoRestanteClienteTopo;
    }
    // Representação em String do caixa
    @Override
    public String toString() {
        return "\nCaixa : " + idCaixa 
        + "\nClientes na fila : " + filaClientes.size() 
        + "\n Tempo restante para atender o cliente topo :" + tempoRestanteClienteTopo 
        + "\nClientes atendidos : " + clientesAtendidos 
        + "\n Tempo total atendimento :" + tempoTotalAtendimento
        + "\nTempo medio atendimento por cliente :" + tempoMedioAtendimentoDeUmCliente + "\nfila de clientes:";
    }
}
