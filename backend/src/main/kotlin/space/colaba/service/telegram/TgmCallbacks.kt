package space.colaba.service.telegram

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

internal object TgmCallbacks {

    fun Dispatcher.loginCallback(): TgmStatus {
        callbackQuery("LOGIN") {
            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Введите свой `email` от Шереметьево"
            )
        }
        return TgmStatus.STARTED
    }

    fun Dispatcher.firstCallback() {
        callbackQuery("FIRST") {
            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val user = callbackQuery.from

            val text = """
                        🎊 С возвращением, *${user.firstName}* 🎉! Начинаем тестирование.
                        🔘 _Вопрос № 1:_
                        Как исключить слово `фишинг` из поискового запроса в `yandex` или `google`?
                    """.trimIndent()
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = text,
                parseMode = ParseMode.MARKDOWN,
                replyMarkup = InlineKeyboardMarkup.create(
                    listOf(
                        InlineKeyboardButton.CallbackData("Не писать в запросе", "Nothing"),
                        InlineKeyboardButton.CallbackData("Добавить `-фишинг`", "SECOND"),
                    )
                )
            )
        }
    }





}
