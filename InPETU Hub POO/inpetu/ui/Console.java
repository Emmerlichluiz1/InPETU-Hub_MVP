package inpetu.ui;

import inpetu.exception.ValidacaoException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Leitura tratada de dados do teclado: entradas invalidas viram ValidacaoException. */
public class Console {
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final Scanner scanner = new Scanner(System.in);

    public String texto(String rotulo) {
        System.out.print(rotulo + ": ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    public int inteiro(String rotulo) {
        String valor = texto(rotulo);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new ValidacaoException("\"" + valor + "\" nao e um numero inteiro valido.");
        }
    }

    public LocalDate data(String rotulo) {
        String valor = texto(rotulo + " (dd/mm/aaaa)");
        try {
            return LocalDate.parse(valor, DATA);
        } catch (DateTimeParseException e) {
            throw new ValidacaoException("Data invalida: \"" + valor + "\".");
        }
    }

    public LocalTime hora(String rotulo) {
        String valor = texto(rotulo + " (hh:mm)");
        try {
            return LocalTime.parse(valor, HORA);
        } catch (DateTimeParseException e) {
            throw new ValidacaoException("Hora invalida: \"" + valor + "\".");
        }
    }

    public void mensagem(String texto) {
        System.out.println(texto);
    }
}
