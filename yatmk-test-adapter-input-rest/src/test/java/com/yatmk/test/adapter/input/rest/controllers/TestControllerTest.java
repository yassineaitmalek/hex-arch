package com.yatmk.test.adapter.input.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import com.yatmk.test.ports.input.TestUseCase;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@WebMvcTest(controllers = TestController.class)
@ContextConfiguration(classes = TestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TestControllerTest {

    private static final String PATH = "/api/test";

    private static final String PATH_FRAGMENT = PATH + "/{id}/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @MockBean
    private TestUseCase testUseCase;

    @BeforeEach
    public void before() {
        //
    }

    @AfterEach
    public void after() {
        //
    }

    @Test
    public void printAllEndpoints() {
        handlerMapping
            .getHandlerMethods()
            .forEach((info, method) -> {
                String methodName = info.getMethodsCondition() + " " + info.getDirectPaths();
                System.out.println(methodName);
            });
    }

    @Test
    public void testCreate() throws Exception {
        TestCreation inputDTO = new TestCreation("obj", BigInteger.ONE, Boolean.TRUE, BigDecimal.valueOf(5.2));
        TestDTO uiDTO = new TestDTO(1L, "obj", BigInteger.ONE, Boolean.TRUE, BigDecimal.valueOf(5.2));

        Mockito.when(testUseCase.create(Mockito.any(TestCreation.class))).thenReturn(uiDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(PATH)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(inputDTO));

        ResultActions response = mockMvc.perform(request);

        response
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(uiDTO.getId().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr1", CoreMatchers.is(uiDTO.getAttr1())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr2", CoreMatchers.is(uiDTO.getAttr2().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr3", CoreMatchers.is(uiDTO.getAttr3())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr4", CoreMatchers.is(uiDTO.getAttr4().doubleValue())));
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;

        Mockito.doNothing().when(testUseCase).delete(Mockito.any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(PATH_FRAGMENT, id);

        ResultActions response = mockMvc.perform(request);

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testGet() throws Exception {
        TestDTO uiDTO = new TestDTO(1L, "obj", BigInteger.ONE, Boolean.TRUE, BigDecimal.valueOf(5.2));

        Long id = 1l;

        Mockito.when(testUseCase.get(Mockito.any(Long.class))).thenReturn(uiDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(PATH_FRAGMENT, id);

        Assertions.assertEquals(id, uiDTO.getId());

        ResultActions response = mockMvc.perform(request);

        response
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(uiDTO.getId().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr1", CoreMatchers.is(uiDTO.getAttr1())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr2", CoreMatchers.is(uiDTO.getAttr2().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr3", CoreMatchers.is(uiDTO.getAttr3())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr4", CoreMatchers.is(uiDTO.getAttr4().doubleValue())));
    }

    @Test
    public void testUpdate() throws Exception {
        TestUpdate inputDTO = new TestUpdate("obj2", null, null, null);

        TestDTO uiDTO = new TestDTO(1L, "obj2", BigInteger.ONE, Boolean.TRUE, BigDecimal.valueOf(5.2));

        Long id = 1L;

        Mockito.when(testUseCase.update(Mockito.any(Long.class), Mockito.any(TestUpdate.class))).thenReturn(uiDTO);

        Assertions.assertEquals(id, uiDTO.getId());
        Assertions.assertEquals(inputDTO.getAttr1(), uiDTO.getAttr1());
        Assertions.assertNotEquals(inputDTO.getAttr2(), uiDTO.getAttr2());
        Assertions.assertNotEquals(inputDTO.getAttr3(), uiDTO.getAttr3());
        Assertions.assertNotEquals(inputDTO.getAttr4(), uiDTO.getAttr4());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .patch(PATH_FRAGMENT, id)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(inputDTO));

        ResultActions response = mockMvc.perform(request);

        response
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(uiDTO.getId().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr1", CoreMatchers.is(uiDTO.getAttr1())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr2", CoreMatchers.is(uiDTO.getAttr2().intValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr3", CoreMatchers.is(uiDTO.getAttr3())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.attr4", CoreMatchers.is(uiDTO.getAttr4().doubleValue())));
    }
}
