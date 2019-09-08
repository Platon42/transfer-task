package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import express.Express;
import express.ExpressRouter;
import mercy.digital.transfer.facade.AccountFacade;
import mercy.digital.transfer.facade.ClientFacade;
import mercy.digital.transfer.facade.ClientFacadeImpl;
import mercy.digital.transfer.presentation.account.AddAccount;
import mercy.digital.transfer.service.impl.AccountServiceImpl;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class Controller {

    private static JsonToClass jsonToClass = new JsonToClass();

    private final ClientFacade clientFacade;
    private final AccountFacade accountFacade;


    @Inject
    Controller(ClientFacade clientFacade, AccountFacade accountFacade) {
        this.clientFacade = clientFacade;
        this.accountFacade = accountFacade;
    }

    public static void main(String[] args) {
        Express app = new Express() {{
            // Define root greeting
            get("/", (req, res) -> res.send("Hello World!"));

            // Define home routes
            use("/home", new ExpressRouter(){{
                get("/account/get/:id", (req, res) -> {
                    String userId = req.getParam("id");
                });
                get("/account/add", (req, res) -> {
                    String context = req.getContext();
                    AddAccount addAccount =
                            (AddAccount) jsonToClass.toClass(req.getContext(), AddAccount.class);

                });
                get("/sponsors", (req, res) -> res.send("Sponsors page"));
            }});

            // Define root routes
            use("/", new ExpressRouter(){{
                get("/login", (req, res) -> res.send("Login page"));
                get("/register", (req, res) -> res.send("Register page"));
                get("/contact", (req, res) -> res.send("Contact page"));
            }});

        }};

        app.listen(8000);
    }
}
