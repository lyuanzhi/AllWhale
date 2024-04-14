<!-- 
	2021/10/25
	match
 -->

<template>
	<view>
		<view class="background" v-if="isopen" @click="cancel">
		</view>
		<view class="body" :class="{'active':isopen}">
			<view v-for="(item,index) in array" :key='index' class="label" @click="select(index)">
				{{item}}
			</view>
			<view class="label" style="color: #00000066;" @click="cancel">取消</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isopen: 0,
				object:0,
			}
		},
		props: ['array'],
		onLoad() {

		},

		methods: {
			OpenPop(index) {
				this.isopen = 1
				this.object=index
			},
			cancel(){
				this.isopen = 0
			},
			select(index){
				this.$emit('select', {type:this.array[index],object:this.object});
				this.isopen = 0
				
			}
		}
	}
</script>

<style scoped>
	
	.background {
		width: 100%;
		background: rgba(0, 0, 0, 0.1);
		height: 100vh;
		position: fixed;
		top: 0rpx;
		z-index: 100;
	}

	.body.active {
		bottom: 0rpx;
		transition: all .3s ease-in-out;
	}

	.body {
		width: 100%;
		z-index: 101;
		background-color: white;
		position: fixed;
		bottom: -500rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: space-evenly;
		padding: 20rpx 0rpx 40rpx 0rpx;
		border-radius: 32rpx 32rpx 0rpx 0rpx;
		background-color: white;
		transition: all .3s ease-in-out;
	}

	.label {
		height: 120rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-weight: bold;
		font-size: 36rpx;
		letter-spacing: 0.140769rpx;
		font-weight: bold;
		width: 100%;
	}
</style>
