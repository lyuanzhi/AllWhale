<template>
	<view class="content" @click="login">
		<view class="topNavigationBar">
			<view class="backicon" :style="{top:(titleBarTop+5)+'px'}" @click="back()"></view>
		</view>
		<view class="userinfo">
			<view class="profilePic">
				<image :src="avatarUrl" mode="" style="width: 96rpx;height: 96rpx; border-radius: 12rpx;"></image>
			</view>
			<view class="nameloaction">
				<view class="name">{{username}}</view>
			</view>
			<view class="time">
				发布于{{createTime}}
			</view>
		</view>
		<view class="line"></view>
		<view class="title">{{name}}</view>
		<view class="detail" v-if="detailedDesc!=null">{{detailedDesc}}</view>
		<view class="label" v-if="time!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">时间</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 30rpx;">
				<view
					style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900; font-size: 24rpx;">
					{{time}}
				</view>
			</view>
		</view>
		<view class="label" v-if="people!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">人数</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 30rpx;">
				<view
					style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900; font-size: 24rpx;">
					{{people}}
				</view>
			</view>
		</view>
		<view class="label" v-if="place!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">地址</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 30rpx;">
				<view
					style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900; font-size: 24rpx;">
					{{place}}
				</view>
			</view>
		</view>
		<view class="label" v-if="price!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">费用</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 30rpx;">
				<view
					style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900; font-size: 24rpx;">
					£{{price}}
				</view>
			</view>
		</view>

		<view class="picarray" v-if="imgNumber">
			<image v-for="(item,index) in picarray" :src="item" mode="widthFix" style="margin-top: 20rpx; width: 90%; "
				:key='index' @click="clkImg(index)">
			</image>
		</view>
		<view class='bottomplace'>
			<view class="button" v-if="currentS>=currentD"
				:style="{background:isCancelled==1?'#E0E0E0':fromName==toName?'#FFFFFF':'#48D1E0',color:isCancelled==1?'#FFFFFF':fromName==toName?'#000000':'#FFFFFF',border:fromName==toName&&isCancelled==0?'1px solid rgba(0, 0, 0, 0.36)':'none'}"
				@click="isCancelled==1?empty():fromName==toName?change(0):signUp()">
				{{isCancelled==1?(isDeleted==1?"已删除":"已下架"):fromName==toName?"更改":"我想报名"}}
			</view>
			<view class="button" v-if="currentS<currentD" :style="{background:'#E0E0E0',color:'#FFFFFF'}">
				已过期
			</view>
			<button class="shareiconlabel" open-type="share">
				<view class="shareicon"></view>
				<view style="font-size: 20rpx;">分享</view>
			</button>
			<view class="collectioniconlabel" @click="collection">
				<image class="collectionicon" :src="isCollected?'../../static/Star1.png':'../../static/Star.png'">
				</image>
				<view style="font-size: 20rpx;">收藏</view>
			</view>
		</view>
		<loading ref="loading"></loading>
		<pullUp :array="item" ref="pullUp" v-on:select="select"></pullUp>
	</view>
</template>

<script>
	import pullUp from '@/components/pull-up/pull-up.vue'
	export default {
		data() {
			return {
				isCollected: false,
				picarray: [],
				name: '',
				data: '',
				info: null,
				time: "",
				people: '',
				place: '',
				price: ' ',
				focusState: 0,
				message: '',
				avatarUrl: '',
				titleBarTop: 0,
				username: '',
				createTime: '',
				detailedDesc: '',
				collectLock: false,
				collectNum: 0,
				imgNumber: 0,
				toName: '',
				isCancelled: 1,
				isDeleted: 0,
				item: ['下架', '删除', ],
				fromName: uni.getStorageSync('userInfo').id,
				currentD: Date.now(),
				currentS: ""
			}
		},
		components: {
			pullUp
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		onLoad(option) {
			if (!this.isLogin()) {
				uni.navigateTo({
					url: '../login/login'
				})
			}
			uni.$on('openSocket1', () => {
				this.init()
			})
			if (this.isLogin()) {
				this.init()
			}
			this.info = option.info
			this.data = option.info.replace(/""/g, "");
			this.data = JSON.parse(this.data)
			this.like = this.data.heartNum
			this.name = this.data.title
			this.avatarUrl = this.data.avatarUrl
			this.picarray.push('https://lyuanzhi.com:8080/img_' + this.data.id + '.jpg')
			this.getActivityDetail()
		},
		onShareAppMessage() {
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个活动",
				path: "pages/detialActivity/detialActivity?info=" + this.info,
				imageUrl: ''
			}
		},
		destroyed() {
			uni.$off("openSocket1")
		},
		methods: {
			clkImg(index) {
				uni.previewImage({
					urls: this.picarray,
					current: index
				})
			},
			change(index) {
				this.$refs.pullUp.OpenPop(index)
			},
			select(data) {
				if (data == '' || data == null) {
					return
				}
				this.$refs.loading.showLoading();
				if (data.type == '下架') {
					uni.request({
						url: this.$serverUrl + '/activity/cancelActivity/' + uni.getStorageSync('userInfo').id,
						method: "POST",
						data: {
							id: this.data.id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								if (res.data.data == "success") {
									this.isCancelled = 1
									uni.$emit("cancelActivity1", this.data.id)
								}
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				} else if (data.type == '删除') {
					uni.request({
						url: this.$serverUrl + '/activity/deleteActivity/' + uni.getStorageSync('userInfo').id,
						method: "POST",
						data: {
							id: this.data.id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								this.isDeleted = 1
								this.isCancelled = 1
								uni.$emit("deleteActivity1", this.data.id)
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				}
			},
			empty() {

			},
			login() {
				if (!this.isLogin()) {
					uni.navigateTo({
						url: '../../pages/login/login'
					})
				}
			},
			init() {
				if (this.$store.state.socket != null) {
					if (this.$store.state.socket.readyState == 1) {
						return
					}
				}
				this.$store.state.socket = uni.connectSocket({
					url: this.$serverWs + uni.getStorageSync('userInfo').id,
					complete: () => {}
				});
				uni.onSocketOpen((res) => {
					this.$store.state.reconnectTime = 0;
					this.heartbeat();
					uni.$emit("reGetInfo");
				});
				uni.onSocketError((res) => {
					this.reconnect();
				});
				uni.onSocketMessage((res) => {
					this.heartbeat();
					if (res.data != "pingdev") {
						this.$store.state.showRed = 1
						uni.$emit("receiveMsg", res.data);
						uni.$emit("receiveMsgInfo", res.data);
					}
				});
				uni.onSocketClose((res) => {
					this.reconnect();
				});
			},
			reconnect() {
				let that = this;
				if (that.$store.state.reconnectTime > 10) {
					return;
				}
				if (that.$store.state.reconnectLocked) {
					return;
				}
				that.$store.state.reconnectLocked = true;
				clearTimeout(that.$store.state.timeoutFunc);
				that.$store.state.timeoutFunc = setTimeout(function() {
					that.init();
					that.$store.state.reconnectLocked = false;
					that.$store.state.reconnectTime++;
				}, that.$store.state.timeout);
			},
			heartbeat() {
				let that = this;
				clearTimeout(that.$store.state.timeoutFunc1);
				clearTimeout(that.$store.state.timeoutFunc2);
				that.$store.state.timeoutFunc1 = setTimeout(function() {
					uni.sendSocketMessage({
						data: "pingcli"
					});
					that.$store.state.timeoutFunc2 = setTimeout(function() {
						uni.closeSocket();
					}, that.$store.state.timeout1);
				}, that.$store.state.timeout1)
			},
			getActivityDetail() {
				this.$refs.loading.showLoading();
				uni.request({
					url: this.$serverUrl + '/activity/getActivityDetailed',
					method: "POST",
					data: {
						activityId: this.data.id,
						userId: uni.getStorageSync('userInfo').id

					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.people = res.data.data.maxPeople
							this.price = res.data.data.activityFee
							this.username = res.data.data.username
							this.place = res.data.data.activityAddr
							this.time = res.data.data.activityTime
							this.detailedDesc = res.data.data.content
							this.createTime = this.checkDateTime(res.data.data.createTime)
							this.isCollected = res.data.data.isCollected
							this.imgNumber = res.data.data.imgNumber
							this.toName = res.data.data.userId
							this.isCancelled = res.data.data.isCancelled
							this.isDeleted = res.data.data.isDeleted
							this.currentS = new Date(this.time.replace(/-/g, "/")).getTime()
						}
						this.$refs.loading.hideLoading();
					},
					fail: (res) => {
						this.$refs.loading.hideLoading();
					}
				})
			},
			collection() {
				if (!this.isLogin()) {
					return;
				}
				if (this.collectLock) {
					return
				}
				if (this.collectNum > 5) {
					uni.showToast({
						icon: "none",
						title: "请勿频繁点击",
						duration: 2000
					})
					return
				}
				this.collectNum++
				this.isCollected = !this.isCollected
				this.collectLock = true
				uni.request({
					url: this.$serverUrl + '/activity/changeCollection',
					method: "POST",
					data: {
						activityId: this.data.id,
						userId: uni.getStorageSync('userInfo').id,
						type: this.isCollected ? 0 : 1
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.collectLock = false
						}
					},
					complete: (res) => {
						setTimeout(() => {
							this.collectLock = false
						}, 2000)
					}
				})
			},
			checkDateTime(createTime) {
				let dateCur = new Date();
				let date = new Date(createTime);
				if (dateCur.getTime() - createTime < 24 * 3600 * 1000) {
					if (dateCur.getTime() - createTime < 3600 * 1000) {
						if (dateCur.getTime() - createTime < 60 * 1000) {
							return "刚刚"
						}
						return Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60)) + "分钟前";
					} else {
						let hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
						let mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
						return Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60 * 60)) + "小时前"
					}
				} else if (dateCur.getTime() - createTime < 30 * 24 * 3600 * 1000 * 12) {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					return MM + DD;
				} else {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					let YY = date.getFullYear() + '-'
					return YY + MM + DD
				}
			},

			back() {
				if (!this.isLogin()) {
					return;
				}
				let pages = getCurrentPages();
				if (pages.length == 1) {
					uni.switchTab({
						url: '../community/community',
						success() {
							uni.hideTabBar()
						}
					})
				} else {
					uni.navigateBack({})
				}
			},
			signUp() {
				if (!this.isLogin()) {
					return;
				}
				let obj = {
					goodsId: null,
					activityId: this.data.id,
					toName: this.toName,
					toAvatarUrl: this.avatarUrl,
					toUsername: this.username,
					imgNumber: this.imgNumber
				}
				let textObj = JSON.stringify(obj)
				uni.navigateTo({
					url: '../chat/chat?ids=' + textObj
				})
			}
		}
	}
</script>

<style scoped>
	button::after {
		border: none;
	}

	.picarray {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin-bottom: 180rpx;
	}

	.content {
		display: flex;
		flex-direction: column;
		height: 100vh;
		overflow-y: scroll;
		overflow-x: hidden;
	}

	.topNavigationBar {
		z-index: 2;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		text-align: center;
		height: 182rpx;
		font-size: 32rpx;
		background: #ffffff;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.userinfo {
		height: 140rpx;
		flex-shrink: 0;
		margin-top: 180rpx;
		width: 100%;
		position: relative;
	}

	.profilePic {
		left: 5.5%;
		top: 15%;
		width: 110rpx;
		height: 110rpx;
		position: absolute;
	}

	.nameloaction {
		position: relative;
		top: 15%;
		left: 21%;
		display: flex;
		align-items: center;
		height: 50rpx;
	}

	.name {
		font-weight: bold;
		color: black;
		font-size: 28rpx;
	}

	.time {
		color: rgba(0, 0, 0, 0.4);
		position: absolute;
		left: 21%;
		top: 55%;
		font-size: 24rpx;
	}

	.line {
		width: 670rpx;
		margin-left: 40rpx;
		border: 2rpx solid rgba(0, 0, 0, 0.06);
	}

	.title {
		height: 70rpx;
		align-items: center;
		font-size: 32rpx;
		display: flex;
		margin-left: 40rpx;
		margin-top: 20rpx;
		font-weight: bold;
		letter-spacing: 0.140769rpx;
		flex-shrink: 0;
	}

	.label {
		margin-top: 15rpx;
		display: flex;
		align-items: center;
		height: 90rpx;
		width: 90%;
		margin-left: 5%;
		font-size: 24rpx;
		flex-shrink: 0;
	}

	.detail {
		width: 90%;
		margin-left: 5%;
		font-size: 28rpx;
		color: #00000099;
		display: flex;
		text-align: start;
		line-height: 56rpx;
		margin-bottom: 5rpx;
		letter-spacing: 0.140769px;
		margin-top: 15rpx;
		flex-shrink: 0;
	}

	.bottomplace {
		position: fixed;
		width: 100%;
		height: 164rpx;
		bottom: 0rpx;
		left: 0rpx;
		box-shadow: 0rpx -4rpx 20rpx rgba(230, 230, 230, 0.4), 0rpx -4rpx 0rpx rgba(0, 0, 0, 0.07);
		border-radius: 32rpx 32rpx 0rpx 0rpx;
		background-color: #ffffff;
		z-index: 5;
	}

	.button {
		top: 12%;
		right: 7%;
		width: 209rpx;
		height: 80rpx;
		position: absolute;
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-style: normal;
		letter-spacing: 0.1403846rpx;
		color: #FFFFFF;
		font-size: 32rpx;
		background: #48D1E0;

	}

	.shareiconlabel {
		border: none;
		outline: none;
		height: 120rpx;
		overflow: auto;
		line-height: 30rpx;
		background-color: transparent;
		position: absolute;
		left: 5%;
		top: 0%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.shareicon {
		background: url(../../static/shareicon.png) center/80% 80% no-repeat;
		width: 40rpx;
		height: 40rpx;
	}

	.collectioniconlabel {
		border: none;
		outline: none;
		height: 120rpx;
		overflow: auto;
		line-height: 30rpx;
		background-color: transparent;
		position: absolute;
		left: 23%;
		top: 0%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.collectionicon {
		width: 40rpx;
		height: 40rpx;
	}
</style>
