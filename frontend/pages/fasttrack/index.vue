<template>
  <v-container fluid class="pa-0">
    <v-row justify="center" class="fill-height ma-0" style="width:100%">
      <v-col cols="12" md="6" class="pa-0">
        <Card :icon="mdiFlash" file-name="FastTrackBig.png"
              service-name="Fast Track"
              title="Быстрая посадка"
              description="Услуга подразумевает под собой прохождение всех формальностей (регистрацию на авиарейс, таможенный и паспортный контроль) в ускоренном режиме и без очередей по спеуиально предусмотренным коридорам"
              titleSecondary="Что вы получите"
              :items="items"
              next-page="/fasttrack/finish"
              price="750₽"
              type="fasttrack"
              button-text="Купить"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Card from "~/components/service/Card";
import {mdiFlash} from '@mdi/js';

export default {
  components: {Card},
  data() {
    return {
      mdiFlash,
      items: [
        'Приоритетный маршрут к зоне посадки',
        'Сэкономите время на контрольно-пропускных пунктах',
        'Если вы хотите избежать очереди или просто отдохнуть, FastTrack - это эксклюзивная услуга, предлагаемая аэропортом Шереметьево',
      ]
    }
  }
}
</script>

<style scoped>

</style>