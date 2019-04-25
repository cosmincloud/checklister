package cloud.cosmin.checklister.it

import cloud.cosmin.checklister.it.TestHelper.post
import cloud.cosmin.checklister.it.TestHelper.put
import cloud.cosmin.checklister.lib.dto.ItemPostDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class ItemMoveSpec: WordSpec({
    "Checklister" should {
        "move an item between lists" {
            val list1 = ListPostDto("list1").post()
            val list2 = ListPostDto("list2").post()

            val item = ItemPostDto(list1.id, "content", "type").post()
            item.list!!.shouldBe(list1.id)

            val movedItem = ItemPostDto(list2.id, "content", "type").put(item.id!!)
            movedItem.list!!.shouldBe(list2.id)
        }
    }
})