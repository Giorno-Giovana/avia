export const state = () => ({
    users: [],
    currentUser: {},
    groups: [],
})
export const mutations = {

    UPDATE_CURRENT_USER: (state, payload) => {
        state.currentUser = payload;
        localStorage.setItem("currentUser", JSON.stringify(payload))
    },
}

/// /////////////
/// / GETTERS //
/// ///////////
export const getters = {
    GET_CURRENT_USER(state) {
        return (state.currentUser ? state.currentUser : (process.client && !!localStorage.getItem("currentUser") ? JSON.parse(localStorage.getItem("currentUser")) : ''));
    },
};

export const actions = {}
