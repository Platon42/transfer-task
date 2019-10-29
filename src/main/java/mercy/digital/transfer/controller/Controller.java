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
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class Controller {

    public static void setProperties(Environment environment) {
        switch (environment) {
            case TEST:
                System.setProperty("db.url", "jdbc:h2:mem:test_db");
                break;
            case PRODUCTION:
                System.setProperty("db.url", "jdbc:h2:tcp://localhost:9092/mem:transfer");
                break;
            default:
                break;
        }
    }

    public static void startDb(Environment environment) {
        switch (environment) {
            case PRODUCTION:
                try {
                    Server.createTcpServer("-tcpPort", "9092", "-tcp", "-tcpAllowOthers", "-ifNotExists").start();
                    Class.forName("org.h2.Driver");
                    Connection conn = DriverManager.getConnection(
                            System.getProperty("db.url"), "sa", "sa");

                    RunScript.execute(conn, new FileReader("./config/init.sql"));
                } catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
                    log.error("Cannot start PROD database " + e.getLocalizedMessage());
                }
                break;
            case TEST:
                try {
                    Class.forName("org.h2.Driver");
                    Connection conn = DriverManager.getConnection(
                            System.getProperty("db.url"), "sa", "sa");
                    RunScript.execute(conn, new FileReader("./config/init.sql"));
                } catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
                    log.error("Cannot start TEST database " + e.getLocalizedMessage());
                }
                break;
        }
    }

    public static void main(String[] args) {
        Environment environment = Environment.PRODUCTION;
        setProperties(Environment.PRODUCTION);

        Injector clientFacadeInjector = Guice.createInjector(new ClientFacadeModule());
        Injector accountFacadeInjector = Guice.createInjector(new AccountFacadeModule());
        Injector beneficiaryFacadeInjector = Guice.createInjector(new BeneficiaryFacadeModule());

        ObjectMapper objectMapper = new ObjectMapper();

        startDb(environment);
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

        /*
        app.get("/client/get", ctx -> {
            GetClient getClient = objectMapper.readValue(ctx.body(), GetClient.class);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
        });
        */
        app.get("/client/account/transaction/details/:account_no", ctx -> {
            Integer accountNo = Integer.valueOf(ctx.pathParam("account_no"));
            ClientAccountFacade clientAccountFacade = accountFacadeInjector.getInstance(ClientAccountFacade.class);
            GetTransactionDetails details = clientAccountFacade.getTransactionDetails(accountNo);
            ctx.result(objectMapper.writeValueAsString(details));
        });


    }
}
