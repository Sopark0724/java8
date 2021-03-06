package com.sopark.demobootweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Jaxb2Marshaller jaxb2Marshaller;

    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello")
                    .param("name", "sopark"))
                .andDo(print())
                .andExpect(content().string("hello sopark"));
    }

    @Test
    public void helloStatic() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello index")));
    }

    @Test
    public void helloMobile() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }

    @Test
    public void stringMessage() throws Exception{
        this.mockMvc.perform(get("/message")
                .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello person"));
    }

    @Test
    public void jsonMessage() throws Exception{
        Person person = new Person();
        person.setId(2019L);
        person.setName("sopark");
        String jsonString = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_JSON_UTF8)   // 내가 보내는 데이터에 대한 타입
                .accept(MediaType.APPLICATION_JSON_UTF8)        // 내가 받고 싶은 데이터 타입
                .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2019))
                .andExpect(jsonPath("$.name").value("sopark"));
    }


    @Test
    public void xmlMessage() throws Exception{
        Person person = new Person();
        person.setId(2019L);
        person.setName("sopark");

        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        jaxb2Marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_XML)   // 내가 보내는 데이터에 대한 타입
                .accept(MediaType.APPLICATION_XML)        // 내가 받고 싶은 데이터 타입
                .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/name").string("sopark"))
                .andExpect(xpath("person/id").string("2019"));
    }

    @Test
    public void handlerMethodArgumentResolverTest() throws Exception {
        this.mockMvc.perform(get("/login")
                .param("username", "sopark")
                .param("password", "1234")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(Matchers.is("sopark")))
                .andExpect(jsonPath("$.password").value(Matchers.is("1234")));
    }
}