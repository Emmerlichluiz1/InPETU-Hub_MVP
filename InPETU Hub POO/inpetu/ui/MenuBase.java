package inpetu.ui;

import inpetu.exception.NegocioException;

/** Laco comum de menu; cada acao falha de forma isolada sem derrubar a aplicacao. */
public abstract class MenuBase implements IMenu {
    protected final Console console;

    protected MenuBase(Console console) {
        this.console = console;
    }

    protected abstract String[] opcoes();
    protected abstract void tratar(int opcao);

    @Override
    public void executar() {
        while (true) {
            console.mensagem("\n=== " + titulo() + " ===");
            String[] opcoes = opcoes();
            for (int i = 0; i < opcoes.length; i++) {
                console.mensagem((i + 1) + " - " + opcoes[i]);
            }
            console.mensagem("0 - Voltar");
            try {
                int opcao = console.inteiro("Opcao");
                if (opcao == 0) {
                    return;
                }
                if (opcao < 1 || opcao > opcoes.length) {
                    console.mensagem("Opcao invalida.");
                    continue;
                }
                tratar(opcao);
            } catch (NegocioException e) {
                console.mensagem("ERRO: " + e.getMessage());
            }
        }
    }
}
