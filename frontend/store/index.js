/// /////////////
/// /// STATE //
/// ///////////
export const state = () => ({
    helloMessage: 'Hello',
    baseUrlForStatic: ''
});


/// /////////////
// MUTATIONS //
/// ///////////
export const mutations = {
    SET_HELLO_MESSAGE(state, helloMessage) {
        state.helloMessage = helloMessage;
    },
    SET_BASE_URL_FOR_STATIC(state, baseUrlForStatic) {
        state.baseUrlForStatic = baseUrlForStatic;
    }
};


/// /////////////
/// ACTIONS //
/// //////////
export const actions = {
    async nuxtServerInit({commit}) {
        /* only when absolutely necessary */
    },
    baseUrlForStatic({commit, getters}) {
        const apiUrl = process.env.API_URL;
        const baseUrlForStatic = getters.isLocalHost ? apiUrl.substr(0, apiUrl.lastIndexOf("/")) : null;
        if (baseUrlForStatic) commit('SET_BASE_URL_FOR_STATIC', baseUrlForStatic);
    }
};

/// /////////////
/// / GETTERS //
/// ///////////
export const getters = {
    helloMessage(state) {
        return state.helloMessage;
    },
    isLocalHost() {
        return process.client ? (location.origin === 'http://localhost:3000') : false;
    },

};
