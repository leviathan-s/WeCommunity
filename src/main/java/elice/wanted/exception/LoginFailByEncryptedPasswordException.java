package elice.wanted.exception;

public class LoginFailByEncryptedPasswordException extends RuntimeException{
    public LoginFailByEncryptedPasswordException(String message) {
        super(message);
    }
}
