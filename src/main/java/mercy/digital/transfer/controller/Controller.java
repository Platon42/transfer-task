package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.facade.AccountFacade;
import mercy.digital.transfer.facade.ClientFacade;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.client.AddClient;

@Slf4j
public class Controller {

    public static void main(String[] args) {

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();
        Javalin app = Javalin.create().start(8000);
        app.get("hello", context -> context.result("hi!"));
        app.get("/client/add", ctx -> {
            AddClient client = objectMapper.readValue(ctx.body(), AddClient.class);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
            clientFacade.addClient(client);
        });

        app.get("/account/add", ctx -> {
            AddClientAccount account = objectMapper.readValue(ctx.body(), AddClientAccount.class);
            AccountFacade accountFacade = accountFacadeInjector.getInstance(AccountFacade.class);
            accountFacade.addClientAccount(account);
        });
    }
}
