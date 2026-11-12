package inpetu.model;

public enum StatusReserva {
    SOLICITADA(true),
    APROVADA(true),
    RECUSADA(false),
    CANCELADA(false);

    private final boolean ocupaAgenda;

    StatusReserva(boolean ocupaAgenda) {
        this.ocupaAgenda = ocupaAgenda;
    }

    /** Reservas ativas bloqueiam o horario do recurso. */
    public boolean ocupaAgenda() {
        return ocupaAgenda;
    }
}
