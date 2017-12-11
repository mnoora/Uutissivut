/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import org.junit.After;
import javax.validation.constraints.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.util.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.test.web.servlet.*;
import org.springframework.web.context.*;
import org.springframework.http.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;

/**
 *
 * @author mnoora
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UutissivuTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

   /* @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void hallintapaneeliStatusOk() throws Exception {
        mockMvc.perform(get("/hallintapaneeli"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void kategoriaJarjestysStatusOk() throws Exception {
        mockMvc.perform(get("/jarjestys/kategoria"))
                .andExpect(status().isOk());
    }
    @Test
    public void kategoriaSivuStatusOk() throws Exception {
        mockMvc.perform(get("/Politiikka"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void edellisenViikonStatusOk() throws Exception {
        mockMvc.perform(get("/jarjestys/edellinenviikko"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void loginStatusOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }


  /*  @Test
    public void responseTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }

    @Test
    public void responseContainsTextUutiset() throws Exception {
        MvcResult res = mockMvc.perform(get("/"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        assertTrue(content.contains("Uutiset"));
    }
    
    @Test
    public void responseContainsTextHallintapaneeli() throws Exception {
        MvcResult res = mockMvc.perform(get("/hallintapaneeli"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        assertTrue(content.contains("Hallintapaneeli"));
    }*/
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
