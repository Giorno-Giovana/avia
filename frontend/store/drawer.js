import Cookie from "js-cookie";

export const state = () => ({
    drawer: null,
    pageTitle: undefined
})

export const mutations = {
    TOGGLE_DRAWER: (state, payload) => {
        state.drawer = payload
        if (process.client) {
            localStorage.setItem('drawer', payload);
            Cookie.set('drawer', payload);
        }
        console.log('store toggleDrawer payload=' + payload);
    }
}

/// /////////////
/// / GETTERS //
/// ///////////
export const getters = {
    IS_DRAWER_OPENED(state) {
        return state.drawer;
    }
};
