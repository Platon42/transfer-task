package mercy.digital.transfer.facade.client;

import mercy.digital.transfer.presentation.client.AddClient;

import java.sql.SQLException;

public interface ClientFacade {
    void addClient(AddClient client) throws SQLException;
}