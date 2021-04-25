<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card class="mx-auto d-flex flex-column" flat>
          <v-card-subtitle class="text-body-1 font-weight-thin pt-2 pb-1" color="grey lighten-2">
            Услуги
          </v-card-subtitle>
          <v-card-title class="text-h6 font-weight-bold shrink mb-1 mt-n5">
            Доставка еды по аэропорту
            <v-icon @click="$router.push('/orderfood/carts')" class="text-h3 ml-2" color="grey lighten-1">{{
                mdiCart
              }}
            </v-icon>
          </v-card-title>
          <v-card-text>
            <v-chip-group v-model="selectedRest" active-class="primary--text">
              <v-chip :value="rest"
                      v-for="rest in restaurants"
                      :key="rest">
                {{ rest }}
              </v-chip>
            </v-chip-group>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="6" v-for="item in foodList" :key="item.id">
        <v-card flat outlined>
          <v-card-text class="py-1">
            <span class="text-body-2 font-weight-bold">{{ item.title }}</span>
          </v-card-text>
          <v-card-text style="display: flex; justify-content: center;">
            <img :src="item.img" width="100px" height="auto"/>
          </v-card-text>
          <v-card-text class="py-1" style="display: flex; justify-content: center;">
            <span class="text-body-2 font-weight-bold">{{ item.weight }}</span>
          </v-card-text>
          <v-card-text class="py-1" style="display: flex; justify-content: center;">
            <span class="text-body-2 font-weight-bold">{{ item.price }}</span>
          </v-card-text>
          <v-card-actions>
            <v-btn block color="primary" class="text-caption" small depressed style="text-transform: none !important;">
              Добавить в корзину
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {mdiCart} from '@mdi/js';

export default {
  data() {
    return {
      mdiCart,
      restaurants: [
        'Макдональдс',
        'Кофемания',
        'Шоколадница'
      ],
      foodList: [],
      selectedRest: 'Макдональдс',
      foodMcDonalds: [
        {
          id: 1,
          title: 'Капучино',
          weight: '300мл',
          price: '156₽',
          img: require('assets/img/mcdonalds/capuch.webp')
        },
        {
          id: 2,
          title: 'Донат',
          weight: '100г',
          price: '100₽',
          img: require('assets/img/mcdonalds/donat_chocolate.webp')
        },
        {
          id: 3,
          title: 'Двойной Чизбургер',
          weight: '247г',
          price: '122₽',
          img: require('assets/img/mcdonalds/doubleCheesburger.webp')
        },
        {
          id: 4,
          title: 'Ролл Креветка',
          weight: '250г',
          price: '199₽',
          img: require('assets/img/mcdonalds/shrimpRoll.webp')
        },
        {
          id: 5,
          title: 'Зеленый чай',
          weight: '300мл',
          price: '126₽',
          img: require('assets/img/mcdonalds/green_tea.webp')
        },
      ],
      foodCofemania: [
        {
          id: 1,
          title: 'Капучино',
          weight: '300мл',
          price: '400₽',
          img: require('assets/img/cofemania/capuch.webp')
        },
        {
          id: 2,
          title: 'Кекс',
          weight: '200г',
          price: '453₽',
          img: require('assets/img/cofemania/keks.webp')
        },
        {
          id: 3,
          title: 'Матча',
          weight: '300мл',
          price: '490₽',
          img: require('assets/img/cofemania/matcha.webp')
        },
      ]
    }
  },
  mounted(){
    if (this.selectedRest === 'Макдональдс') {
      this.foodList = [];
      for (const item of this.foodMcDonalds) {
        this.foodList.push(item);
      }

    } else {
      this.foodList = [];
      for (const item of this.foodCofemania) {
        this.foodList.push(item);
      }

    }
  },
  watch: {
    selectedRest(val) {
      if (val === 'Макдональдс') {
        this.foodList = [];
        for (const item of this.foodMcDonalds) {
          this.foodList.push(item);
        }

      } else {
        this.foodList = [];
        for (const item of this.foodCofemania) {
          this.foodList.push(item);
        }

      }

    }
  }

}
</script>

<style scoped>

</style>