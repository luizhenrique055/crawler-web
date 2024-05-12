package br.com.Exceptions;

public class NullPageException extends RuntimeException {
    public NullPageException() {
        super("Pagina vazia ou invalida");
    }
}
