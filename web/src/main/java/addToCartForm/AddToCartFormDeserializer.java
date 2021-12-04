package addToCartForm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AddToCartFormDeserializer extends StdDeserializer<AddToCartForm> {
    private static final String PHONE_ID_FIELD = "phoneId";
    private static final String QUANTITY_FIELD = "quantity";

    public AddToCartFormDeserializer() {
        this(null);
    }

    public AddToCartFormDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AddToCartForm deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long phoneId = node.get(PHONE_ID_FIELD).asLong();
        Long quantity = node.get(QUANTITY_FIELD).asLong();
        return new AddToCartForm(phoneId, quantity);
    }
}
