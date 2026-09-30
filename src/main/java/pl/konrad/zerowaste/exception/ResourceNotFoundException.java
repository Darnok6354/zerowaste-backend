package pl.konrad.zerowaste.exception;

// RuntimeException pozwala nam rzucać ten błąd w dowolnym miejscu bez wymuszania bloku try-catch
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}