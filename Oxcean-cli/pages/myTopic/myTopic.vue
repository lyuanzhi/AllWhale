<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">我的话题帖</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>

		<view class="LabelBox">
			<view v-if="dataList.length==0" style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无在展</view>
			</view>
			<view v-for="(item,index) in dataList" class="label" :key="index" @click="go(index)">
				<view class="pull" @click.stop="change(index)"></view>
				<view style="display: flex; margin-top: 32rpx; margin-left: 32rpx; align-items: center;">
					<image :src="item.avatarUrl==null?'../../static/none3.png':item.avatarUrl"
						style="height: 56rpx; width: 56rpx; border-radius: 50%; align-items: center;">
					</image>
					<view style="display: flex; margin-left: 17rpx; flex-direction: column;">
						<view style="font-size: 24rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.6);">
							{{item.username==null?"某同学":item.username}}
						</view>
						<view
							style="font-size: 18rpx;line-height: 21rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6); margin-top: 10rpx;">
							{{checkDateTime(item.createTime)}}
						</view>
					</view>

				</view>
				<view class="Box"
					style="margin-left: 32rpx; margin-right: 32rpx; margin-top: 28rpx;margin-bottom: 16rpx; font-size: 28rpx; line-height: 48rpx;letter-spacing: 0.140769rpx;word-wrap: break-word;word-break: break-all;">
					{{item.content}}
				</view>
				<view style="display: flex;">
					<view v-for="(item2, index2) in item.itsTags.split(';').slice(1, -1)" :key="index2" class="tag"
						:style="{color: 'rgb(' + $tagsColorMap.get(item2) + ')'}">
						#{{item2}}</view>
				</view>
				<view style="display: flex;margin-top: 10rpx;padding-left: 24rpx;padding-right: 24rpx;">
					<image v-if="item.imgNumber>=1" @click.stop="clkImg(0, item.id, item.imgNumber)"
						:src="'https://lyuanzhi.com:8080/img_' + item.id + '_' + '0' + '.jpg'" mode="aspectFill"
						style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
					</image>
					<image v-if="item.imgNumber>=2" @click.stop="clkImg(1, item.id, item.imgNumber)"
						:src="'https://lyuanzhi.com:8080/img_' + item.id + '_' + '1' + '.jpg'" mode="aspectFill"
						style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
					</image>
					<image v-if="item.imgNumber>=3" @click.stop="clkImg(2, item.id, item.imgNumber)"
						:src="'https://lyuanzhi.com:8080/img_' + item.id + '_' + '2' + '.jpg'" mode="aspectFill"
						style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
					</image>
				</view>
				<view
					style="display: flex; width: 100%; justify-content: space-around; margin-top: 25rpx; margin-bottom: 25rpx; align-items: center;">
					<button class="share" open-type="share" @click.stop="" :id="index"></button>
					<view style="display: flex; align-items: center">
						<view class="commet"></view>
						<view class="font" style="margin-left: 8rpx;">{{item.commentNum}}
						</view>
					</view>
					<view style="display: flex; align-items: center;" @click.stop="love(index)">
						<view class="love" :class="{'active':item.hasHeart}"></view>
						<view class="font">{{item.heartNum}}
						</view>
					</view>
				</view>
			</view>
			<view style="height: 100rpx;"></view>
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
				lock: false,
				lock2: false,
				titleBarTop: 0,
				typeList: ["在展", "已下架"],
				dataList: [],
				item: ['删除']
			}
		},
		components: {
			pullUp
		},
		onShareAppMessage(res) {
			let textObj = JSON.stringify(this.dataList[Number(res.target.id)])
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个话题",
				path: "pages/detailTopic/detailTopic?info=" + textObj + '&index=0',
				imageUrl: '../../static/none2.png'
			}
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
				url: this.$serverUrl + '/community/getMyTopic',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.dataList = res.data.data

					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})
			uni.$on('changeHeartById1', (res) => {
				for (let i = 0; i < this.dataList.length; i++) {
					if (this.dataList[i].id == res[0]) {
						this.dataList[i].hasHeart = res[1]
						this.dataList[i].heartNum = res[2]
					}
				}
			})
			uni.$on('updateCommentNumById1', (res) => {
				for (let i = 0; i < this.dataList.length; i++) {
					if (this.dataList[i].id == res[0]) {
						this.dataList[i].commentNum = res[1]
					}
				}
			})
			uni.$on("deleteCommunity1", (id) => {
				for (let i = 0; i < this.dataList.length; i++) {
					if (this.dataList[i].id == id) {
						this.dataList.splice(i, 1)
						break
					}
				}
			})
		},
		destroyed() {
			uni.$off("changeHeartById1")
			uni.$off("updateCommentNumById1")
			uni.$off("deleteCommunity1")
		},
		methods: {
			clkImg(index, id, imgNumber) {
				if (!this.isLogin()) {
					return;
				}
				let arr = [];
				for (let i = 0; i < imgNumber; i++) {
					arr.push('https://lyuanzhi.com:8080/img_' + id + '_' + i + '.jpg');
				}
				uni.previewImage({
					urls: arr,
					current: index
				})
			},
			go(index) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(this.dataList[index])
				uni.navigateTo({
					url: '../../pages/detailTopic/detailTopic?info=' + textObj + '&index=' + index,
				})
			},
			love(index) {
				if (!this.isLogin()) {
					return;
				}
				if (this.lock2) {
					return
				}
				this.lock2 = true
				this.func = setTimeout(() => {
					this.lock2 = false
				}, 4000);
				this.dataList[index].hasHeart = !this.dataList[index].hasHeart
				if (this.dataList[index].hasHeart == 1) {
					this.dataList[index].heartNum++
				} else {
					this.dataList[index].heartNum--
				}
				uni.request({
					url: this.$serverUrl + '/community/changeCommunityHeart',
					method: "POST",
					data: {
						communityId: this.dataList[index].id,
						type: this.dataList[index].hasHeart ? 0 : 1,
						userId: uni.getStorageSync('userInfo').id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.lock2 = false
							clearTimeout(this.func)
							uni.$emit("changeHeartById", [this.dataList[index].id, this.dataList[index]
								.hasHeart, this.dataList[index].heartNum
							]);
						}
					},
					fail: (res) => {

					}
				})
			},
			change(index) {
				this.$refs.pullUp.OpenPop(index)
			},
			// 该方程接受子组件信息
			select(data) {
				if (data == '' || data == null) {
					return
				}
				this.$refs.loading.showLoading();
				if (data.type == '删除') {
					uni.request({
						url: this.$serverUrl + '/community/deleteCommunity/' + uni.getStorageSync('userInfo').id,
						method: "POST",
						data: {
							id: this.dataList[data.object].id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								this.dataList.splice(data.object, 1)
								uni.$emit("addTopic")
							}
							this.$refs.loading.hideLoading();
						},
						fail: (res) => {
							this.$refs.loading.hideLoading();
						}
					})
				}
			},
			back() {
				uni.navigateBack({})
			},
			checkDateTime(createTime) {
				if (createTime == "刚刚") {
					return '刚刚'
				}
				let dateCur = new Date();
				let date = new Date(createTime);
				if (dateCur.getTime() - createTime < 24 * 3600 * 1000) {
					if (dateCur.getTime() - createTime < 3600 * 1000) {
						if (dateCur.getTime() - createTime < 60 * 1000) {
							return "刚刚"
						}
						return Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60)) + "分钟前";
					} else {
						let hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
						let mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
						return Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60 * 60)) + "小时前"
					}
				} else if (dateCur.getTime() - createTime < 30 * 24 * 3600 * 1000 * 12) {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					return MM + DD;
				} else {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					let YY = date.getFullYear() + '-'
					return YY + MM + DD
				}
			}
		}
	}
</script>

<style scoped>
	button::after {
		border: none;
	}

	.commet {
		height: 32rpx;
		width: 32rpx;
		background: url(../../static/commet.png) center/100% 100% no-repeat;
	}

	.love {
		height: 40rpx;
		width: 40rpx;
		background: url(../../static/likeit.png) center/100% 100% no-repeat;
		margin-bottom: 8rpx;
	}

	.love.active {
		background: url(../../static/likeitred.png) center/100% 100% no-repeat;
	}

	.share {
		height: 32rpx;
		width: 32rpx;
		background: url(../../static/Share.png) center/60% 100% no-repeat;
		margin: 0;
	}

	.pull {
		height: 36rpx;
		width: 36rpx;
		background: url(../../static/labelicon.png) center/80% 80% no-repeat;
		right: 5%;
		top: 32rpx;
		position: absolute;
	}

	.font {
		font-size: 24rpx;
		line-height: 34rpx;
		letter-spacing: 0.140769rpx;
		color: rgba(0, 0, 0, 0.4);

	}

	.label {
		width: 100%;
		background-color: #ffffff;
		margin-bottom: 16rpx;
		display: flex;
		flex-direction: column;
		position: relative;
	}

	.Box {
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 5;
		/* 可以显示的行数，超出部分用...表示*/
		-webkit-box-orient: vertical;
	}

	.content {
		display: flex;
		flex-direction: column;
		background-color: #F5F7F7;

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
		margin-top: 190rpx;
		flex: 0;
		background-color: #F5F7F7;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
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

	.tag {
		font-size: 28rpx;
		letter-spacing: 0.140769rpx;
		line-height: 33rpx;
		margin-left: 32rpx;
		margin-bottom: 20rpx;
		margin-top: 10rpx;
	}
</style>
