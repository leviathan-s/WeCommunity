package elice.wanted.exception;

public class LoginFailByNicknameException extends RuntimeException{
    public LoginFailByNicknameException(String message) {
        super(message);
    }
}
