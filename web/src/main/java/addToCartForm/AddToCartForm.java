package addToCartForm;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(using = AddToCartFormDeserializer.class)
public class AddToCartForm implements Serializable {
    private Long phoneId;
    private Long quantity;

    public AddToCartForm() {
    }

    public AddToCartForm(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
