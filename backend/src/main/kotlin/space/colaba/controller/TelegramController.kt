package space.colaba.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import space.colaba.service.telegram.TelegramBotService
import space.colaba.service.telegram.TgmPushDto
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping(path = ["/telegram"])
class TelegramController(private val telegramBotService: TelegramBotService) {

    @PostMapping("/generic")
    fun push(@RequestBody data: TgmPushDto, request: HttpServletRequest): Long {
        log.info("💰 user push")
        return telegramBotService.sendPush(pushAvia = data)
    }

    @PostMapping("/fast-track")
    fun buyFatTrack(@RequestParam chatId: Long?, request: HttpServletRequest): Long {
        log.info("💰 user  bought FAST-TRACK")
        return telegramBotService.sendFastTrackPush(chatId = chatId)
    }

    @PostMapping("/food")
    fun buyFood(@RequestParam chatId: Long?, request: HttpServletRequest): Long {
        log.info("💰 user bought FOOD")
        return telegramBotService.sendFoodPush(chatId = chatId)
    }

    @PostMapping("/late")
    fun lateRace(@RequestParam chatId: Long?, request: HttpServletRequest): Long {
        log.info("🌿 user late")
        return telegramBotService.sendLateRace(chatId = chatId)
    }


    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
