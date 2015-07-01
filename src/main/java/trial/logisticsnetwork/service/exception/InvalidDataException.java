package trial.logisticsnetwork.service.exception;

/**
 * Created by mateus on 7/1/15.
 */
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(Throwable cause){
        super(cause);
    }

    public InvalidDataException(String message){
        super(message);
    }

}
