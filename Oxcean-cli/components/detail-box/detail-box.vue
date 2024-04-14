<template>
	<view class="Box" @click="detailinfo">
		<image :src="array.imgNumber!=0?'https://lyuanzhi.com:8080/img_'+array.id+'_0.jpg':'../../static/none2.png'" mode="widthFix"></image>
		<view class="text-title">{{array.name}}</view>
		<view style="display: flex;">
			<view class="text-price">£{{array.price}}</view>
			<view class="text-old_price" v-if="(array.oldPrice!=-1)&&(array.oldPrice!=null)&&(array.oldPrice!='null')">£{{array.oldPrice}}</view>
		</view>
		<view class="label-row">
			<view class='label' v-if="(array.howNewType!=null)&&(array.howNewType!='null')">
				<view style="margin-left: 6rpx; margin-right: 6rpx;">
					{{array.howNewType}}
				</view>
			</view>
			<view class='label' v-if="(array.pickUpType!=null)&&(array.pickUpType!='null')">
				<view style="margin-left: 6rpx; margin-right: 6rpx;">
					{{array.pickUpType}}
				</view>
			</view>
			<view class='label' v-if="(array.locationType!=null)&&(array.locationType!='null')">
				<view style="margin-left: 6rpx; margin-right: 6rpx;">
					{{array.locationType}}
				</view>
			</view>
			<view class='label' v-if="(array.houseType!=null)&&(array.houseType!='null')">
				<view style="margin-left: 6rpx; margin-right: 6rpx;">
					{{array.houseType}}
				</view>
			</view>
			<view class='label' v-if="(array.tenancyType!=null)&&(array.tenancyType!='null')">
				<view style="margin-left: 6rpx; margin-right: 6rpx;">
					{{array.tenancyType == '>3月'?'长租':'短租'}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return{
			
			}
		},
		props: ['array'],
		onLoad() {
		},
		
		methods:{
			detailinfo(){
				if(!this.isLogin()){
					return
				}
				var textObj = JSON.stringify(this.array)
				uni.navigateTo({
					url: '../../pages/detailInfo/detailInfo?info='+textObj,
				})
			},
		}
	}
</script>

<style scoped>
	.Box {
		margin-bottom: 20rpx;
		display: flex;
		flex-direction: column;
		box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
		border-radius: 6px;
		overflow: hidden;
	}

	.text-title {
		font-size: 28rpx;
		margin-top: 12rpx;
		margin-left: 20rpx;
	}

	.text-price {
		color: #E06653;
		margin-left: 20rpx;
		margin-top: 12rpx;
		font-size: 28rpx;
	}

	.text-old_price {
		margin-left: 12rpx;
		margin-top: 12rpx;
		font-size: 20rpx;
		text-decoration-line: line-through;
		color: rgba(0, 0, 0, 0.4);
		line-height: 20rpx;
		padding-top: 12rpx;
		box-sizing: border-box;
	}

	.label-row {
		display: flex;
		line-height: 14px;
		margin-bottom: 25rpx;
		margin-left: 10rpx;
		flex-wrap: wrap;
	}

	.label {
		margin-top: 12rpx;
		height: 35rpx;
		margin-left: 10rpx;
		border: 1px solid #D9D9D9;
		box-sizing: border-box;
		border-radius: 2px;
		font-size: 20rpx;
		line-height: 32rpx;
		letter-spacing: 0.0703846px;
		color: #000000;
	}

	image {
		width: 100%;
		filter: drop-shadow(0 0 0 #000000);
		max-height: 500rpx;
	}
</style>
