package inpetu.repository;

import inpetu.model.Reserva;

import java.util.List;

public interface IReservaRepository extends IRepository<Reserva> {
    List<Reserva> listarPorRecurso(int recursoId);
    boolean existePorContato(int contatoId);
    boolean existePorRecurso(int recursoId);
}
