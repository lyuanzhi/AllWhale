<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">关于</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="icon"></view>
		<view style="font-size: 32rpx;line-height: 40rpx; text-align: justify;width: 84%;">
			ALLWHALE是一个基于英国伦敦的潮流生活社区，主打流行文化与艺术，目前有 20
			余位成员，拥有独立的技术、媒体、法务、财务团队，已注册公司与商标，有独立设计的小程序、公众号、网站。我们的目标是组建好玩儿的圈子、聚集有意思的话题、发布最新的潮流资讯。ALLWHALE旗下生活类小程序和国潮艺术品平台NextrendiN希望在建立留学生“聚集地”的同时，用艺术和潮流为国人发声，建立一个连接艺术与潮流的生活空间，将亚洲的潮牌、设计师品牌推向世界。
		</view>
		<view style="font-size: 32rpx;line-height: 40rpx; text-align: justify;width: 84%; margin-top: 100rpx;">
			开发者微信号：lyuanzhiHi、Match---L
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				titleBarTop: 0,
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
		}
	}
</script>

<style scoped>
	.content {
		display: flex;
		flex-direction: column;
		background-color: #FFFFFF;
		height: 100vh;
		align-items: center;
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

	.icon {
		margin-top: 200rpx;
		margin-bottom: 80rpx;
		width: 50vw;
		height: 50vw;
		background: url(../../static/logo.png) center/100% 100% no-repeat;
		border-radius: 50%;
	}
</style>
