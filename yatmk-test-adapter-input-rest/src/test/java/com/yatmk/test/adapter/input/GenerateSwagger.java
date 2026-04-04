package com.yatmk.test.adapter.input;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class GenerateSwagger {
	@Autowired
	private WebApplicationContext context;

	private static final String SWAGGER_JSON_FILE = "/home/yf/Desktop/workspace/architectures/hex-arch/swagger.yaml";

	@Test
	public void generateSwagger() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/doc.yaml").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(
		        result -> {
			        FileUtils.writeStringToFile(
			                new File(SWAGGER_JSON_FILE), result.getResponse().getContentAsString(), StandardCharsets.UTF_8);
		        });
		Assertions.assertTrue(Files.exists(Path.of(SWAGGER_JSON_FILE)));
	}

}