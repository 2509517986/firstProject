<template>
    <div :class="{'has-logo':showLogo}" :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }">
        
        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper" class="side-bar-container" size="mini">
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :background-color="settings.sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg"
                :text-color="variables['menu-color']"
                :unique-opened="true"
                :active-text-color="variables['menu-color-active']"
                :collapse-transition="false"
                size="mini"
                mode="vertical"
            >
                <sidebar-item
                    v-for="(route, index) in sidebarRouters"
                    :key="route.path  + index"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";

export default {
    components: { SidebarItem },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar"]),
        activeMenu() {
            const route = this.$route;
            const { meta, path } = route;
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            return path;
        },
        showLogo() {
            return this.$store.state.settings.sidebarLogo;
        },
        variables() {
            return variables;
        },
        isCollapse() {
            return !this.sidebar.opened;
        }
    }
};
</script>
<style lang="scss" scoped>
 @mixin active {
    &:hover {
      color: $base-color-white !important;
      // background-color: $base-menu-background-active !important;
      background-color: $base-left-background !important;
    }

    &.is-active {
      color: $base-color-white;
      // background-color: $base-menu-background-active !important;
      background-color:$base-left-background!important;
    }
  }

  .side-bar-container {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: $base-z-index;
    width: $base-left-menu-width;
    height: 100vh;
    overflow: hidden;
    background: $base-left-background;
    box-shadow: $base-box-shadow;
    transition: width $base-transition-time;
    .el-menu{background-color:$base-left-background;}
    &.side-bar-common {
      top: $base-top-bar-height;
      height: calc(100vh - #{$base-top-bar-height});
    }
    &.is-collapse {
      width: $base-left-menu-width-min;
      border-right: 0;

      ::v-deep {
        .el-menu--collapse.el-menu {
          > .el-menu-item,
          > .el-submenu {
            text-align: center;
            transition: width $base-transition-time;
          }
        }

        .el-menu-item,
        .el-submenu {
          text-align: left;
        }
      
        .el-menu--collapse {
          border-right: 0;

          .el-submenu__icon-arrow {
            right: 10px;
            margin-top: -3px;
          }
        }
      }
    }

    ::v-deep {       
            
        .el-menu--collapse>div>.el-submenu>.el-submenu__title span{
                display: none;
            }
            /*隐藏 > */
            .el-menu--collapse>div>.el-submenu>.el-submenu__title .el-submenu__icon-arrow{
                display: none;
            }
      .el-scrollbar__wrap {
        overflow-x: hidden;
      }

      .el-menu {
        border: 0;

        .vab-fas-icon {
          padding-right: 3px;
          font-size: $base-font-size-default;
        }

        .vab-remix-icon {
          padding-right: 3px;
          font-size: $base-font-size-default + 2;
        }
      }

      .el-menu-item,
      .el-submenu__title {
        height: $base-menu-item-height;
        overflow: hidden;
        line-height: $base-menu-item-height;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: middle;
        background:$base-left-background;
        color:$base-menu-color;
        i {
          color: inherit;
        }
      }

      .el-menu-item {
        @include active;
        svg{margin-right:3px;vertical-align:-0.25em;}
      }
      .el-submenu.is-opened {
        background-color:$base-left-active-background!important;
        .el-menu-item,.el-submenu__title{background-color:$base-left-active-background!important;}
        .el-submenu__title{color:$base-menu-color-active!important;}
      }      
        .el-submenu{svg{margin-right:3px;vertical-align:-0.25em;}}
    }
  }
.app-wrapper.hideSidebar{
    .side-bar-container {
        width: $base-left-menu-width-min;
      border-right: 0;
    }
    }
.side-bar-container {
    position: fixed;
    top: $base-nav-bar-height;
    height: calc(100vh - #{$base-nav-bar-height});
    bottom: 0;
    left: 0;
    z-index: $base-z-index;
    width: $base-left-menu-width;
    overflow: hidden;
    background: $base-left-background;
    box-shadow: $base-box-shadow;
    transition: width $base-transition-time;
}

</style>
