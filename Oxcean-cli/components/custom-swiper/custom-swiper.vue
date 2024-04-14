<!-- 
	description:轮播图
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view class="box">
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true"
			indicator-color="rgb(255,255,255)" indicator-active-color="#363b59">
			<block v-for="(item,index) in banner" :key="index">
				<swiper-item>
					<view class="swiper-item" id="swiper-item">
						<image :src="item" class="imageurl" @click="previewImg(index)" mode="scaleToFill">
						</image>
					</view>
				</swiper-item>
			</block>
		</swiper>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				banner: [
					"https://lyuanzhi.com:8080/swiper_1.png",
					"https://lyuanzhi.com:8080/swiper_2.png",
					"https://lyuanzhi.com:8080/swiper_3.png",
					"https://lyuanzhi.com:8080/swiper_4.png",
					"https://lyuanzhi.com:8080/swiper_5.png",
					"https://lyuanzhi.com:8080/swiper_6.png"
				]
			}
		},
		methods: {
			previewImg(current) {
				if (uni.getStorageSync('userInfo') == "" || uni.getStorageSync('userInfo') == null) {
					return;
				};
				uni.previewImage({
					current,
					urls: this.banner
				})
			}
		}
	}
</script>

<style scoped>
	.box {
		width: 700rpx;
		margin-left: 23.5rpx;
		margin-right: 26.5rpx;
		border-radius: 10rpx;
		overflow: hidden;
		background-color: #FFFFFF !important;

	}

	.imageurl {
		width: 100%;
		height: 300upx !important;
	}

	swiper {
		height: 300upx !important;
		width: 100%;
	}
</style>
