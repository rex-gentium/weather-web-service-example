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
import zamyslov.centreit.testtask.json.RequestForRead;
import zamyslov.centreit.testtask.json.ResponseForWeatherEntryRead;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static zamyslov.centreit.testtask.ApplicationTests.urlGet;
import static zamyslov.centreit.testtask.ApplicationTests.objectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReadRequestTests {

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void init() {
        ApplicationTests.init();
    }

    @Test
    public void emptyWeatherEntryListTest() throws Exception {
        String requestJson = "{\"username\":\"baron\"," +
                "\"password\":\"baron74\"," +
                "\"cityId\":" + DataLoader.moscowId + "," +
                "\"date\":\"01-01-2018\"}";
        String expectedJson = "{\"resultState\":\"HANDLED\"," +
                "\"resultMessage\":null," +
                "\"cityName\":\"Москва\"," +
                "\"date\":\"01-01-2018\"," +
                "\"weatherConditionList\":[]}";
        mvc.perform(post(urlGet)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(expectedJson));
    }

    @Test
    public void oneWeatherEntryListTest() throws Exception {
        String requestJson = "{\"username\":\"baron\"," +
                "\"password\":\"baron74\"," +
                "\"cityId\":" + DataLoader.moscowId + "," +
                "\"date\":\"02-01-2018\"}";
        String actualJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();
        ResponseForWeatherEntryRead response = objectMapper.readValue(actualJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.HANDLED, response.getResultState());
        assertEquals("Москва", response.getCityName());
        assertEquals(LocalDate.of(2018, Month.JANUARY, 2), response.getDate());
        assertNotNull(response.getWeatherConditionList());
        assertEquals(1, response.getWeatherConditionList().size());
    }

    @Test
    public void multipleWeatherEntryListTest() throws Exception {
        String requestJson = "{\"username\":\"baron\"," +
                "\"password\":\"baron74\"," +
                "\"cityId\":" + DataLoader.moscowId + "," +
                "\"date\":\"03-01-2018\"}";
        String actualJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();
        ResponseForWeatherEntryRead response = objectMapper.readValue(actualJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.HANDLED, response.getResultState());
        assertEquals("Москва", response.getCityName());
        assertEquals(LocalDate.of(2018, Month.JANUARY, 3), response.getDate());
        assertNotNull(response.getWeatherConditionList());
        assert(response.getWeatherConditionList().size() > 1);
    }

    @Test
    public void invalidLoginTest() throws Exception {
        RequestForRead request = new RequestForRead();
        request.setUsername("graaf");
        String requestJson = objectMapper.writeValueAsString(request);
        String actualJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();
        ResponseForWeatherEntryRead response = objectMapper.readValue(actualJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
        assertNull(response.getWeatherConditionList());
    }

    @Test
    public void invalidPasswordTest() throws Exception {
        RequestForRead request = new RequestForRead();
        request.setUsername("graf");
        request.setPassword("1234");
        String requestJson = objectMapper.writeValueAsString(request);
        String actualJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();
        ResponseForWeatherEntryRead response = objectMapper.readValue(actualJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
        assertNull(response.getWeatherConditionList());
    }

    @Test
    public void invalidCityTest() throws Exception {
        RequestForRead request = new RequestForRead();
        request.setUsername("graf");
        request.setPassword("graf74");
        request.setCityId(-1L);
        String requestJson = objectMapper.writeValueAsString(request);
        String actualJson = mvc.perform(post(urlGet)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();
        ResponseForWeatherEntryRead response = objectMapper.readValue(actualJson, ResponseForWeatherEntryRead.class);
        assertEquals(BasicResponse.ResultState.ERROR, response.getResultState());
        assertNotNull(response.getResultMessage());
        assertNull(response.getWeatherConditionList());
    }

}
