<template>
  <v-app id="inspire">
    <!--NAVIGATION DRAWER-->
    <v-navigation-drawer :value="drawer" app clipped disable-resize-watcher hide-overlay height="100%">
      <v-list>
        <v-list-item v-for="(item, i) in items" :key="i" :to="item.to" router exact class="radius"
                     v-show="ifItemVisible(item)">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title v-text="item.title"/>
          </v-list-item-content>

        </v-list-item>
      </v-list>

    </v-navigation-drawer>

    <!--TOOLBAR-->
    <v-app-bar app clipped-left class="border-bottom elevation-0" style="background:white">

      <v-btn class="mr-2" depressed icon>
        <v-icon color="primary-lighten1">{{ mdiHomeSearchOutline }}</v-icon>
      </v-btn>
      <nuxt-link class="logo" to="/" exact>
        <img :src="logo" alt="logo" class="mx-2" width="auto" height="55"/>
      </nuxt-link>
      <v-spacer/>

      <v-btn icon @click.stop="drawer = !drawer" class="ml-n3">
        <v-icon>{{ mdiMenu }}</v-icon>
      </v-btn>
    </v-app-bar>


    <v-main>
      <nuxt/>
    </v-main>

    <!--    &lt;!&ndash;FOOTER&ndash;&gt;-->
    <!--    <v-footer dark padless>-->
    <!--      <v-card class="flex" text tile>-->
    <!--        <v-card-title>-->
    <!--          <v-btn v-for="icon in footerIcons" :key="icon" class="mx-3" icon>-->
    <!--            <v-icon size="2rem">{{ icon }}</v-icon>-->
    <!--          </v-btn>-->
    <!--          <v-spacer/>-->
    <!--          &copy; colaba - 2021-->
    <!--        </v-card-title>-->
    <!--      </v-card>-->
    <!--    </v-footer>-->

  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import {mapMutations, mapGetters} from 'vuex';
import Cookie from "js-cookie";
import {
  mdiLoginVariant,
  mdiCommentProcessingOutline,
  mdiFacebook,
  mdiInstagram,
  mdiMenu,
  mdiSignal,
  mdiHomeSearchOutline,
  mdiLocationEnter, mdiLocationExit, mdiElevationRise, mdiBullseye, mdiGamepadVariant, mdiAccountCircle, mdiGamepad
} from '@mdi/js'


export default Vue.extend({
  created() {
    this.initDrawer();
  },
  mounted() {

  },
  computed: {
    ...mapGetters("drawer", ["IS_DRAWER_OPENED"]),
  },
  methods: {
    ...mapMutations("drawer", ["TOGGLE_DRAWER"]),
    changeTheme(isDark) {
      this.goDark = isDark;
    },
    toggleDrawer() {
      this.drawer = !this.drawer;
      this.TOGGLE_DRAWER(this.drawer);
      return this.drawer
    },
    initDrawer() {
      if (process.server) {
        this.drawer = this.$parseCookies(this.$nuxt.context.req)['drawer'] === 'true'
      } else {
        this.drawer = (Cookie.get('drawer') === 'true' || localStorage.getItem('drawer') === 'true')
      }
      this.TOGGLE_DRAWER(this.drawer);
      // console.warn('initDrawer ' + this.drawer)
    },
    ifItemVisible(item) {
      if (item.title === 'Войти' && this.$auth.loggedIn === true)
        return false;
      else if (item.title === 'Выйти' && this.$auth.loggedIn === true) {
        return true;
      }
      return true
    }
  },
  data() {
    return {
      mdiCommentProcessingOutline,
      mdiInstagram,
      mdiFacebook,
      mdiMenu,
      mdiSignal,
      mdiLocationEnter,
      mdiHomeSearchOutline,
      logo: require(`assets/img/svo_logo.png`),
      goDark: false,
      drawer: false,
      footerIcons: [mdiFacebook, mdiInstagram],
      tab: null,
      items: [
        {
          icon: mdiBullseye,
          title: 'Главная',
          to: '/',
          visible: true
        },
        {
          icon: mdiElevationRise,
          title: 'Мои кабинет',
          to: '/dashboard',
          visible: true
        },
        {
          icon: mdiLocationEnter,
          title: 'Войти',
          to: '/login',
          visible: true
        },
        {
          icon: mdiLocationExit,
          title: 'Выйти',
          to: '/logout',
          visible: false
        }
      ],

    }
  }
})
</script>

<style scoped lang="scss">
.border-bottom {
  border-bottom: 2px solid black;
}

</style>
