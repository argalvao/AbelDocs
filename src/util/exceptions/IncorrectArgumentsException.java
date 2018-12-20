package util.exceptions;

public class IncorrectArgumentsException extends RuntimeException {

    /**
     * Necessario para a serializacao
     */
    private static final long serialVersionUID = 8741016812744242724L;

    /**
     * Constrói uma exceção do tipo InputInformationIncorrect indicando a linha do arquivo que
     * contém uma quantidade inválida de dados
     *
     * @param lineNumber - Linha do arquivo com erro
     */
    public IncorrectArgumentsException(int lineNumber) {
        super("A linha " + lineNumber + " do arquivo de entrada contem quantidade incorreta de dados");
    }

    /**
     * Constrói uma exceção do tipo InputInformationIncorrect
     */
    public IncorrectArgumentsException() {
        super("Quantidade incorreta de dados");
    }
}
