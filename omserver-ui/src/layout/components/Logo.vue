<template>
  <!-- <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 v-else class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
    </transition>
  </div> -->
  <div class="logo-container-common">
    <router-link to="/">
      <span class="logo">
        <img :src="require('$img/logo.png')" alt="" style="width:90px;display:inline-block;vertical-align: middle;">
        <span class="title" style="margin-left:10px;">{{ title }} </span>
      </span>
    </router-link>
  </div>
</template>

<script>
import logoImg from '@/assets/logo/logo.png'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'SidebarLogo',
  props: {
  },
  computed: {
    variables() {
      return variables;
    },
	sideTheme() {
      return this.$store.state.settings.sideTheme
    }
  },
  data() {
    return {
      title: '配电在线监测系统V3.0',
      logo: logoImg
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin container {
    position: relative;
    height: $base-nav-bar-height;
    overflow: hidden;
    line-height: $base-nav-bar-height;
    background: transparent;
  }

  @mixin logo {
    display: inline-block;
    width: 32px;
    height: 32px;
    color: $base-title-color;
    vertical-align: middle;
  }

  @mixin title {
    display: inline-block;
    overflow: hidden;
    font-size: 18px;
    line-height: 55px;
    color: $base-title-color;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: middle;
  }
  .logo-container-common {
    @include container;

    .logo {
      svg {
        @include logo;
      }
    }

    .title {
      @include title;
    }
  }
</style>
