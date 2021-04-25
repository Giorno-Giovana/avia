<template>
  <v-container fluid>
    <v-row justify="center" align="center" class="d-flex">
      <v-col cols="12">
        <v-stepper value="1" class="elevation-0">
          <v-stepper-header style="border:none; box-shadow: none!important">
            <v-stepper-step step="1" color="primary" complete style="width: 70px; height:70px">
            </v-stepper-step>
            <v-divider></v-divider>
            <v-stepper-step step="2" editable color="black">
            </v-stepper-step>
            <v-divider></v-divider>
            <v-stepper-step step="3" editable style="width: 70px; height:70px">
            </v-stepper-step>
          </v-stepper-header>
        </v-stepper>
      </v-col>
    </v-row>
    <v-row justify="center" align="center" class="d-flex">
      <v-col cols="12" class="d-flex  justify-center">
        <div class="text-h5 font-weight-bold">Услуга оформлена</div>
      </v-col>
    </v-row>
    <v-row justify="center" align="center" class="d-flex  justify-center">
      <v-col cols="12" class="d-flex  justify-center">
        <div class="text-body-1 font-weight-thin">За 5 часов до вашего рейса по указанному адресу приедет сотрудник
          аэропорта и заберет ваш
          багаж
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {mdiBagSuitcase, mdiFlagVariantOutline} from '@mdi/js';

export default {
  data() {
    return {
      mdiBagSuitcase, mdiFlagVariantOutline
    }
  }
}
</script>

<style scoped lang="scss">
.v-stepper__step__step {
  width: 70px !important;
  height: 70px !important;
}

</style>