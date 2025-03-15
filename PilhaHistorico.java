public class PilhaHistorico {
    private String[] historico;  // Atributo que armazena o histórico de operações, usando um vetor de Strings
    private int topo;            // Atributo que armazena o índice do topo da pilha (última operação adicionada)

    // Construtor que inicializa o histórico com uma capacidade especificada e define o topo como -1 (indicando que a pilha está vazia)
    public PilhaHistorico(int capacidade) {
        historico = new String[capacidade];  // Cria o vetor 'historico' com a capacidade fornecida
        topo = -1;                           // Inicializa o topo como -1, indicando que a pilha está vazia
    }

    // Método para registrar uma operação no histórico
    public void registrarAtendimento(String operacao) {
        if (topo < historico.length - 1) {   // Verifica se ainda há espaço na pilha para adicionar uma nova operação
            historico[++topo] = operacao;    // Adiciona a operação no próximo espaço disponível (incrementando o topo antes)
        } else {
            System.out.println("Histórico cheio, não é possível adicionar mais operações.");
            // Se a pilha estiver cheia (topo alcançou o limite do vetor), imprime uma mensagem de erro
        }
    }

    // Método para desfazer a última operação registrada no histórico
    public String desfazerUltimaOperacao() {
        if (topo >= 0) {                       // Verifica se há operações na pilha para desfazer
            return historico[topo--];          // Retorna a última operação (topo) e decrementa o topo
        }
        return null;                           // Se a pilha estiver vazia (não há operações para desfazer), retorna null
    }
}
