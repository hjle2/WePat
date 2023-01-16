import { createStore } from "vuex";

// state, getters, mutations, actions, modules
// 아무 내용이나 넣었음
export default createStore({
    state : {
        counter : 2
    },
    getters : {
        getTwoPowerCounter(state){
            return state.counter ** 2;
        }
    },
    mutations : {
        setCounter(state, value){
            state.counter = value;
        }
    },
});