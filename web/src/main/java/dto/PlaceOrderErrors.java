package dto;

import java.util.Map;

public class PlaceOrderErrors {
    private final Map<String, String> errors;

    public PlaceOrderErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
