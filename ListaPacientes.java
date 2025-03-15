public class ListaPacientes {
    private Paciente[] pacientes; // Vetor que armazena os pacientes cadastrados
    private int tamanho; // Quantidade atual de pacientes na lista

    // Construtor que inicializa a lista com uma capacidade específica
    public ListaPacientes(int capacidade) {
        pacientes = new Paciente[capacidade]; // Cria o vetor com a capacidade especificada
        tamanho = 0; // Inicializa o tamanho da lista como 0
    }

    // Método para adicionar um paciente à lista
    public void adicionar(Paciente p) {
        if (tamanho < pacientes.length) { // Verifica se ainda há espaço na lista
            pacientes[tamanho++] = p; // Adiciona o paciente ao vetor e incrementa o tamanho
        } else {
            System.out.println("Lista Cheia"); // Exibe mensagem caso a lista esteja cheia
        }
    }

    // Método para buscar um paciente pelo ID
    public Paciente buscar(int id) {
        for (int i = 0; i < tamanho; i++) { // Percorre a lista de pacientes
            if (pacientes[i].getId() == id) { // Compara os IDs
                return pacientes[i]; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null caso o paciente não seja encontrado
    }

    // Novo método: Buscar um paciente pelo nome
    public Paciente buscarPorNome(String nome) {
        for (int i = 0; i < tamanho; i++) { // Percorre a lista de pacientes
            if (pacientes[i].getNome().equalsIgnoreCase(nome)) { // Compara os nomes ignorando maiúsculas/minúsculas
                return pacientes[i]; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null caso o paciente não seja encontrado
    }

    // Novo método: Remover um paciente pelo ID
    public boolean remover(int id) {
        for (int i = 0; i < tamanho; i++) { // Percorre a lista de pacientes
            if (pacientes[i].getId() == id) { // Compara os IDs
                // Desloca os pacientes seguintes para preencher o espaço do paciente removido
                for (int j = i; j < tamanho - 1; j++) {
                    pacientes[j] = pacientes[j + 1];
                }
                pacientes[--tamanho] = null; // Diminui o tamanho e remove a referência ao último elemento
                return true; // Retorna true indicando que a remoção foi bem-sucedida
            }
        }
        return false; // Retorna false caso o paciente não seja encontrado
    }
}
