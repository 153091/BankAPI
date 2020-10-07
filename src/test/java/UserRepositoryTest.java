import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.UserRepositoryImpl;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final UserRepositoryImpl userRepository = new UserRepositoryImpl(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/TablesInit.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void addClient() {
        try {
            User user = User.builder()
                    .id(100006)
                    .name("newName")
                    .email("new@mail.ru")
                    .build();
            userRepository.addUser(user);
            List<User> allUsers = userRepository.getAll();
            USERS_MATCHER.assertMatch(allClients, CLIENT_1, CLIENT_2, user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getClientById() {
        Client client;
        try {
            client = clientRepository.getClientById(CLIENT_1_ID);
            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void updateClient() {
        try {

            Client client = Client.builder()
                    .id(CLIENT_1.getId())
                    .name("update name")
                    .email("update@mail.ru")
                    .build();
            clientRepository.updateClient(client);
            Client client1 = clientRepository.getClientById(CLIENT_1.getId());
//            Assert.assertEquals(client,client1);
            CLIENTS_MATCHER.assertMatch(client, client1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void deleteClient() {
        try {
            Client client = Client.builder()
                    .name("update name")
                    .email("update@mail.ru")
                    .build();
            clientRepository.addClient(client);
            Client client1 = clientRepository.getClientById(100006);
            clientRepository.deleteClient(client1);
            List<Client> clients = clientRepository.getAll();
            CLIENTS_MATCHER.assertMatch(clients, CLIENT_1, CLIENT_2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    @Test
    public void getAllClients() {
        try {
            List<Client> allClients = clientRepository.getAll();
            Collections.sort(allClients, clientComparator);
            Assert.assertEquals(allClients.size(), 2);
            CLIENTS_MATCHER.assertMatch(allClients, CLIENTS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}