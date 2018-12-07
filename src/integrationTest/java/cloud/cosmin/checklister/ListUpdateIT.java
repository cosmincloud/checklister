package cloud.cosmin.checklister;

import cloud.cosmin.checklister.dto.ListGetDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListUpdateIT extends BaseIT {
    @Test
    public void listUpdate() {
        ListGetDto newList = createList("testlist");
        ListGetDto updatedList = updateList(newList.getId(), "newtitle");
        assertEquals("newtitle", updatedList.getTitle());
    }
}
