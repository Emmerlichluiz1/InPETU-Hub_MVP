package inpetu.model;

public enum TipoRecurso {
    SALA_REUNIAO("Sala de reuniao"),
    AUDITORIO("Auditorio"),
    EQUIPAMENTO("Equipamento"),
    VISITA_TECNICA("Visita tecnica");

    private final String descricao;

    TipoRecurso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
