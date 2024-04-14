<template>
	<view class="outer">
		<view v-if="isRelease" class="masking" @click="release"></view>
		<view class="releaseBox" :class="{active:isRelease}">
			<block v-for="(item,index) in releaseType" :key="index">
				<view
					style="display: flex; flex-direction: column;align-items: center;padding-top: 58rpx;box-sizing: border-box;"
					@click="toReleasePage(item.index)">
					<image v-show="isRelease" class="release_img animated fadeIn" :src="item.url"></image>
					<view v-show="isRelease" class="release_name animated fadeIn">{{item.name}}</view>
				</view>
			</block>
		</view>
		<view class="tabBar" :class="{active: !isRelease}">
			<view class="square" @click="changeBar('community')" v-if="!isRelease">
				<image style="width:38rpx;height: 38rpx;" v-if="currentPage=='community'"
					src="../../static/communityS.png"></image>
				<image style="width: 38rpx;height: 38rpx;" v-if="currentPage!='community'"
					src="../../static/community.png"></image>
				<view class="text">社区</view>
			</view>
			<view class="square" @click="changeBar('goods')" v-if="!isRelease">
				<image style="width:42rpx;height: 38rpx;" v-if="currentPage=='goods'" src="../../static/goodsS.png">
				</image>
				<image style="width:42rpx;height: 38rpx;" v-if="currentPage!='goods'" src="../../static/goods.png">
				</image>
				<view class="text">置物</view>
			</view>
			<view class="bigSquare">
				<image class="release" src="../../static/release.png" @click="release" :class="{'rotate':isRelease}">
				</image>
			</view>
			<view class="square" @click="changeBar('info')" v-if="!isRelease">
				<image style="width: 40rpx;height: 38rpx;" v-if="currentPage=='info'&&showRed==0"
					src="../../static/infoS.png"></image>
				<image style="width: 40rpx;height: 38rpx;" v-if="currentPage!='info'&&showRed==0"
					src="../../static/info.png"></image>
				<image style="width: 40rpx;height: 38rpx;" v-if="currentPage=='info'&&showRed!=0"
					src="../../static/infoSA.png"></image>
				<image style="width: 40rpx;height: 38rpx;" v-if="currentPage!='info'&&showRed!=0"
					src="../../static/infoA.png"></image>
				<view class="text">消息</view>
			</view>
			<view class="square" @click="changeBar('my')" v-if="!isRelease">
				<image style="width: 36rpx;height: 38rpx;" v-if="currentPage=='my'" src="../../static/myS.png"></image>
				<image style="width: 36rpx;height: 38rpx;" v-if="currentPage!='my'" src="../../static/my.png"></image>
				<view class="text">我的</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isRelease: false,
				releaseType: [{
					name: "话题",
					index: 3,
					url: "../../static/release3.png"
				}, {
					name: "活动",
					index: 4,
					url: "../../static/release4.png"
				}, {
					name: "闲置",
					index: 1,
					url: "../../static/release1.png"
				}, {
					name: "租房",
					index: 2,
					url: "../../static/release2.png"
				}]
			};
		},
		computed: {
			showRed() {
				return this.$store.state.showRed
			}
		},
		props: {
			currentPage: ''
		},
		methods: {
			changeBar(page) {
				if (this.currentPage == page) {
					return
				}
				if (!this.isLogin()) {
					return
				}
				uni.switchTab({
					url: '../../pages/' + page + '/' + page
				})
			},
			release() {
				if (!this.isLogin()) {
					return
				}
				if (this.isRelease == false) {
					this.isRelease = true
				} else {
					this.isRelease = false
				}
			},
			toReleasePage(index) {
				uni.navigateTo({
					url: "../../pages/release/release_" + index
				})
				this.release()
			}
		}
	}
</script>

<style scoped>
	.masking {
		position: fixed;
		top: 0;
		left: 0;
		width: 100vw;
		height: 100vh;
		z-index: -1 !important;
		background-color: rgba(0, 0, 0, 0.1) !important;
	}

	.outer {
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		z-index: 1 !important;
	}

	.releaseBox {
		width: 100%;
		height: 0rpx;
		transition: all 0.2s ease-in-out;
		display: flex;
		justify-content: center;
		background-color: #ffffff;
	}

	.releaseBox.active {
		height: 304rpx;
		border-radius: 16px 16px 0 0;
		transition: all 0.2s ease-in-out;
	}

	.release_img {
		height: 112rpx;
		width: 112rpx;
		border-radius: 50%;
		margin: 0 26rpx;
	}

	.release_name {
		width: 66rpx;
		height: 44rpx;
		font-size: 32rpx;
		line-height: 44rpx;
		margin-top: 8rpx;
	}

	.tabBar {
		height: 164rpx;
		width: 100%;
		display: flex;
		justify-content: center;
		padding-top: 16rpx;
		box-sizing: border-box;
		background-color: white;
	}

	.tabBar.active {
		box-shadow: 0px -2px 0px rgba(230, 230, 230, 0.4);
	}

	.square {
		width: 18%;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding-top: 8rpx;
	}

	.bigSquare {
		width: 28%;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.text {
		margin-top: 6rpx;
		width: 42rpx;
		height: 28rpx;
		font-size: 20rpx;
		line-height: 24rpx;
		letter-spacing: 0.14rpx;
		text-align: center;
		white-space: nowrap;
	}

	.release {
		width: 84rpx;
		height: 84rpx;
		transition: all .5s ease-in-out;
	}

	.rotate {
		transform: rotate(45deg);
		transition: all .5s ease-in-out;
	}
</style>
