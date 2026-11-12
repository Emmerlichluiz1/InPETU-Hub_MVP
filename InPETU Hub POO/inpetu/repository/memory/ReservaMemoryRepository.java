package inpetu.repository.memory;

import inpetu.model.Reserva;
import inpetu.repository.IReservaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservaMemoryRepository implements IReservaRepository {
    private final List<Reserva> reservas = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void gravar(Reserva reserva) {
        if (reserva.getId() == 0) {
            reserva.setId(proximoId++);
            reservas.add(reserva);
        }
    }

    @Override
    public void remover(Reserva reserva) {
        reservas.remove(reserva);
    }

    @Override
    public Optional<Reserva> buscarPorId(int id) {
        return reservas.stream().filter(r -> r.getId() == id).findFirst();
    }

    @Override
    public List<Reserva> listarTodos() {
        return new ArrayList<>(reservas);
    }

    @Override
    public List<Reserva> listarPorRecurso(int recursoId) {
        return reservas.stream().filter(r -> r.getRecurso().getId() == recursoId).toList();
    }

    @Override
    public boolean existePorContato(int contatoId) {
        return reservas.stream().anyMatch(r -> r.getSolicitante().getId() == contatoId);
    }

    @Override
    public boolean existePorRecurso(int recursoId) {
        return reservas.stream().anyMatch(r -> r.getRecurso().getId() == recursoId);
    }
}
