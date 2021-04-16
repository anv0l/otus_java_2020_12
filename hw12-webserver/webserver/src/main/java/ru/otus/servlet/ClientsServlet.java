package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dao.UserDao;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientsServlet extends HttpServlet {
    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String ALL_CLIENTS_PARAM = "allClients";
    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    // Определим, что пользователь пытается сделать:
    // action = viewAll - просмотр всех клиентов
    // action = view&id= - просмотр конкретного клиента
    // action = add - добавить клиента
    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var clients = dbServiceClient.findAll();
        Map resultMap = new HashMap();
        resultMap.put(ALL_CLIENTS_PARAM, gson.toJson(clients));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, resultMap));
    }
}
