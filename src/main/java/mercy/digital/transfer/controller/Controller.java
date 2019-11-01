package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;

@Slf4j
public class Controller {

    public static void main(String[] args) {
        Environment environment = Environment.PRODUCTION;
        PropUtils.setProperties(Environment.PRODUCTION);

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());
        Injector beneficiaryFacadeInjector = Guice.createInjector(new BeneficiaryFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();

        H2Utils.startDb(environment);
        Javalin app = Javalin.create().start(8000);

        app.get("/client/add", ctx -> {
            AddClient client = objectMapper.readValue(ctx.body(), AddClient.class);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
            ResponseModel responseModel = clientFacade.addClient(client);
            ctx.result(objectMapper.writeValueAsString(responseModel));
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
