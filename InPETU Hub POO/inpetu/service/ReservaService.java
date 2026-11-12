package inpetu.service;

import inpetu.exception.EntidadeNaoEncontradaException;
import inpetu.exception.RegraNegocioException;
import inpetu.exception.ValidacaoException;
import inpetu.model.Contato;
import inpetu.model.Recurso;
import inpetu.model.Reserva;
import inpetu.model.StatusReserva;
import inpetu.repository.IReservaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaService implements IReservaService {

    private final IReservaRepository repository;
    private final IContatoService contatoService;
    private final IRecursoService recursoService;

    public ReservaService(IReservaRepository repository,
                          IContatoService contatoService,
                          IRecursoService recursoService) {
        this.repository = repository;
        this.contatoService = contatoService;
        this.recursoService = recursoService;
    }

    @Override
    public Reserva solicitar(int contatoId, int recursoId, LocalDate data,
                             LocalTime horaInicio, LocalTime horaFim,
                             int qtdPessoas, String motivo) {
        Contato contato = this.contatoService.consultar(contatoId);
        Recurso recurso = this.recursoService.consultar(recursoId);
        validarPeriodoECapacidade(recurso, data, horaInicio, horaFim, qtdPessoas, motivo);
        garantirDisponibilidade(recursoId, data, horaInicio, horaFim, 0);

        Reserva reserva = new Reserva(contato, recurso, data, horaInicio, horaFim,
                qtdPessoas, motivo.trim());
        this.repository.gravar(reserva);
        return reserva;
    }

    @Override
    public Reserva editar(int id, LocalDate data, LocalTime horaInicio, LocalTime horaFim,
                          int qtdPessoas, String motivo) {
        Reserva reserva = consultar(id);
        if (!reserva.getStatus().ocupaAgenda()) {
            throw new RegraNegocioException(
                    "Reserva " + reserva.getStatus() + " nao pode ser editada.");
        }
        Recurso recurso = reserva.getRecurso();
        validarPeriodoECapacidade(recurso, data, horaInicio, horaFim, qtdPessoas, motivo);
        garantirDisponibilidade(recurso.getId(), data, horaInicio, horaFim, id);

        reserva.setData(data);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFim(horaFim);
        reserva.setQtdPessoas(qtdPessoas);
        reserva.setMotivo(motivo.trim());
        this.repository.gravar(reserva);
        return reserva;
    }

    @Override
    public Reserva consultar(int id) {
        return this.repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva", id));
    }

    @Override
    public List<Reserva> listar() {
        return this.repository.listarTodos();
    }

    @Override
    public Reserva aprovar(int id) {
        Reserva reserva = consultar(id);
        exigirStatus(reserva, "aprovada", StatusReserva.SOLICITADA);
        reserva.setStatus(StatusReserva.APROVADA);
        this.repository.gravar(reserva);
        return reserva;
    }

    @Override
    public Reserva recusar(int id) {
        Reserva reserva = consultar(id);
        exigirStatus(reserva, "recusada", StatusReserva.SOLICITADA);
        reserva.setStatus(StatusReserva.RECUSADA);
        this.repository.gravar(reserva);
        return reserva;
    }

    @Override
    public Reserva cancelar(int id) {
        Reserva reserva = consultar(id);
        exigirStatus(reserva, "cancelada", StatusReserva.SOLICITADA, StatusReserva.APROVADA);
        reserva.setStatus(StatusReserva.CANCELADA);
        this.repository.gravar(reserva);
        return reserva;
    }

    @Override
    public boolean verificarDisponibilidade(int recursoId, LocalDate data,
                                            LocalTime horaInicio, LocalTime horaFim) {
        recursoService.consultar(recursoId);
        return semConflito(recursoId, data, horaInicio, horaFim, 0);
    }

    // ----- regras internas -----

    private void validarPeriodoECapacidade(Recurso recurso, LocalDate data, LocalTime inicio,
                                           LocalTime fim, int qtdPessoas, String motivo) {
        if (data == null || inicio == null || fim == null) {
            throw new ValidacaoException("Data, hora de inicio e hora de fim sao obrigatorias.");
        }
        if (!fim.isAfter(inicio)) {
            throw new ValidacaoException("A hora de fim deve ser posterior a hora de inicio.");
        }
        if (data.isBefore(LocalDate.now())) {
            throw new ValidacaoException("Nao e possivel reservar em data passada.");
        }
        if (qtdPessoas < 1) {
            throw new ValidacaoException("A quantidade de pessoas deve ser de pelo menos 1.");
        }
        if (qtdPessoas > recurso.getCapacidade()) {
            throw new RegraNegocioException("A quantidade de pessoas (" + qtdPessoas
                    + ") excede a capacidade do recurso (" + recurso.getCapacidade() + ").");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new ValidacaoException("O motivo da reserva e obrigatorio.");
        }
    }

    private void garantirDisponibilidade(int recursoId, LocalDate data, LocalTime inicio,
                                         LocalTime fim, int ignorarReservaId) {
        if (!semConflito(recursoId, data, inicio, fim, ignorarReservaId)) {
            throw new RegraNegocioException(
                    "Recurso indisponivel: ja existe reserva ativa no periodo solicitado.");
        }
    }

    private boolean semConflito(int recursoId, LocalDate data, LocalTime inicio,
                                LocalTime fim, int ignorarReservaId) {
        return this.repository.listarPorRecurso(recursoId).stream()
                .filter(r -> r.getId() != ignorarReservaId)
                .noneMatch(r -> r.temConflitoCom(recursoId, data, inicio, fim));
    }

    private void exigirStatus(Reserva reserva, String acao, StatusReserva... permitidos) {
        for (StatusReserva s : permitidos) {
            if (reserva.getStatus() == s) {
                return;
            }
        }
        throw new RegraNegocioException("Reserva " + reserva.getStatus()
                + " nao pode ser " + acao + ".");
    }
}
