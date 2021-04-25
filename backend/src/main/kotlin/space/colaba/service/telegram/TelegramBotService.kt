package space.colaba.service.telegram

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.photos
import com.github.kotlintelegrambot.dispatcher.telegramError
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup.Companion.createSingleButton
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton.Url
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import space.colaba.config.frontendMainPage
import space.colaba.service.UserService
import space.colaba.service.telegram.TgmCommands.chatId
import space.colaba.service.telegram.TgmCommands.localFile
import space.colaba.service.telegram.TgmCommands.startCommand
import space.colaba.service.telegram.TgmKeyWords.iAmLateWordTrigger
import space.colaba.service.telegram.TgmMsgs.locationTrigger
import space.colaba.service.telegram.TgmMsgs.testEmail
import space.colaba.service.telegram.TgmMsgs.ticketNumberTrigger
import space.colaba.service.telegram.TgmStatus.LOGGED_IN
import space.colaba.service.telegram.predicats.IsEmail
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Service
@ConfigurationProperties("telegram")
class TelegramBotService(private val userService: UserService, private val db: TelegramDB) {
    lateinit var accessKey: String
    lateinit var bot: Bot

    @EventListener(ApplicationReadyEvent::class)
    fun createBot() {
        bot = bot {
            token = accessKey
            dispatch {
                startCommand()
                iAmLateWordTrigger()
                locationTrigger()
                ticketNumberTrigger()

                message(IsEmail) {
                    val email = message.text!!.trim().lowercase(Locale.getDefault())
                    val user = userService.find(email)
                    val text = if (user != null) {
                        val telegramUserId = message.from!!.id
                        val chatId = chatId().id
                        GlobalScope.launch {
                            val telegram = Telegram(
                                status = LOGGED_IN,
                                userId = telegramUserId,
                                chatId = chatId,
                                loginTriggerStart = ZonedDateTime.now()
                            )
                            db.save(telegram)
                            cacheEmail.put(email, chatId().id)
                            cacheStatuses.put(chatId().id, LOGGED_IN)
                        }
                        "🎉 С возвращением, ${user.firstName}! Мы нашли ваш рейс."
                    } else "🥺 Мы не нашли пользователя с таким email: *$email*." +
                            "\nСначала зарегистируйтесь здесь: $frontendMainPage/login " +
                            "\n_или попробуйте ввести email еще раз._"
                    bot.sendMessage(chatId = chatId(), text = text, parseMode = MARKDOWN)
                }

                photos { bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "Классные фотки!!! :P") }
                telegramError { println(error.getErrorMessage()) }
            }
        }
        bot.startPolling()
    }


    fun sendFastTrackPush(email: String? = null, chatId: Long? = null): Long {
        val tgmChatId = chatId?.let { ChatId.fromId(it) } ?: chatIdFromCache(testEmail)
        bot.sendPhoto(chatId = tgmChatId, photo = "".localFile("/qr-white.png"))
        bot.sendMessage(
            chatId = tgmChatId,
            text = "Вами была куплена приоритетная посадка. Воспользуйтесь QR-кодом.",
            parseMode = MARKDOWN,
            replyMarkup = createSingleButton(Url("Подробности на сайте", "$frontendMainPage"))
        )
        return tgmChatId.id
    }

    fun sendFoodPush(email: String? = null, chatId: Long? = null): Long {
        val tgmChatId = chatId?.let { ChatId.fromId(it) } ?: chatIdFromCache(testEmail)
        bot.sendPhoto(chatId = tgmChatId, photo = "".localFile("/QR.png"))
        bot.sendMessage(
            chatId = tgmChatId,
            text = "Заказ на еду создан. Ждите 5 минут на пункте. Покажите QR-код курьеру",
            parseMode = MARKDOWN,
            replyMarkup = createSingleButton(Url("Подробности на сайте", "$frontendMainPage"))
        )
        return tgmChatId.id
    }

    fun sendLateRace(email: String? = null, chatId: Long? = null): Long {
        val tgmChatId = chatId?.let { ChatId.fromId(it) } ?: chatIdFromCache(testEmail)
        bot.sendPhoto(chatId = tgmChatId, photo = "".localFile("/QR_delayed.png"))
        bot.sendMessage(
            chatId = tgmChatId,
            text = "\uD83E\uDD7A Нам очень жаль, но ваш рейс задерживается. Ориентировочное время задержки +4 часа. Номер нового выхода на посадку  мы сообщим позже. А пока получите ужин по QR-коду в ресторане аэропорта",
            parseMode = MARKDOWN,
            replyMarkup = createSingleButton(Url("Подробности на сайте", "$frontendMainPage"))
        )
        return tgmChatId.id
    }

    fun sendPush(email: String? = null, pushAvia: TgmPushDto, chatId: Long? = null): Long {
        log.info("🐲 Generic telegram: $pushAvia \n")
        val tgmChatId = chatId?.let { ChatId.fromId(it) } ?: chatIdFromCache(testEmail)

        pushAvia.adv?.run {
            log.warn("Photo to send (generic): $this")
            bot.sendPhoto(chatId = tgmChatId, photo = "".localFile(this)) }

        val actions = pushAvia.actions
        val buttons = if (actions != null && actions.isNotEmpty())
            InlineKeyboardMarkup.create(actions.map { Url(it.title!!, it.page) })
        else createSingleButton(Url("Подробности на сайте", "$frontendMainPage"))

        log.warn("OK (generic): Buttons done")
        GlobalScope.launch {
            delay(300)
            bot.sendMessage(
                chatId = tgmChatId,
                text = buildAviaGenericText(pushAvia),
                parseMode = MARKDOWN,
                replyMarkup = buttons
            )
        }
        return tgmChatId.id
    }


    fun buildAviaGenericText(pushAvia: TgmPushDto): String {
        val text = "Время: *${pushAvia.currentTime}*. До вылета *${pushAvia.timeToFlight}* часов." +
                "\n${pushAvia.status}" +
                "\n_Что нужно сделать_:\n✓ " +
                pushAvia.itemsToDo.joinToString(separator = "\n✓ ")
        println(text)
        return text
    }

    private fun chatIdFromCache(email: String? = null) = ChatId.fromId(cacheEmail.getIfPresent(testEmail)!!)

    companion object {
        internal val cacheStatuses: Cache<Long, TgmStatus> =
            Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build()
        internal val cacheEmail: Cache<String, Long> = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build()

        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
