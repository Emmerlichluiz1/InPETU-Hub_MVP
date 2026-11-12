package inpetu.repository;

import inpetu.model.Contato;

import java.util.Optional;

public interface IContatoRepository extends IRepository<Contato> {
    Optional<Contato> buscarPorNome(String nome);
    Optional<Contato> buscarPorEmail(String email);
}
