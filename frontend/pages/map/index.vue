<template>
  <v-container>
    <!--    <v-dialog-->
    <!--        v-model="dialog"-->
    <!--        persistent-->
    <!--        max-width="600px">-->
    <!--      <v-card>-->
    <!--        <v-card-title>-->
    <!--          <span class="headline" style="overflow-wrap: normal !important; word-break: keep-all;">Воспользоваться помощником</span>-->
    <!--        </v-card-title>-->
    <!--        <v-card-text>-->
    <!--          <v-container>-->
    <!--            <v-row>-->
    <!--              <v-col cols="12">-->
    <!--                <v-text-field label="Номер рейса или билета" required></v-text-field>-->
    <!--              </v-col>-->
    <!--            </v-row>-->
    <!--            <v-row>-->
    <!--              <v-col cols="12">-->
    <!--                <div class="text-caption font-weight-thin">* обратите внимание, что нажав кнопку "Подписаться" вы даете-->
    <!--                  согласие на получение данных о ваших перемещениях до момента посадки в самолет-->
    <!--                </div>-->
    <!--              </v-col>-->
    <!--            </v-row>-->
    <!--          </v-container>-->
    <!--        </v-card-text>-->
    <!--        <v-card-actions>-->
    <!--          <v-spacer></v-spacer>-->
    <!--          <v-btn color="grey lighten-1" text @click="dialog = false">-->
    <!--            Отмена-->
    <!--          </v-btn>-->
    <!--          <v-btn color="primary" depressed @click="subscribe">-->
    <!--            Подписаться-->
    <!--          </v-btn>-->
    <!--&lt;!&ndash;          <v-btn color="primary" depressed @click="subscribe">&ndash;&gt;-->
    <!--&lt;!&ndash;            Чат-бот&ndash;&gt;-->
    <!--&lt;!&ndash;          </v-btn>&ndash;&gt;-->
    <!--        </v-card-actions>-->
    <!--      </v-card>-->
    <!--    </v-dialog>-->
    <v-row style="height: 400px">
      <v-col cols="12" class="pa-0">
        <MapBox :address="address"
                :zoom='zoom' :pitch='pitch'
                @map-instantiated='initMap'/>
        <div class="BeaconInfo">
          <div><strong>BLE Tag Id:</strong>&nbsp;<span id="bleid"></span></div>
          <div><strong>Lng:</strong>&nbsp;<span id="lng"></span></div>
          <div><strong>Lat:</strong>&nbsp;<span id="lat"></span></div>
          <div><strong>Место:</strong>&nbsp;<span id="place"></span></div>
          <div><strong>Этаж:</strong>&nbsp;<span id="floor"></span></div>
          <div><strong>Статус:</strong>&nbsp;<span id="status"></span></div>
          <div><strong>Тип:</strong>&nbsp;<span id="type"></span></div>
          <div><strong>Оповещения сегодня:</strong>&nbsp;<span id="today"></span></div>
          <div><strong>Оповещения за все время:</strong>&nbsp;<span id="all"></span></div>
        </div>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-switch
            v-model="fastTrackProcedure"
            label="Сценарий с FastTrack"
            color="orange"
            value="1"
            hide-details
        ></v-switch>
      </v-col>
    </v-row>
    <v-row style="height: 200px">
      <v-col cols="12" v-if="fastTrackProcedure==='1'">
        <v-btn @click="homePosition()" depressed outlined style="text-transform: none" class="text-caption my-1">1.
          Путешествие начинается дома
        </v-btn>
        <v-btn @click="aeroexpressStartPosition()" outlined depressed style="text-transform: none"
               class="text-caption my-1">2. Аэроэкспресс Белорусский вокзал
        </v-btn>
        <v-btn @click="aeroexpressEndPosition()" outlined depressed style="text-transform: none"
               class="text-caption my-1">3. Аэроэкспресс Шереметьево
        </v-btn>
        <v-btn @click="securityGateAtEnter()" outlined depressed style="text-transform: none" class="text-caption my-1">
          4. Безопасность на входе(FastTrack)
        </v-btn>
        <v-btn @click="fastTrackSecurity()" outlined depressed style="text-transform: none" class="text-caption my-1">
          5. Прошел досмотр вещей(FastTrack)
        </v-btn>
        <v-btn @click="fastTrackPassport()" outlined depressed style="text-transform: none" class="text-caption my-1">
          6. Паспортный контроль(FastTrack)
        </v-btn>
        <v-btn @click="wrongDirection()" outlined depressed style="text-transform: none" class="text-caption my-1">
          7. Перепутал Gate и пошел в другом направлении
        </v-btn>
        <v-btn @click="atGate()" outlined depressed style="text-transform: none" class="text-caption my-1">
          8. У своего выхода
        </v-btn>
        <v-btn @click="showAdv2()" outlined depressed style="text-transform: none" class="text-caption my-1">
          9. Рекламный сигнал у выхода на посадку
        </v-btn>
      </v-col>


      <v-col cols="12" v-else>
        <v-btn @click="homePosition()" depressed outlined style="text-transform: none" class="text-caption my-1">
          1. Путешествие начинается дома
        </v-btn>
        <v-btn @click="taxiEndPosition()" outlined depressed style="text-transform: none" class="text-caption my-1">
          2. На парковке
        </v-btn>
        <v-btn @click="securityGateAtEnter()" outlined depressed style="text-transform: none" class="text-caption my-1">
          3. Безопасность на входе и регистрация
        </v-btn>
        <v-btn @click="standardSecurity()" outlined depressed style="text-transform: none" class="text-caption my-1">
          4. Прошел досмотр вещей
        </v-btn>
        <v-btn @click="standardPassport()" outlined depressed style="text-transform: none" class="text-caption my-1">
          5. Паспортный контроль
        </v-btn>
        <v-btn @click="showAdv1()" outlined depressed style="text-transform: none" class="text-caption my-1">
          6. Реклама после прохождения формальностей
        </v-btn>
        <v-btn @click="atGate()" outlined depressed style="text-transform: none" class="text-caption my-1">
          7. У своего выхода
        </v-btn>
        <v-btn @click="showAdv2()" outlined depressed style="text-transform: none" class="text-caption my-1">
          8. Рекламный сигнал у выхода на посадку
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      map: null,
      marker: null,
      dialog: false,
      beacons: require('assets/data/beacons.json'),
      address: {
        longitude: 37.41753445212805,
        latitude: 55.964061386824426
      },
      currentMarkers: [],
      userLocation: {
        lng: '',
        lat: ''
      },
      fastTrackProcedure: 0,
      zoom: 15,
      pitch: 0,
      index: 0,
      mapboxgl: require('mapbox-gl/dist/mapbox-gl.js'),
      currentUserFlowInfo: {},
      currentCase: {},


      caseWithFastTrack: {
        userFlowInfoHome1: {
          timeToFlight: '8 часов',
          floor: '-',
          color: 'black',
          currentTime: '11:55',
          isDirectionRight: true,
          showTicketField: true,
          currentAddress: {
            longitude: 37.6352030223305,
            latitude: 55.73271827848768
          },
          status: 'У вас еще много времени',
          itemsToDo: [
            'Проверить документы',
            'Зарегистрироваться он-лайн',
            'Подписаться на обновления рейса',
            'Купить билет на аэроэкспресс',
            'Заказать услугу "Доставка багажа"',
            'Перестать волноваться - вы все успеете ✈️'],
          actions: [
            {title: 'Услуга доставки багажа', page: 'https://colaba.space/luggage'}
          ]
        },
        userFlowInfoAeroexpressStart2: {
          timeToFlight: '~3 часа',
          floor: '-',
          currentTime: '16:20',
          isDirectionRight: true,
          showTicketField: false,
          color: 'black',
          currentAddress: {
            longitude: 37.580449184952954,
            latitude: 55.776615081751885
          },
          status: 'Вы - молодец, приедете в аэропорт с запасом.',
          itemsToDo: [
            'Скоро отправление поезда',
            'Сесть в кресло и расслабиться 🚆'
          ],
          actions: []
        },
        userFlowInfoAeroexpressEnd3: {
          timeToFlight: '~1 час 40 минут',
          floor: '-',
          currentTime: '18:10',
          isDirectionRight: true,
          color: '#f5c614',
          showTicketField: false,
          currentAddress: {
            longitude: 37.41627305922782,
            latitude: 55.96390253571778
          },
          status: 'Осталось самое простое - пройти все формальности',
          itemsToDo: [
            'Ваш терминал F',
            'Ориентируйтесь на табличку "Вылет". Пройдите первый досмотр на входе',
            'Ваш багаж уже готов к вылету',
            'Ускорить прохождение к самолету всего за 750₽'
          ],
          actions: [
            {title: 'Услуга FastTrack', page: 'https://colaba.space/fasttrack'}
          ]
        },
        userFlowInfoAtEntrance4: {
          timeToFlight: '~1 час 35 минут',
          floor: '1',
          currentTime: '18:15',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.414525443645175,
            latitude: 55.96489972914489
          },
          status: 'Вы у входа терминала F',
          itemsToDo: [
            'Вы купили FastTrack - вас встретит сотрудник аэропорта у входа B12',
            'Приготовьте паспорт и посадочный талон'
          ],
          actions: []
        },
        userFlowInfoSecurity5: {
          timeToFlight: '~1 час 25 минут',
          floor: '1',
          currentTime: '18:30',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41395459904106,
            latitude: 55.96575306076869
          },
          status: 'Вы внутри!',
          itemsToDo: [
            'С FastTrack вам не о чем беспокоиться'
          ],
          actions: []
        },
        userFlowInfoPassport6: {
          timeToFlight: '~1 час 15 минут',
          floor: '2',
          currentTime: '18:40',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41469475875306,
            latitude: 55.96660409829863
          },
          status: 'Осталось совсем немного - вы прошли досмотр всего за 15 минут!',
          itemsToDo: [
            'Следуйте за cотрудником к "Паспортному контролю"',
            'Приготовьте паспорт, снимите головной убор, солнцезащитные очки, улыбайтесь ☺️'
          ],
          actions: []
        },
        userFlowInfoWrongDirection7: {
          timeToFlight: '35 минут',
          floor: '2',
          currentTime: '19:20',
          color: '#f5c614',
          isDirectionRight: false,
          showTicketField: false,
          currentAddress: {
            longitude: 37.413388915596215,
            latitude: 55.96408113101663
          },
          status: 'Вы пошли в обратную сторону❗',
          itemsToDo: [
            '✋ Развернитесь и следуйте за указателями к выходу 32. Осталось мало времени'
          ],
          actions: []
        },
        userFlowInfoAtGate8: {
          timeToFlight: '30 минут',
          floor: '2',
          currentTime: '19:25',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41358696511156,
            latitude: 55.966617866724
          },
          status: 'Вы на месте!',
          itemsToDo: [
            'Ожидайте начало посадки'
          ],
          actions: []
        },
        userFlowInfoAdv2: {
          timeToFlight: '25 минут',
          floor: '2',
          currentTime: '19:30',
          color: '#6123d4',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41364232518663,
            latitude: 55.96675901466608
          },
          status: 'Чтобы вам приятнее было ожидать начало посадки - воспользуйтесь специальным предложением ',
          adv: '/adv22.png',
          itemsToDo: [
            'Заказать еду 🍔 ☕'
          ],
          actions: [
            {title: '🔥 Успей получить скидку на заказ', page: 'https://colaba.space/orderfood?ad=2'}
          ]
        },
      },
      caseWithoutFastTrack: {
        userFlowInfoHome1: {
          timeToFlight: '9 часов',
          floor: '-',
          color: 'black',
          currentTime: '10:55',
          isDirectionRight: true,
          showTicketField: true,
          currentAddress: {
            longitude: 37.6352030223305,
            latitude: 55.73271827848768
          },
          status: 'У вас еще много времени',
          itemsToDo: [
            'Проверить документы',
            'Зарегистрироваться он-лайн',
            'Подписаться на обновления рейса',
            'Вызвать такси',
            '✨ Заказать услугу "Доставка багажа"',
            'Перестать волноваться - вы все успеете ✈️'
          ],
          actions: [
            {title: '🧳 Услуга доставки багажа здесь', page: 'https://colaba.space/luggage'}
          ]
        },
        userFlowInfoTaxiStart2: {
          timeToFlight: '~3 часа',
          floor: '-',
          currentTime: '16:20',
          isDirectionRight: true,
          showTicketField: false,
          color: 'black',
          currentAddress: {
            longitude: 37.6352030223305,
            latitude: 55.73271827848768
          },
          status: 'Вы - молодец, приедете в аэропорт с запасом.',
          itemsToDo: [
            'Такси уже ждет вас у подъезда'
          ],
          actions: []
        },
        userFlowInfoTaxiEnd3: {
          timeToFlight: '~1 час 40 минут',
          floor: '-',
          currentTime: '18:10',
          isDirectionRight: true,
          color: '#f5c614',
          showTicketField: false,
          currentAddress: {
            longitude: 37.40399765642121,
            latitude: 55.96083395648978
          },
          status: 'Осталось самое простое - пройти все формальности',
          itemsToDo: [
            'Ваш терминал F',
            'Ориентируйтесь на табличку "Вылет". Пройдите первый досмотр на входе',
            'Далее направляйтесь к стойкам регистрации 112-117',
            'А еще вы можете ускорить прохождение к самолету всего за 750₽ 🏃💨'
          ],
          actions: [
            {title: '⚡ Услуга FastTrack', page: 'https://colaba.space/fasttrack'}
          ]
        },
        userFlowInfoAtEntrance4: {
          timeToFlight: '~1 час 35 минут',
          floor: '1',
          currentTime: '18:15',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.414525443645175,
            latitude: 55.96489972914489
          },
          status: 'Вы внутри терминала F!',
          itemsToDo: [
            'Приготовьте паспорт и багаж 💼',
            'Сдайте багаж на стойке регистрации',
          ],
          actions: []
        },
        userFlowInfoSecurity5: {
          timeToFlight: '~1 час 25 минут',
          floor: '1',
          currentTime: '18:30',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41395459904106,
            latitude: 55.96575306076869
          },
          status: 'Вы зарегистрированы на рейс',
          itemsToDo: [
            'Приготовьте паспорт и посадочный талон',
            'Поднимитесь на 2й этаж по эскалатору рядом',
            'Идите прямо в Зеленый коридор, если вам не нужно ничего декларировать 🛃'
          ],
          actions: []
        },
        userFlowInfoPassport6: {
          timeToFlight: '~1 час 15 минут',
          floor: '2',
          currentTime: '18:40',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41469475875306,
            latitude: 55.96660409829863
          },
          status: 'Осталось совсем немного - вы прошли досмотр 🛡️',
          itemsToDo: [
            'Следуйте за указателем "Паспортный контроль" 🛂 📵',
            'Достаньте паспорт из обложки, снимите головной убор, солнцезащитные очки, улыбайтесь ☺️'
          ],
          actions: []
        },
        userFlowInfoAdv1: {
          timeToFlight: '55 минут',
          floor: '2',
          currentTime: '19:00',
          color: '#6123d4',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.415648181995266,
            latitude: 55.9665318028284
          },
          status: 'Вот и все - вы справились 👍, осталось найти ваш выход на посадку 32',
          adv: '/adv1.jpg',
          itemsToDo: [
            'Заказать кофе ☕'
          ],
          actions: [
            {title: '🔥 Горячее предложение от партнера', page: '/orderfood?ad=1'}
          ]
        },
        userFlowInfoAtGate8: {
          timeToFlight: '30 минут',
          floor: '2',
          currentTime: '19:25',
          color: '#f5c614',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41358696511156,
            latitude: 55.966617866724
          },
          status: 'Вы на месте!',
          itemsToDo: [
            'Ожидайте начало посадки'
          ],
          actions: []
        },
        userFlowInfoAdv2: {
          timeToFlight: '25 минут',
          floor: '2',
          currentTime: '19:30',
          color: '#6123d4',
          isDirectionRight: true,
          showTicketField: false,
          currentAddress: {
            longitude: 37.41364232518663,
            latitude: 55.96675901466608
          },
          status: 'Чтобы вам приятнее было ожидать начало посадки - воспользуйтесь специальным предложением ',
          adv: '/adv22.png',
          itemsToDo: [
            'Заказать еду 🍔 ☕'
          ],
          actions: [
            {title: '💡 Заказ еды', page: '/orderfood?ad=2'}
          ]
        },
      }
    }
  },
  watch: {
    fastTrackProcedure(val) {
      console.log('fastTrackProcedure = ' + val)
      if (this.currentMarkers !== null) {
        for (let i = this.currentMarkers.length - 1; i >= 0; i--) {
          this.currentMarkers[i].remove();
        }
      }
      if (val === '1') {
        this.currentCase = this.caseWithFastTrack
      } else {
        console.log('this.currentCase= ')
        this.currentCase = this.caseWithoutFastTrack;
        console.log(this.currentCase)
      }
    }
  },
  methods: {
    success(position) {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      this.userLocation.lat = latitude;
      this.userLocation.lng = longitude;
    },

    error() {
      console.log('Unable to retrieve your location');
    },
    subscribe() {
      this.dialog = false;
      if ("geolocation" in navigator) {
        if (!navigator.geolocation) {
          console.log('Geolocation is not supported by your browser');
        } else {
          console.log('Locating…');
          navigator.geolocation.getCurrentPosition(this.success, this.error);
        }
      } else {

      }
      this.currentUserFlowInfo = this.currentCase.userFlowInfoHome1;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.notifyMe();
    },
    initMap(map) {
      console.log("Init Map start");
      this.map = map;
      this.permission()
      // this.map.on('click', this.addMarker);
      // this.map.on("load", this.loadBeacons());
      this.loadBeacons();
    },
    notifyMe(message) {
      // Let's check if the browser supports notifications
      if (!("Notification" in window)) {
        console.log("This browser does not support desktop notification");
      }

      // Let's check whether notification permissions have alredy been granted
      else if (Notification.permission === "granted") {
        // If it's okay let's create a notification
        var notification = new Notification("Hi there!");
      }

      // Otherwise, we need to ask the user for permission
      else if (Notification.permission !== 'denied' || Notification.permission === "default") {
        Notification.requestPermission(function (permission) {
          // If the user accepts, let's create a notification
          if (permission === "granted") {
            var notification = new Notification("Hi there!");
          }
        });
      }

      // At last, if the user has denied notifications, and you
      // want to be respectful there is no need to bother them any more.
    },
    loadBeacons() {
      this.map.once('styledata', () => {
        this.map.addSource('beacons', {
          'type': 'geojson',
          'data': this.beacons,
          'generateId': true
        });
        this.map.addLayer({
          'id': 'beacons-viz',
          'type': 'circle',
          'source': 'beacons',
          'paint': {
            // The feature-state dependent circle-radius expression will render
            // the radius size according to its magnitude when
            // a feature's hover state is set to true
            'circle-radius': [
              'case',
              ['boolean', ['feature-state', 'hover'], false],
              [
                'interpolate',
                ['linear'],
                ['get', 'mag'],
                1, 8, 1.5, 10, 2, 12, 2.5, 14, 3, 16, 3.5, 18, 4.5, 20, 6.5, 22, 8.5, 24, 10.5, 26],
              5
            ],
            'circle-stroke-color': '#000',
            'circle-stroke-width': 1,
            // The feature-state dependent circle-color expression will render
            // the color according to its magnitude when
            // a feature's hover state is set to true
            'circle-color': [
              'case',
              ['boolean', ['feature-state', 'click'], false],
              [
                'interpolate',
                ['linear'],
                ['get', 'today'],
                1,
                '#fff7ec', 300, '#fee8c8', 500, '#fdd49e', 700, '#fdbb84', 900, '#fc8d59', 1000, '#ef6548', 1500, '#d7301f', 2000, '#b30000', 2500, '#7f0000', 3000, '#000'
              ],
              '#000'
            ]
          }
        });
      });
      let beaconId = null;
      // Target the span elements used in the sidebar
      let placeDisplay = document.getElementById('place');
      let BLEIdDisplay = document.getElementById('bleid');
      let todayDisplay = document.getElementById('today');
      let allDisplay = document.getElementById('all');
      let floorDisplay = document.getElementById('floor');
      let lngDisplay = document.getElementById('lng');
      let latDisplay = document.getElementById('lat');
      let statusDisplay = document.getElementById('status');
      let typeDisplay = document.getElementById('type');

      this.map.on('mousemove', 'beacons-viz', (e) => {
        this.map.getCanvas().style.cursor = 'pointer';
        // Set variables equal to the current feature's magnitude, location, and time
        let place = e.features[0].properties.place;
        let BLEid = e.features[0].properties.id;
        let today = e.features[0].properties.today;
        let all = e.features[0].properties.all;
        let floor = e.features[0].properties.floor;
        let status = e.features[0].properties.status;
        let lng = e.features[0].properties.lng;
        let lat = e.features[0].properties.lat;
        let BLEtype = e.features[0].properties.type;

        if (e.features.length > 0) {
          // Display the magnitude, location, and time in the sidebar
          placeDisplay.textContent = place;
          BLEIdDisplay.textContent = BLEid;
          todayDisplay.textContent = today;
          allDisplay.textContent = all;
          floorDisplay.textContent = floor;
          lngDisplay.textContent = lng;
          latDisplay.textContent = lat;
          statusDisplay.textContent = status;
          typeDisplay.textContent = BLEtype;

          // When the mouse moves over the beacons-viz layer, update the
          // feature state for the feature under the mouse
          if (beaconId) {
            this.map.removeFeatureState({
              source: 'beacons',
              id: beaconId
            });
          }
          beaconId = e.features[0].id;
          console.log("CURRENT BLE TAG=" + beaconId)
          this.map.setFeatureState(
              {
                source: 'beacons',
                id: beaconId
              },
              {
                hover: true
              }
          );
        }
      });
      this.map.on('click', 'circle', function (e) {
        this.map.flyTo({
          center: e.features[0].geometry.coordinates
        });
      });
      // When the mouse leaves the earthquakes-viz layer, update the
      // feature state of the previously hovered feature
      this.map.on('mouseleave', 'beacons-viz', function () {
        if (beaconId) {
          this.map.setFeatureState(
              {
                source: 'beacons',
                id: beaconId
              },
              {
                hover: false
              }
          );
        }
        beaconId = null;
        // Remove the information from the previously hovered feature from the sidebar
        placeDisplay.textContent = '';
        BLEIdDisplay.textContent = '';
        todayDisplay.textContent = '';
        allDisplay.textContent = '';
        floorDisplay.textContent = '';
        lngDisplay.textContent = '';
        latDisplay.textContent = '';
        statusDisplay.textContent = '';
        typeDisplay.textContent = '';
        // Reset the cursor style
        this.map.getCanvas().style.cursor = '';
      });
    },
    mapFlyAndDrawMarker(lng, lat, colorMarker) {
      this.map.flyTo({
        center: [
          lng,
          lat,
        ],
        essential: true // this animation is considered essential with respect to prefers-reduced-motion
      });
      const marker = new this.mapboxgl.Marker({
        color: colorMarker,
        draggable: false
      }).setLngLat([lng, lat])
          .addTo(this.map);
      this.currentMarkers.push(marker);
    },
    async pushTelegram(toDoItems) {
      console.log("pushTelegram: ")
      console.log(toDoItems)

      await this.$axios.$post('/telegram/generic', toDoItems);
    },
    homePosition() {
      // this.dialog = true;
      // this.dialog = false;
      if ("geolocation" in navigator) {
        if (!navigator.geolocation) {
          console.log('Geolocation is not supported by your browser');
        } else {
          console.log('Locating…');
          navigator.geolocation.getCurrentPosition(this.success, this.error);
        }
      } else {

      }
      this.currentUserFlowInfo = this.currentCase.userFlowInfoHome1;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.notifyMe();
      this.pushTelegram(this.currentUserFlowInfo);
    },
    aeroexpressStartPosition() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAeroexpressStart2;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    aeroexpressEndPosition() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAeroexpressEnd3;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },

    // taxiStartPosition() {
    //   this.currentUserFlowInfo = this.currentCase.userFlowInfoTaxiStart2;
    //   this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
    // },

    taxiEndPosition() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoTaxiEnd3;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },


    securityGateAtEnter() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAtEntrance4;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    fastTrackSecurity() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoSecurity5;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    fastTrackPassport() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoPassport6;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    standardSecurity() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoSecurity5;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    standardPassport() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoPassport6;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    wrongDirection() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoWrongDirection7;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    atGate() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAtGate8;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    showAdv1() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAdv1;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
    },
    showAdv2() {
      this.currentUserFlowInfo = this.currentCase.userFlowInfoAdv2;
      this.mapFlyAndDrawMarker(this.currentUserFlowInfo.currentAddress.longitude, this.currentUserFlowInfo.currentAddress.latitude, this.currentUserFlowInfo.color)
      this.pushTelegram(this.currentUserFlowInfo);
      this.$axios.$post('/telegram/late');

    },
    permission() {
      if ('Notification' in window) {
        Notification.requestPermission(result => {

          if (result !== 'granted') {
            console.log('Нотификашки отключены')
          } else {
            this.confPushSub()
            console.log('Нотификашки активны')
          }
        })
      } else {
        console.log('уведомления не поддерживаются')
      }
    },
    confPushSub() {
      let that = this
      if (!('serviceWorker' in navigator)) {
        // проверьте, существует ли сервис-воркер / нет
        return
      }
      navigator.serviceWorker.ready.then(function (sw) {
        console.log(sw)
        sw.pushManager.getSubscription().then(sub => {
          // проверьте, подписано ли это устройство или нет
          if (sub === null) {
            sw.pushManager
                .subscribe({
                  userVisibleOnly: true,
                  applicationServerKey: that.urlB64ToUint8Array(keys.publicKey)
                })
                .then(subscription => {
                  console.log('Subscribe OK:', subscription)
                  return fetch('http://localhost:3000/api/add/subscription', {
                    method: 'POST',
                    body: JSON.stringify(subscription.toJSON()),
                    headers: {
                      'Content-Type': 'application/json',
                      Accept: 'application/json'
                    }
                  })
                })
                .then(() => {
                  that.newNotif('Успешно подписаны')
                  console.log('Server Stored Subscription.')
                })
                .catch(err => {
                  console.log('Subscribe Error:', err)
                })
          } else {
            that.newNotif('Подписка уже существует')
          }
        })
      })
    },
    newNotif(msg) {
      let opt = {
        body: msg,
        icon: '../icons/app-icon-96x96.png',
        image: '../icon.png',
        dir: 'ltr',
        lang: 'en-US', //standard
        vibrate: [100, 50, 200],
        badge: '../icon.png',
        actions: [
          //! list aksi saat notifikasi di pilih
          {
            action: 'confirm',
            title: 'Yes',
            icon: '../icon.png'
          },
          {
            action: 'cancel',
            title: 'No',
            icon: '../icon.png'
          }
        ]
      }
      //! cara
      // new Notification('new notif', opt)
      //! как запустить уведомление от сервис-воркера через навигатор
      if ('serviceWorker' in navigator) {
        // если сервис-воркер существует, то запустите уведомление через сервис-воркер
        navigator.serviceWorker.ready.then(function (sw) {
          // service worker
          sw.showNotification('Subscribe', opt)
        })
      }
    },
    urlB64ToUint8Array(base64String) {
      const padding = '='.repeat((4 - (base64String.length % 4)) % 4)
      const base64 = (base64String + padding)
          .replace(/\-/g, '+')
          .replace(/_/g, '/')
      const rawData = window.atob(base64)
      const outputArray = new Uint8Array(rawData.length)
      for (let i = 0; i < rawData.length; ++i) {
        outputArray[i] = rawData.charCodeAt(i)
      }
      return outputArray
    }


  },
  mounted() {
    this.beacons = require('assets/data/beacons.json');
    this.currentCase = this.caseWithoutFastTrack;
  }
}
</script>

<style scoped lang="scss">
#map {
  height: calc(100vh - 4rem);
}

.BeaconInfo {
  position: absolute;
  font-family: sans-serif;
  margin-top: 5px;
  margin-left: 5px;
  padding: 5px;
  width: 40%;
  border: 1px solid #b5b5b5;
  font-size: 10px;
  color: #2b2b2b;
  background: #fff;
  border-radius: 7px;
  top: 150px;
  box-shadow: 9px 11px 18px 7px #b7b7b7;
}
</style>
