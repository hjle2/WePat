<template>
  <div>
    <h1>Vuex Test with Composition API</h1>
    {{counter}}
    {{ $store.getters.getTwoPowerCounter }}
    <button @click="increase">increase</button>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  name: 'VueTest',
  setup() {
    const store = useStore();
    const counter = computed(() => store.state.counter);
    const increase = () => store.commit("setCounter", ++counter.value);

    return {counter, increase}
  }
}
</script>

<style>
</style>