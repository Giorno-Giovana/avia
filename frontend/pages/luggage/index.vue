<template>
  <v-container fluid class="pa-0">
    <v-row justify="center" class="fill-height ma-0" style="width:100%">
      <v-col cols="12" md="6" class="pa-0">
        <Card :icon="mdiBagSuitcase" file-name="LuggageDeliveryBig.png" type="luggage"
              service-name="Услуги"
              title="Багаж от дома и до места назначения"
              description="В день вашего вылета по указанному адресу приедет сотрудник аэропорта, заберет ваши вещи, отвезет в аэропорт и сразу же погрузит их в самолет."
              titleSecondary="Вы можете избежать ожидания у багажной карусели и отправиться прямо из аэропорта прибытия к месту назначения, будь то дом, гостиница или офис"
              :items="items"
              button-text="Запланируйте доставку"
              next-page="/luggage/finish"
              price="2900₽/ед"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Card from "~/components/service/Card";
import { mdiBagSuitcase} from '@mdi/js';

export default {
  components: {Card},
  data() {
    return {
      mdiBagSuitcase,
      items:[
        'спросите на стойке регистрации в отеле, поддерживают ли они эту услугу',
        'получите свой багаж в течение четырех часов после прибытия рейса, если пункт назначения находится в пределах 40км от аэропорта',
        'если пункт назначения находится более чем в 40 км от аэропорта, вы получите свой багаж в течение шести часов после прибытия рейса',
        'пункты назначения, расположенные более чем в 100км от аэропорта прибытия, не обслуживаются'
      ]
    }
  }
}
</script>

<style scoped>

</style>