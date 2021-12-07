package dto;

import java.util.Map;

public class UpdateCartResponse {
    private final Map<Long, String> errors;
    private final String cartStatus;

    public UpdateCartResponse(Map<Long, String> errors, String cartStatus) {
        this.errors = errors;
        this.cartStatus = cartStatus;
    }

    public Map<Long, String> getErrors() {
        return errors;
    }

    public String getCartStatus() {
        return cartStatus;
    }
}
