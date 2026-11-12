package inpetu.model;

public class Contato extends Entidade {
    private String nome;
    private String email;
    private String telefone;
    private String empresa;
    private String motivoVisita;

    /** Construtor antigo mantido para compatibilidade (empresa e motivo ficam vazios). */
    public Contato(String nome, String email, String telefone) {
        this(nome, email, telefone, "", "");
    }

    public Contato(String nome, String email, String telefone,
                   String empresa, String motivoVisita) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.empresa = empresa;
        this.motivoVisita = motivoVisita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    @Override
    public String toString() {
        return "Contato{id=" + getId() + ", nome='" + nome + "', email='" + email
                + "', telefone='" + telefone + "', empresa='" + empresa
                + "', motivoVisita='" + motivoVisita + "'}";
    }
}
