package inpetu.ui;

import inpetu.exception.NegocioException;

import java.util.List;

public class MenuPrincipal {
    private final Console console;
    private final List<IMenu> menus;

    public MenuPrincipal(Console console, List<IMenu> menus) {
        this.console = console;
        this.menus = menus;
    }

    public void executar() {
        while (true) {
            console.mensagem("\n===== InPETU hub - Reserva de Recursos =====");
            for (int i = 0; i < menus.size(); i++) {
                console.mensagem((i + 1) + " - " + menus.get(i).titulo());
            }
            console.mensagem("0 - Sair");
            try {
                int opcao = console.inteiro("Opcao");
                if (opcao == 0) {
                    console.mensagem("Ate logo!");
                    return;
                }
                if (opcao < 1 || opcao > menus.size()) {
                    console.mensagem("Opcao invalida.");
                    continue;
                }
                menus.get(opcao - 1).executar();
            } catch (NegocioException e) {
                console.mensagem("ERRO: " + e.getMessage());
            }
        }
    }
}
