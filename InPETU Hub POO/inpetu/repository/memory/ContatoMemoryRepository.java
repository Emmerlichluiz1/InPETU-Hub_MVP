package inpetu.repository.memory;

import inpetu.model.Contato;
import inpetu.repository.IContatoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoMemoryRepository implements IContatoRepository {
    private final List<Contato> contatos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void gravar(Contato contato) {
        if (contato.getId() == 0) {
            contato.setId(proximoId++);
            contatos.add(contato);
        }
    }

    @Override
    public void remover(Contato contato) {
        contatos.remove(contato);
    }

    @Override
    public Optional<Contato> buscarPorId(int id) {
        return contatos.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public List<Contato> listarTodos() {
        return new ArrayList<>(contatos);
    }

    @Override
    public Optional<Contato> buscarPorNome(String nome) {
        return contatos.stream().filter(c -> c.getNome().equalsIgnoreCase(nome)).findFirst();
    }

    @Override
    public Optional<Contato> buscarPorEmail(String email) {
        return contatos.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}
