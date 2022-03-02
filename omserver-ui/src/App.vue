<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'
export default  {
  name:  'App',  
  data () {return {}},
  created() {    
    if(getToken()){this.init();}
  },
  methods: {    
    init () {    
      // 字典
      this.$store.dispatch("SetDicts").then(() => {}).catch(() => {});
    },
  }
}
</script>
<style lang="scss" >
@import '@/assets/styles/index.scss'; // global css
</style>
