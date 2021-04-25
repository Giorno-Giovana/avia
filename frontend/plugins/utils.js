import Cookie from "js-cookie";

export default (context, inject) => {
    const isNotEmptyArray = function (array) {
        return Array.isArray(array) && array.length > 0;
    }
    inject('isNotEmptyArray', isNotEmptyArray)
    context.$isNotEmptyArray = isNotEmptyArray;

    const getInitials = function (str) {
        if (!str) return "";
        const FirstName = str.split(' ')[0];
        const LastName = str.split(' ').reduceRight(a => a);

        let initials;
        if (FirstName === LastName) {
            return FirstName.trim()
                .substring(0, 2)
                .toUpperCase();
        }
        return initials = [FirstName, LastName]
            .map(name => name[0])
            .join('').toUpperCase();
    }
    inject('getInitials', getInitials)
    context.$getInitials = getInitials;

    const stringToHslColor = function (str, s, l) {
        let hash = 0;
        for (let i = 0; i < str.length; i++) {
            hash = str.charCodeAt(i) + ((hash << 5) - hash);
        }
        const h = hash % 360;
        return 'hsl(' + h + ', ' + s + '%, ' + l + '%)';
    }
    inject('stringToHslColor', stringToHslColor)
    context.$stringToHslColor = stringToHslColor;


    const defineColors = function () {
        const lightness = this.$vuetify.theme.dark ? 75 : 66;
        if (!this.bgColor) {
            const saturation = this.$vuetify.theme.dark ? 70 : 66;
            this.bgColor = stringToHslColor(this.text, saturation, lightness);
        }
        if (!this.textColor) {
            this.textColor = lightness > 70 ? 'var(--v-txt-lighten2)' : 'var(--v-txt-lighten4)';
        }
    }
    inject('defineColors', defineColors)
    context.$defineColors = defineColors;


    const parseCookies = function (request) {
        let cookies = {};
        request.headers && request.headers.cookie && request.headers.cookie.split(';').forEach(function (cookie) {
            let parts = cookie.match(/(.*?)=(.*)$/);
            cookies[parts[1].trim()] = (parts[2] || '').trim();
        });
        return cookies;
    };

    inject('parseCookies', parseCookies)
    context.$parseCookies = parseCookies
}