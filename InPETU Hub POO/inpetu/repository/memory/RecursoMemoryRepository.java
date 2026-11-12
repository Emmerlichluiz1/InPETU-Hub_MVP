package inpetu.repository.memory;

import inpetu.model.Recurso;
import inpetu.repository.IRecursoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursoMemoryRepository implements IRecursoRepository {
    private final List<Recurso> recursos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void gravar(Recurso recurso) {
        if (recurso.getId() == 0) {
            recurso.setId(proximoId++);
            recursos.add(recurso);
        }
    }

    @Override
    public void remover(Recurso recurso) {
        recursos.remove(recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(int id) {
        return recursos.stream().filter(r -> r.getId() == id).findFirst();
    }

    @Override
    public List<Recurso> listarTodos() {
        return new ArrayList<>(recursos);
    }

    @Override
    public Optional<Recurso> buscarPorNome(String nome) {
        return recursos.stream().filter(r -> r.getNome().equalsIgnoreCase(nome)).findFirst();
    }
}
