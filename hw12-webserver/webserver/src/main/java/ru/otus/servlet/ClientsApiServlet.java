package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientsApiServlet extends HttpServlet {
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";
    private static final int ID_PATH_PARAM_POSITION = 1;

    private static final Logger log = LoggerFactory.getLogger(ClientsApiServlet.class);

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/clients");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);

        switch (id) {
            case ("add"):{

                String name = request.getParameter(PARAM_NAME);
                String address = request.getParameter(PARAM_ADDRESS);
                String[] phones = request.getParameterValues(PARAM_PHONES);

                var clientAddress = new AddressDataSet();
                var client = new Client(name);
                clientAddress.setStreet(address, client);
                var clientPhones = new ArrayList<PhoneDataSet>();

                if (phones != null) {
                    Arrays.stream(phones).forEach(phone -> clientPhones.add(new PhoneDataSet(phone, client)));
                }

                client.setAddress(clientAddress);
                client.setPhones(clientPhones);

                dbServiceClient.saveClient(client);
                break;
            }
            default:
                break; // operation not supported
        }

        response.sendRedirect("/clients");
    }
}
