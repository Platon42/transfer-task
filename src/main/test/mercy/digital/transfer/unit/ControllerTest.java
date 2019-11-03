package mercy.digital.transfer.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import mercy.digital.transfer.controller.Controller;
import mercy.digital.transfer.presentation.response.ResponseModel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {

    private final static String JSON_HOME_SUCCESS = "./json_test_case/success";
    private final static String JSON_HOME_ERROR = "./json_test_case/error";
    private ObjectMapper objectMapper = new ObjectMapper();
    private CloseableHttpClient httpclient = HttpClients.createDefault();

    @BeforeAll
    static void setUp() {
        Controller.main(new String[]{});
    }

    @AfterAll
    static void stop() {
        Controller.stopJavalin();
    }

    @Order(1)
    @Test
    void addClientJsonCorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/client/add");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/add_client.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("Success client added", responseModel.getMessage());
    }

    @Order(2)
    @Test
    void addClientJsonIncorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/client/add");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/client/add_client_error_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.substring(validationError.indexOf("message")).contains("11992-08-14 is an invalid date"));
    }

    @Order(3)
    @Test
    void addClientAccountJsonCorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/client/account/add");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/add_client_account.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("Success created client Account", responseModel.getMessage());
    }

    @Order(4)
    @Test
    void addClientAccountJsonClientNotFound() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/client/account/add");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/client/add_client_account_not_found_client.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("Cannot create client account, with Client Id 10 see log for details", responseModel.getMessage());
    }

    @Order(5)
    @Test
    void addClientAccountJsonIncorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/client/account/add");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/client/add_client_account_wrong_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);

        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.substring(validationError.indexOf("message")).contains("$.currency: is missing but it is required"));
    }

    @Order(6)
    @Test
    void addClientBeneficiaryJsonCorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/beneficiary/add");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/add_beneficiary.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);

        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("Success beneficiary added", responseModel.getMessage());

    }

    @Order(7)
    @Test
    void addClientBeneficiaryJsonIncorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/beneficiary/add");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/beneficiary/add_beneficiary_wrong_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.substring(validationError.indexOf("message")).contains("name: is missing but it is required"));

    }

    @Order(8)
    @Test
    void addClientBeneficiaryAccountJsonCorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/beneficiary/account/add");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/add_account_beneficiary.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("Success beneficiary account added", responseModel.getMessage());

    }

    @Order(9)
    @Test
    void addClientBeneficiaryAccountClientIncorrect() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/beneficiary/account/add");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/beneficiary/add_beneficiary_client_wrong_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.substring(validationError.indexOf("message")).contains("$.currency: is missing but it is required"));
    }

    @Order(10)
    @Test
    void doRefillSuccess() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/refill");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/refill.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("REFILL_COMPLETED", responseModel.getMessage());
    }

    @Order(11)
    @Test
    void doRefillIncorrectCurrencyJson() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/refill");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/refill/refill_wrong_currency.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.contains("Request body as DoRefill invalid - Failed check"));
    }

    @Order(12)
    @Test
    void doRefillIncorrectJson() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/refill");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/refill/refill_wrong_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.contains("$.account_no: is missing but it is required"));
    }

    @Order(13)
    @Test
    void doTransferSuccess() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/transfer");
        String entity = Files.readString(Paths.get(JSON_HOME_SUCCESS + "/transfer.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("TRANSFER_COMPLETED", responseModel.getMessage());
    }

    @Order(14)
    @Test
    void doTransferIncorrectClientNo() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/transfer");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/transfer/transfer_not_a_client.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String json = new String(response.getEntity().getContent().readAllBytes());
        ResponseModel responseModel = objectMapper.readValue(json, ResponseModel.class);
        Assertions.assertEquals("ERROR_OCCURRED", responseModel.getMessage());
    }

    @Order(15)
    @Test
    void doTransferIncorrectJson() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:7000/transaction/transfer");
        String entity = Files.readString(Paths.get(JSON_HOME_ERROR + "/transfer/transfer_wrong_structure.json"));
        httpPost.setEntity(new StringEntity(entity));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String validationError = new String(response.getEntity().getContent().readAllBytes());
        Assertions.assertTrue(validationError.contains("$.account_no_sender: is missing but it is required"));

    }
}