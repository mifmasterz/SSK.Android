package p008rx.exceptions;

/* renamed from: rx.exceptions.OnCompletedFailedException */
public final class OnCompletedFailedException extends RuntimeException {
    private static final long serialVersionUID = 8622579378868820554L;

    public OnCompletedFailedException(Throwable throwable) {
        super(throwable != null ? throwable : new NullPointerException());
    }

    public OnCompletedFailedException(String message, Throwable throwable) {
        super(message, throwable != null ? throwable : new NullPointerException());
    }
}
