package mercy.digital.transfer.exception;

public class IncorrectDaoOperation extends Exception {
    public IncorrectDaoOperation(String errorMessage) {
        super(errorMessage);
    }
}
