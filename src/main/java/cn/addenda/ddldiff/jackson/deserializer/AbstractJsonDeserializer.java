package cn.addenda.ddldiff.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public abstract class AbstractJsonDeserializer<T>
        extends JsonDeserializer<T> {

  protected MismatchedInputException from(JsonParser jp, JsonNode jsonNode, Class<T> diffClass) {
    return MismatchedInputException.from(jp, diffClass,
            String.format("Can not deserialize %s to %s", jsonNode, diffClass));
  }

}
