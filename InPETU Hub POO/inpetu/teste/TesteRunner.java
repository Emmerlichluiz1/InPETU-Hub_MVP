package inpetu.teste;

import inpetu.exception.EntidadeNaoEncontradaException;
import inpetu.exception.NegocioException;
import inpetu.exception.RegraNegocioException;
import inpetu.exception.ValidacaoException;
import inpetu.model.Contato;
import inpetu.model.Recurso;
import inpetu.model.Reserva;
import inpetu.model.StatusReserva;
import inpetu.model.TipoRecurso;
import inpetu.repository.memory.ContatoMemoryRepository;
import inpetu.repository.memory.RecursoMemoryRepository;
import inpetu.repository.memory.ReservaMemoryRepository;
import inpetu.service.ContatoService;
import inpetu.service.IContatoService;
import inpetu.service.IRecursoService;
import inpetu.service.IReservaService;
import inpetu.service.RecursoService;
import inpetu.service.ReservaService;

import java.time.LocalDate;
import java.time.LocalTime;

/** Roteiro de testes automatizado (sem dependencias externas). Execute: java inpetu.teste.TesteRunner */
public class TesteRunner {

    private static int passou = 0;
    private static int falhou = 0;

    private IContatoService contatos;
    private IRecursoService recursos;
    private IReservaService reservas;

    private final LocalDate amanha = LocalDate.now().plusDays(1);

    public static void main(String[] args) {
        TesteRunner t = new TesteRunner();
        t.contatoCadastrarConsultarListar();
        t.contatoEditar();
        t.contatoValidacoes();
        t.contatoEmailDuplicado();
        t.contatoInexistente();
        t.recursoCadastrarConsultarListar();
        t.recursoEditar();
        t.recursoValidacoes();
        t.recursoNomeDuplicado();
        t.reservaSolicitarComSucesso();
        t.reservaConflitoDeHorario();
        t.reservaHorariosAdjacentesSaoPermitidos();
        t.reservaEmOutroRecursoOuDiaNaoConflita();
        t.reservaCapacidadeExcedida();
        t.reservaValidacoes();
        t.reservaEntidadesInexistentes();
        t.reservaEditarSemConflitoConsigoMesma();
        t.reservaEditarComConflito();
        t.reservaCicloDeStatus();
        t.reservaCanceladaLiberaHorario();
        t.reservaRecusadaNaoPodeSerEditada();
        t.exclusaoBloqueadaPorReserva();
        t.exclusaoPermitidaSemReserva();

        System.out.println("\nResultado: " + passou + " passaram, " + falhou + " falharam.");
        System.exit(falhou == 0 ? 0 : 1);
    }

    private void novoCenario() {
        var reservaRepo = new ReservaMemoryRepository();
        contatos = new ContatoService(new ContatoMemoryRepository(), reservaRepo);
        recursos = new RecursoService(new RecursoMemoryRepository(), reservaRepo);
        reservas = new ReservaService(reservaRepo, contatos, recursos);
    }

    private Contato contatoPadrao() {
        return contatos.cadastrar("Maria", "maria@ufsc.br", "48999990000", "", "Visita de teste");
    }

    private Recurso salaPadrao() {
        return recursos.cadastrar("Sala 1", TipoRecurso.SALA_REUNIAO, 10, "Sala com projetor");
    }

    private Reserva reservar(Contato c, Recurso r, String ini, String fim, int pessoas) {
        return reservas.solicitar(c.getId(), r.getId(), amanha,
                LocalTime.parse(ini), LocalTime.parse(fim), pessoas, "Reuniao");
    }

    // ---------------- Contatos ----------------

    void contatoCadastrarConsultarListar() {
        teste("Contato: cadastrar, consultar e listar", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            igual(1, c.getId());
            igual("Maria", contatos.consultar(1).getNome());
            contatos.cadastrar("Hugo", "hugo@ufsc.br", "48911112222", "", "Visita de teste");
            igual(2, contatos.listar().size());
        });
    }

    void contatoEditar() {
        teste("Contato: editar", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            contatos.editar(c.getId(), "Maria Silva", "maria.silva@ufsc.br", "48988887777");
            Contato lido = contatos.consultar(c.getId());
            igual("Maria Silva", lido.getNome());
            igual("maria.silva@ufsc.br", lido.getEmail());
        });
    }

    void contatoValidacoes() {
        teste("Contato: nome vazio, e-mail invalido e telefone vazio sao rejeitados", () -> {
            novoCenario();
            lanca(ValidacaoException.class, () -> contatos.cadastrar(" ", "a@b.com", "1", "", "Visita de teste"));
            lanca(ValidacaoException.class, () -> contatos.cadastrar("A", "sem-arroba", "1", "", "Visita de teste"));
            lanca(ValidacaoException.class, () -> contatos.cadastrar("A", null, "1", "", "Visita de teste"));
            lanca(ValidacaoException.class, () -> contatos.cadastrar("A", "a@b.com", "", "", "Visita de teste"));
            lanca(ValidacaoException.class, () -> contatos.cadastrar("A", "a@b.com", "1", "", ""));
            lanca(ValidacaoException.class, () -> contatos.cadastrar("A", "a@b.com", "1", "", null));
            igual(0, contatos.listar().size());
        });
    }

    void contatoEmailDuplicado() {
        teste("Contato: e-mail duplicado e rejeitado (cadastro e edicao), mas editar o proprio e ok", () -> {
            novoCenario();
            Contato c1 = contatoPadrao();
            lanca(RegraNegocioException.class,
                    () -> contatos.cadastrar("Outra", "MARIA@ufsc.br", "1", "", "Visita de teste"));
            Contato c2 = contatos.cadastrar("Hugo", "hugo@ufsc.br", "2", "", "Visita de teste");
            lanca(RegraNegocioException.class,
                    () -> contatos.editar(c2.getId(), "Hugo", "maria@ufsc.br", "2"));
            contatos.editar(c1.getId(), "Maria S.", "maria@ufsc.br", "3");
            igual("hugo@ufsc.br", contatos.consultar(c2.getId()).getEmail());
        });
    }

    void contatoInexistente() {
        teste("Contato: consultar/editar/remover ID inexistente", () -> {
            novoCenario();
            lanca(EntidadeNaoEncontradaException.class, () -> contatos.consultar(99));
            lanca(EntidadeNaoEncontradaException.class,
                    () -> contatos.editar(99, "A", "a@b.com", "1"));
            lanca(EntidadeNaoEncontradaException.class, () -> contatos.remover(99));
        });
    }

    // ---------------- Recursos ----------------

    void recursoCadastrarConsultarListar() {
        teste("Recurso: cadastrar, consultar e listar", () -> {
            novoCenario();
            Recurso r = salaPadrao();
            igual("Sala 1", recursos.consultar(r.getId()).getNome());
            recursos.cadastrar("Auditorio", TipoRecurso.AUDITORIO, 100, "");
            igual(2, recursos.listar().size());
        });
    }

    void recursoEditar() {
        teste("Recurso: editar", () -> {
            novoCenario();
            Recurso r = salaPadrao();
            recursos.editar(r.getId(), "Sala Grande", TipoRecurso.SALA_REUNIAO, 20, "Ampliada");
            igual(20, recursos.consultar(r.getId()).getCapacidade());
            igual("Sala Grande", recursos.consultar(r.getId()).getNome());
        });
    }

    void recursoValidacoes() {
        teste("Recurso: nome vazio, tipo nulo e capacidade < 1 sao rejeitados", () -> {
            novoCenario();
            lanca(ValidacaoException.class,
                    () -> recursos.cadastrar("", TipoRecurso.SALA_REUNIAO, 5, ""));
            lanca(ValidacaoException.class, () -> recursos.cadastrar("X", null, 5, ""));
            lanca(ValidacaoException.class,
                    () -> recursos.cadastrar("X", TipoRecurso.AUDITORIO, 0, ""));
            lanca(ValidacaoException.class,
                    () -> recursos.cadastrar("X", TipoRecurso.AUDITORIO, -3, ""));
            igual(0, recursos.listar().size());
        });
    }

    void recursoNomeDuplicado() {
        teste("Recurso: nome duplicado e rejeitado", () -> {
            novoCenario();
            salaPadrao();
            lanca(RegraNegocioException.class,
                    () -> recursos.cadastrar("sala 1", TipoRecurso.SALA_REUNIAO, 5, ""));
        });
    }

    // ---------------- Reservas ----------------

    void reservaSolicitarComSucesso() {
        teste("Reserva: solicitar, consultar e listar (status inicial SOLICITADA)", () -> {
            novoCenario();
            Reserva r = reservar(contatoPadrao(), salaPadrao(), "09:00", "10:00", 5);
            igual(StatusReserva.SOLICITADA, reservas.consultar(r.getId()).getStatus());
            igual(1, reservas.listar().size());
        });
    }

    void reservaConflitoDeHorario() {
        teste("Reserva: periodo sobreposto no mesmo recurso e rejeitado", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            reservar(c, s, "09:00", "11:00", 5);
            lanca(RegraNegocioException.class, () -> reservar(c, s, "10:00", "12:00", 5));
            lanca(RegraNegocioException.class, () -> reservar(c, s, "08:00", "09:30", 5));
            lanca(RegraNegocioException.class, () -> reservar(c, s, "09:30", "10:00", 5));
            lanca(RegraNegocioException.class, () -> reservar(c, s, "08:00", "12:00", 5));
            igual(1, reservas.listar().size());
            igual(false, reservas.verificarDisponibilidade(s.getId(), amanha,
                    LocalTime.of(10, 0), LocalTime.of(10, 30)));
        });
    }

    void reservaHorariosAdjacentesSaoPermitidos() {
        teste("Reserva: horarios que apenas se tocam (fim = inicio) nao conflitam", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            reservar(c, s, "09:00", "10:00", 5);
            reservar(c, s, "10:00", "11:00", 5);
            reservar(c, s, "08:00", "09:00", 5);
            igual(3, reservas.listar().size());
        });
    }

    void reservaEmOutroRecursoOuDiaNaoConflita() {
        teste("Reserva: mesmo horario em outro recurso ou outro dia e permitido", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s1 = salaPadrao();
            Recurso s2 = recursos.cadastrar("Sala 2", TipoRecurso.SALA_REUNIAO, 10, "");
            reservar(c, s1, "09:00", "10:00", 5);
            reservar(c, s2, "09:00", "10:00", 5);
            reservas.solicitar(c.getId(), s1.getId(), amanha.plusDays(1),
                    LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "Outro dia");
            igual(3, reservas.listar().size());
        });
    }

    void reservaCapacidadeExcedida() {
        teste("Reserva: quantidade de pessoas acima da capacidade e rejeitada", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            lanca(RegraNegocioException.class, () -> reservar(c, s, "09:00", "10:00", 11));
            reservar(c, s, "09:00", "10:00", 10);
        });
    }

    void reservaValidacoes() {
        teste("Reserva: fim <= inicio, data passada, pessoas < 1, motivo vazio, campos nulos", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            lanca(ValidacaoException.class, () -> reservar(c, s, "10:00", "09:00", 5));
            lanca(ValidacaoException.class, () -> reservar(c, s, "10:00", "10:00", 5));
            lanca(ValidacaoException.class, () -> reservar(c, s, "09:00", "10:00", 0));
            lanca(ValidacaoException.class, () -> reservas.solicitar(c.getId(), s.getId(),
                    LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "x"));
            lanca(ValidacaoException.class, () -> reservas.solicitar(c.getId(), s.getId(),
                    amanha, LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "  "));
            lanca(ValidacaoException.class, () -> reservas.solicitar(c.getId(), s.getId(),
                    null, LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "x"));
            igual(0, reservas.listar().size());
        });
    }

    void reservaEntidadesInexistentes() {
        teste("Reserva: contato ou recurso inexistente e rejeitado", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            lanca(EntidadeNaoEncontradaException.class, () -> reservas.solicitar(99, s.getId(),
                    amanha, LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "x"));
            lanca(EntidadeNaoEncontradaException.class, () -> reservas.solicitar(c.getId(), 99,
                    amanha, LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "x"));
            lanca(EntidadeNaoEncontradaException.class, () -> reservas.consultar(99));
        });
    }

    void reservaEditarSemConflitoConsigoMesma() {
        teste("Reserva: editar dentro do proprio periodo nao conflita consigo mesma", () -> {
            novoCenario();
            Reserva r = reservar(contatoPadrao(), salaPadrao(), "09:00", "10:00", 5);
            reservas.editar(r.getId(), amanha, LocalTime.of(9, 30), LocalTime.of(10, 30), 8, "Novo motivo");
            Reserva lida = reservas.consultar(r.getId());
            igual(LocalTime.of(9, 30), lida.getHoraInicio());
            igual(8, lida.getQtdPessoas());
            igual("Novo motivo", lida.getMotivo());
        });
    }

    void reservaEditarComConflito() {
        teste("Reserva: edicao que conflita com outra e rejeitada e preserva o estado original", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            reservar(c, s, "09:00", "10:00", 5);
            Reserva r2 = reservar(c, s, "11:00", "12:00", 5);
            lanca(RegraNegocioException.class, () -> reservas.editar(r2.getId(), amanha,
                    LocalTime.of(9, 30), LocalTime.of(11, 30), 5, "x"));
            lanca(RegraNegocioException.class, () -> reservas.editar(r2.getId(), amanha,
                    LocalTime.of(11, 0), LocalTime.of(12, 0), 50, "x"));
            igual(LocalTime.of(11, 0), reservas.consultar(r2.getId()).getHoraInicio());
            igual(5, reservas.consultar(r2.getId()).getQtdPessoas());
        });
    }

    void reservaCicloDeStatus() {
        teste("Reserva: aprovar, recusar e cancelar respeitam as transicoes validas", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            Reserva a = reservar(c, s, "09:00", "10:00", 5);
            igual(StatusReserva.APROVADA, reservas.aprovar(a.getId()).getStatus());
            lanca(RegraNegocioException.class, () -> reservas.aprovar(a.getId()));
            lanca(RegraNegocioException.class, () -> reservas.recusar(a.getId()));
            igual(StatusReserva.CANCELADA, reservas.cancelar(a.getId()).getStatus());
            lanca(RegraNegocioException.class, () -> reservas.cancelar(a.getId()));
            lanca(RegraNegocioException.class, () -> reservas.aprovar(a.getId()));

            Reserva b = reservar(c, s, "11:00", "12:00", 5);
            igual(StatusReserva.RECUSADA, reservas.recusar(b.getId()).getStatus());
            lanca(RegraNegocioException.class, () -> reservas.cancelar(b.getId()));
        });
    }

    void reservaCanceladaLiberaHorario() {
        teste("Reserva: cancelada ou recusada libera o horario para nova reserva", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            Reserva a = reservar(c, s, "09:00", "10:00", 5);
            reservas.cancelar(a.getId());
            Reserva b = reservar(c, s, "09:00", "10:00", 5);
            reservas.recusar(b.getId());
            reservar(c, s, "09:00", "10:00", 5);
            igual(3, reservas.listar().size());
        });
    }

    void reservaRecusadaNaoPodeSerEditada() {
        teste("Reserva: reserva cancelada/recusada nao pode ser editada", () -> {
            novoCenario();
            Reserva r = reservar(contatoPadrao(), salaPadrao(), "09:00", "10:00", 5);
            reservas.cancelar(r.getId());
            lanca(RegraNegocioException.class, () -> reservas.editar(r.getId(), amanha,
                    LocalTime.of(9, 0), LocalTime.of(10, 0), 5, "x"));
        });
    }

    // ---------------- Exclusao ----------------

    void exclusaoBloqueadaPorReserva() {
        teste("Exclusao: contato e recurso vinculados a reserva (mesmo cancelada) nao podem ser excluidos", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            Reserva r = reservar(c, s, "09:00", "10:00", 5);
            lanca(RegraNegocioException.class, () -> contatos.remover(c.getId()));
            lanca(RegraNegocioException.class, () -> recursos.remover(s.getId()));
            reservas.cancelar(r.getId());
            lanca(RegraNegocioException.class, () -> contatos.remover(c.getId()));
            lanca(RegraNegocioException.class, () -> recursos.remover(s.getId()));
            igual(1, contatos.listar().size());
            igual(1, recursos.listar().size());
        });
    }

    void exclusaoPermitidaSemReserva() {
        teste("Exclusao: contato e recurso sem reserva podem ser excluidos", () -> {
            novoCenario();
            Contato c = contatoPadrao();
            Recurso s = salaPadrao();
            contatos.remover(c.getId());
            recursos.remover(s.getId());
            igual(0, contatos.listar().size());
            igual(0, recursos.listar().size());
            lanca(EntidadeNaoEncontradaException.class, () -> contatos.consultar(c.getId()));
        });
    }

    // ---------------- mini framework ----------------

    private static void teste(String nome, Runnable corpo) {
        try {
            corpo.run();
            passou++;
            System.out.println("[OK]    " + nome);
        } catch (Throwable e) {
            falhou++;
            System.out.println("[FALHA] " + nome + " -> " + e);
        }
    }

    private static void igual(Object esperado, Object atual) {
        if (!esperado.equals(atual)) {
            throw new AssertionError("esperado <" + esperado + "> mas foi <" + atual + ">");
        }
    }

    private static void lanca(Class<? extends NegocioException> tipo, Runnable acao) {
        try {
            acao.run();
        } catch (NegocioException e) {
            if (!tipo.isInstance(e)) {
                throw new AssertionError("esperado " + tipo.getSimpleName()
                        + " mas foi " + e.getClass().getSimpleName());
            }
            return;
        }
        throw new AssertionError("esperado " + tipo.getSimpleName() + " mas nada foi lancado");
    }
}
