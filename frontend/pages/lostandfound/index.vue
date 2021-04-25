<template>
  <v-container fluid class="pa-0">
    <v-row justify="center" class="fill-height ma-0" style="width:100%">
      <v-col cols="12" md="6" class="pa-0">
        <Card :icon="mdiGhost" file-name="LostAndFound.png" type="kids"
              service-name="Услуги"
              title="Lost&Found"
              description="`Вещи, забытые в самолёте, хранятся в Камерах хранения невостребованного багажа бесплатно. \n Вещи, забытые в аэропорту и на привокзальной площади, хранятся в Бюро находок бесплатно до 2х суток.`"
              titleSecondary="Обратитесь в камеру хранения невостребованного багажа или в Бюро находок."
              :items="items"
              price="*270₽/сутки"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Card from "~/components/service/Card";
import { mdiGhost} from '@mdi/js';

export default {
  components: {Card},
  data() {
    return {
      mdiGhost,
      items:[
          'Оформите зявку он-лайн в один клик, сэкономив время на заполнение всех формальностей',
          'Покажите штрих-код в камере хранения',
          'Получите утеренную вещь'
      ]
    }
  }
}
</script>

<style scoped>

</style>