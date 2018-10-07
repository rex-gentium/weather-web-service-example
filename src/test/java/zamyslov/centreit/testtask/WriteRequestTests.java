package zamyslov.centreit.testtask;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import zamyslov.centreit.testtask.json.BasicResponse;
import zamyslov.centreit.testtask.json.RequestForWrite;
import zamyslov.centreit.testtask.json.ResponseForWeatherEntryRead;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zamyslov.centreit.testtask.ApplicationTests.urlGet;
import static zamyslov.centreit.testtask.ApplicationTests.urlPut;
import static zamyslov.centreit.testtask.ApplicationTests.objectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WriteRequestTests {

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void init() {
        ApplicationTests.init();
    }

    @Test
    public void writeAndReadEntryTest() throws Exception {
        String writeRequestJson = "{\"username\":\"graf\"," +
            "\"password\":\"graf74\"," +
            "\"cityId\":" + DataLoader.ekaterinburgId + "," +
            "\"date\":\"05-01-2018\"," +
            "\"weatherConditionId\":" + DataLoader.humidityId + "," +
            "\"weatherConditionValue\": 20}";
        String writeExpectedJson = "{\"resultState\":\"HANDLED\"," +
            "\"resultMessage\":null}";
        mvc.perform(post(urlPut)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeRequestJson))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(writeExpectedJson));
        String readRequestJson = "{\"username\":\"baron\"," +
            "\"password\":\"baron74\"," +
            "\"cityId\":" + DataLoader.ekaterinburgId + "," +
            "\"date\":\"05-01-2018\"}";
        String readExpectedJson = "{\"resultState\":\"HANDLED\"," +
            "\"resultMessage\":null," +
            "\"cityName\":\"Екатеринбург\"," +
            "\"date\":\"05-01-2018\"," +
            "\"weatherConditionList\":[" +
                "{\"name\":\"Относительная влажность воздуха\"," +
                "\"value\":20," +
                "\"measurementUnitName\":\"%\"}" +
            "]}";
        mvc.perform(post(urlGet)
            .contentType(MediaType.APPLICATION_JSON)
            .content(readRequestJson))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(readExpectedJson));
    }

    @Test
    public void noPermissionTest() throws Exception {
        String requestJson = "{\"username\":\"baron\"," +
            "\"password\":\"baron74\"," +
            "\"cityId\":" + DataLoader.ekaterinburgId + "," +
            "\"date\":\"08-01-2018\"," +
            "\"weatherConditionId\":" + DataLoader.humidityId + "," +
            "\"weatherConditionValue\": 25}";
        String responseJson = mvc.perform(post(urlPut)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void invalidLoginTest() throws Exception {
        RequestForWrite request = new RequestForWrite();
        request.setUsername("someusername");
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void invalidPasswordTest() throws Exception {
        RequestForWrite request = new RequestForWrite();
        request.setUsername("herzog");
        request.setPassword("1234");
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void invalidCityTest() throws Exception {
        RequestForWrite request = new RequestForWrite();
        request.setUsername("herzog");
        request.setPassword("herzog74");
        request.setCityId(-1L);
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void invalidIndicatorTest() throws Exception {
        RequestForWrite request = new RequestForWrite();
        request.setUsername("herzog");
        request.setPassword("herzog74");
        request.setCityId(DataLoader.ekaterinburgId);
        request.setWeatherConditionId(-1L);
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void invalidIndicatorValueTest() throws Exception {
        RequestForWrite request = new RequestForWrite();
        request.setUsername("herzog");
        request.setPassword("herzog74");
        request.setCityId(DataLoader.ekaterinburgId);
        request.setWeatherConditionId(DataLoader.humidityId);
        request.setWeatherConditionValue(146L);
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        BasicResponse response = objectMapper.readValue(responseJson, BasicResponse.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
    }

    @Test
    public void doubleWriteTest() throws Exception {
        String firstRequestJson = "{\"username\":\"herzog\"," +
                "\"password\":\"herzog74\"," +
                "\"cityId\":" + DataLoader.ekaterinburgId + "," +
                "\"date\":\"10-01-2018\"," +
                "\"weatherConditionId\":" + DataLoader.humidityId + "," +
                "\"weatherConditionValue\": 20}";
        String secondRequestJson = "{\"username\":\"graf\"," +
                "\"password\":\"graf74\"," +
                "\"cityId\":" + DataLoader.ekaterinburgId + "," +
                "\"date\":\"10-01-2018\"," +
                "\"weatherConditionId\":" + DataLoader.humidityId + "," +
                "\"weatherConditionValue\": 0}";
        String writeExpectedJson = "{\"resultState\":\"HANDLED\"," +
                "\"resultMessage\":null}";
        String readRequestJson = "{\"username\":\"baron\"," +
                "\"password\":\"baron74\"," +
                "\"cityId\":" + DataLoader.ekaterinburgId + "," +
                "\"date\":\"10-01-2018\"}";
        mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firstRequestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(writeExpectedJson));
        String firstReadResponseJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readRequestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseForWeatherEntryRead firstReadResponse = objectMapper.readValue(firstReadResponseJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.HANDLED, firstReadResponse.getResultState());
        assertEquals(Long.valueOf(20L), firstReadResponse.getWeatherConditionList().get(0).getValue());
        mvc.perform(post(urlPut)
                .contentType(MediaType.APPLICATION_JSON)
                .content(secondRequestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(writeExpectedJson));
        String secondReadResponseJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readRequestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseForWeatherEntryRead secondReadResponse = objectMapper.readValue(secondReadResponseJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.HANDLED, secondReadResponse.getResultState());
        assertEquals(Long.valueOf(0L), secondReadResponse.getWeatherConditionList().get(0).getValue());
    }
}
