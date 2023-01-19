<template>
  <p>일정 추가페이지</p>
  <TodoName/>
  <TodoPetSelect/>
  <TodoTimeSelect/>
  <TodoChargeSelect/>
  <TodoCycle/>
</template>

<script>
import TodoName from "@/components/TodoItems/TodoName.vue"
import TodoPetSelect from "@/components/TodoItems/TodoPetSelect.vue"
import TodoTimeSelect from "@/components/TodoItems/TodoTimeSelect.vue"
import TodoChargeSelect from"@/components/TodoItems/TodoChargeSelect.vue"
import TodoCycle from "@/components/TodoItems/TodoCycle.vue"

export default {
  components: {
    TodoName,
    TodoPetSelect,
    TodoTimeSelect,
    TodoChargeSelect,
    TodoCycle,
  },
}
</script>

<style>

</style>