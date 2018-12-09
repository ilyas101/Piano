package ru.karimov.piano.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.karimov.piano.model.Items;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by 777 on 06.12.2018.
 */
@Service
public class StackExchangeServiceImpl implements StackExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackExchangeServiceImpl.class);

    @Value("${searchLink}")
    private String searchLink;

    @Override
    public Items search(int page, String query) throws IOException {
        String link = String.format(searchLink, page, query);
        LOGGER.debug("API link: " + link);
        String data = new String();

        try {
            data = loadData(link);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Data successfully loaded");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, Items.class);
    }

    private String loadData(String link) throws ClientProtocolException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseText = new String();
        HttpGet httpGet = new HttpGet(link);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            responseText = EntityUtils.toString(httpEntity);
        }

        return responseText;
    }
}
