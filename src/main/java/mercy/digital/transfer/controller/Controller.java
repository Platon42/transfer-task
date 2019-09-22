package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.facade.account.AccountFacade;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacade;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.client.GetClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;

@Slf4j
public class Controller {

    public static void main(String[] args) {

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());
        Injector beneficiaryFacadeInjector = Guice.createInjector(new BeneficiaryFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();

        Javalin app = Javalin.create().start(8000);
        app.get("/client/add", ctx -> {
            AddClient client = objectMapper.readValue(ctx.body(), AddClient.class);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
            clientFacade.addClient(client);
        });
        app.get("/client/get", ctx -> {
            GetClient getClient = objectMapper.readValue(ctx.body(), GetClient.class);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
            //clientFacade;
        });
        app.get("/beneficiary/add", ctx -> {
            AddBeneficiary beneficiary = objectMapper.readValue(ctx.body(), AddBeneficiary.class);
            BeneficiaryFacade beneficiaryFacade = beneficiaryFacadeInjector.getInstance(BeneficiaryFacade.class);
            beneficiaryFacade.addBeneficiary(beneficiary);
        });

        app.get("/client/account/add", ctx -> {
            AddClientAccount account = objectMapper.readValue(ctx.body(), AddClientAccount.class);
            AccountFacade accountFacade = accountFacadeInjector.getInstance(AccountFacade.class);
            ResponseModel responseModel = accountFacade.addClientAccount(account);
            ctx.result(objectMapper.writeValueAsString(responseModel));
        });
    }
}
