package inpetu.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva extends Entidade {
    private final Contato solicitante;
    private final Recurso recurso;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private int qtdPessoas;
    private String motivo;
    private StatusReserva status = StatusReserva.SOLICITADA;

    public Reserva(Contato solicitante, Recurso recurso, LocalDate data,
                   LocalTime horaInicio, LocalTime horaFim, int qtdPessoas, String motivo) {
        this.solicitante = solicitante;
        this.recurso = recurso;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.qtdPessoas = qtdPessoas;
        this.motivo = motivo;
    }

    public Contato getSolicitante() {
        return solicitante;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(int qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    /** Esta reserva (ativa) ocupa o recurso no periodo informado? Fim exclusivo. */
    public boolean temConflitoCom(int recursoId, LocalDate outraData,
                                  LocalTime outroInicio, LocalTime outroFim) {
        return status.ocupaAgenda()
                && recurso.getId() == recursoId
                && data.equals(outraData)
                && horaInicio.isBefore(outroFim)
                && outroInicio.isBefore(horaFim);
    }

    @Override
    public String toString() {
        return "Reserva{id=" + getId() + ", recurso='" + recurso.getNome()
                + "', solicitante='" + solicitante.getNome() + "', data=" + data
                + ", " + horaInicio + "-" + horaFim + ", pessoas=" + qtdPessoas
                + ", motivo='" + motivo + "', status=" + status + "}";
    }
}
