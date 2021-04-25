package space.colaba.service.telegram

data class TgmPushDto(
    val currentTime  : String, // 11:55.
    val timeToFlight : String, // 8 часов
    val status       : String, // У вас еще много времени.
    val adv          : String? = null, // URL photo.
    val itemsToDo    : List<String>,
    val actions      : List<PageDto>? = null,

    val color           : String?=null,
    val floor           : String?=null,
    val showTicketField : Boolean?=null,
    val isDirectionRight: Boolean?=null,
    val currentAddress  : TgmAddress? = null
)

data class PageDto(val title: String?=null, val page: String)
data class TgmAddress(val longitude: Double?=null, val latitude: Double?=null)
