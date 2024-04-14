<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">我的院校</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view style="position: relative;">
			<input type="text" v-model="schoolName" maxlength="20" focus>
			<view class="cancel" @click="cancel" v-if="schoolName!=''"></view>
		</view>
		<view class="bottomplace">
			<view class="updatebotton" @click="comfirm">确认修改</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				titleBarTop: 0,
				schoolName: ''
			}
		},
		onLoad() {
			this.schoolName = uni.getStorageSync('schoolName')
			if (this.schoolName == null) {
				this.schoolName = ""
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
			comfirm() {
				if (this.schoolName == '') {
					uni.showToast({
						icon: "none",
						title: "院校不可为空",
						duration: 2000
					})
					return
				}
				uni.setStorageSync('schoolName', this.schoolName)
				let pages = getCurrentPages()
				let prevPage = pages[pages.length - 2]
				prevPage.$vm.schoolName = this.schoolName
				this.back()
			},
			cancel() {
				this.schoolName = ''
			}
		}
	}
</script>

<style scoped>
	input {
		background-color: #FFFFFF;
		margin-left: 24rpx;
		margin-right: 24rpx;
		height: 110rpx;
		margin-top: 200rpx;
		border-radius: 6rpx;
		padding: 0rpx 30rpx 0rpx 30rpx;
		font-size: 32rpx;
	}

	.content {
		display: flex;
		flex-direction: column;
		background-color: #F2F2F2;
		height: 100vh;
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
		color: #000000;
		font-size: 32rpx;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.bottomplace {
		position: fixed;
		width: 100%;
		height: 150rpx;
		bottom: 0rpx;
		left: 0rpx;
		z-index: 5;
	}

	.updatebotton {
		position: absolute;
		width: 90%;
		height: 90rpx;
		left: 5%;
		top: 15rpx;
		background: #48D1E0;
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 34rpx;
		color: #FFFFFF;
	}
	
	.cancel {
		background: url(../../static/cancel.png) center/100% 100% no-repeat;
		width: 28rpx;
		height: 28rpx;
		position: absolute;
		bottom: 41rpx;
		right: 56rpx;
		z-index: 99;
	}
</style>
