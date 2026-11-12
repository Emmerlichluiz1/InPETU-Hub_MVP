package inpetu.repository;

import inpetu.model.Entidade;

import java.util.List;
import java.util.Optional;

public interface IRepository<T extends Entidade> {
    void gravar(T entidade);
    void remover(T entidade);
    Optional<T> buscarPorId(int id);
    List<T> listarTodos();
}
