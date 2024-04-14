<template>
	<view class="content">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">
				{{data.toUsername}}
			</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view v-if="data.goodsId!=null"
			style="position: fixed; height: 11%; top: 10%; width: 100%; z-index: 4; background-color: #ffffff; box-shadow: 0rpx 3rpx 25rpx rgba(230, 230, 230, 0.4);">
			<view class="pic">
				<image @click="sendMessage" :src="picurl" style="height: 60px;width:60px;" mode="aspectFill"></image>
			</view>
			<view style="position: absolute; left: 200rpx; font-weight: 700; font-size: 28rpx; bottom: 90rpx;">{{name}}
			</view>
			<view
				style="position: absolute; left: 200rpx; font-weight: 500; font-size: 28rpx; bottom: 30rpx; color: red; display: flex; align-items: flex-end;">
				£
				<view style="font-size: 24rpx;">{{price}}</view>
			</view>
		</view>
		<view class="Label" v-if="data.activityId!=null">
			<view
				style="justify-content: center;display: flex;align-items: center;width: 81rpx;height: 54rpx;font-weight: 900;
		font-size: 24rpx;color: #499199; background: #DCF1F3; border-radius: 12rpx; margin-left: 32rpx; margin-right: 32rpx;">
				活动
			</view>
			<view style="font-size: 28rpx; font-weight: bold;">{{title}}</view>
		</view>
		<view :style="{top:data.activityId==null?'20%':'18%'}"
			style="position: fixed;top:20%;width: 100%;background-color: #ffffff;padding-bottom:10rpx;height:25rpx;line-height:25rpx;font-size: 22rpx;text-align: center;z-index: 100; display: flex; align-items: center; justify-content: center;">
			对方不在线？
			<span style="text-decoration: underline;" @click="nudge">拍一拍TA</span>
		</view>
		<scroll-view class="messageList" scroll-y="true" :scroll-into-view="scrollId" refresher-enabled
			@refresherrefresh="getMore" scroll-with-animation :refresher-triggered="triggered"
			:class="{'active':isopen}">
			<block v-for="(messageInfo,index) in messageList" :key="index">
				<view style="display: flex;justify-content: center; margin-top: 30rpx;">
					<view class="timeNotification" v-if="messageInfo.showTime">
						{{checkDateTime(messageInfo.createTime)}}
					</view>
				</view>
				<view class="messageCard" :id="'s' + index"
					:style="{flexDirection: messageInfo.fromName == fromName?'row-reverse':'row'}">
					<view class="messageBox"
						:style="{flexDirection: messageInfo.fromName == fromName?'row-reverse':'row'}">
						<image :src="messageInfo.fromName == fromName?myAvater:heAvater" class="image"
							@click="messageInfo.fromName == fromName?'':dblclick()"></image>
						<view class="message">
							<view class="textOuter"
								:style="{background: messageInfo.fromName == fromName?' #4BDBEB99':'#ffffff', borderRadius: messageInfo.fromName == fromName?'36rpx 0rpx 36rpx 36rpx;':' 0rpx 36rpx 36rpx 36rpx;'}">
								{{messageInfo.message}}
							</view>
						</view>
					</view>
				</view>
			</block>
			<view id="hiddenView" :style="{height:(hiddenViewHeight+h40)+'px'}"></view>
		</scroll-view>
		<view class="inputBox" :class="{'active':isopen}"
			:style="{bottom:focus?(hiddenViewHeight+h40)+'px !important':'0px'}">
			<view style="width: 100%; display: flex; margin-top: 30rpx;">
				<textarea class="input" type="text" cursor-spacing="25" confirm-type='send' v-model="message" fixed
					:adjust-position="false" disable-default-padding show-confirm-bar maxlength="200"
					:style="{height:textareaHeight+'px'}" @confirm='sendMessage' @focus="focusMethod" @blur="blurMethod"
					@linechange="inputMethod" />
				<view class="addIcon" @click="open()" :class="{'active':isopen}"></view>
			</view>
		</view>
		<view class="hidebox" :class="{'active':isopen}">
			<view
				style="position: absolute; display: flex; flex-direction: column; left: 70rpx; align-items: center; bottom: 130rpx;"
				@click="sendWxNumber">
				<view class="wxLogo"></view>
				<view style="font-size: 24rpx;">联系方式</view>
			</view>
		</view>
		<loading ref="loading"></loading>
		<view class="hiddenDiv">
			{{message}}
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				myAvater: '',
				heAvater: '',
				picurl: '',
				data: '',
				price: '',
				name: '',
				isopen: 0,
				fromName: "",
				toName: "",
				message: "",
				messageList: [],
				scrollId: "",
				lasttime: '',
				page: 1,
				total: 1,
				triggered: false,
				isFreshing: false,
				isSending: false,
				titleBarTop: 0,
				nudgeLock: false,
				focus: false,
				textareaHeight: 27,
				title: "",
				hiddenViewHeight: 0,
				h40: 0,
				lastTapTime: 0,
				lock: false
			};
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		// 所有的name都是id
		onLoad(option) {
			if (uni.getSystemInfoSync().system.slice(0, 3) == "iOS") {
				this.h40 = 0
			} else {
				this.h40 = 40
			}
			this.data = option.ids.replace(/""/g, "");
			this.data = JSON.parse(this.data)
			this.toName = this.data.toName
			this.fromName = uni.getStorageSync('userInfo').id
			this.myAvater = uni.getStorageSync('userInfo').avatarUrl
			this.heAvater = this.data.toAvatarUrl
			if (this.data.imgNumber != 0) {
				this.picurl = "https://lyuanzhi.com:8080/img_" + this.data.goodsId + "_0.jpg"
			} else {
				this.picurl = '../../static/none2.png'
			}
			this.$refs.loading.showLoading();
			let param = {}
			if (this.data.goodsId != null) {
				param = {
					page: this.page,
					toName: this.toName,
					fromName: this.fromName,
					goodsId: this.data.goodsId
				}
			} else {
				param = {
					page: this.page,
					toName: this.toName,
					fromName: this.fromName,
					activityId: this.data.activityId
				}
			}

			uni.request({
				url: this.$serverUrl + '/chat/getChat',
				method: 'POST',
				data: param,
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: res => {
					if (res.data.code == 200 && res.data.data.pagedResult.rows.length != 0) {
						this.total = res.data.data.pagedResult.total;
						this.messageList = res.data.data.pagedResult.rows;
						this.scrollId = 's' + (this.messageList.length - 1);
						this.messageList[0].showTime = true;
						let dateCurrent = new Date();
						for (let i = 1; i <= this.messageList.length - 1; i++) {
							let minute = (dateCurrent.getTime() - this.messageList[i].createTime) / (1000 * 60)
							let second = (minute <= 10 ? 5 : minute <= 30 ? 15 : minute <= 60 ? 30 : minute <=
								60 * 12 ? 60 * 6 : 60 * 24) * 60;
							if ((this.messageList[i].createTime - this.messageList[i - 1].createTime) / 1000 >=
								second) {
								this.messageList[i].showTime = true
							} else {
								this.messageList[i].showTime = false
							}
						}
						this.messageList[this.messageList.length - 1].showTime = true;
					}
					if (res.data.code == 200) {
						this.name = res.data.data.name
						this.price = res.data.data.price
						this.title = res.data.data.title
					}
					this.$refs.loading.hideLoading();
				},
				fail: res => {
					this.$refs.loading.hideLoading();
					this.showToast("您的网络已断开，请重试");
				}
			});
			uni.$on("receiveMsg", (res) => {
				let item = JSON.parse(res)

				if (item.task != 0) {
					return
				}

				if (item.fromName != this.toName) {
					return
				}

				uni.request({
					url: this.$serverUrl + '/chat/updateIsChecked',
					method: 'POST',
					data: {
						id: item.id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					}
				});

				this.messageList.push(item)
				this.scrollId = 's' + (this.messageList.length - 1);
				if (this.messageList.length > 1) {
					if ((this.messageList[this.messageList.length - 1].createTime - this.messageList[this
							.messageList.length - 2].createTime) / 1000 >= 180) {
						this.messageList[this.messageList.length - 1].showTime = true
					} else {
						this.messageList[this.messageList.length - 1].showTime = false
					}
				} else {
					this.messageList[0].showTime = true
				}
			});
		},
		destroyed() {
			uni.$off("receiveMsg")
			uni.$emit("dontShowRed", {
				toName: this.toName,
				fromName: this.fromName
			})
		},
		methods: {
			inputMethod() {
				let that = this
				this.$nextTick(function() {
					uni.createSelectorQuery().select('.hiddenDiv').boundingClientRect(function(data) {
						that.textareaHeight = data.height
					}).exec();
				})
			},
			focusMethod(e) {
				this.focus = true;
				this.hiddenViewHeight = e.detail.height;
				this.$nextTick(() => {
					this.scrollId = 'hiddenView';
				})
			},
			blurMethod() {
				this.focus = false;
				this.hiddenViewHeight = 0;
				this.$nextTick(() => {
					this.scrollId = 's' + (this.messageList.length - 1);
				})
			},
			sendWxNumber() {
				let wxNum = uni.getStorageSync('wxNumber')
				if (wxNum == null || wxNum == "") {
					uni.showToast({
						icon: "none",
						title: "您未填写联系方式",
						duration: 1500
					})
					return
				}
				this.message = "我的联系方式是：" + wxNum
			},
			showToast(title) {
				uni.showToast({
					icon: "none",
					title: title,
					duration: 1500
				})
			},
			getMore() {
				if (this.isFreshing) {
					return;
				}
				this.isFreshing = true;
				if (!this.triggered)
					this.triggered = true;
				setTimeout(() => {
					this.isFreshing = false;
					this.triggered = false;
				}, 3000);

				this.page++;
				if (this.page > this.total) {
					this.showToast("请客官移驾微信聊天~");
					return;
				}
				uni.request({
					url: this.$serverUrl + '/chat/getChat',
					method: 'POST',
					data: {
						page: this.page,
						toName: this.toName,
						fromName: this.fromName
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						if (res.data.code == 200) {
							let dateCurrent = new Date();
							res.data.data.pagedResult.rows[0].showTime = true;
							for (let i = 1; i < res.data.data.pagedResult.rows.length; ++i) {
								let minute = (dateCurrent.getTime() - res.data.data.pagedResult.rows[i]
									.createTime) / (
									1000 * 60)
								let second = (minute <= 10 ? 5 : minute <= 30 ? 15 : minute <= 60 ? 30 :
									minute <= 60 * 12 ? 60 * 6 : 60 * 24) * 60;
								if ((res.data.data.pagedResult.rows[i].createTime - res.data.data.pagedResult
										.rows[i - 1].createTime) /
									1000 >= second) {
									res.data.data.pagedResult.rows[i].showTime = true;
								} else {
									res.data.data.pagedResult.rows[i].showTime = false;
								}
							}
							this.messageList = res.data.data.pagedResult.rows.concat(this.messageList);
							this.isFreshing = false;
							this.triggered = false;
						}
					},
					fail: res => {
						this.showToast("您的网络已断开，请重试");
					}
				});
			},
			dblclick() {
				let currentTime = new Date().getTime()
				let lastTapTime = this.lastTapTime
				this.lastTapTime = currentTime
				if (currentTime - lastTapTime < 300) {
					if (this.lock == true) {
						return
					}
					this.nudge()
				}
			},
			nudge() {
				if (this.nudgeLock) {
					this.showToast("对方已收到您的提醒，请耐心等待");
					return
				}
				let that = this;
				that.lock = true;
				uni.requestSubscribeMessage({
					tmplIds: ['Zdco4YkW9anTkzmccHG_wj3JBpvDCRFsFFDAsqR2Ikc'],
					success(res) {
						if (res.Zdco4YkW9anTkzmccHG_wj3JBpvDCRFsFFDAsqR2Ikc == "accept") {
							uni.request({
								url: that.$serverUrl + '/chat/pushMessage',
								method: 'POST',
								data: {
									fromName: uni.getStorageSync('userInfo').username + "拍了拍您",
									toId: that.toName
								},
								header: {
									'content-type': 'application/x-www-form-urlencoded'
								},
								success: res => {
									that.nudgeLock = true
									that.lock = false;
								},
								fail: res => {
									that.showToast("您的网络已断开，请重试");
									that.lock = false;
								}
							});
						} else {
							that.lock = false;
						}
					}
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
			back() {
				let pages = getCurrentPages();
				if (pages.length == 1) {
					uni.switchTab({
						url: '../goods/goods',
						success() {
							uni.hideTabBar()
						}
					})
				} else {
					uni.navigateBack({})
				}
			},
			open() {
				this.isopen = !this.isopen
			},
			sendMessage() {
				if (this.message == "")
					return;
				if(this.message.length > 200){
					this.message = this.message.substr(0, 200)
				}
				if (this.isSending) {
					return;
				}
				this.isSending = true;
				setTimeout(() => {
					this.isSending = false;
				}, 5000);

				let data = {
					fromName: this.fromName,
					toName: this.toName,
					message: this.message,
					goodsId: this.data.goodsId,
					activityId: this.data.activityId,
					imgNumber: this.data.imgNumber
				}
				let data2 = {
					fromName: this.fromName,
					toName: this.toName,
					message: this.message,
					goodsId: this.data.goodsId,
					activityId: this.data.activityId,
					imgNumber: this.data.imgNumber,
					username: this.data.toUsername,
					avatarUrl: this.myAvater,
					id: "",
					title: this.title,
					task: 0
				}
				let date = new Date()
				let data3 = {
					fromName: this.fromName,
					toName: this.toName,
					message: this.message,
					goodsId: this.data.goodsId,
					activityId: this.data.activityId,
					imgNumber: this.data.imgNumber,
					username: this.data.toUsername,
					avatarUrl: this.heAvater,
					isChecked: 0,
					createTime: date.getTime(),
					isYours: 1,
					title: this.title,
					task: 0
				}
				this.message = "";
				this.textareaHeight = 27
				uni.request({
					url: this.$serverUrl + '/chat/addChat',
					method: 'POST',
					data: data,
					header: {
						'content-type': 'application/json'
					},
					success: res => {
						if (res.data.code == 200) {
							data2.id = res.data.data.id
							uni.sendSocketMessage({
								data: JSON.stringify(data2)
							});
							uni.$emit("receiveMsgInfo", JSON.stringify(data3))
							this.messageList.push(res.data.data);
							this.scrollId = 's' + (this.messageList.length - 1);
							if (this.messageList.length > 1) {
								if ((this.messageList[this.messageList.length - 1].createTime - this
										.messageList[this.messageList.length - 2].createTime) / 1000 >= 180) {
									this.messageList[this.messageList.length - 1].showTime = true
								} else {
									this.messageList[this.messageList.length - 1].showTime = false
								}
							} else {
								this.messageList[0].showTime = true
							}
							this.isSending = false;
						} else {
							this.showToast("您的网络已断开，请重试");
						}
					},
					fail: res => {
						this.showToast("您的网络已断开，请重试");
					}
				});
			}
		}
	}
</script>

<style scoped>
	.pic {
		position: absolute;
		width: 115rpx;
		height: 115rpx;
		left: 50rpx;
		bottom: 25rpx;
		border-radius: 12rpx;
		overflow: hidden;
	}

	.Label {
		height: 8%;
		width: 100%;
		display: flex;
		align-items: center;
		top: 10%;
		position: fixed;
		background-color: #ffffff;
		z-index: 4;
	}


	.textOuter {
		margin-left: 10rpx;
		margin-right: 10rpx;
		padding: 10rpx 30rpx 15rpx 30rpx;
		display: flex;
		align-items: center;
		line-height: 40rpx;
		font-size: 32rpx;
		letter-spacing: 0.1403846rpx;
	}

	.message {
		margin-top: 25rpx;
		word-break: break-all;
		display: flex;
		box-shadow: 0rpx 2rpx 40rpx rgba(230, 230, 230, 0.2);
	}

	.image {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
		flex-shrink: 0;
	}

	.messageBox {
		max-width: 80%;
		display: flex;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.topNavigationBar {
		z-index: 5;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		text-align: center;
		background-color: #ffffff;
		color: #000000;
		font-size: 32rpx;
		height: 10%;

	}

	.content {
		overflow: hidden;
		display: flex;
		flex-direction: column;
		align-items: center;
		background-color: #F2F2F2;
		height: 100%;
		width: calc(100% + 0rpx);
		overflow-x: hidden;
	}

	.messageList {
		position: fixed;
		width: 100%;
		height: calc(79% - 160rpx);
		top: 21%;
		box-sizing: border-box;
		padding-bottom: 20rpx;
		border: none;
		background-color: #F2F2F2;
		transition: all .3s ease-in-out;
	}

	.messageList.active {
		top: calc(21% - 230rpx);
		transition: all .3s ease-in-out;
	}

	.messageCard {
		width: 90%;
		margin-left: 5%;
		margin-bottom: 50rpx;
		display: flex;
	}

	.timeNotification {
		font-size: 28rpx;
		color: rgba(0, 0, 0, 0.4);
	}

	.inputBox {
		display: flex;
		justify-content: center;
		width: 100%;
		background: #F9F9F9;
		position: fixed;
		bottom: 0rpx;
		display: flex;
		box-shadow: 0rpx -3prx 25rpx rgba(216, 216, 216, 0.8), 0rpx -3rpx 0rpx rgba(0, 0, 0, 0.1);
		border-radius: 32rpx 32rpx 0rpx 0rpx;
		/* transition: all .3s ease-in-out; */
	}

	.inputBox.active {
		bottom: 230rpx !important;
		/* transition: all .3s ease-in-out; */
	}

	.hidebox {
		position: fixed;
		width: 100%;
		background: #F9F9F9;
		height: 230rpx;
		bottom: -230rpx;
		/* transition: all .3s ease-in-out; */
	}

	.hidebox.active {
		bottom: 0rpx;
		/* transition: all .3s ease-in-out; */
	}

	.input {
		margin-bottom: 60rpx;
		margin-left: 8%;
		background: #FFFFFF;
		width: 72%;
		max-height: 200rpx;
		min-height: 55rpx;
		border-radius: 35rpx;
		padding: 0rpx 20rpx 0rpx 20rpx;
		box-sizing: border-box;
		font-size: 30rpx;
		line-height: 55rpx;
		border: 3rpx solid rgba(0, 0, 0, 0.1);
	}

	.addIcon {
		margin-left: 5%;
		width: 55rpx;
		height: 55rpx;
		background: url(../../static/addicon.png) center/100% 100% no-repeat;
		transition: all .3s ease-in-out;
	}

	.addIcon.active {
		transform: rotate(45deg);
		transition: all .3s ease-in-out;
	}

	.wxLogo {
		width: 80rpx;
		height: 80rpx;
		background: url(../../static/wxLogo.png) center/100% 100% no-repeat;
	}

	#hiddenView {
		width: 100%;
	}

	.hiddenDiv {
		position: fixed;
		top: -500px;
		left: -500px;
		visibility: hidden;
		height: auto;
		margin-bottom: 60rpx;
		margin-left: 8%;
		background: #FFFFFF;
		width: 72%;
		max-height: 200rpx;
		min-height: 55rpx;
		border-radius: 35rpx;
		padding: 0rpx 20rpx 0rpx 20rpx;
		box-sizing: border-box;
		font-size: 30rpx;
		line-height: 55rpx;
		border: 3rpx solid rgba(0, 0, 0, 0.1);
		word-wrap: break-word;
		word-break: break-all;
	}
</style>
