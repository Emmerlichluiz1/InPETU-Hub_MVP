package inpetu.service;

import inpetu.exception.EntidadeNaoEncontradaException;
import inpetu.exception.RegraNegocioException;
import inpetu.exception.ValidacaoException;
import inpetu.model.Contato;
import inpetu.repository.IContatoRepository;
import inpetu.repository.IReservaRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ContatoService implements IContatoService {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final IContatoRepository repository;
    private final IReservaRepository reservaRepository;

    public ContatoService(IContatoRepository repository, IReservaRepository reservaRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Contato cadastrar(String nome, String email, String telefone,
                             String empresa, String motivoVisita) {
        validar(nome, email, telefone, motivoVisita);
        garantirEmailLivre(email.trim(), 0);
        Contato contato = new Contato(nome.trim(), email.trim(), telefone.trim(),
                textoOuVazio(empresa), motivoVisita.trim());
        this.repository.gravar(contato);
        return contato;
    }

    @Override
    public Contato editar(int id, String nome, String email, String telefone,
                          String empresa, String motivoVisita) {
        Contato contato = consultar(id);
        validar(nome, email, telefone, motivoVisita);
        garantirEmailLivre(email.trim(), id);
        contato.setNome(nome.trim());
        contato.setEmail(email.trim());
        contato.setTelefone(telefone.trim());
        contato.setEmpresa(textoOuVazio(empresa));
        contato.setMotivoVisita(motivoVisita.trim());
        this.repository.gravar(contato);
        return contato;
    }

    @Override
    public Contato consultar(int id) {
        return this.repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Contato", id));
    }

    @Override
    public List<Contato> listar() {
        return this.repository.listarTodos();
    }

    @Override
    public void remover(int id) {
        Contato contato = consultar(id);
        if (this.reservaRepository.existePorContato(id)) {
            throw new RegraNegocioException(
                    "Contato vinculado a uma reserva nao pode ser excluido.");
        }
        this.repository.remover(contato);
    }

    private void validar(String nome, String email, String telefone, String motivoVisita) {
        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("O nome do contato e obrigatorio.");
        }
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            throw new ValidacaoException("E-mail invalido.");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new ValidacaoException("O telefone do contato e obrigatorio.");
        }
        if (motivoVisita == null || motivoVisita.isBlank()) {
            throw new ValidacaoException("O motivo da visita e obrigatorio.");
        }
    }

    private void garantirEmailLivre(String email, int idAtual) {
        Optional<Contato> existente = this.repository.buscarPorEmail(email);
        if (existente.isPresent() && existente.get().getId() != idAtual) {
            throw new RegraNegocioException("Ja existe um contato com o e-mail " + email + ".");
        }
    }

    private String textoOuVazio(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
