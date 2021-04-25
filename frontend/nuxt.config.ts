import {NuxtConfig} from "@nuxt/types";
require('dotenv').config();

const siteTitle = "The-ONE";
const description = "Участники";
const config: NuxtConfig = {
    head: {
        titleTemplate: 'The-one',
        title: 'Hack',
        meta: [
            {charset: 'utf-8'},
            {name: 'viewport', content: 'width=device-width, initial-scale=1'},
            {hid: 'description', name: 'description', content: ''},
            // OGP
            {hid: "og:site_name", property: "og:site_name", content: siteTitle,},
            {hid: "og:type", property: "og:type", content: "website"},
            {hid: "og:url", property: "og:url", content: "https://colaba.space/"},
            {hid: "og:title", property: "og:title", content: siteTitle},
            {hid: "og:description", property: "og:description", content: description},
            {hid: "og:image", property: "og:image", content: `${process.env.STATIC}/main.png`},
        ],
        link: [{rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'}]
    },
    loading: {color: '#F4AE1C'},
    css: ['@/scss/main.scss', '@/assets/fonts/fonts.css'],
    plugins: ['@/plugins/utils.js', {src: "~/plugins/aos", mode: "client"}],
    components: true,
    buildModules: ['@nuxtjs/vuetify', '@nuxtjs/dotenv', '@nuxtjs/date-fns', "@nuxt/typescript-build", "@nuxtjs/pwa"],
    modules: ['@nuxtjs/axios', '@nuxtjs/auth-next',],
    axios: {debug: true},
    vuetify: {
        /** Теперь тема в: `plugins/vuetify.options.ts`  */
        customVariables: ['~/assets/variables.scss'],
        optionsPath: "./plugins/vuetify.options.ts",
        defaultAssets: false,
    },
    pwa: {
        icon: false, // disables the icon module
        meta: {
            title: 'SVO PWA',
            author: 'The One team',
        },
        manifest: {
            name: 'SVO',
            lang: 'ru',
            short_name: 'SVO',
            useWebmanifestExtension: false
        },

    },
    auth: {
        scopeKey: 'scope',
        resetOnError: true,
        redirect: {login: '/login', logout: '/', callback: '/', home: '/'},
        strategies: {
            local: {
                scheme: 'refresh',
                user: {property: false},
                token: {property: 'access_token', required: true, type: 'Bearer', maxAge: 300},
                refreshToken: {property: 'refresh_token', tokenRequired: false, maxAge: 600},
                endpoints: {
                    login: {url: '/login', method: 'post'},
                    user: {url: '/user', method: 'post'},
                    refresh: {url: '/refresh', method: 'post'},
                    logout: {url: '/logout', method: 'post'}
                }
            }
        }
    },
    healthcheck: {
        contentType: 'application/json', healthy: () => {
            return JSON.stringify({status: "UP", details: {exists: true}})
        }
    },
    build: {}
}

export default config;
