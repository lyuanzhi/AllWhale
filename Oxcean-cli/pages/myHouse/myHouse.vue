<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">我的租房帖</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="typeselect">
			<view v-for="(item, index) in typeList" :key="index" class="typebar"
				:class="{'active':selectdedType==index}" @click="typeSelect(index)">
				{{item}}
			</view>
		</view>
		<view class="LabelBox">
			<view v-if="selectdedType?noList.length==0:List.length==0"
				style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无租房帖</view>
			</view>
			<view v-for="(item, index) in !selectdedType?List:noList" :key="index" class="Label"
				@click="toDetailInfo(index)">
				<view class="fontBox" @click.stop="change(index)">更改
				</view>

				<image :src="item.imgNumber!=0?'https://lyuanzhi.com:8080/img_'+item.id+'_0.jpg':'../../static/none2.png'"
					style="width: 188rpx; height: 188rpx; margin-left: 20rpx; margin-top: 40rpx;" mode="aspectFill">
				</image>

				<view style="display: flex; flex-direction: column; margin-left: 30rpx;">
					<view style="margin-top: 40rpx; font-size: 28rpx; letter-spacing: 0.140769rpx; ">
						{{item.name.length>=14?item.name.substring(0,14)+"...":item.name}}
					</view>
					<view
						style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: #E06653; margin-top: 30rpx; display: flex; align-items: flex-end; font-weight: bold;">
						£
						<view style="font-size: 30rpx">{{item.price}}</view>
					</view>
					<view
						style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.4); margin-top: 50rpx; display: flex; align-items: flex-end;">
						{{item.wanted}}人想要
					</view>
				</view>
			</view>
		</view>
		<pullUp :array="item" ref="pullUp" v-on:select="select"></pullUp>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	import pullUp from '@/components/pull-up/pull-up.vue'
	export default {
		data() {
			return {
				selectdedType: 0,
				titleBarTop: 0,
				typeList: ["在售", "已下架"],
				List: [],
				noList: [],
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
		onLoad() {
			this.$refs.loading.showLoading();
			uni.request({
				url: this.$serverUrl + '/goods/getMyGoods',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id,
					type: "租房"
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						for (let i = 0; i < res.data.data.length; i++) {
							if (res.data.data[i].isCancelled == 0) {
								this.List.push(res.data.data[i])
							} else {
								this.noList.push(res.data.data[i])
							}
						}
					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})

			uni.$on("cancelGoods1", (id) => {
				for (let i = 0; i < this.List.length; i++) {
					if (this.List[i].id == id) {
						this.noList.unshift(this.List[i])
						this.List.splice(i, 1)
						uni.$emit("flushGoodsPage", 1)
						break
					}
				}
			})

			uni.$on("deleteGoods1", (id) => {
				let f = 0
				for (let i = 0; i < this.List.length; i++) {
					if (this.List[i].id == id) {
						this.List.splice(i, 1)
						uni.$emit("flushGoodsPage", 1)
						f = 1
						break
					}
				}
				if (f == 1) {
					return
				}
				for (let i = 0; i < this.noList.length; i++) {
					if (this.noList[i].id == id) {
						this.noList.splice(i, 1)
						uni.$emit("flushGoodsPage", 1)
						break
					}
				}
			})
		},
		destroyed() {
			uni.$off("cancelGoods1")
			uni.$off("deleteGoods1")
		},
		methods: {
			change(index) {
				if (!this.selectdedType) {
					this.item = ['下架', '删除']
				} else {
					this.item = ['删除']
				}
				this.$refs.pullUp.OpenPop(index)
			},
			typeSelect(index) {
				if (this.selectdedType == index) {
					return
				}
				this.selectdedType = index
			},
			back() {
				uni.navigateBack({})
			},
			toDetailInfo(index) {
				var textObj = JSON.stringify(this.selectdedType ? this.noList[index] : this.List[index])
				uni.navigateTo({
					url: '../detailInfo/detailInfo?info=' + textObj,
				})
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
							id: !this.selectdedType ? this.List[data.object].id : this.noList[data.object].id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								if (res.data.data == "success") {
									this.noList.unshift(this.List[data.object])
									this.List.splice(data.object, 1)
									uni.$emit("flushGoodsPage", 1)
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
							id: !this.selectdedType ? this.List[data.object].id : this.noList[data.object].id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								if (!this.selectdedType) {
									this.List.splice(data.object, 1)
								} else {
									this.noList.splice(data.object, 1)
								}
								uni.$emit("flushGoodsPage", 1)
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				}

			},

		}
	}
</script>

<style scoped>
	.content {
		display: flex;
		flex-direction: column;

		justify-content: center;
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

	.typeselect {
		display: flex;
		width: 100%;
		margin-top: 180rpx;
	}

	.typebar.active {
		border-width: 0 0 10rpx 0;
		border-style: solid;
		border-color: #48D1E0;
		font-weight: 700;
	}

	.typebar {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 50%;
		height: 96rpx;
		font-size: 32rpx;
	}

	.LabelBox {
		width: 100%;
		display: flex;
		flex-direction: column;
		margin-bottom: 100rpx;
	}

	.Label {
		width: 100%;
		background-color: #ffffff;
		display: flex;
		position: relative;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.fontBox {
		font-size: 24rpx;
		line-height: 34rpx;
		position: absolute;
		right: 40rpx;
		bottom: 0rpx;
		padding: 10rpx 20rpx 10rpx 20rpx;
		color: rgba(0, 0, 0, 0.6);
		border: 1rpx solid rgba(0, 0, 0, 0.12);
		box-sizing: border-box;
		border-radius: 200rpx;
	}

	.fontBox.active {
		background: rgba(0, 0, 0, 0.12);
		border: none;
		color: white;
	}
</style>
