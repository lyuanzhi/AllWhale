<template>
	<view class="content">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height+'px'}" @click="back('')">
				</view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" placeholder="搜索" confirm-type='search'
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-45)+'px'}"
					disabled @click="back(content)" :value="content">
				</input>
			</view>
		</uni-nav-bar>
		<view v-if="detailArray[0].length+detailArray[1].length==0"
			style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;">
			<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
			<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无商品</view>
		</view>
		<view class="thrid-place" v-if="detailArray[0].length+detailArray[1].length>0">
			<view class="thrid-place-half">
				<detail-box v-for="(item,index) in detailArray[0]" :key="index" :array="item" style="width: 100%;">
				</detail-box>
			</view>
			<view class="thrid-place-half">
				<detail-box v-for="(item,index) in detailArray[1]" :key="index" :array="item" style="width: 100%;">
				</detail-box>
			</view>
		</view>
		<view style="width: 100%;height: 60rpx; position: fixed;bottom: 0;left: 0;background: #FFFFFF;"></view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	import detailBox from '@/components/detail-box/detail-box.vue'
	export default {
		data() {
			return {
				lock: false,
				content: '',
				titleBar: null,
				page: 1,
				total: 1,
				detailArray: [
					[],
					[]
				]
			}
		},
		components: {
			detailBox
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		onLoad(option) {
			this.content = option.content
			this.flash()
		},
		onReachBottom() {
			this.flash()
		},
		methods: {
			arraySeparate(array) {
				for (let i = 0; i < array.length; i++) {
					this.detailArray[i % 2].push(array[i])
				}
			},
			back(content) {
				let pages = getCurrentPages()
				let prevPage = pages[pages.length - 2];
				prevPage.$vm.content = content
				uni.navigateBack({})
			},
			flash() {
				if (this.page > this.total) {
					return
				}
				if (this.lock) {
					return
				}
				this.lock = true
				this.$refs.loading.showLoading();
				uni.request({
					url: this.$serverUrl + '/goods/searchGoods',
					method: 'POST',
					data: {
						page: this.page,
						searchValue: this.content
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.arraySeparate(res.data.data.rows)
							this.total = res.data.data.total
							this.page++
							this.lock = false
							this.$refs.loading.hideLoading();
						}
					},
					complete: (res) => {
						setTimeout(() => {
							this.lock = false;
							this.$refs.loading.hideLoading();
						}, 2000);
					}
				});
			}
		}
	}
</script>

<style scoped>
	.content {
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: #FFFFFF;
		flex-direction: column;
		overflow-x: hidden;
	}

	.bar {
		display: flex;
		flex-direction: row;
		align-items: center;
		position: fixed;
	}

	.logo {
		margin-left: 10px;
		margin-right: 10px;
		background: url(../../static/back.png) center/40% 70% no-repeat;
		filter: brightness(0%);
	}

	.navBarSearch {
		border-top-right-radius: 50rpx;
		border-bottom-right-radius: 50rpx;
		background-color: #EBF2F2;
		color: rgb(34, 21, 51);
		padding-right: 80rpx;
		box-sizing: border-box;
		font-size: 28rpx;
	}

	.cancenl {
		background: url(../../static/cancel.png) center/100% 100% no-repeat;
		width: 35rpx;
		height: 35rpx;
		position: absolute;
		z-index: 99;
	}

	.searchIcon {
		width: 28px;
		display: flex;
		justify-content: center;
		border-top-left-radius: 50rpx;
		border-bottom-left-radius: 50rpx;
		align-items: center;
		background: #EBF2F2 url(../../static/search.png) center/60% no-repeat;
	}

	.thrid-place {
		box-sizing: border-box;
		padding-bottom: 170rpx;
		padding-top: 30rpx;
		display: flex;
		flex-direction: row;
		width: 100%;
		align-content: flex-start;
		justify-content: space-evenly;
		background-color: #ffffff;
	}

	.thrid-place-half {
		width: 45%;
		display: flex;
		flex-direction: column;
	}
</style>
