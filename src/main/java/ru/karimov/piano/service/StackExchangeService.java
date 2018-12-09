package ru.karimov.piano.service;

import ru.karimov.piano.model.Items;

import java.io.IOException;

/**
 * Created by 777 on 06.12.2018.
 */
public interface StackExchangeService {
    Items search(int page, String query) throws IOException;
}
