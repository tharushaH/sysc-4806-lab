package org.tharushaherath;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootTest(classes = AddressBookControllerTest.TestApplication.class)
@AutoConfigureMockMvc
class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // This will be the Mockito.mock(AddressBookRepository.class) provided below
    @Autowired
    private AddressBookRepository repository;

    @SpringBootConfiguration
    @Import({ AddressBookController.class, AddressBookControllerTest.MockConfig.class })
    static class TestApplication {
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AddressBookRepository addressBookRepository() {
            return Mockito.mock(AddressBookRepository.class);
        }

        // Simple resolver so view names (like "addressbooks", "buddies") can be resolved in tests.
        @Bean
        public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver r = new InternalResourceViewResolver();
            r.setPrefix("/templates/"); // not used to load real files here, just resolves names
            r.setSuffix(".html");
            return r;
        }
    }

    @Test
    void shouldReturnAddressBooksView() throws Exception {
        given(repository.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(view().name("addressbooks"))
                .andExpect(model().attributeExists("addressbooks"));
    }

    @Test
    void shouldReturnBuddiesView() throws Exception {
        AddressBook mockBook = new AddressBook();
        // if your AddressBook has no setId(), that's fine — controller only needs the Optional non-empty
        given(repository.findById(1L)).willReturn(Optional.of(mockBook));

        mockMvc.perform(get("/addressbooks/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("buddies"))
                .andExpect(model().attributeExists("addressbook"))
                .andExpect(model().attributeExists("buddies"));
    }
}
