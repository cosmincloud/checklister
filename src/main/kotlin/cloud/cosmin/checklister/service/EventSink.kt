package cloud.cosmin.checklister.service

interface EventSink {
    fun accept(event: ItemEvent)
}