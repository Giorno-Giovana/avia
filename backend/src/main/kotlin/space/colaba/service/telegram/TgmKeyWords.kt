package space.colaba.service.telegram

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.handlers.TextHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import org.slf4j.LoggerFactory
import space.colaba.service.telegram.TelegramBotService.Companion.cacheStatuses
import space.colaba.service.telegram.TgmStatus.COORDINATES

internal object TgmKeyWords {

    fun Dispatcher.iAmLateWordTrigger() {
        text("багаж") {
            val chatId = chatId()
            log.info("🏺 Багаж: chat Id = $chatId")

            cacheStatuses.put(chatId, COORDINATES)

            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Найдем пункт сдачи багажа рядом с вами. \nНажмите на кнопку 📍 внизу",
                parseMode = ParseMode.MARKDOWN,
                replyMarkup = KeyboardReplyMarkup(
                    KeyboardButton("Найти 📍 рядом со мной", requestLocation = true),
                    resizeKeyboard = true
                ))
        }
    }



    fun TextHandlerEnvironment.chatId() = message.chat.id

    private val log = LoggerFactory.getLogger(this::class.java)

}
