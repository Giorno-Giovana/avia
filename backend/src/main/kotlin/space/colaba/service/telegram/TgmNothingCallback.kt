package space.colaba.service.telegram

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import space.colaba.config.frontendMainPage

internal object TgmNothingCallback {
    fun Dispatcher.nothingCallback() {
        callbackQuery("Nothing") {
            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Вы проиграли 😩 Пройдите дополнительное обучение $frontendMainPage",
                replyMarkup = ReplyKeyboardRemove()
            )
        }
    }

}
