<template>
  <v-container fluid class="pa-0">
    <v-row justify="center" class="fill-height ma-0" style="width:100%">
      <v-col cols="12" md="6" class="pa-0">
        <Card :icon="mdiFlash" file-name="FastTrackBig.png" type="fastTrack"
              service-name="Услуги"
              title="Маломобильным"
              description="В случае если вам нужна помощь в аэропорту, закажите ее бесплатно заблаговременно — на этой странице, в телеграмм-боте или по телефону контактного центра Шереметьево."
              titleSecondary="Чем мы можем вам помочь"
              :items="items"
              price="Бесплатно" buttonText="Заказать"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Card from "~/components/service/Card";
import { mdiFlash} from '@mdi/js';

export default {
  components: {Card},
  data() {
    return {
      mdiFlash,
      items:[
          'Встретим по прибытии в аэропорт',
          'По вылету - на парковке, остановке общественного транспорта или станции Аэроэкспресса. По прилету - у трапа самолета',
          'Предоставим инвалидное кресло или носилки при необходимости',
          'Поможем с документами (с регистрацией, сдачей багажа, оформлением собаки-поводыря)',
          'Проведем через контроль и досмотр без очереди',
          'Если у вас вживлен кардиостимулятор или ограничена подвижность, вы можете пройти все досмотры и контроли в медпункте',
          'Сопроводим до трапа самолета',
          'При необходимости поднимем в самолет на амбулифте',
      ]
    }
  }
}
</script>

<style scoped>

</style>