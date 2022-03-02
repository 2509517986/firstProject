import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import onePMap from './modules/onePMap'
import tagsView from './modules/tagsView'
import common from './modules/common'
import permission from './modules/permission'
import settings from './modules/settings'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings,
    onePMap,
    common
  },
  getters
})

export default store
