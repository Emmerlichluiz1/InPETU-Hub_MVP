package inpetu.service;

import inpetu.exception.EntidadeNaoEncontradaException;
import inpetu.exception.RegraNegocioException;
import inpetu.exception.ValidacaoException;
import inpetu.model.Recurso;
import inpetu.model.TipoRecurso;
import inpetu.repository.IRecursoRepository;
import inpetu.repository.IReservaRepository;

import java.util.List;
import java.util.Optional;

public class RecursoService implements IRecursoService {

    private final IRecursoRepository repository;
    private final IReservaRepository reservaRepository;

    public RecursoService(IRecursoRepository repository, IReservaRepository reservaRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Recurso cadastrar(String nome, TipoRecurso tipo, int capacidade, String descricao) {
        validar(nome, tipo, capacidade);
        garantirNomeLivre(nome.trim(), 0);
        Recurso recurso = new Recurso(nome.trim(), tipo, capacidade, textoOuVazio(descricao));
        this.repository.gravar(recurso);
        return recurso;
    }

    @Override
    public Recurso editar(int id, String nome, TipoRecurso tipo, int capacidade, String descricao) {
        Recurso recurso = consultar(id);
        validar(nome, tipo, capacidade);
        garantirNomeLivre(nome.trim(), id);
        recurso.setNome(nome.trim());
        recurso.setTipo(tipo);
        recurso.setCapacidade(capacidade);
        recurso.setDescricao(textoOuVazio(descricao));
        this.repository.gravar(recurso);
        return recurso;
    }

    @Override
    public Recurso consultar(int id) {
        return this.repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso", id));
    }

    @Override
    public List<Recurso> listar() {
        return this.repository.listarTodos();
    }

    @Override
    public void remover(int id) {
        Recurso recurso = consultar(id);
        if (this.reservaRepository.existePorRecurso(id)) {
            throw new RegraNegocioException(
                    "Recurso vinculado a uma reserva nao pode ser excluido.");
        }
        this.repository.remover(recurso);
    }

    private void validar(String nome, TipoRecurso tipo, int capacidade) {
        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("O nome do recurso e obrigatorio.");
        }
        if (tipo == null) {
            throw new ValidacaoException("O tipo do recurso e obrigatorio.");
        }
        if (capacidade < 1) {
            throw new ValidacaoException("A capacidade deve ser de pelo menos 1.");
        }
    }

    private void garantirNomeLivre(String nome, int idAtual) {
        Optional<Recurso> existente = this.repository.buscarPorNome(nome);
        if (existente.isPresent() && existente.get().getId() != idAtual) {
            throw new RegraNegocioException("Ja existe um recurso chamado \"" + nome + "\".");
        }
    }

    private String textoOuVazio(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
