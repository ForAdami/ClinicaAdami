public class Paciente {
    private String nome;             // Atributo que armazena o nome do paciente
    private int id;                  // Atributo que armazena o identificador único (ID) do paciente
    private int idade;               // Atributo que armazena a idade do paciente
    private String motivoConsulta;   // Atributo que armazena o motivo da consulta do paciente
    private boolean emergencia;      // Atributo booleano que indica se o paciente está em uma situação de emergência

    // Construtor que inicializa os atributos do paciente com os valores fornecidos
    public Paciente(String nome, int id, int idade, String motivoConsulta, boolean emergencia) {
        this.nome = nome;                     // Inicializa o atributo nome com o valor fornecido
        this.id = id;                         // Inicializa o atributo id com o valor fornecido
        this.idade = idade;                   // Inicializa o atributo idade com o valor fornecido
        this.motivoConsulta = motivoConsulta;  // Inicializa o atributo motivoConsulta com o valor fornecido
        this.emergencia = emergencia;         // Inicializa o atributo emergencia com o valor fornecido
    }

    // Getters e setters para acessar e modificar os atributos da classe Paciente

    public String getNome() { return nome; }               // Retorna o nome do paciente
    public int getId() { return id; }                       // Retorna o ID do paciente
    public int getIdade() { return idade; }                 // Retorna a idade do paciente
    public String getMotivoConsulta() { return motivoConsulta; }  // Retorna o motivo da consulta do paciente
    public boolean isEmergencia() { return emergencia; }   // Retorna se o paciente é uma emergência ou não

    // Sobrescreve o método equals da classe Object para comparar dois pacientes com base na ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                 // Se os dois objetos são o mesmo (mesma referência), retorna true
        if (obj == null || getClass() != obj.getClass()) return false;  // Se o objeto é nulo ou não é da mesma classe, retorna false
        Paciente paciente = (Paciente) obj;           // Faz o cast de obj para um objeto da classe Paciente
        return id == paciente.id;                     // Compara o ID dos dois pacientes e retorna true se forem iguais
    }

    // Sobrescreve o método hashCode da classe Object para gerar um código hash baseado na ID
    @Override
    public int hashCode() {
        return Integer.hashCode(id);  // Retorna o código hash do ID do paciente
    }
}
