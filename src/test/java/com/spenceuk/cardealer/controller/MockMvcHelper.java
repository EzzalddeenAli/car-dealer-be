package com.spenceuk.cardealer.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.spenceuk.cardealer.api.controller.Controller;
import com.spenceuk.cardealer.config.GlobalExceptionHandler;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MockMvcHelper {

  public static MockMvc mvcWithRestDocs(Controller controller,
                                        RestDocumentationContextProvider context) {
    return MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new GlobalExceptionHandler())
        .apply(documentationConfiguration(context)
          .uris().withHost("car-dealer.com")
          .and()
          .operationPreprocessors()
            .withResponseDefaults(prettyPrint())
            .withRequestDefaults(prettyPrint()))
        .build();

  }
}