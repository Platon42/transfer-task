package mercy.digital.transfer.facade.client;

import com.google.inject.Inject;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.client.ClientService;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GuiceExtension.class)
@IncludeModule(ClientFacadeModule.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientFacadeImplTest {

    @Inject
    private ClientFacade clientFacade;
    private AddClient addClient;




/*    @InjectMocks
    private ClientFacade clientFacade;*/
/*    @InjectMocks
    private ClientService clientService;*/

    @BeforeAll
    void initTest(){

        addClient = new AddClient();
        Date date = new Date(2019-10-10);
        String firstName = "Ivan";
        String lastName = "Petrov";
        String middle = "Oleksiyviechis";

        addClient.setBirthday(date);
        addClient.setFirstName(firstName);
        addClient.setLastName(lastName);
        addClient.setMiddleName(middle);
        addClient.setSex(0);
        addClient.setResidentCountry("USA");
    }

    @Test
    void addClient() {
        ClientService clientService = Mockito.mock(ClientService.class);

        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setId(10);
        expectedResponseModel.setMessage("Success client added");


        when(clientService.addEntityClient(any())).thenReturn(10);
        //when(clientFacade.addClient(any())).thenReturn(expectedResponseModel);
        ResponseModel actualResponseModel = clientFacade.addClient(addClient);

        //System.out.println(actualResponseModel);
        Assertions.assertEquals(expectedResponseModel.getMessage(),actualResponseModel.getMessage());
        Assertions.assertEquals(expectedResponseModel.getId(),expectedResponseModel.getId());


    }
}