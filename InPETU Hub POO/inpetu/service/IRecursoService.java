package inpetu.service;

import inpetu.model.Recurso;
import inpetu.model.TipoRecurso;

import java.util.List;

public interface IRecursoService {
    Recurso cadastrar(String nome, TipoRecurso tipo, int capacidade, String descricao);
    Recurso editar(int id, String nome, TipoRecurso tipo, int capacidade, String descricao);
    Recurso consultar(int id);
    List<Recurso> listar();
    void remover(int id);
}
