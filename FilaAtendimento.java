public class FilaAtendimento {
    private Paciente[] fila;  // Vetor para armazenar os pacientes
    private int tamanho;      // Número de pacientes na fila
    private int inicio;       // Índice do primeiro paciente na fila
    private int fim;          // Índice onde o próximo paciente será inserido
    private String[] historico;  // Vetor para armazenar o histórico de operações
    private int historicoTamanho;  // Quantidade de operações registradas no histórico

    // ✅ Construtor que inicializa a fila de atendimento
    public FilaAtendimento(int capacidade) {
        fila = new Paciente[capacidade];   // Cria a fila com o tamanho fornecido
        historico = new String[capacidade]; // Cria o vetor para armazenar histórico
        tamanho = 0;   // Inicializa a fila vazia
        inicio = 0;    // O primeiro paciente estará no índice 0
        fim = 0;       // O próximo paciente será inserido no índice 0
        historicoTamanho = 0;  // Histórico começa vazio
    }

    // ✅ Método para adicionar um paciente à fila com prioridade
    public void entrarNaFila(Paciente p) {
        if (tamanho >= fila.length) {  // Verifica se a fila está cheia
            System.out.println("Fila cheia! Não é possível adicionar o paciente.");
            return;
        }

        // 🔹 Se a fila estiver vazia, apenas adiciona o paciente normalmente
        if (tamanho == 0) {
            fila[fim] = p;  // Adiciona o primeiro paciente
        } else {
            // 🔹 Encontrar a posição correta para inserir baseado na prioridade
            int posicaoInsercao = fim; // Inicialmente, a posição é o último índice
            int i = tamanho;  // Começamos a verificar a partir do último paciente

            while (i > 0) {  // Percorre os elementos de trás para frente
                int posicaoAnterior = (inicio + i - 1) % fila.length; // Índice do paciente anterior
                Paciente pacienteAnterior = fila[posicaoAnterior]; // Pega o paciente na posição

                // 🔥 Se o novo paciente tem prioridade maior, deslocamos os outros para frente
                if (p.isEmergencia() ||
                        (p.getIdade() >= 60 && !pacienteAnterior.isEmergencia())) {
                    fila[posicaoInsercao] = pacienteAnterior; // Desloca o paciente para frente
                    posicaoInsercao = posicaoAnterior; // Atualiza posição para inserir o novo paciente
                } else {
                    break; // Sai do loop quando encontra a posição correta
                }
                i--; // Decrementa o índice para continuar percorrendo a fila
            }

            // 🔹 Insere o paciente na posição correta da fila
            fila[posicaoInsercao] = p;
        }

        // Atualiza os índices da fila
        fim = (fim + 1) % fila.length; // Atualiza o índice de fim (fila circular)
        tamanho++; // Aumenta o tamanho da fila
        registrarHistorico(p.getNome(), "ENTRADA"); // Registra a operação no histórico
    }

    // ✅ Método para chamar um paciente da fila
    public Paciente chamarPaciente() {
        if (tamanho == 0) {  // Se a fila estiver vazia, não há ninguém para chamar
            System.out.println("Não há pacientes na fila.");
            return null;
        }
        Paciente p = fila[inicio];  // Pega o paciente que está na primeira posição (prioridade)
        inicio = (inicio + 1) % fila.length; // Atualiza o índice de início (fila circular)
        tamanho--; // Reduz o número de pacientes na fila
        registrarHistorico(p.getNome(), "CHAMADO"); // Registra a operação no histórico
        return p;  // Retorna o paciente chamado
    }

    // ✅ Método para exibir todos os pacientes da fila
    public void exibirFila() {
        if (tamanho == 0) {  // Verifica se a fila está vazia
            System.out.println("Nenhum paciente na fila.");
            return;
        }
        System.out.println("\n--- Pacientes na Fila de Atendimento ---");
        for (int i = 0; i < tamanho; i++) {
            int posicao = (inicio + i) % fila.length;  // Calcula a posição correta na fila circular
            Paciente p = fila[posicao];  // Obtém o paciente na posição calculada
            String tipoAtendimento = p.isEmergencia() ? "🚨 Emergência" :
                    (p.getIdade() >= 60 ? "👴 Prioridade" : "Paciente comum"); // Define a prioridade
            System.out.println((i + 1) + ". " + p.getNome() + " (Idade: " + p.getIdade() + ") - " + tipoAtendimento);
        }
    }

    // ✅ Método para registrar operações no histórico
    private void registrarHistorico(String nomePaciente, String operacao) {
        if (historicoTamanho < historico.length) { // Se o histórico não estiver cheio
            historico[historicoTamanho] = "Paciente: " + nomePaciente + " - Operação: " + operacao; // Salva a operação
            historicoTamanho++; // Incrementa o número de operações registradas
        }
    }

    // ✅ Método para exibir o histórico de operações realizadas
    public void exibirHistorico() {
        if (historicoTamanho == 0) { // Se não houver registros
            System.out.println("Nenhuma operação no histórico.");
        } else {
            System.out.println("\n--- Histórico de Operações ---");
            for (int i = 0; i < historicoTamanho; i++) {
                System.out.println((i + 1) + ". " + historico[i]); // Exibe cada operação registrada
            }
        }
    }
}

