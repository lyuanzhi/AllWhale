<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">我的收藏</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="typeselect">
			<view v-for="(item, index) in typeList" :key="index" class="typebar"
				:class="{'active':selectdedType==index}" @click="typeSelect(index)">
				{{item}}
			</view>
		</view>
		<view class="LabelBox">
			<view v-if="selectdedType==0?goodList.length==0:selectdedType==1?houseList.length==0:activityList.length==0"
				style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无收藏</view>
			</view>
			<block v-for="(item, index) in selectdedType==0?goodList:selectdedType==1?houseList:activityList"
				:key="index">
				<view class="Label" @click="toDetailInfo(index)" v-if="selectdedType==0||selectdedType==1">
					<view class="fontBox" @click.stop="cancelCollection(index)">取消收藏</view>
					<view class="soldout" v-if="item.isCancelled">
					</view>
					<image :src="item.imgNumber!=0?'https://lyuanzhi.com:8080/img_'+item.id+'_0.jpg':'../../static/none2.png'"
						style="width: 224rpx; height: 224rpx; margin-left: 20rpx; margin-top: 40rpx;border-radius: 6rpx;"
						mode="aspectFill">
					</image>

					<view style="display: flex; flex-direction: column; margin-left: 30rpx;">
						<view style="margin-top: 40rpx; font-size: 28rpx; letter-spacing: 0.140769rpx; ">
							{{item.name.length>=14?item.name.substring(0,14)+"...":item.name}}
						</view>
						<view
							style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: #E06653; margin-top: 20rpx; display: flex; align-items: flex-end; font-weight: bold;">
							<view style="font-size: 30rpx">£{{item.price}}</view>
						</view>
						<view class="label-row">
							<view class='smalllabel' v-if="(item.howNewType!=null)&&(item.howNewType!='null')">
								<view style="margin-left: 6rpx; margin-right: 6rpx;">
									{{item.howNewType}}
								</view>
							</view>
							<view class='smalllabel' v-if="(item.pickUpType!=null)&&(item.pickUpType!='null')">
								<view style="margin-left: 6rpx; margin-right: 6rpx;">
									{{item.pickUpType}}
								</view>
							</view>
							<view class='smalllabel' v-if="(item.locationType!=null)&&(item.locationType!='null')">
								<view style="margin-left: 6rpx; margin-right: 6rpx;">
									{{item.locationType}}
								</view>
							</view>
							<view class='smalllabel' v-if="(item.houseType!=null)&&(item.houseType!='null')">
								<view style="margin-left: 6rpx; margin-right: 6rpx;">
									{{item.houseType}}
								</view>
							</view>
							<view class='smalllabel' v-if="(item.tenancyType!=null)&&(item.tenancyType!='null')">
								<view style="margin-left: 6rpx; margin-right: 6rpx;">
									{{item.tenancyType == '>3月'?'长租':'短租'}}
								</view>
							</view>
						</view>
						<view style="display: flex; align-items: center; margin-top: 30rpx;">
							<image :src="item.avatarUrl" style="width: 42rpx; height: 42rpx; border-radius: 50%; ">
							</image>
							<view
								style="font-size: 24rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.4); line-height: 34rpx; margin-left: 15rpx;">
								{{item.username}}
							</view>
						</view>
					</view>
				</view>
				<view style="width: 686rpx; left: 32rpx; height: 167rpx; position: relative; margin-top: 35rpx;"
					v-if="selectdedType==2" @click="selectAc(index)">
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
					<image :src="item.avatarUrl" class="avatar"></image>
					<view
						style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6); position: absolute; left: 23.13%;top:50%">
						{{item.activityTime}}
					</view>
					<view class="soldout" v-if="item.isCancelled" style="right: 10rpx !important;top: 0rpx !important;">
					</view>
					<view class="change" @click.stop="cancelCollection(index)">
						取消收藏
					</view>
				</view>
			</block>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectdedType: 0,
				titleBarTop: 0,
				typeList: ["闲置", "租房", '活动'],
				goodList: [],
				houseList: [],
				activityList: [],
				shit: ''
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
				url: this.$serverUrl + '/goods/getMyCollection',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						for (let i = 0; i < res.data.data.length; i++) {
							if (res.data.data[i].secondHandType == "闲置") {
								this.goodList.push(res.data.data[i])
							} else {
								this.houseList.push(res.data.data[i])
							}
						}
					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})
			uni.request({
				url: this.$serverUrl + '/activity/getMyCollection',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.activityList = res.data.data
					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})
		},
		methods: {
			selectAc(index) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(this.activityList[index])
				uni.navigateTo({
					url: '../../pages/detialActivity/detialActivity?info=' + textObj,
				})
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
				var textObj = JSON.stringify(this.selectdedType ? this.houseList[index] : this.goodList[index])
				uni.navigateTo({
					url: '../detailInfo/detailInfo?info=' + textObj,
				})
			},
			cancelCollection(index, num) {
				let that = this
				uni.showModal({
					title: '提示',
					content: '确认取消收藏？',
					success: function(res) {
						if (res.confirm) {
							that.$refs.loading.showLoading();

							if (that.selectdedType == 1 || that.selectdedType == 0) {
								uni.request({
									url: that.$serverUrl + '/goods/changeCollection',
									method: "POST",
									data: {
										goodsId: that.selectdedType == 0 ? that.goodList[index].id :
											that
											.houseList[index].id,
										userId: uni.getStorageSync('userInfo').id,
										type: 1
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200 && res.data.data == "success") {
											if (that.selectdedType == 1) {
												that.houseList.splice(index, 1)
											} else if (that.selectdedType == 0) {
												that.goodList.splice(index, 1)
											}
										}
									},
									fail: (res) => {

									},
									complete: (res) => {
										that.$refs.loading.hideLoading();
									}
								})
							} else if (that.selectdedType == 2) {
								uni.request({
									url: that.$serverUrl + '/activity/changeCollection',
									method: "POST",
									data: {
										activityId: that.activityList[index].id,
										userId: uni.getStorageSync('userInfo').id,
										type: 1
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200 && res.data.data == "success") {
											that.activityList.splice(index, 1)
										}
									},
									fail: (res) => {

									},
									complete: (res) => {
										that.$refs.loading.hideLoading();
									}
								})
							}

						}
					}
				});
			}
		}
	}
</script>

<style scoped>
	.avatar {
		width: 42rpx;
		position: absolute;
		height: 42rpx;
		border-radius: 50%;
		left: 15.53%;
		top: 48%;


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

	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.change {
		position: absolute;
		right: 0%;
		bottom: 5%;
		width: 153rpx;
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

	.label-row {
		display: flex;
		line-height: 14px;
		margin-top: 20rpx;
		flex-wrap: wrap;
	}

	.smalllabel {
		height: 35rpx;
		margin-right: 10rpx;
		border: 1rpx solid #D9D9D9;
		box-sizing: border-box;
		border-radius: 2rpx;
		font-size: 20rpx;
		line-height: 28rpx;
		letter-spacing: 0.1403846rpx;
		color: #000000;
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

	.soldout {
		right: 30rpx;
		top: 40rpx;
		width: 115rpx;
		height: 115rpx;
		position: absolute;
		background: url(../../static/soldout.png) center/100% 100% no-repeat;
	}
</style>
