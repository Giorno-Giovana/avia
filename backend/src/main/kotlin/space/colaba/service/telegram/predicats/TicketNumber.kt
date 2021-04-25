package space.colaba.service.telegram.predicats

import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.extensions.filters.Filter
import java.util.*

object TicketNumber : Filter {
    override fun Message.predicate(): Boolean = text?.trim()?.lowercase(Locale.getDefault())?.let {
        if (it.split(" ").size != 1) false
        else it.contains("19")
    } ?: false
}
