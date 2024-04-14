<template>
	<view class="content">
		<image src="../../static/backgound.png" style="width: 100%; position: absolute; top: 0;"></image>
		<view
			style="display: flex; flex-direction: column; margin-left: 54rpx; margin-top: 153rpx; z-index: 1; position: absolute;">
			<image :src='avatarUrl'
				style="width: 156rpx; height: 156rpx; border-radius: 50%;border: 8rpx solid #ffffff;"></image>
			<view
				style="font-weight: 900; font-size: 36rpx; letter-spacing: 0.140769rpx; color: #FFFFFF;margin-top: 20rpx;">
				{{userName}}
			</view>
		</view>
		<view class="firstplace">
			<view class="Label" @click="redirect('myCollection')">
				<image class="img" src="../../static/myStar.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的收藏
				</view>
				<view class="icon"></view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('myGoods')">
				<image class="img" src="../../static/myGoods.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的闲置帖
				</view>
				<view class="icon"></view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('myHouse')">
				<image class="img" src="../../static/myHouse.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的租房帖
				</view>
				<view class="icon"></view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('myTopic')">
				<image class="img" src="../../static/myTopic.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的话题帖
				</view>
				<view class="icon"></view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('myActivity')">
				<image class="img" src="../../static/myActivity.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的活动帖
				</view>
				<view class="icon"></view>
			</view>
		</view>
		<view class="second-place">
			<view class="Label" @click="redirect('myWechat')">
				<image class="img" src="../../static/myWx.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的联系方式
				</view>
				<view
					style="display: flex; flex-direction: row-reverse; position: absolute; right: 80rpx; font-size: 28rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.4);">
					{{wxNumber}}
				</view>
				<view class="icon"></view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('mySchool')">
				<image class="img" src="../../static/mySchool.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">我的院校
				</view>
				<view
					style="display: flex; flex-direction: row-reverse; position: absolute; right: 80rpx; font-size: 28rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.4);">
					{{schoolName}}
				</view>
				<view class="icon"></view>
			</view>
		</view>
		<view class="second-place" style="margin-bottom: 180rpx;">
			<view class="Label" @click="redirect('about')">
				<image class="img" src="../../static/about.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">关于
				</view>
			</view>
			<view class="line"></view>
			<view class="Label" @click="redirect('login')">
				<image class="img" src="../../static/myExit.png"></image>
				<view style="font-size: 28rpx; letter-spacing: 0.140769rpx; color: #000000; margin-left: 32rpx;">重新登录
				</view>
			</view>
		</view>
		<custom-tab-bar currentPage="my"></custom-tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				avatarUrl: uni.getStorageSync('userInfo').avatarUrl,
				userName: uni.getStorageSync('userInfo').username,
				schoolName: "",
				wxNumber: ""
			}
		},
		onLoad() {
			uni.$on("changeUserInfo", () => {
				this.avatarUrl = uni.getStorageSync('userInfo').avatarUrl
				this.userName = uni.getStorageSync('userInfo').username
			})
		},
		onShow() {
			this.schoolName = uni.getStorageSync('schoolName')
			if (this.schoolName == null) {
				this.schoolName = ""
			}
			this.wxNumber = uni.getStorageSync('wxNumber')
			if (this.wxNumber == null) {
				this.wxNumber = ""
			}
		},
		methods: {
			redirect(e) {
				uni.navigateTo({
					url: '../../pages/' + e + '/' + e,
				})
			}
		}
	}
</script>

<style scoped>
	.content {
		width: 100%;
		display: flex;
		flex-direction: column;
		background-color: #F3F5F5;
		height: 100vh;
	}

	.firstplace {
		margin-top: 442rpx;
		background: #ffffff;
		width: 100%;
		border-radius: 25rpx 25rpx 0rpx 0rpx;
		z-index: 0;
		display: flex;
		flex-direction: column;
	}

	.second-place {
		margin-top: 14rpx;
		background: #ffffff;
		display: flex;
		flex-direction: column;
		width: 100%;
	}

	.Label {
		margin-left: 40rpx;
		height: 104rpx;
		display: flex;
		align-items: center;
		position: relative;
	}

	.img {
		width: 42rpx;
		height: 42rpx;
	}

	.line {
		border: 1rpx solid rgba(0, 0, 0, 0.06);
		width: calc(100% - 80rpx);
		margin-left: 40rpx;
	}

	.icon {
		background: url(../../static/labelicon.png) center/100% 100% no-repeat;
		transform: rotate(-90deg);
		width: 22rpx;
		height: 22rpx;
		margin-left: 440rpx;
		position: absolute;
		right: 54rpx;
	}
</style>
