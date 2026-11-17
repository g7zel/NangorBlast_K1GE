/*
 * NIM  : 18225045
 * Nama : Tristan Anthony Wijaya
 */
package nangorblast.exception;

public class InvalidMapException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidMapException(String message) { super(message); }
    public InvalidMapException(String message, Throwable cause) { super(message, cause); }
}
