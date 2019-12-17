package ru.karimov.piano.controller;

import ru.karimov.piano.model.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karimov.piano.service.StackExchangeService;

import java.io.IOException;

/**
 * Created by 777 on 06.12.2018.
 */
@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private StackExchangeService service;

    private int page = 1;

    private String query;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String result(ModelMap model) {
        model.put("page", 0);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String search(ModelMap model, @RequestParam String query) {
        if (query == null || query.isEmpty())  {
            return "index";
        }

        if (this.page != 0) {
            this.page = 1;
        }
        this.query = query;

        preparationModel(model);
        return "index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String search(@PathVariable int id, ModelMap model) {
        page = id;
        preparationModel(model);
        return "index";
    }

    private void preparationModel(ModelMap model) {
        try {
            Items items = service.search(page, query);
            model.put("items", items);
            model.put("page", page);
        } catch (IOException e) {
            model.put("page", 0);
            model.put("error", e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
        } finally {
            model.put("query", query);
        }
    }
}
