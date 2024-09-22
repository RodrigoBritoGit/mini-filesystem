<template>
   <div id="app">
    <DirectoryList />
  </div>
</template>

<script>
import DirectoryList from './components/DirectoryList.vue'

export default {
  name: 'App',
  components: {
    DirectoryList
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0;
}
</style>
