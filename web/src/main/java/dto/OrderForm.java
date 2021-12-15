package dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderForm {
    private static final String PHONE_NUMBER_REGEXP = "\\+[0-9]{7,}";
    private static final String VALUE_IS_REQUIRED_MESSAGE = "The value is required";
    private static final String NOT_A_PHONE_NUMBER_MESSAGE = "Not a phone number";

    @Size(min = 1, message = VALUE_IS_REQUIRED_MESSAGE)
    private String firstName;
    @Size(min = 1, message = VALUE_IS_REQUIRED_MESSAGE)
    private String lastName;
    @Size(min = 1, message = VALUE_IS_REQUIRED_MESSAGE)
    private String deliveryAddress;
    @Pattern(regexp = PHONE_NUMBER_REGEXP, message = NOT_A_PHONE_NUMBER_MESSAGE)
    private String contactPhoneNo;

    private String additionalInformation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
