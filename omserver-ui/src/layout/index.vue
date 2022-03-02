<template>
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
      <div class="fixedHeader fixed-header">
        <navbar />
        <div>
          <sidebar class="sidebar-container" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }" />
          <tags-view v-if="needTagsView" />
        </div>
      </div>
    <div :class="{hasTagsView:needTagsView}" class="main-container">     
      <app-main />
      <right-panel v-if="showSettings">
        <settings />
      </right-panel>
    </div>
  </div>
</template>

<script>
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      theme: state => state.settings.theme,
      sideTheme: state => state.settings.sideTheme,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hasTagsView: this.needTagsView,
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    variables() {
      return variables;
    }
  },
  created() {},
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/assets/styles/mixin.scss";
  @import "~@/assets/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
      padding-top: $base-nav-bar-height;
    &.hasTagsView{padding-top: calc(#{$base-nav-bar-height} + #{$base-tabs-bar-height});}

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
 .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    left: 0;
    z-index: 997;
    width: 100%;
    // overflow: hidden;
    transition: all 0.2s;
     box-shadow: $base-box-shadow;
}
  .main-container{
    position: relative;
      width: auto;
      height: 100%;
      overflow: hidden;
      margin-left: $base-left-menu-width;
      background: $base-background-color;
      transition: $base-transition;
  }
  &.hideSidebar .main-container{margin-left: $base-left-menu-width-min;}
}
</style>
