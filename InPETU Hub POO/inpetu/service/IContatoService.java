package inpetu.service;

import inpetu.model.Contato;

import java.util.List;

public interface IContatoService {
    Contato cadastrar(String nome, String email, String telefone,
                      String empresa, String motivoVisita);
    Contato editar(int id, String nome, String email, String telefone,
                   String empresa, String motivoVisita);

    /** Atalho que preserva empresa/motivo ja gravados. */
    default Contato editar(int id, String nome, String email, String telefone) {
        Contato atual = consultar(id);
        return editar(id, nome, email, telefone, atual.getEmpresa(), atual.getMotivoVisita());
    }

    Contato consultar(int id);
    List<Contato> listar();
    void remover(int id);
}
