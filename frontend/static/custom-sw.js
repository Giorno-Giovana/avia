console.log('service worker work')


self.addEventListener('notificationclick', function(event) {
    const notif = event.notification
    const action = event.action
    console.log(notif)
    console.log(action)
    if (action === 'confirm') {
        console.log('Ok')
        notif.close()
    } else {
        console.log('close')
        notif.close()
    }
})

self.addEventListener('notificationclose', function(event) {
    console.log('Уведомление закрыто')
})

//listen  push-событие с сервера уведомлений
self.addEventListener('push', function(event) {
    console.log('Уведомление получено', event)
    if (event.data) {
        data = JSON.parse(event.data.text())
    }
    let opt = {
        body: data.content, //это текст уведомления, более понятный, чем заголовок
        icon: './icons/app-icon-96x96.png', //может ссылаться на внешнее или локальное изображение
        image: './icon.png', // выводит изображение в уведомлении
        // dir: 'ltr', // left to right (ltr)
        lang: 'en-US', //standard
        vibrate: [100, 50, 200], // device vibrate
        badge: './icon.png', // маленький значок в ящике
        // sound: '../notif.mp3', // звук при получении уведомления (не вся поддержка)
        //! advance option
        // tag: 'confirm-notification', // мы устанавливаем тег, чтобы уведомление не складывалось на устройстве
        // renotify: true, // если тег такой же в некоторых уведомлениях, то статус уведомления будет считаться новым, и устройство будет вибрировать, поэтому, если оно ложно, то тот же тег, что и устройство, не будет вибрировать
        actions: [
            //! действие при выборе уведомления
            {
                action: 'confirm',
                title: 'Yes',
                icon: './icon.png'
            },
            {
                action: 'cancel',
                title: 'No',
                icon: './icon.png'
            }
        ]
    }
    event.waitUntil(self.registration.showNotification(data.title, opt))
})