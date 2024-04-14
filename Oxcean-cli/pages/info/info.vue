<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">消息</view>
		</view>
		<view class="typeselect">
			<view v-for="(item, index) in typeList" :key="index" class="typebar"
				:class="{'active':selectdedType==index}" @click="typeSelect(index)">
				{{item}}
				<view v-show="index==1 && redNum!=0 && selectdedType!=index"
					style="text-align: center;line-height: 28rpx;position: absolute;right:31%;top:11%; width: 36rpx;height:28rpx;background: #E06653;border-radius: 12rpx;font-weight: bold;font-size: 18rpx;color: #FFFFFF;">
					{{redNum}}
				</view>
				<view v-show="index==0 && redNum2!=0 && selectdedType!=index"
					style="text-align: center;line-height: 28rpx;position: absolute;right:31%;top:11%; width: 36rpx;height:28rpx;background: #E06653;border-radius: 12rpx;font-weight: bold;font-size: 18rpx;color: #FFFFFF;">
					{{redNum2}}
				</view>
			</view>
		</view>
		<!-- 列表组件 -->
		<view class="messageBar" v-if="selectdedType==1">
			<view style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;" v-if="accpetList2.length==0">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无评论</view>
			</view>
			<block v-for="(item, index) in accpetList2" :key="index">
				<view class="Box" @click="toTopic(item.getCommunityListVo)">
					<view
						style="margin-left: 5%; width: 15%; height: 100%; display: flex; justify-content: center;padding-top: 20rpx; position: relative;">
						<view v-if="item.isChecked==0"
							style="position: absolute; width: 16rpx; height: 16rpx;  border: 2px solid #ffffff;background-color: #E06653; border-radius: 50%; top: 20rpx; left: 70rpx;">
						</view>
						<image style="width: 84rpx; height: 84rpx; border-radius: 50%;"
							:src="item.avatarUrl==null?'../../static/none3.png':item.avatarUrl"></image>
					</view>
					<view
						style="width: 60%; display: flex; flex-direction: column; justify-content: space-evenly; margin-left: 4%;">
						<view style="font-size: 30rpx;">{{item.username==null?'某同学':item.username}}</view>
						<view style="font-size: 24rpx; color: rgba(0, 0, 0, 0.4);">
							{{item.content.length>=12?item.content.substring(0,12)+"...":item.content}}
						</view>
						<view style="font-size: 23rpx; color: rgba(0, 0, 0, 0.4);">{{checkDateTime(item.createTime)}}
						</view>
					</view>
					<view
						style="width: 224rpx; margin-right: 5%; display: flex; justify-content: center; align-items: center;">
						<view
							style="padding-left: 10rpx;padding-right: 10rpx;width: 224rpx; height: 112rpx;border-radius:12rpx;font-size: 20rpx;display: flex;justify-content: center;align-items: center;background: rgba(0, 0, 0, 0.02);overflow: hidden;word-wrap: break-word;word-break: break-all;">
							{{item.getCommunityListVo.content.length>=25?item.getCommunityListVo.content.substring(0,25)+"...":item.getCommunityListVo.content}}
						</view>
					</view>
				</view>
			</block>
		</view>
		<view class="messageBar" v-if="selectdedType==0">
			<view style="width: 100%;margin-top: 400rpx;margin-bottom: 300rpx;" v-if="accpetList.length==0">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无私信</view>
			</view>
			<block v-for="(item, index) in accpetList" :key="index">
				<view class="Box" @click="toChat(item)">
					<view
						style="margin-left: 5%; width: 15%; height: 100%; display: flex; justify-content: center;padding-top: 20rpx; position: relative;">
						<view v-if="item.isChecked==0&&item.isYours==0"
							style="position: absolute; width: 16rpx; height: 16rpx;  border: 2px solid #ffffff;background-color: #E06653; border-radius: 50%; top: 18rpx; left: 72rpx;">
						</view>
						<image style="width: 84rpx; height: 84rpx; border-radius: 50%;" :src="item.avatarUrl"></image>
					</view>
					<view
						style="width: 60%; display: flex; flex-direction: column; justify-content: space-evenly; margin-left: 2%;">
						<view style="font-size: 30rpx;">{{item.username}}</view>
						<view style="font-size: 24rpx; color: rgba(0, 0, 0, 0.4);">
							{{item.message.length>=16?item.message.substring(0,16)+"...":item.message}}
						</view>
						<view style="font-size: 23rpx; color: rgba(0, 0, 0, 0.4);">{{checkDateTime(item.createTime)}}
						</view>
					</view>
					<view
						style="width: 15%; margin-right: 5%; display: flex; justify-content: center; align-items: center;">
						<image v-if="item.goodsId!=null" style="width: 112rpx; height: 112rpx;border-radius:12rpx;"
							:src="item.imgNumber==0?'../../static/none2.png':'https://lyuanzhi.com:8080/img_' + item.goodsId + '_0.jpg'"
							mode="aspectFill"></image>
						<view v-if="item.activityId!=null"
							style="width: 112rpx; height: 112rpx;border-radius:12rpx;font-size: 20rpx;display: flex;justify-content: center;align-items: center;background: rgba(0, 0, 0, 0.02);overflow: hidden;word-wrap: break-word;word-break: break-all;">
							{{item.title.length>=12?item.title.substring(0,12)+"...":item.title}}
						</view>
					</view>
				</view>
			</block>
		</view>
		<custom-tab-bar currentPage="info"></custom-tab-bar>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectdedType: 0,
				typeList: ["私信", "评论"],
				titleBarTop: 0,
				accpetList: [],
				accpetList2: [],
				accpetList2_flag: 0
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		computed: {
			redNum() {
				let num = 0
				for (let i = 0; i < this.accpetList2.length; i++) {
					if (this.accpetList2[i].isChecked == 0) {
						num++;
					}
				}
				return num > 99 ? 99 : num;
			},
			redNum2() {
				let num = 0
				for (let i = 0; i < this.accpetList.length; i++) {
					if (this.accpetList[i].isChecked == 0 && this.accpetList[i].isYours == 0) {
						num++;
					}
				}
				return num > 99 ? 99 : num;
			}
		},
		onLoad() {
			this.$refs.loading.showLoading();
			uni.request({
				url: this.$serverUrl + '/chat/getChatList',
				method: "POST",
				data: {
					userId: uni.getStorageSync('userInfo').id
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.accpetList = res.data.data
					}
					this.$refs.loading.hideLoading();
				},
				fail: (res) => {
					this.$refs.loading.hideLoading();
				}
			})
			if (this.accpetList2_flag == 0) {
				this.accpetList2_flag = 1
				uni.request({
					url: this.$serverUrl + '/chat/getAccpetList2',
					method: "POST",
					data: {
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.accpetList2 = res.data.data
							this.accpetList2.sort(function(a, b) {
								return b.createTime - a.createTime;
							})
						}
					}
				});
			}
			uni.$on("receiveMsgInfo", (res) => {
				let parseRes = JSON.parse(res)

				if (parseRes.task == 0) {
					if (parseRes.fromName == uni.getStorageSync("userInfo").id) {
						parseRes.userId = parseRes.toName
					} else {
						parseRes.userId = parseRes.fromName
					}
					for (let i = 0; i < this.accpetList.length; i++) {
						if (this.accpetList[i].userId == parseRes.userId) {
							this.accpetList.splice(i, 1);
						}
					}
					this.accpetList.unshift(parseRes)
				} else if (parseRes.task == 1) {
					if (this.accpetList2_flag == 0) {
						this.accpetList2_flag = 1
						uni.request({
							url: this.$serverUrl + '/chat/getAccpetList2',
							method: "POST",
							data: {
								userId: uni.getStorageSync('userInfo').id
							},
							header: {
								'content-type': 'application/x-www-form-urlencoded'
							},
							success: (res) => {
								if (res.data.code == 200) {
									this.accpetList2 = res.data.data
									this.accpetList2.sort(function(a, b) {
										return b.createTime - a.createTime;
									})
									for (let i = 0; i < this.accpetList2.length; i++) {
										if (this.accpetList2[i].getCommunityListVo.id == parseRes
											.getCommunityListVo.id) {
											this.accpetList2.splice(i, 1);
										}
									}
									this.accpetList2.unshift(parseRes)
								}
							}
						});
					} else {
						for (let i = 0; i < this.accpetList2.length; i++) {
							if (this.accpetList2[i].getCommunityListVo.id == parseRes.getCommunityListVo.id) {
								this.accpetList2.splice(i, 1);
							}
						}
						this.accpetList2.unshift(parseRes)
					}
				}
			});
			uni.$on("dontShowRed", (res) => {
				let userId = ""
				if (res.fromName == uni.getStorageSync("userInfo").id) {
					userId = res.toName
				} else {
					userId = res.fromName
				}
				for (let i = 0; i < this.accpetList.length; i++) {
					if (this.accpetList[i].userId == userId) {
						let temp = this.accpetList[i]
						this.$set(temp, "isChecked", 1)
						this.$set(this.accpetList, i, temp)
					}
				}
				this.$store.state.showRed = (this.redNum + this.redNum2) > 0 ? 1 : 0
			})
			uni.$on("reGetInfo", (res) => {
				uni.request({
					url: this.$serverUrl + '/chat/getChatList',
					method: "POST",
					data: {
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.accpetList = res.data.data
						}
					},
					fail: (res) => {

					}
				})
			})
			uni.$on('changeHeartById', (res) => {
				for (let i = 0; i < this.accpetList2.length; i++) {
					if (this.accpetList2[i].getCommunityListVo.id == res[0]) {
						this.accpetList2[i].getCommunityListVo.hasHeart = res[1]
						this.accpetList2[i].getCommunityListVo.heartNum = res[2]
					}
				}
			})
			uni.$on('updateCommentNumById', (res) => {
				for (let i = 0; i < this.accpetList2.length; i++) {
					if (this.accpetList2[i].getCommunityListVo.id == res[0]) {
						this.accpetList2[i].getCommunityListVo.commentNum = res[1]
					}
				}
			})
			uni.$on('dontShowRedNum', (id) => {
				for (let i = 0; i < this.accpetList2.length; i++) {
					if (this.accpetList2[i].getCommunityListVo.id == id) {
						let temp = this.accpetList2[i]
						this.$set(temp, "isChecked", 1)
						this.$set(this.accpetList2, i, temp)
					}
				}
				this.$store.state.showRed = (this.redNum + this.redNum2) > 0 ? 1 : 0
			})
		},
		methods: {
			typeSelect(index) {
				if (this.selectdedType == index) {
					return
				}
				this.selectdedType = index
				if (this.accpetList2_flag == 1 || this.selectdedType == 0) {
					return
				}
				this.accpetList2_flag = 1
				this.$refs.loading.showLoading()
				uni.request({
					url: this.$serverUrl + '/chat/getAccpetList2',
					method: "POST",
					data: {
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.accpetList2 = res.data.data
							this.accpetList2.sort(function(a, b) {
								return b.createTime - a.createTime;
							})
						}
						this.$refs.loading.hideLoading();
					},
					fail: (res) => {
						this.$refs.loading.hideLoading();
					}
				});
			},
			toChat(item) {
				let obj = {
					goodsId: item.goodsId,
					activityId: item.activityId,
					toName: item.userId,
					toAvatarUrl: item.avatarUrl,
					toUsername: item.username,
					imgNumber: item.imgNumber
				}
				let textObj = JSON.stringify(obj)
				uni.navigateTo({
					url: '../chat/chat?ids=' + textObj
				})
			},
			checkDateTime(createTime) {
				let dateCur = new Date();
				let date = new Date(createTime);
				if (dateCur.getTime() - createTime < 24 * 3600 * 1000) {
					if (dateCur.getTime() - createTime < 3600 * 1000) {
						return Math.floor(1.1 + (dateCur.getTime() - createTime) / (1000 * 60)) + "分钟前";
					} else {
						let hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
						let mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
						if (dateCur.getDate() == date.getDate()) {
							return hh + mm;
						} else {
							return '昨天 ' + hh + mm;
						}
					}
				} else {
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					return MM + DD;
				}
			},
			toTopic(getCommunityListVo) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(getCommunityListVo)
				uni.navigateTo({
					url: '../../pages/detailTopic/detailTopic?info=' + textObj + '&index=-1',
				})
			}
		}
	}
</script>

<style scoped>
	.typebar {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 50%;
		height: 90rpx;
		font-size: 32rpx;
		position: relative;
	}

	.typebar.active {
		border-width: 0 0 10rpx 0;
		border-style: solid;
		border-color: #48D1E0;
		font-weight: 700;
	}

	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.typeselect {
		display: flex;
		width: 100%;
		margin-top: 180rpx;
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

	.messageBar {
		display: flex;
		flex-direction: column;
		align-items: center;
		background-color: #ffffff;
		width: 100%;
		padding-top: 30rpx;
		padding-bottom: 180rpx;
	}

	.Box {
		width: 100%;
		height: 150rpx;
		display: flex;
		background-color: #ffffff;
		margin-bottom: 30rpx;
	}
</style>
