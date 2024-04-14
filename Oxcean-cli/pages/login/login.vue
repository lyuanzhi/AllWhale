<template>
	<view class="background">
		<view class="backicon" :style="{top:(titleBarTop+5)+'px'}" @click="back()"></view>
		<view class="backgroundsphere" style="left: 50%; top: -100rpx;"></view>
		<view class="backgroundsphere" style="left: -10%; bottom: -200rpx;"></view>
		<view class="bigfont">
			欢迎使用 ALLWHALE
		</view>
		<view class="logobox">
			<view class="logo"></view>
			<view style="font-size: 27rpx; margin-top: 100rpx;">为了提供优质服务，ALLWHALE需要获取你的以下信息：</view>
			<view
				style="display: flex; justify-content: left; align-items: flex-start; width: 100%; margin-top: 20rpx;">
				<view
					style="font-weight: 900; margin-left: 8%; color: #00000099; line-height: 40rpx; font-size: 25rpx;">·
				</view>
				<view
					style=" color: #00000099;margin-left: 5rpx; letter-spacing: 0.0703846px; font-size: 25rpx; line-height: 40rpx;">
					你的微信头像、昵称</view>
			</view>
			<button open-type="getUserInfo" class="button" @click="loadInfo">
				授权进入ALLWHALE
			</button>
			<view style="width: 100%; display: flex; justify-content: center; position: absolute; bottom: 3%;">
				<view style="font-size: 27rpx; ">登录即代表同意</view>
				<view style=" color: #73dbe7;font-size: 27rpx;">《用户使用协议》</view>
			</view>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				lock: false,
				func: null,
				titleBarTop: 0
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		methods: {
			back() {
				uni.navigateBack({})
			},
			loadInfo() {
				if (this.lock) {
					return
				}
				this.lock = true
				var that = this;
				uni.getUserProfile({
					desc: "登录",
					success: info => {
						uni.login({
							success: res_login => {
								this.$refs.loading.showLoading();
								uni.request({
									url: that.$serverUrl + '/user/login',
									method: "POST",
									data: {
										jsCode: res_login.code,
										username: info.userInfo.nickName,
										avatarUrl: info.userInfo.avatarUrl
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200) {
											uni.setStorageSync('userInfo', res.data
												.data);
											uni.$emit('openSocket')
											uni.$emit('openSocket1')
											uni.$emit('changeUserInfo')
											that.lock = false
											clearTimeout(that.func)
											this.$refs.loading.hideLoading();
											uni.navigateBack({})
										}
									},
									complete() {
										that.func = setTimeout(() => {
											that.lock = false
											this.$refs.loading.hideLoading();
										}, 2000)
									}
								})
							}
						})
					}
				})
			}
		}
	}
</script>

<style scoped>
	.background {
		width: 100%;
		height: 100vh;
		position: relative;
	}

	.backgroundsphere {
		position: fixed;
		width: 400rpx;
		height: 400rpx;
		border-radius: 50%;
		background: rgba(0, 255, 240, 0.06);
		filter: blur(60px);
	}

	.bigfont {
		position: absolute;
		top: 15%;
		left: 10%;
		font-size: 50rpx;
		font-weight: 700;
		letter-spacing: 0.0703846px;
		color: #000000;
	}

	.logobox {
		position: absolute;
		width: 100%;
		display: flex;
		align-items: center;
		height: 80%;
		top: 20%;
		flex-direction: column;
	}

	.logo {
		width: 250px;
		height: 250px;
		background: url(../../static/logo.png) center/100% 100% no-repeat;
	}

	.button {
		margin-top: 20rpx;
		letter-spacing: 0.0703846px;
		color: #FFFFFF;
		width: 88%;
		background: #48D1E0;
		border-radius: 200rpx;
		font-size: 34rpx;
		height: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 3%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}
</style>
