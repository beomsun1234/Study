package jpabook.jpashop.exception;

public class NotEnoughtStockEeception extends RuntimeException{

    public NotEnoughtStockEeception() {
        super();
    }

    public NotEnoughtStockEeception(String message) {
        super(message);
    }

    public NotEnoughtStockEeception(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughtStockEeception(Throwable cause) {
        super(cause);
    }

}
