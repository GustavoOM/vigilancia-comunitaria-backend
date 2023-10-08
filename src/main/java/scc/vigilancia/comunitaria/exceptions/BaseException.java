package scc.vigilancia.comunitaria.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = -1505353848714979625L;
    private String message;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }
}
