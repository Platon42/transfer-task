package mercy.digital.transfer.utils.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import mercy.digital.transfer.controller.ApiRequestType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class SchemaValidator {

    private final static String SCHEMA_HOME = "./schema";
    private final static JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
    private final static ObjectMapper mapper = new ObjectMapper();

    public static Set<ValidationMessage> validateSchema(String jsonInstance, ApiRequestType apiRequestType) throws IOException {

        JsonSchema finalSchema;
        String rawSchema;
        JsonNode rawNode;
        Path schemaPath = null;

        if (apiRequestType == ApiRequestType.ADD_BENEFICIARY) {
            schemaPath = Paths.get(SCHEMA_HOME + "/beneficiary/add_bnf.json");
        }
        if (apiRequestType == ApiRequestType.ADD_BENEFICIARY_ACCOUNT) {
            schemaPath = Paths.get(SCHEMA_HOME + "/beneficiary/account/add_bnf_account.json");
        }
        if (apiRequestType == ApiRequestType.ADD_CLIENT_ACCOUNT) {
            schemaPath = Paths.get(SCHEMA_HOME + "/client/account/add_client_account.json");
        }
        if (apiRequestType == ApiRequestType.ADD_CLIENT) {
            schemaPath = Paths.get(SCHEMA_HOME + "/client/add_client.json");
        }
        if (apiRequestType == ApiRequestType.REFILL) {
            schemaPath = Paths.get(SCHEMA_HOME + "/transaction/refill.json");
        }
        if (apiRequestType == ApiRequestType.TRANSFER) {
            schemaPath = Paths.get(SCHEMA_HOME + "/transaction/transfer.json");
        }
        if (schemaPath == null) return Collections.emptySet();

        rawSchema = new String(Files.readAllBytes(schemaPath));
        finalSchema = factory.getSchema(rawSchema);
        rawNode = mapper.readTree(jsonInstance);

        return finalSchema.validate(rawNode);

    }
}
