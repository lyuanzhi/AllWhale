import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
	socket: null,
	timeout: 4000,
	timeoutFunc: null,
	timeout1: 5000,
	timeoutFunc1: null,
	timeoutFunc2: null,
	reconnectLocked: false,
	reconnectTime: 0,
	showRed: 0,
	config: []
}

export default new Vuex.Store({
	state
})
