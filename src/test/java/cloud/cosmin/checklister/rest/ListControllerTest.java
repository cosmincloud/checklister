package cloud.cosmin.checklister.rest;

import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.repo.ListRepo;
import cloud.cosmin.checklister.service.ConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import org.springframework.boot.test.mock.mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ListController.class)
@Import(ConverterService.class)
public class ListControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ListRepo listRepo;

    @Test
    public void testCreate() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        ListEntity savedEntity = new ListEntity();
        savedEntity.setId(randomUUID);
        savedEntity.setTitle("title");
        when(listRepo.save(any())).thenReturn(savedEntity);

        this.mvc.perform(
                post("/api/v1/list")
                        .contentType("application/json")
                        .content("{\"title\":\"mytitle\"}")
        ).andExpect(status().isCreated());


    }
}