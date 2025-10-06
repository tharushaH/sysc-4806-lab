package org.tharushaherath;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  // Replaces @MockBean - part of spring-boot-test since 3.4
    private AddressBookRepository repository;

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
        given(repository.findById(1L)).willReturn(Optional.of(mockBook));

        mockMvc.perform(get("/addressbooks/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("buddies"))
                .andExpect(model().attributeExists("addressbook"))
                .andExpect(model().attributeExists("buddies"));
    }
}