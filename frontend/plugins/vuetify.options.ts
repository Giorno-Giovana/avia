import colors from 'vuetify/es5/util/colors';

export default {
    icons: {
        iconfont: "mdiSvg",
    },
    theme: {
        dark: false,
        themes: {
            light: {
                primary: '#F4AE1C',
                secondary: colors.indigo.accent1,
                accent: '#FF0010',
                darken: colors.grey.darken3,
            },
            dark: {
                darken: colors.grey.lighten3,
                primary: '#F4AE1C',
            }
        }
    }
};
