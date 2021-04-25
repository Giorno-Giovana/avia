<template>
  <v-container fluid>
    <v-row justify="center" align="center">
      <v-col cols="12">
        <v-card class="mx-auto d-flex flex-column" flat>
          <v-row class="grow" style="margin-bottom:150px;">
            <v-col cols="12">
              <v-text-field label="Ваш телефон" color="grey lighten-2"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field label="Номер рейса" color="grey lighten-2"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field label="Ваш адрес" color="grey lighten-2"></v-text-field>
            </v-col>
            <v-col cols="12" class="pb-10 mt-n8">
              <span class="text-caption font-weight-thin" style="color: #404040">Адрес, откуда забрать багаж</span>
            </v-col>
          </v-row>

          <v-spacer/>
          <v-divider style="color:black !important"/>

          <v-card-actions class="d-flex shrink">
            <v-row>
            <v-col cols="12" >
              <span class="text-caption font-weight-thin" style="color: #404040">Способ оплаты</span>
            </v-col>
            <v-col>
            <div class="grow">
              <v-chip class="ma-2" color="black" outlined label>
                <v-icon left> {{ mdiApple }}</v-icon>
                Pay
              </v-chip> Apple Pay
            </div>
            </v-col></v-row>

          </v-card-actions>
          <v-card-actions>
            <v-btn outlined color="black" depressed block @click="next">
              Оплатить
            </v-btn>
          </v-card-actions>
        </v-card>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {mdiApple} from '@mdi/js'

export default {
  data() {
    return {
      mdiApple,
    }
  },
  methods:{
    next(){
      // this.$axios.post()
      this.$router.push('/luggage/success');
    }
  }
}
</script>

<style scoped>

</style>