package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.Min;
import java.io.Serializable;

@JsonDeserialize(using = AddToCartFormDeserializer.class)
public class AddToCartForm implements Serializable {
    private Long phoneId;

    @Min(1)
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
