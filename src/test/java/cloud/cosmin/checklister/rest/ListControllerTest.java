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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void testGetSingle() throws Exception {
        ListEntity listEntity = new ListEntity();
        listEntity.setId(UUID.randomUUID());
        listEntity.setTitle("title");

        when(listRepo.findById(any())).thenReturn(Optional.of(listEntity));

        String id = "/api/v1/list/" + listEntity.getId().toString();
        MvcResult result = this.mvc.perform(get(id)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}