<template>
	<view class="background" @click="login">
		<view class="topNavigationBar">
			<view class="backiconout" :style="{top:(titleBarTop+5)+'px'}" @click="back">
				<view class="backicon"></view>
			</view>
		</view>
		<view class="userinfo">
			<view class="profilePic">
				<image :src="avatarUrl" mode="" style="width: 110rpx;height: 110rpx; border-radius: 10rpx;"></image>
			</view>
			<view class="nameloaction">
				<view class="name">{{data.username}}</view>
				<view class="locationicon"></view>
				<view class="location">{{data.locationType}}</view>
			</view>
			<view class="time">
				发布于{{data.createTime}}
			</view>
		</view>
		<view class="line"></view>
		<view class="pricelabel">
			<view style="margin-left: 6rpx; color: #ff0000; font-size: 30rpx; font-weight: bold;line-height: 34rpx">£
			</view>
			<view style=" color: #ff0000; font-size: 30rpx; font-weight: bold; line-height: 34rpx;">{{data.price}}
			</view>
			<view v-if="data.oldPrice!=null"
				style="font-size: 24rpx;margin-left: 10rpx; text-decoration-line: line-through; color: #00000066; line-height: 28rpx;">
				£{{data.oldPrice}}</view>
		</view>
		<view class="title">{{data.name}}</view>
		<view class="detail">{{data.detailedDesc}}</view>
		<view class="label">
			<view style="color: rgba(0, 0, 0, 0.4);">位置</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.locationType}}
				</view>
			</view>
		</view>
		<view class="label" v-if="data.howNewType!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">新旧</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.howNewType}}
				</view>
			</view>
		</view>
		<view class="label" v-if="data.pickUpType!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">取货</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.pickUpType}}
				</view>
			</view>
		</view>
		<view class="label" v-if="data.tenancyType">
			<view style="color: rgba(0, 0, 0, 0.4);">租期</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.tenancyType}}
				</view>
			</view>
		</view>
		<view class="label" v-if="data.houseType!=null">
			<view style="color: rgba(0, 0, 0, 0.4);">房型</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.houseType}}
				</view>
			</view>
		</view>
		<view class="label" v-if="data.ensuiteType">
			<view style="color: rgba(0, 0, 0, 0.4);">卫浴</view>
			<view
				style="height: 70%; background: #DCF1F3; border-radius: 12rpx;display: flex; align-items: center; margin-left: 20rpx;">
				<view style="margin-left: 20rpx; margin-right: 20rpx; color: #499199; font-weight: 900;">
					{{data.ensuiteType}}
				</view>
			</view>
		</view>
		<view class="picarray">
			<image v-for="(item,index) in picarray" :src="item" mode="widthFix"
				style="margin-top: 20rpx; width: 90%; border-radius: 10rpx;" :key='index' @click="clkImg(index)">
			</image>
		</view>
		<view class="whowant">{{number}}人想要</view>
		<view class='bottomplace'>
			<view class="button"
				:style="{background:isCancelled==1?'#E0E0E0':fromName==toName?'#FFFFFF':'#48D1E0',color:isCancelled==1?'#FFFFFF':fromName==toName?'#000000':'#FFFFFF',border:fromName==toName&&isCancelled==0?'1px solid rgba(0, 0, 0, 0.36)':'none'}"
				@click="isCancelled==1?empty():fromName==toName?change(0):toChat()">
				{{isCancelled==1?(isDeleted==1?"已删除":"已下架"):fromName==toName?"更改":"我想要"}}
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
				data: '',
				avatarUrl: '',
				number: 0,
				picarray: [],
				info: null,
				titleBarTop: 0,
				isCollected: false,
				collectLock: false,
				collectNum: 0,
				wantedLock: false,
				toName: 'toName',
				isCancelled: 1,
				isDeleted: 0,
				fromName: uni.getStorageSync('userInfo').id,
				item: ['下架', '删除', ]
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
			this.$refs.loading.showLoading();
			uni.request({
				url: this.$serverUrl + '/goods/getGoodsDetailed',
				method: "POST",
				data: {
					goodsId: this.data.id,
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.avatarUrl = res.data.data.avatarUrl
						this.data.username = res.data.data.username
						this.data.detailedDesc = res.data.data.detailedDesc
						this.data.ensuiteType = res.data.data.ensuiteType
						this.isCollected = res.data.data.isCollected
						this.number = res.data.data.wanted
						this.toName = res.data.data.userId
						this.isCancelled = res.data.data.isCancelled
						this.isDeleted = res.data.data.isDeleted
					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})
			this.data.createTime = this.checkDateTime(this.data.createTime)
			for (let i = 0; i <= this.data.imgNumber - 1; i++) {
				this.picarray.push('https://lyuanzhi.com:8080/img_' + this.data.id + '_' + i + '.jpg')
			}
		},
		destroyed() {
			uni.$off("openSocket1")
		},
		onShareAppMessage() {
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个商品",
				path: "pages/detailInfo/detailInfo?info=" + this.info,
				imageUrl: this.data.imgNumber == 0 ? '' : 'https://lyuanzhi.com:8080/img_' + this.data.id + '_0.jpg'
			}
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
						url: this.$serverUrl + '/goods/cancelGoods/' + uni.getStorageSync('userInfo').id,
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
									uni.$emit("cancelGoods1", this.data.id)
								}
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				} else if (data.type == '删除') {
					uni.request({
						url: this.$serverUrl + '/goods/deleteGoods/' + uni.getStorageSync('userInfo').id,
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
								uni.$emit("deleteGoods1", this.data.id)
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				}
			},
			login() {
				if (!this.isLogin()) {
					uni.navigateTo({
						url: '../../pages/login/login'
					})
				}
			},
			empty() {

			},
			toChat() {
				if (!this.isLogin()) {
					return;
				}
				this.addWanted()
				let obj = {
					goodsId: this.data.id,
					activityId: null,
					toName: this.toName,
					toAvatarUrl: this.avatarUrl,
					toUsername: this.data.username,
					imgNumber: this.data.imgNumber
				}
				let textObj = JSON.stringify(obj)
				uni.navigateTo({
					url: '../chat/chat?ids=' + textObj
				})
			},
			addWanted() {
				if (!this.isLogin()) {
					return;
				}
				if (this.wantedLock) {
					return
				}
				this.wantedLock = true
				uni.request({
					url: this.$serverUrl + '/goods/addWanted',
					method: "POST",
					data: {
						goodsId: this.data.id,
						userId: uni.getStorageSync('userInfo').id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.wantedLock = false
							if (res.data.data == 1) {
								this.number++
							}
						}
					},
					complete: (res) => {
						setTimeout(() => {
							this.wantedLock = false
						}, 2000)
					}
				})
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
				this.isCollected = !this.isCollected
				this.collectNum++;
				this.collectLock = true
				uni.request({
					url: this.$serverUrl + '/goods/changeCollection',
					method: "POST",
					data: {
						goodsId: this.data.id,
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
			back() {
				if (!this.isLogin()) {
					return;
				}
				let pages = getCurrentPages();
				if (pages.length == 1) {
					uni.switchTab({
						url: '../goods/goods',
						success() {
							uni.hideTabBar()
						}
					})
				} else {
					uni.navigateBack({})
				}
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
				} else if (dateCur.getTime() - createTime < 30 * 24 * 3600 * 1000) {
					let DD = Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60 * 60 * 24));
					return DD + "天前";
				} else if (dateCur.getTime() - createTime < 365 * 24 * 3600 * 1000) {
					let MM = Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60 * 60 * 24 * 30));
					return MM + "月前";
				} else {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					let YY = date.getFullYear() + '-'
					return YY + MM + DD
				}
			},
		}
	}
</script>

<style scoped>
	.background {
		padding: 0;
		margin: 0;
		width: 100%;
		display: flex;
		flex-direction: column;
		overflow-x: hidden;
	}

	.picarray {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.whowant {
		width: 90%;
		margin-left: 5%;
		margin-top: 20rpx;
		text-align: end;
		color: rgba(0, 0, 0, 0.4);
		margin-bottom: 200rpx;
		font-size: 28rpx;
	}

	.topNavigationBar {
		z-index: 5;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		text-align: center;
		height: 180rpx;
		background-color: #ffffff;
		color: #ffffff;
		font-size: 32rpx;
	}

	.backiconout {
		position: absolute;
		width: 60rpx;
		height: 50rpx;
		left: 4%;
		display: flex;
		justify-content: center;
	}

	.backicon {
		width: 30rpx;
		height: 50rpx;
		background: url(../../static/back.png) center/85% 85% no-repeat;
		filter: brightness(0%);
	}

	.line {
		width: 90%;
		border: 3rpx solid rgba(0, 0, 0, 0.06);
		margin-left: 5%;
	}

	.userinfo {
		height: 165rpx;
		margin-top: 180rpx;
		width: 100%;
		position: relative;
	}

	.nameloaction {
		position: relative;
		top: 15%;
		left: 23%;
		display: flex;
		align-items: center;
		height: 50rpx;
	}

	.name {
		font-weight: bold;
		color: black;
		font-size: 28rpx;
	}

	.profilePic {
		left: 6%;
		top: 15%;
		width: 110rpx;
		height: 110rpx;
		position: absolute;
	}

	.locationicon {
		width: 34rpx;
		height: 40rpx;
		background: url(../../static/locationicon.png) center/80% 80% no-repeat;
		margin-left: 16rpx;
	}

	.location {
		color: #5DB8C2;
		font-size: 24rpx;
	}

	.time {
		color: rgba(0, 0, 0, 0.4);
		position: absolute;
		left: 23%;
		top: 50%;
		font-size: 24rpx;
	}

	.pricelabel {
		height: 70rpx;
		width: 90%;
		margin-left: 5%;
		display: flex;
		align-items: flex-end;
	}

	.title {
		height: 70rpx;
		align-items: center;
		font-size: 32rpx;
		display: flex;
		margin-left: 40rpx;
		margin-top: 20rpx;
		font-weight: bold;
		letter-spacing: 0.140769px;
	}

	.detail {
		width: 90%;
		margin-left: 5%;
		font-size: 28rpx;
		color: #00000099;
		display: flex;
		text-align: start;
		line-height: 56rpx;
		margin-bottom: 20rpx;
		letter-spacing: 0.140769px;
	}

	.label {
		margin-top: 20rpx;
		display: flex;
		align-items: center;
		height: 100rpx;
		width: 90%;
		margin-left: 5%;
		font-size: 24rpx;
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
		width: 178rpx;
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

	button::after {
		border: none;
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
