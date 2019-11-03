package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.networknt.schema.ValidationMessage;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacade;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacade;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.account.AddBeneficiaryAccount;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.GetTransactionDetails;
import mercy.digital.transfer.presentation.transaction.refill.DoRefill;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import mercy.digital.transfer.utils.schema.SchemaValidator;

import java.util.Set;

@Slf4j
public class Controller {

    public static void main(String[] args) {
        PropUtils.setProperties(Environment.PRODUCTION);

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());
        Injector beneficiaryFacadeInjector = Guice.createInjector(new BeneficiaryFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationSchemaFactoryWrapper visitor = new ValidationSchemaFactoryWrapper();
        H2Utils.startDb(Environment.PRODUCTION);
        Javalin app = Javalin.create().start(7000);

        app.get("/client/add", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.ADD_CLIENT);
            System.out.println(validationMessages);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                AddClient client = objectMapper.readValue(ctx.body(), AddClient.class);
                ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
                ResponseModel responseModel = clientFacade.addClient(client);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }

        });
        app.get("/client/account/add", ctx -> {
            AddClientAccount account = objectMapper.readValue(ctx.body(), AddClientAccount.class);
            ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
            ResponseModel responseModel = clientAccountFacade.addClientAccount(account);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });

        app.get("/beneficiary/add", ctx -> {
            AddBeneficiary beneficiary = objectMapper.readValue(ctx.body(), AddBeneficiary.class);
            BeneficiaryFacade beneficiaryFacade = beneficiaryFacadeInjector.getInstance(BeneficiaryFacade.class);
            ResponseModel responseModel = beneficiaryFacade.addBeneficiary(beneficiary);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });
        app.get("/beneficiary/account/add", ctx -> {
            AddBeneficiaryAccount account = objectMapper.readValue(ctx.body(), AddBeneficiaryAccount.class);
            BeneficiaryFacade beneficiaryFacade = beneficiaryFacadeInjector.getInstance(BeneficiaryFacade.class);
            ResponseModel responseModel = beneficiaryFacade.addBeneficiaryAccount(account);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });

        app.get("/transaction/transfer", ctx -> {
            DoTransfer transfer = objectMapper.readValue(ctx.body(), DoTransfer.class);
            ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
            ResponseModel responseModel = clientAccountFacade.doTransfer(transfer);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });

        app.get("/transaction/refill", ctx -> {
            DoRefill refill = objectMapper.readValue(ctx.body(), DoRefill.class);
            ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
            ResponseModel responseModel = clientAccountFacade.doRefill(refill);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });

        app.get("/client/account/transaction/details/:account_no", ctx -> {
            Integer accountNo = Integer.valueOf(ctx.pathParam("account_no"));
            ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
            GetTransactionDetails details = clientAccountFacade.getTransactionDetails(accountNo);
            ctx.result(objectMapper.writeValueAsString(details));
        });


    }
}
