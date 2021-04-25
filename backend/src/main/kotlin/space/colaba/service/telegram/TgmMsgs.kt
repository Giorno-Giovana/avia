package space.colaba.service.telegram

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup.Companion.createSingleButton
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton.Url
import com.github.kotlintelegrambot.extensions.filters.Filter
import org.slf4j.LoggerFactory
import space.colaba.config.frontendMainPage
import space.colaba.service.telegram.TelegramBotService.Companion.cacheEmail
import space.colaba.service.telegram.TelegramBotService.Companion.cacheStatuses
import space.colaba.service.telegram.TgmCommands.chatId
import space.colaba.service.telegram.predicats.TicketNumber

internal object TgmMsgs {
    var testEmail = "tmtaniuha@gmail.com"


    fun Dispatcher.locationTrigger() {
        message(Filter.Location) {
            val chatId = chatId().id
            cacheStatuses.getIfPresent(chatId)?.run {
                log.info("Coordinates: chatId=${chatId}, status=$this, location=${message.location}")
                if (this == TgmStatus.COORDINATES) {
                    cacheStatuses.put(chatId, TgmStatus.STARTED)
                    bot.sendMessage(
                        chatId = chatId(),
                        text = "Мы нашли пункт сдачи багажа 📍 `ул. Пятницкая 65/10`  в *2х* минутах от вас. " +
                                "\nВы можете сдать багаж самостоятельно, либо заказать курьера.",
                        parseMode = MARKDOWN,
                        replyMarkup = createSingleButton(
                            Url("Подробности на сайте", "$frontendMainPage")
                    ))
    } } } }


    fun Dispatcher.ticketNumberTrigger() {
        message(TicketNumber) {
            val chatId = chatId().id
            log.info("Coordinates: chatId=${chatId}, location=${message.location}")
            log.info("🚁 chatId: [${chatId().id}] for TEST user: [$testEmail]")

            cacheEmail.put(testEmail, chatId().id)

            cacheStatuses.put(chatId, TgmStatus.LOGGED_IN)

            bot.sendMessage(
                chatId = chatId(),
                text = "\uD83C\uDF89 С возвращением! Мы нашли ваш рейс." +
                        "\nОтправление: 19:55 (вскр, 25 апреля)" +
                        "\nПрибытие: 20:55" +
                        "\nГейт: *32*",
                parseMode = MARKDOWN,
                replyMarkup = createSingleButton(
                    Url("Подробности на сайте", "$frontendMainPage")
            ))
    } }

    private val log = LoggerFactory.getLogger(this::class.java)

}
