package cloud.cosmin.checklister;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListUpdateIT extends BaseIT {
    @Test
    public void listUpdate() {
        var newList = createList("testlist");
        var updatedList = updateList(newList.id, "newtitle");
        assertEquals("newtitle", updatedList.title);
    }
}
