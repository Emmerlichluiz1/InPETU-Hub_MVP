package inpetu.repository;

import inpetu.model.Recurso;

import java.util.Optional;

public interface IRecursoRepository extends IRepository<Recurso> {
    Optional<Recurso> buscarPorNome(String nome);
}
