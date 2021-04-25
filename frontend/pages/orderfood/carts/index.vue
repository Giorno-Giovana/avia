<template>
  <v-container fluid>
    <v-row justify="center" align="center">
      <v-col cols="12">
        <v-card class="mx-auto d-flex flex-column" flat>
          <v-card-title class="text-h6 font-weight-bold shrink mb-1 mt-n5 d-flex justify-center">
            Ваш заказ
          </v-card-title>
          <v-card-title class="text-h3 font-weight-bold shrink mb-1 mt-n1 d-flex justify-center">
            278₽
          </v-card-title>
          <v-card-text class="d-flex" style="justify-content: start; align-content: start; align-items: start">

            <v-list>
              <v-list-item
                  v-for="item in foodMcDonalds"
                  :key="item.id">

                <v-list-item-avatar>
                  <v-img :src="item.img"></v-img>
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title v-text="item.title"></v-list-item-title>
                </v-list-item-content>

                <v-list-item-icon>
                  <v-icon>
                    {{ mdiTrashCan }}
                  </v-icon>
                </v-list-item-icon>
              </v-list-item>
            </v-list>


          </v-card-text>
          <v-card-actions>
            <v-btn outlined color="black" depressed block @click="gotoPayment">К оплате</v-btn>
          </v-card-actions>
        </v-card>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {mdiTelegram, mdiTrashCan} from '@mdi/js'

export default {
  data() {
    return {
      mdiTelegram, mdiTrashCan,
      foodMcDonalds: [
        {
          id: 1,
          title: 'Капучино',
          weight: '300мл',
          price: '156₽',
          img: require('assets/img/mcdonalds/capuch.webp')
        },

        {
          id: 3,
          title: 'Двойной Чизбургер',
          weight: '247г',
          price: '122₽',
          img: require('assets/img/mcdonalds/doubleCheesburger.webp')
        }]

    }
  },
  methods: {
    gotoPayment() {
      this.$axios.$post('/telegram/food');
      this.$router.push('/orderfood/finish')
    }
  }
}
</script>

<style scoped>

</style>