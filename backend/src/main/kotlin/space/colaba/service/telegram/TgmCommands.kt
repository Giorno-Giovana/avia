package space.colaba.service.telegram

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ChatId.Companion.fromId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton.Url
import org.slf4j.LoggerFactory
import space.colaba.config.frontendMainPage
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

internal object TgmCommands {
    fun MessageHandlerEnvironment.chatId(): ChatId.Id = fromId(message.chat.id)

    fun Dispatcher.startCommand() {
        command("start") {
            log.info("🔑 TELEGRAM: Start called")
            val startImage = "/monster-ufo.png"
            bot.sendPhoto(chatId = fromId(message.chat.id), photo = "".localFile(startImage))
            val firstName = message.from?.firstName
            bot.sendMessage(
                chatId = fromId(update.message!!.chat.id),
                text = "Добрый день, $firstName ✌️. " +
                        "\nЧем вам помочь? Если у вас есть вопрос - отправьте его мне." +
                        "\nЕсли вы хотите узнать информацию о рейсе - введите номер своего билета " +
                        "или серию и номер паспорта или город и время вылета.",
                parseMode = MARKDOWN,
                replyMarkup = InlineKeyboardMarkup.createSingleButton(
                    Url("Подробности на сайте", "$frontendMainPage")
                )

            )
        }
    }
    private val log = LoggerFactory.getLogger(this::class.java)
    fun String.localFile(fileName: String): File = File("${pathInStaticFolder()}/$fileName")
    private fun String.pathInStaticFolder()       : Path = Paths.get("", "static", this).toAbsolutePath()

}
