<template>
  <v-container>
    <v-snackbar top multi-line v-model="snackbar.show"
                :timeout="snackbar.timeout"
                :color="snackbar.color">
      {{ snackbar.text }}
      <v-btn text @click="snackbar.show=false" >Закрыть</v-btn>
    </v-snackbar>
    <v-card flat>
      <v-toolbar dark color="primary" flat>
        <v-toolbar-title>Войдите или зарегистрируйтесь</v-toolbar-title>
      </v-toolbar>
      <v-card-text>
        <v-form ref="form">
          <v-text-field
              :prepend-icon="mdiAccount"
              name="login"
              label="Имя пользователя"
              type="text"
              v-model="userPrincipal.email"
          ></v-text-field>
          <v-text-field
              id="password"
              :prepend-icon="mdiLock"
              name="password"
              label="Пароль"
              type="password"
              v-model="userPrincipal.password"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" depressed @click="login" class="rounded-0">Войти</v-btn>
        <v-btn color="black" dark depressed @click="register" class="rounded-0">Зарегистрироваться</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>

</template>

<script>
import Cookie from "js-cookie";
import {mdiChevronLeft, mdiEyeOffOutline, mdiEyeOutline, mdiFacebook, mdiGoogle, mdiVk,mdiAccount,mdiLock} from "@mdi/js";

export default {
  mounted() {
    if (!this.$auth.user && !this.$auth.loggedIn) {
      Cookie.remove(process.env.TOKEN_COOKIE_KEY, {path: ''});
      Cookie.remove(process.env.TOKEN_COOKIE_KEY.replace('token', 'refresh_token'), {path: ''});
    } else this.$router.push('/');
    this.userLocaleAndTimezone();
    Cookie.set('Referer', location.origin, {path: '/login'});
  },
  data() {
    const baseUrl = process.env.API_URL;
    return {
      mdiVk, mdiGoogle, mdiFacebook, mdiEyeOutline, mdiChevronLeft, mdiEyeOffOutline,mdiAccount,mdiLock,
      tabs: null,
      valid: true,
      loading: false,
      iForgotMode: false,
      showPassword: false,

      yandex: require('assets/svg/yandex_icon.svg'),
      vkontakte: baseUrl + process.env.VK_URL,
      googleLink: baseUrl + process.env.GOOGLE_URL,
      yandexOauth: baseUrl + process.env.YANDEX_URL,
      facebookLink: baseUrl + process.env.FACEBOOK_URL,
      emailRules: [
        v => !!v || 'Укажите адресс своей эл. почты',
        v => /.+@.+\..+/.test(v) || 'Введите правильный адрес',
        v => (v && v.length < 40) || 'Адрес слишком длинный'
      ],
      passwordRules: [
        v => !!v || 'Придумайте пароль',
        v => (v == null || v.length >= 4) || 'Минимум 4 символа',
        v => (v && v.length < 250) || 'Пароль слишком длинный'
      ],
      snackbar: {show: false, timeout: 3000, text: '', color: 'info'},
      userPrincipal: {email: null, password: null, timezone: null, locale: null},
      passwordConfirmation: null,
    };
  },
  computed: {
    passwordConfirmationRules() {
      return [
        v => !!v || 'Введите пароль еше раз',
        v => (v == null || v === this.userPrincipal.password) || 'Пароль не совпадает'
      ]
    }
  },
  methods: {
    showSnackbar(text) {
      this.snackbar.color = 'info';
      this.snackbar.text = text;
      this.snackbar.show = true;
    },
    showError(text) {
      this.snackbar.color = 'error';
      this.snackbar.text = text;
      this.snackbar.show = true;
    },
    showUserNotFoundSnackbar() {
      this.showError('Пользователь с таким адресом электронной почты не найден. Постарайтесь вспомнить правильный адрес своей почты или зарегистрируйтесь');
    },
    validate() {
      this.valid = this.$refs.form.validate();
      return this.valid;
    },
    async isUsernameExist() {
      try {
        console.log(`[isUsernameExist] login=${this.userPrincipal.email} password=${this.userPrincipal.password}`)
        return await this.$axios.$get('/guest/' + this.userPrincipal.email);
      } catch (exception) {
        this.valid = false;
        if (exception.response.status == process.env.NOT_ALLOWED_STATUS_CODE) this.showError('Вы заблокированы за плохое поведение 🥺 Уходите прочь, вам здесь не рады');

        throw exception;
      }
    },
    userLocaleAndTimezone() {
      try {
        const localOptions = Intl.DateTimeFormat().resolvedOptions();
        this.userPrincipal.timezone = localOptions.timeZone;
        this.userPrincipal.locale = localOptions.locale;
      } catch (e) {
        console.error('Error when computing user timezone & locale', e);
        return this.userPrincipal;
      }
    },
    async register() {
      if (this.validate()) {
        this.loading = true;
        try {
          const newUser = !await this.isUsernameExist();
          if (newUser) {
            const tokenInfo = await this.$axios.$post('/registration', this.userPrincipal)
                .catch(error => {
                  if (error.response.status != process.env.UNAUTHORIZED) this.showError('Упс, регистрация не прошла 🥺');
                  console.error('Ошибка регистрации (бек): ', error);
                });
            if (!this.$auth.loggedIn) {
              console.warn('Registered. Not logged In... [setUserToken]')
              await this.$auth.setUserToken(tokenInfo.access_token, tokenInfo.refresh_token);
              // await this.$auth.local.token.set(tokenInfo.access_token);
              // await this.$auth.local.refreshToken.set(tokenInfo.refresh_token);
            }
          } else this.showError('Пользователь с таким адресом электронной почты уже зарегистрирован.');
        } catch (error) {
          console.error('Ошибка регистрации: ', error);
        } finally {
          this.loading = false;
        }
      }
    },
    async login() {
      if (this.validate()) {
        try {
          this.loading = true;
          const userExist = await this.isUsernameExist();
          if (userExist) {
            await this.$auth.loginWith('local', {data: this.userPrincipal})
                .catch(error => {
                  if (error.response.status != process.env.NOT_ALLOWED_STATUS_CODE)
                    this.showError('Неправильный пароль. Воспользуйтесь востановлением с отправкой письма на вашу электронную почту');
                  console.error('Ошибка логина (бек)', error);
                });
          } else this.showUserNotFoundSnackbar();
        } catch (error) {
          console.error('Ошибка логина: ', error);
        } finally {
          this.loading = false;
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
