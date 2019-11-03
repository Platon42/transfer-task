package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import mercy.digital.transfer.utils.schema.SchemaValidator;
import org.apache.commons.lang3.EnumUtils;

import java.util.Set;

@Slf4j
public class Controller {

    private static Javalin app;

    public static void main(String[] args) {

        PropUtils.setProperties(Environment.PRODUCTION);

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());
        Injector beneficiaryFacadeInjector = Guice.createInjector(new BeneficiaryFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();
        H2Utils.startDb(Environment.PRODUCTION);
        app = Javalin.create().start(7000);

        app.post("/client/add", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.ADD_CLIENT);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {

                AddClient client = objectMapper.readValue(ctx.body(), AddClient.class);
                ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
                ResponseModel responseModel = clientFacade.addClient(client);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }

        });
        app.post("/client/account/add", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.ADD_CLIENT_ACCOUNT);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                AddClientAccount account = ctx.bodyValidator(AddClientAccount.class)
                        .check(obj -> EnumUtils.isValidEnum(CurrencyCode.class, obj.getCurrency()))
                        .get();
                ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
                ResponseModel responseModel = clientAccountFacade.addClientAccount(account);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }
        });

        app.post("/beneficiary/add", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.ADD_BENEFICIARY);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                AddBeneficiary beneficiary = objectMapper.readValue(ctx.body(), AddBeneficiary.class);
                BeneficiaryFacade beneficiaryFacade = beneficiaryFacadeInjector.getInstance(BeneficiaryFacade.class);
                ResponseModel responseModel = beneficiaryFacade.addBeneficiary(beneficiary);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }
        });

        app.post("/beneficiary/account/add", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.ADD_BENEFICIARY_ACCOUNT);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                AddBeneficiaryAccount account = ctx.bodyValidator(AddBeneficiaryAccount.class)
                        .check(obj -> EnumUtils.isValidEnum(CurrencyCode.class, obj.getCurrency()))
                        .get();
                BeneficiaryFacade beneficiaryFacade = beneficiaryFacadeInjector.getInstance(BeneficiaryFacade.class);
                ResponseModel responseModel = beneficiaryFacade.addBeneficiaryAccount(account);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }
        });

        app.post("/transaction/transfer", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.TRANSFER);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                DoTransfer transfer = ctx.bodyValidator(DoTransfer.class)
                        .check(obj -> EnumUtils.isValidEnum(CurrencyCode.class, obj.getChangeCurrency()))
                        .get();
                ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
                ResponseModel responseModel = clientAccountFacade.doTransfer(transfer);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }
        });

        app.post("/transaction/refill", ctx -> {
            Set<ValidationMessage> validationMessages = SchemaValidator.validateSchema(ctx.body(), ApiRequestType.REFILL);
            if (!validationMessages.isEmpty()) {
                ctx.result(objectMapper.writeValueAsString(validationMessages));
            } else {
                DoRefill refill = ctx.bodyValidator(DoRefill.class)
                        .check(obj -> EnumUtils.isValidEnum(CurrencyCode.class, obj.getCurrency()))
                        .get();
                ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
                ResponseModel responseModel = clientAccountFacade.doRefill(refill);
                ctx.result(objectMapper.writeValueAsString(responseModel));
            }
        });

        app.get("/client/account/transaction/details/:account_no", ctx -> {
                    Integer accountNo = Integer.valueOf(ctx.pathParam("account_no"));
                    ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
                    GetTransactionDetails details = clientAccountFacade.getTransactionDetails(accountNo);
                    ctx.result(objectMapper.writeValueAsString(details));
                }
        );
    }

    public static void stopJavalin() {
        app.stop();
    }
}
