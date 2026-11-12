package inpetu;

import inpetu.repository.IContatoRepository;
import inpetu.repository.IRecursoRepository;
import inpetu.repository.IReservaRepository;
import inpetu.repository.memory.ContatoMemoryRepository;
import inpetu.repository.memory.RecursoMemoryRepository;
import inpetu.repository.memory.ReservaMemoryRepository;
import inpetu.service.ContatoService;
import inpetu.service.IContatoService;
import inpetu.service.IRecursoService;
import inpetu.service.IReservaService;
import inpetu.service.RecursoService;
import inpetu.service.ReservaService;
import inpetu.ui.Console;
import inpetu.ui.IMenu;
import inpetu.ui.MenuContato;
import inpetu.ui.MenuPrincipal;
import inpetu.ui.MenuRecurso;
import inpetu.ui.MenuReserva;

import java.util.List;

/** Composition root: unico lugar que conhece as implementacoes concretas. */
public class Application {
    public static void main(String[] args) {
        IContatoRepository contatoRepository = new ContatoMemoryRepository();
        IRecursoRepository recursoRepository = new RecursoMemoryRepository();
        IReservaRepository reservaRepository = new ReservaMemoryRepository();

        IContatoService contatoService = new ContatoService(contatoRepository, reservaRepository);
        IRecursoService recursoService = new RecursoService(recursoRepository, reservaRepository);
        IReservaService reservaService =
                new ReservaService(reservaRepository, contatoService, recursoService);

        Console console = new Console();
        List<IMenu> menus = List.of(
                new MenuContato(console, contatoService),
                new MenuRecurso(console, recursoService),
                new MenuReserva(console, reservaService));

        new MenuPrincipal(console, menus).executar();
    }
}
