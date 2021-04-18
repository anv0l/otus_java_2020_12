package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientsServlet extends HttpServlet {
    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String ALL_CLIENTS_PARAM = "allClients";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_ALERT_MESSAGE = "alertMessage";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter(PARAM_ACTION);

        Map<String, Object> resultMap = new HashMap<>();

        switch (action) {
            case ("add") -> {
                String name = request.getParameter(PARAM_NAME);
                String address = request.getParameter(PARAM_ADDRESS);
                String[] phones = request.getParameterValues(PARAM_PHONES);

                if (name.isEmpty() || address.isEmpty()) {
                    resultMap.put(PARAM_ALERT_MESSAGE, "Название или адрес не могут быть пустыми.");
                    break;
                }

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
            }
            default -> resultMap.put(PARAM_ALERT_MESSAGE, "Действие неопределено");
        }

        findAllClientsAndPutInResultMap(resultMap);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, resultMap));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        findAllClientsAndPutInResultMap(resultMap);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, resultMap));
    }

    private void findAllClientsAndPutInResultMap(Map<String, Object> resultMap) {
        var clients = dbServiceClient.findAll();
        resultMap.put(ALL_CLIENTS_PARAM, clients);
    }
}
