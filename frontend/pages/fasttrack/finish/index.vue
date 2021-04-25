<template>
  <v-container fluid>
    <v-row justify="center" align="center">
      <v-col cols="12">
        <v-card class="mx-auto d-flex flex-column" flat>
          <v-card-title class="text-h6 font-weight-bold shrink mb-1 mt-n5">
            Ваш пропуск
          </v-card-title>
          <v-card-subtitle class="text-body-1 font-weight-thin pt-2 pb-1" color="grey lighten-2">
            Приложите QR-код к считывающим устройствам на турникетах коридора Fast Track и следуйте указателям по ходу
            движения в аэропорту
          </v-card-subtitle>
          <v-card-text class="d-flex" style="justify-content: center; align-content: center; align-items: center">
            <img :src="qrCode" alt="qr-code-fast-track" height="300px"/>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined color="black" depressed block>Скачать QR-code</v-btn>

          </v-card-actions>
          <v-card-actions>
            <v-btn outlined color="black" depressed block @click="sendToTelegram">
              <v-icon class="text-h5" color="black">{{ mdiTelegram }}</v-icon>
              Отправить в телеграмм
            </v-btn>

          </v-card-actions>
        </v-card>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {mdiTelegram} from '@mdi/js'

export default {
  data() {
    return {
      mdiTelegram,
      qrCode: require('assets/img/qrCode_fastTrack.png')
    }
  },
  methods:{
    sendToTelegram(){
      this.$axios.$post('/telegram/fast-track');
    }
  }
}
</script>

<style scoped>

</style>