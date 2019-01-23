package cloud.cosmin.checklister.service

interface EventSink {
    fun accept(event: ItemUpdateEvent)
    fun accept(event: ItemRankEvent)
}