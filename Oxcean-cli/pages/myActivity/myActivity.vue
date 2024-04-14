<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">我的活动帖</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="typeselect">
			<view v-for="(item, index) in typeList" :key="index" class="typebar"
				:class="{'active':selectdedType==index}" @click="typeSelect(index)">
				{{item}}
			</view>
		</view>
		<view class="LabelBox">
			<view v-if="selectdedType?soldout.length==0:onshow.length==0"
				style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无在展</view>
			</view>
			<view v-for="(item,index) in !selectdedType?onshow:soldout" :key="index " @click="selectAc(index)"
				style="width: 686rpx; left: 32rpx; height: 167rpx; position: relative; margin-top: 35rpx;">
				<view class="outer"
					:class="{'game':item.activityType=='娱乐局','sport':item.activityType=='运动局','study':item.activityType=='学习局','Spelllist':item.activityType=='拼团','else':item.activityType=='其他'}">
					<view
						style="writing-mode:vertical-rl;font-weight: bold;font-size: 32rpx;line-height: 46rpx;letter-spacing: 0.280769rpx;color: #FFFFFF;">
						{{item.activityType}}
					</view>
				</view>
				<view
					style="letter-spacing: 0.140769rpx; font-size: 32rpx; line-height: 45rpx; position: absolute; left: 15.53%; top:10%">
					{{item.title.length>=14?item.title.substring(0,12)+"...":item.title}}
				</view>
				<image v-if="item.currentS<currentD" src="../../static/outOfTime.png"
					style="width:60rpx;height:60rpx;position: absolute;top:0;left:0;"></image>
				<view
					style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6); position: absolute; left: 15.53%;top:50%">
					活动时间：{{item.activityTime}}
				</view>
				<view class="change" @click.stop="change(index)">
					更改
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
				typeList: ["在展", "已下架"],
				onshow: [],
				soldout: [],
				item: ['下架', '删除', ],
				labels: ['全部', '娱乐局', '运动局', '学习局', '拼团', '其他'],
				currentD: Date.now()
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
				url: this.$serverUrl + '/activity/getMyActivity',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						for (let i = 0; i <= res.data.data.length - 1; i++) {
							res.data.data[i].currentS = new Date(res.data.data[i].activityTime.replace(/-/g, "/")).getTime()
							if (res.data.data[i].isCancelled == 0) {
								this.onshow.push(res.data.data[i])
							} else {
								this.soldout.push(res.data.data[i])
							}
						}

					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})

			uni.$on("cancelActivity1", (id) => {
				for (let i = 0; i < this.onshow.length; i++) {
					if (this.onshow[i].id == id) {
						uni.$emit("addActivity", this.labels.indexOf(this.onshow[i].activityType))
						this.soldout.unshift(this.onshow[i])
						this.onshow.splice(i, 1)
						break
					}
				}
			})

			uni.$on("deleteActivity1", (id) => {
				let f = 0
				for (let i = 0; i < this.onshow.length; i++) {
					if (this.onshow[i].id == id) {
						uni.$emit("addActivity", this.labels.indexOf(this.onshow[i].activityType))
						this.onshow.splice(i, 1)
						f = 1
						break
					}
				}
				if (f == 1) {
					return
				}
				for (let i = 0; i < this.soldout.length; i++) {
					if (this.soldout[i].id == id) {
						uni.$emit("addActivity", this.labels.indexOf(this.soldout[i].activityType))
						this.soldout.splice(i, 1)
						break
					}
				}
			})

		},
		destroyed() {
			uni.$off("cancelActivity1")
			uni.$off("deleteActivity1")
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
			selectAc(index) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(!this.selectdedType ? this.onshow[index] : this.soldout[index])
				uni.navigateTo({
					url: '../../pages/detialActivity/detialActivity?info=' + textObj,
				})
			},
			// 该方程接受子组件信息
			select(data) {
				if (data == '' || data == null) {
					return
				}
				this.$refs.loading.showLoading();
				if (data.type == '下架') {
					uni.request({
						url: this.$serverUrl + '/activity/cancelActivity/' + uni.getStorageSync('userInfo').id,
						method: "POST",
						data: {
							id: !this.selectdedType ? this.onshow[data.object].id : this.soldout[data.object].id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								uni.$emit("addActivity", this.labels.indexOf(this.onshow[data.object]
									.activityType))
								this.soldout.unshift(this.onshow[data.object])
								this.onshow.splice(data.object, 1)
							}
							this.$refs.loading.hideLoading();
						},
						fail: (res) => {
							this.$refs.loading.hideLoading();
						}
					})
				} else if (data.type == '删除') {
					uni.request({
						url: this.$serverUrl + '/activity/deleteActivity/' + uni.getStorageSync('userInfo').id,
						method: "POST",
						data: {
							id: !this.selectdedType ? this.onshow[data.object].id : this.soldout[data.object].id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								if (!this.selectdedType) {
									uni.$emit("addActivity", this.labels.indexOf(this.onshow[data.object]
										.activityType))
									this.onshow.splice(data.object, 1)
								} else {
									uni.$emit("addActivity", this.labels.indexOf(this.soldout[data.object]
										.activityType))
									this.soldout.splice(data.object, 1)
								}
							}
							this.$refs.loading.hideLoading();
						},
						fail: (res) => {
							this.$refs.loading.hideLoading();
						}
					})
				}

			},
			typeSelect(index) {
				if (this.selectdedType == index) {
					return
				}
				this.selectdedType = index
			},
			back() {
				uni.navigateBack({})
			}
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

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.outer {
		border-radius: 12rpx 0rpx 0rpx 12rpx;
		position: absolute;
		left: 0rpx;
		height: 100%;
		width: 82rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.outer.eat {
		background: #F3D4B5;
	}

	.outer.game {
		background: #CBC9E8;
	}

	.outer.sport {
		background: #AFE8D7;
	}

	.outer.study {
		background: #B2E0EE;
	}

	.outer.friend {
		background: #C2EABC;
	}

	.outer.Spelllist {
		background: #ECC7E4;

	}

	.outer.Spellcar {
		background: #ECC3C3;
	}

	.outer.else {
		background: #C3D3EC;
	}

	.change {
		position: absolute;
		right: 0%;
		bottom: 5%;
		width: 105rpx;
		height: 52rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: right;
		letter-spacing: 0.140769rpx;
		color: rgba(0, 0, 0, 0.6);
		font-size: 24rpx;
		border: 1rpx solid rgba(0, 0, 0, 0.12);
		box-sizing: border-box;
		border-radius: 200rpx;
	}
</style>
