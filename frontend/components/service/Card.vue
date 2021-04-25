<template>
  <v-card class="mx-auto d-flex flex-column" flat height="82vh">
    <img :src="require(`@/assets/img/pages/${fileName}`)" style="width:100%">
    <v-card-subtitle class="text-body-1 font-weight-thin pt-2 pb-1" color="grey lighten-2">
      {{ serviceName }}
    </v-card-subtitle>

    <v-row class="text-h6 font-weight-bold mx-0 shrink mb-1 mt-n5"
           style="overflow-wrap: normal;word-break: keep-all !important; text-overflow: initial; white-space: normal; -webkit-line-clamp: unset !important;">
      <v-col cols="1" class="pb-0">
        <v-icon class="text-h3 font-weight-bold">{{ icon }}</v-icon>
      </v-col>
      <v-col cols="6" class="pb-0">
        <span>{{ title }}</span>
      </v-col>
      <v-col cols="5" class="pb-0"><span style="color:#F4AE1C !important" class="font-italic text-h6"> {{
          price
        }}</span></v-col>
    </v-row>

    <v-row class="text-h6 font-weight-bold mx-0 shrink mt-1"
           style="overflow-wrap: normal;word-break: keep-all !important; text-overflow: initial; white-space: normal; -webkit-line-clamp: unset !important;">
      <v-col v-if="type==='luggage'" cols="12" class="pa-0 font-weight-thin text-subtitle-2">
        <div style="display: inline-block" class="ml-2">
          <nuxt-link to="/map/luggage" style="text-decoration: none !important">
            <v-icon color="primary">{{ mdiMapMarker }}</v-icon>
            <span class="text-caption font-weight-thin">Пункты приема багажа на карте </span></nuxt-link>
        </div>
      </v-col>
      <v-col cols="12" class="font-weight-thin text-subtitle-2">{{ titleSecondary }}</v-col>
      <v-col cols="12" class="pa-0">
        <v-list class="py-0 my-0" dense two-line>
          <v-list-item v-for="(item,index) in items" :key="index"
                       style="min-height: 50px !important; height: 50px !important">
            <v-list-item-icon class="mr-2">
              <v-icon v-text="mdiCircleMedium"></v-icon>
            </v-list-item-icon>
            <v-list-item-title class="text-caption font-weight-thin"
                               style="overflow-wrap: normal;word-break: keep-all !important; text-overflow: initial; white-space: normal; -webkit-line-clamp: unset !important;">
              {{ item }}
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>

    <v-spacer></v-spacer>
    <v-card-actions class="shrink">
      <v-spacer></v-spacer>
      <v-btn color="primary" x-large depressed block class="rounded-0" @click="next">{{ buttonText }}</v-btn>
    </v-card-actions>


  </v-card>
</template>

<script>
import {mdiCircleMedium, mdiMapMarker} from '@mdi/js';

export default {
  props: {
    serviceName: {
      type: String,
      required: true
    },
    fileName: {
      type: String,
      required: true
    },
    title: {
      type: String,
      required: true
    },
    description: {
      type: String,
      required: true
    },
    price: {
      type: String,
      required: true
    },
    icon: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    buttonText: {
      type: String,
      required: false,
      default: 'К оплате'
    },
    secondButtonText: {
      type: String,
      required: false,
      default: null
    },
    titleSecondary: {
      type: String,
      required: true
    },
    nextPage: {
      type: String,
      required: false
    },
    items: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      mdiCircleMedium, mdiMapMarker
    }
  },
  methods: {
    gotoMap() {
      this.$router.push('/luggage/map')
    },
    next() {
      if (this.type === 'luggage') {
        this.$router.push(this.nextPage);
      }
      if (this.type === 'fasttrack') {
        this.$router.push(this.nextPage);
       // this.$axios.post('/telegram/fast-track')
      }
    }
  }
}
</script>

<style scoped>
.colaba-btn:hover {
  transform: translate3d(-4px, -4px, 0);
}

.colaba-btn:before {
  position: absolute;
  right: 0;
  bottom: 0;
  display: block;
  box-sizing: border-box;
  border: 1px solid #000;
  width: 100%;
  height: 100%;
  background-color: #fff;
  content: '';
  z-index: -1;
  transform: translate3d(0, 0, 0);
}

.colaba-btn:hover:before {
  -webkit-backface-visibility: hidden;
  right: 0;
  bottom: 0;
}

.colaba-btn:after {
  position: absolute;
  right: 0;
  bottom: 0;
  display: block;
  opacity: 0;
  width: 100%;
  height: 100%;
  background-color: #ffac02;
  content: '';
  z-index: -2;
  transform: translate3d(0, 0, 0);
  transition: transform .2s ease-out, opacity 0s linear .2s;
}

</style>