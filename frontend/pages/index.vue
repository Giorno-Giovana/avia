<template>
  <v-container class="fill-height colaba">
    <v-row justify="start" align="start">
      <v-col md="5" sm="12" class="pb-0">
        <section class="my-1 subtitle-1 text-wrap">
          <v-alert width="100%"
                   color="#039BE5"
                   :icon="mdiTelegram"
                   border="left" prominent
                   dark class="text-caption">
            <v-row>
              <v-col cols="8" class="font-weight-thin">Все услуги доступны в телеграм-боте "Шереметьево"</v-col>
              <v-col cols="2">
                <v-btn text style="text-transform: none" class="px-1"><a href="https://t.me/colaba_bot"
                                                                         style="text-decoration: none; color:inherit !important">Перейти</a>
                </v-btn>
              </v-col>
            </v-row>
          </v-alert>
        </section>
      </v-col>
    </v-row>
    <v-row>
      <v-col md="5" sm="12">
        <v-row>
          <v-col cols="5" class="py-1 pr-1">
            <nuxt-link to="/map" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-subtitle-2 font-weight-bold text-wrap px-3"
                              style="text-decoration: none; overflow-wrap: normal !important; word-break: keep-all;">
                  Карта аэропорта
                </v-card-title>
                <v-card-text class="text-caption text-wrap px-3 py-1">Больше, чем
                  <v-chip color="primary">навигация</v-chip>
                </v-card-text>
                <div class="d-flex">
                  <v-spacer></v-spacer>
                  <img :src="aircraft" style="margin-top:-60px !important"/>
                </div>

              </v-card>
            </nuxt-link>
          </v-col>
          <v-col cols="7" class="py-1 pl-1">
            <nuxt-link to="/schedule" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-subtitle-2 font-weight-bold text-wrap px-3 pb-1"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Онлайн-табло рейсов
                </v-card-title>
                <v-card-text class="text-caption text-wrap px-3 py-1">Подпишитесь на обновления</v-card-text>
                <div class="d-flex mt-n2">
                  <v-spacer></v-spacer>
                  <img :src="tablo" style="vertical-align: middle"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="7" class="py-1 pr-1">
            <nuxt-link to="/orderfood" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-subtitle-2 font-weight-bold text-wrap  px-3 pb-1"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Доставка еды
                </v-card-title>
                <v-card-text class="text-caption text-wrap px-3 py-1">По аэропорту <br/> за 10-20 мин</v-card-text>
                <div class="d-flex mt-n4">
                  <v-spacer></v-spacer>
                  <img :src="snacks" style="vertical-align: middle" class="mt-n12"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
          <v-col cols="5" class="py-1 pl-1">
            <nuxt-link to="/restaurants" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-subtitle-2 font-weight-bold text-wrap px-3"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Рестораны
                </v-card-title>
                <div class="d-flex">
                  <v-spacer></v-spacer>
                  <img :src="food" style="vertical-align: middle"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-row>
      <v-col md="5" sm="12">
        <v-row>
          <v-col cols="6" class="pr-1">
            <h3>Быстрая посадка</h3></v-col>
          <v-col cols="6" class="pl-1">
            <h3 style="color:grey">Fast Track</h3>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" class="py-1">
            <nuxt-link to="/fasttrack" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-subtitle-2 text-wrap px-3 pb-0"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Быстрое прохождение предполётных процедур в аэропорту без очередей
                </v-card-title>
                <div class="d-flex mt-n6">
                  <v-spacer></v-spacer>
                  <img :src="fasttrack"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="6">
            <h3>Дополнительные услуги</h3></v-col>
        </v-row>
        <v-row>
          <v-col cols="4" class="py-1 pr-1">
            <nuxt-link to="/luggage" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-caption  text-wrap  px-3 font-weight-bold"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Багаж от дома до самолета
                </v-card-title>

                <div class="d-flex">
                  <v-spacer></v-spacer>
                  <img :src="luggageDelivery" style="vertical-align: middle"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
          <v-col cols="4" class="py-1 px-1">
            <nuxt-link to="/lostandfound" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-caption font-weight-bold text-wrap px-3"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Lost & Found
                </v-card-title>
                <div class="d-flex">
                  <v-spacer></v-spacer>
                  <img :src="lostandfound" style="vertical-align: middle"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
          <v-col cols="4" class="py-1 pl-1">
            <nuxt-link to="/disabled" style="text-decoration: none">
              <v-card height="122" color="grey lighten-3" flat class="rounded-xl" style="overflow:hidden">
                <v-card-title class="text-caption font-weight-bold text-wrap px-3"
                              style="text-decoration: none; overflow-wrap: normal !important;    word-break: keep-all;">
                  Для маломобильных
                </v-card-title>
                <div class="d-flex">
                  <v-spacer></v-spacer>
                  <img :src="disabledPerson" style="vertical-align: middle"/>
                </div>
              </v-card>
            </nuxt-link>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

  </v-container>
</template>


<script lang="ts">
import Vue from "vue";
import {mdiHeart, mdiHeartOutline, mdiTelegram} from '@mdi/js'

export default Vue.extend({

  data() {
    return {
      mdiHeartOutline, mdiHeart, mdiTelegram,
      aircraft: require(`assets/svg/aircraft.svg`),
      tablo: require(`assets/img/tablo.png`),
      snacks: require(`assets/img/snacks.png`),
      food: require(`assets/img/restaurants.png`),
      luggageDelivery: require(`assets/img/luggage.png`),
      disabledPerson: require(`assets/img/disabled.png`),
      assistance: require(`assets/svg/aircraft.svg`),
      fasttrack: require(`assets/img/fasttrack.png`),
      lostandfound: require(`assets/img/lostandfound.png`),
    };
  },
  methods: {}
})
</script>

<style lang="scss" scoped>

</style>
