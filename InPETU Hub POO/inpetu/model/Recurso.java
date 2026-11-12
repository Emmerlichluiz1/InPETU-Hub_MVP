package inpetu.model;

public class Recurso extends Entidade {
    private String nome;
    private TipoRecurso tipo;
    private int capacidade;
    private String descricao;

    public Recurso(String nome, TipoRecurso tipo, int capacidade, String descricao) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Recurso{id=" + getId() + ", nome='" + nome + "', tipo=" + tipo.getDescricao()
                + ", capacidade=" + capacidade + ", descricao='" + descricao + "'}";
    }
}
