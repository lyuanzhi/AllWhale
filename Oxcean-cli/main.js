import Vue from 'vue'
import App from './App'
import store from './vuex/store'

import customTabBar from 'components/custom-tab-bar/custom-tab-bar.vue'
import uniPopup from 'components/uni-popup/uni-popup.vue'
import uniNavBar from 'components/uni-nav-bar/uni-nav-bar.vue'
import loading from 'components/wyb-loading/wyb-loading.vue'
Vue.component('custom-tab-bar', customTabBar)
Vue.component('uni-popup', uniPopup)
Vue.component('uni-nav-bar', uniNavBar)
Vue.component('loading', loading)

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	...App,
	store
})
app.$mount()

uni.hideTabBar()

Vue.prototype.$tagsColorMap = new Map()
Vue.prototype.$tagsColorMap.set("搞笑", "228, 150, 125")
Vue.prototype.$tagsColorMap.set("帅哥美女", "190, 146, 206")
Vue.prototype.$tagsColorMap.set("学术", "124, 187, 206")
Vue.prototype.$tagsColorMap.set("emo", "152, 147, 221")
Vue.prototype.$tagsColorMap.set("运动", "131, 180, 165")
Vue.prototype.$tagsColorMap.set("日常拍", "134, 191, 125")
Vue.prototype.$tagsColorMap.set("生活", "235, 171, 108")
Vue.prototype.$tagsColorMap.set("八卦", "215, 142, 199")

Vue.prototype.$serverUrl = "https://lyuanzhi.com:8080"
// Vue.prototype.$serverUrl = "http://localhost:8080"
// Vue.prototype.$serverUrl = "https://oxceanuk.com"

Vue.prototype.$serverWs = "wss://lyuanzhi.com:8080/websocket/"
// Vue.prototype.$serverWs = "ws://localhost:8080/websocket/"
// Vue.prototype.$serverWs = "wss://oxceanuk.com/websocket/"

Vue.prototype.isLogin = function() {
	if (uni.getStorageSync('userInfo') == "" || uni.getStorageSync('userInfo') == null) {
		return false;
	}
	if (Date.now() - uni.getStorageSync('userInfo').updateTime > 10*24*60*60*1000) {
		return false;
	}
	return true;
}