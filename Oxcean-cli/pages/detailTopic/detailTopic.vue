<template>
	<view class="content" @click="login">
		<view class="topNavigationBar">
			<view class="backicon" :style="{top:(titleBarTop+5)+'px'}" @click="back()"></view>
		</view>
		<view class="userinfo" @click="changePlaceholder('none', 'none', 'none', 'none')">
			<view class="profilePic">
				<image :src="avatarUrl==null?'../../static/none3.png':avatarUrl"
					style="width: 96rpx;height: 96rpx; border-radius: 12rpx;"></image>
			</view>
			<view class="nameloaction">
				<view class="name">{{username==null?"某同学":username}}</view>
			</view>
			<view class="time">
				发布于{{checkDateTime(createTime)}}
			</view>
			<view v-if="yourUserId==myUserId" @click="change(0)"
				style="position: absolute;width: 100rpx;height: 48rpx;border-radius: 100rpx;border: 2rpx solid rgba(0, 0, 0, 0.2);right: 50rpx;top: 20rpx;font-size: 24rpx;letter-spacing: 0.14rpx;line-height: 48rpx;color: rgba(0, 0, 0, 0.6);text-align: center;">
				更改
			</view>
		</view>
		<view class="line"></view>
		<view
			style="width: 670rpx; margin-left:40rpx; font-size: 28rpx;line-height: 48rpx;letter-spacing: 0.140769rpx;color: #000000;margin-bottom: 16rpx; margin-top: 30rpx;word-wrap: break-word;word-break: break-all;">
			{{content}}
		</view>
		<view style="display: flex;">
			<view v-for="(item2, index2) in itsTags.split(';').slice(1, -1)" :key="index2" class="tag"
				:style="{color: 'rgb(' + $tagsColorMap.get(item2) + ')'}">
				#{{item2}}</view>
		</view>
		<view style="display: flex;margin-top: 14rpx;margin-bottom: 14rpx;padding-left: 24rpx;padding-right: 24rpx;">
			<image v-if="imgNumber>=1" @click.stop="clkImg(0, data.id, imgNumber)"
				:src="'https://lyuanzhi.com:8080/img_' + data.id + '_' + '0' + '.jpg'" mode="aspectFill"
				style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
			</image>
			<image v-if="imgNumber>=2" @click.stop="clkImg(1, data.id, imgNumber)"
				:src="'https://lyuanzhi.com:8080/img_' + data.id + '_' + '1' + '.jpg'" mode="aspectFill"
				style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
			</image>
			<image v-if="imgNumber>=3" @click.stop="clkImg(2, data.id, imgNumber)"
				:src="'https://lyuanzhi.com:8080/img_' + data.id + '_' + '2' + '.jpg'" mode="aspectFill"
				style="width: 218rpx;height:218rpx;margin-left: 8rpx;margin-right: 8rpx;">
			</image>
		</view>
		<view style="width: 100%; display: flex; align-items: center; margin-top: 25rpx; margin-bottom: 25rpx;">
			<button class="share" open-type="share"></button>
			<view @click="givealike" :class="{'active':islike}" class="like"
				style="margin-left: 15.2%; margin-bottom: 10rpx;"></view>
			<view style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.4);">
				{{like}}
			</view>
		</view>
		<view class="line"></view>
		<view
			style="margin-left: 32rpx; margin-top: 30rpx; font-weight: bold;font-size: 28rpx;line-height: 39rpx;letter-spacing: 0.140769rpx;color: #000000;">
			全部评论 ({{MessageNum}})</view>
		<view class="comments">
			<view v-for="(item,index) in comments" :key='index'
				style="display: flex; flex-direction: column; width: 100%;">
				<view class="line" v-if="index!=0" style="margin-top: 30rpx; margin-bottom: 30rpx;"></view>
				<view @longpress="onLongpress(item.userId, item.id, index)"
					@click="changePlaceholder(item.username==null?'某同学':item.username, item.id, index, 'none')">
					<view style="width: 100%; display: flex; align-items: center;">
						<image :src="item.avatarUrl==null?'../../static/none3.png':item.avatarUrl"
							style="height: 56rpx; width: 56rpx; border-radius: 50%; margin-left: 4.27%;"></image>
						<view
							style="margin-left: 19rpx; font-size: 26rpx;line-height: 36rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6);">
							{{item.username==null?"某同学":item.username}}
						</view>
						<image v-show="item.mouTX" src="../../static/coordinate.png"
							style="width: 30rpx;height:30rpx;margin-left: 10rpx;"></image>
					</view>
					<view style="display: flex;">
						<view
							style="width: 496rpx;margin-left: 106rpx; font-size: 28rpx;line-height: 42rpx;letter-spacing: 0.140769rpx;color: #000000;">
							{{item.content}}
						</view>
						<view class="like" @click="love(index)" style="margin-top: -7rpx; margin-left: 25rpx"
							:class="{'active':item.hasHeart}">
						</view>
						<view
							style="margin-top: 4rpx;font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.4);">
							{{item.heartNum}}
						</view>
					</view>
					<view
						style=" margin-left: 106rpx;margin-top: 8rpx; font-size: 24rpx;line-height: 42rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6);">
						{{checkDateTime(item.createTime)}}
					</view>
				</view>
				<view v-for="(item1,index1) in item.child" :key='index1'
					style="display: flex; flex-direction: column; width: 100%; padding-left: 70rpx; box-sizing: border-box;">
					<view class="line1" style="margin-top: 30rpx; margin-bottom: 30rpx;"></view>
					<view @longpress="onLongpress1(item1.userId, item1.id, index, index1)"
						@click="changePlaceholder(item1.username==null?'某同学':item1.username, item.id, index, index1)">
						<view style="width: 100%; display: flex; align-items: center;">
							<image :src="item1.avatarUrl==null?'../../static/none3.png':item1.avatarUrl"
								style="height: 48rpx; width: 48rpx; border-radius: 50%; margin-left: 4.27%;"></image>
							<view
								style="margin-left: 19rpx; font-size: 26rpx;line-height: 36rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6);">
								{{item1.username==null?"某同学":item1.username}}
							</view>
							<image v-show="item1.mouTX" src="../../static/coordinate.png"
								style="width: 30rpx;height:30rpx;margin-left: 10rpx;"></image>
						</view>
						<view style="display: flex;">
							<view
								style="width: 434rpx;margin-left: 96rpx; font-size: 28rpx;line-height: 42rpx;letter-spacing: 0.140769rpx;color: #000000;">
								{{item1.content}}
							</view>
							<view class="like" @click="love1(index, index1)"
								style="margin-top: -7rpx; margin-left: 25rpx" :class="{'active':item1.hasHeart}">
							</view>
							<view
								style="margin-top: 4rpx;font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.4);">
								{{item1.heartNum}}
							</view>
						</view>
						<view
							style=" margin-left: 96rpx;margin-top: 8rpx; font-size: 24rpx;line-height: 42rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6);">
							{{checkDateTime(item1.createTime)}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<view :style="{background: !isAnonymous?'#F9F9F9;':'#45474C;'}"
			style="transition: all .3s ease-in-out; width: 100%; height:222rpx; box-shadow: 0rpx -2rpx 0rpx rgba(230, 230, 230, 0.4); position: fixed; bottom: 0;">
			<view v-if="hideStealth" class="outer" @click="anonymous" :style="{background: !isAnonymous?'#E0E0E0;':'#48D1E0'}"></view>
			<view v-if="hideStealth" class="hex" @click="anonymous" :class="{active:isAnonymous}"></view>
			<view :style="{color: !isAnonymous?'rgba(0, 0, 0, 0.4)':'#48D1E0'}"
				style="transition: all .3s ease-in-out; position: absolute;left: 114rpx;top: 20rpx;font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;rgba(0, 0, 0, 0.4);">
				{{!isAnonymous?'实名':'匿名'}}
			</view>
			<input hold-keyboard cursor-spacing="30" maxlength="200" v-model="message" type="text" class="input"
				:placeholder="placeholderText"
				:style="{background: !isAnonymous?'#FFFFFF;':'#5c5d62',color: !isAnonymous?'#000000':'#ffffff'}">
			<view class="send" :style="{color: message.length!=0?'#48D1E0':isAnonymous?'#2B7D86':'#91E3EC'}"
				@click="send">发送</view>
			<view class="mo" v-show="isAnonymous">某同学</view>
		</view>
		<loading ref="loading"></loading>
		<pullUp :array="item" ref="pullUp" v-on:select="select"></pullUp>
	</view>
</template>

<script>
	import pullUp from '@/components/pull-up/pull-up.vue'
	export default {
		data() {
			return {
				lock: false,
				lock2: false,
				lock3: false,
				data: '',
				info: null,
				func: null,
				message: '',
				avatarUrl: '',
				titleBarTop: 0,
				username: '',
				createTime: '',
				content: '',
				like: '',
				islike: 0,
				MessageNum: 0,
				isAnonymous: 1,
				comments: [],
				index: 0,
				item: ['删除'],
				yourUserId: "yourUserId",
				myUserId: "myUserId",
				parentId: "none",
				placeholderText: "请输入你的评论...",
				comIndex: -1,
				comIndex1: -1,
				imgNumber: 0,
				itsTags: ";"
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		computed: {
			hideStealth() {
				return this.$store.state.config[0].funcValue;
			}
		},
		onLoad(option) {
			if(this.$store.state.config[0].funcValue == 0){
				this.isAnonymous = 0
			}
			if (!this.isLogin()) {
				uni.navigateTo({
					url: '../login/login'
				})
			}
			uni.$on('openSocket1', () => {
				this.init()
			})
			if (this.isLogin()) {
				this.init()
			}
			this.info = option.info
			this.index = option.index
			this.data = option.info.replace(/""/g, "")
			this.data = JSON.parse(this.data)
			this.yourUserId = this.data.userId
			this.myUserId = uni.getStorageSync('userInfo').id
			this.content = this.data.content
			this.createTime = this.data.createTime
			this.like = this.data.heartNum
			this.username = this.data.username
			this.avatarUrl = this.data.avatarUrl
			this.MessageNum = this.data.commentNum
			this.imgNumber = this.data.imgNumber
			this.itsTags = this.data.itsTags
			this.islike = this.data.hasHeart
			this.getCommentList()
			// uni.request({
			// 	url: this.$serverUrl + '/community/getCommunityHasHeart',
			// 	method: "POST",
			// 	data: {
			// 		communityId: this.data.id,
			// 		userId: uni.getStorageSync('userInfo').id
			// 	},
			// 	header: {
			// 		'content-type': 'application/x-www-form-urlencoded'
			// 	},
			// 	success: (res) => {
			// 		if (res.data.code == 200) {
			// 			this.islike = res.data.data
			// 		}
			// 	}
			// })
		},
		onShareAppMessage() {
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个话题",
				path: "pages/detailTopic/detailTopic?info=" + this.info + '&index=-1',
				imageUrl: ''
			}
		},
		destroyed() {
			uni.$off("openSocket1")
			uni.$emit("dontShowRedNum", this.data.id)
		},
		components: {
			pullUp
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
			onLongpress1(userId, id, index, index1) {
				if (!this.isLogin()) {
					return;
				}
				if (userId == uni.getStorageSync('userInfo').id) {
					let that = this
					uni.showModal({
						title: '提示',
						content: '确认删除这条评论？',
						success: function(res) {
							if (res.confirm) {
								that.$refs.loading.showLoading();
								uni.request({
									url: that.$serverUrl + '/community/deleteComment/' + uni
										.getStorageSync('userInfo').id,
									method: "POST",
									data: {
										id: id
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200) {
											that.changePlaceholder("none", "none", "none", "none")
											that.comments[index].child.splice(index1, 1)
											that.MessageNum--
											uni.$emit("updateCommentNumById", [that.data.id, that
												.MessageNum
											]);
											uni.$emit("updateCommentNumById1", [that.data.id, that
												.MessageNum
											]);
											uni.showToast({
												icon: "none",
												title: "评论删除成功",
												duration: 2000
											})
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
					})
				}
			},
			onLongpress(userId, id, index) {
				if (!this.isLogin()) {
					return;
				}
				if (userId == uni.getStorageSync('userInfo').id) {
					let that = this
					uni.showModal({
						title: '提示',
						content: '确认删除这条评论？',
						success: function(res) {
							if (res.confirm) {
								that.$refs.loading.showLoading();
								uni.request({
									url: that.$serverUrl + '/community/deleteComment/' + uni
										.getStorageSync('userInfo').id,
									method: "POST",
									data: {
										id: id
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200) {
											that.changePlaceholder("none", "none", "none", "none")
											that.MessageNum = that.MessageNum - that.comments[
												index].child.length - 1
											that.comments.splice(index, 1)
											uni.$emit("updateCommentNumById", [that.data.id, that
												.MessageNum
											]);
											uni.$emit("updateCommentNumById1", [that.data.id, that
												.MessageNum
											]);
											uni.showToast({
												icon: "none",
												title: "评论删除成功",
												duration: 2000
											})
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
					})
				}
			},
			change(index) {
				if (!this.isLogin()) {
					return;
				}
				this.$refs.pullUp.OpenPop(index)
			},
			select(data) {
				if (data == '' || data == null) {
					return
				}
				this.$refs.loading.showLoading();
				if (data.type == '删除') {
					uni.request({
						url: this.$serverUrl + '/community/deleteCommunity/' + uni.getStorageSync(
							'userInfo').id,
						method: "POST",
						data: {
							id: this.data.id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								uni.$emit("deleteCommunity1", this.data.id)
								uni.$emit("addTopic")
								this.back()
							}
						},
						complete: (res2) => {
							this.$refs.loading.hideLoading();
						}
					})
				}
			},
			login() {
				if (!this.isLogin()) {
					uni.navigateTo({
						url: '../../pages/login/login'
					})
				}
			},
			changePlaceholder(name, id, index, index1) {
				if (!this.isLogin()) {
					return;
				}
				if (index1 == "none") index1 = -1
				if (index == "none") index = -1
				if (this.comIndex != -1) this.comments[this.comIndex].mouTX = false
				if (this.comIndex != -1 && this.comIndex1 != -1) this.comments[this.comIndex].child[this.comIndex1].mouTX =
					false
				if (name == "none" && id == "none") {
					this.placeholderText = "请输入你的评论...";
					this.comIndex = -1
					this.comIndex1 = -1
				} else {
					if (this.comIndex == index && this.comIndex1 == index1 && this.parentId == id && this
						.placeholderText == "回复 @" + name) {
						this.parentId = "none"
						this.placeholderText = "请输入你的评论..."
						if (this.comIndex1 == -1) {
							this.comments[this.comIndex].mouTX = false
						} else {
							this.comments[this.comIndex].child[this.comIndex1].mouTX = false
						}
						this.comIndex = -1
						this.comIndex1 = -1
						return
					}
					this.comIndex = index
					this.comIndex1 = index1
					if (this.comIndex1 == -1) {
						this.comments[this.comIndex].mouTX = true
					} else {
						this.comments[this.comIndex].child[this.comIndex1].mouTX = true
					}
					this.placeholderText = "回复 @" + name;
				}
				this.parentId = id;
			},
			init() {
				if (this.$store.state.socket != null) {
					if (this.$store.state.socket.readyState == 1) {
						return
					}
				}
				this.$store.state.socket = uni.connectSocket({
					url: this.$serverWs + uni.getStorageSync('userInfo').id,
					complete: () => {}
				});
				uni.onSocketOpen((res) => {
					this.$store.state.reconnectTime = 0;
					this.heartbeat();
					uni.$emit("reGetInfo");
				});
				uni.onSocketError((res) => {
					this.reconnect();
				});
				uni.onSocketMessage((res) => {
					this.heartbeat();
					if (res.data != "pingdev") {
						this.$store.state.showRed = 1
						uni.$emit("receiveMsg", res.data);
						uni.$emit("receiveMsgInfo", res.data);
					}
				});
				uni.onSocketClose((res) => {
					this.reconnect();
				});
			},
			reconnect() {
				let that = this;
				if (that.$store.state.reconnectTime > 10) {
					return;
				}
				if (that.$store.state.reconnectLocked) {
					return;
				}
				that.$store.state.reconnectLocked = true;
				clearTimeout(that.$store.state.timeoutFunc);
				that.$store.state.timeoutFunc = setTimeout(function() {
					that.init();
					that.$store.state.reconnectLocked = false;
					that.$store.state.reconnectTime++;
				}, that.$store.state.timeout);
			},
			heartbeat() {
				let that = this;
				clearTimeout(that.$store.state.timeoutFunc1);
				clearTimeout(that.$store.state.timeoutFunc2);
				that.$store.state.timeoutFunc1 = setTimeout(function() {
					uni.sendSocketMessage({
						data: "pingcli"
					});
					that.$store.state.timeoutFunc2 = setTimeout(function() {
						uni.closeSocket();
					}, that.$store.state.timeout1);
				}, that.$store.state.timeout1)
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
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) +
						'-';
					return MM + DD;
				} else {
					let DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
					let MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) +
						'-';
					let YY = date.getFullYear() + '-'
					return YY + MM + DD
				}
			},
			back() {
				if (!this.isLogin()) {
					return;
				}
				let pages = getCurrentPages();
				if (pages.length == 1) {
					uni.switchTab({
						url: '../community/community',
						success() {
							uni.hideTabBar()
						}
					})
				} else {
					uni.navigateBack({})
				}
			},
			send() {
				if (!this.isLogin()) {
					return;
				}
				if (this.message == '') {
					return
				}
				if (this.lock3) {
					return
				}
				this.lock3 = true
				this.$refs.loading.showLoading();
				this.func = setTimeout(() => {
					this.lock3 = false;
					this.$refs.loading.hideLoading();
				}, 3000);
				uni.request({
					url: this.$serverUrl + '/chat/checkContent',
					method: "POST",
					data: {
						content: this.message,
						scene: 2,
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res == "failed") {
							uni.showToast({
								icon: "none",
								title: "服务器开小差了，请稍后再试",
								duration: 1500
							})
							this.lock3 = false;
							this.$refs.loading.hideLoading();
							clearTimeout(this.func)
							return
						}
						if (res.data.errcode == 0) {
							if (res.data.result.label == 20002 || res.data.result.label == 20003 || res.data
								.result.label == 20006 || res.data.result.label == 20008) {
								uni.showToast({
									icon: "none",
									title: "您的言论涉及色情、辱骂、违法犯罪等，请重新编辑",
									duration: 1500
								})
								this.lock3 = false;
								this.$refs.loading.hideLoading();
								clearTimeout(this.func)
							} else {
								uni.request({
									url: this.$serverUrl + '/community/addComment',
									method: "POST",
									data: {
										communityId: this.data.id,
										content: this.message,
										isAnonymous: this.isAnonymous,
										parentId: this.parentId,
										userId: uni.getStorageSync('userInfo').id
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res) => {
										if (res.data.code == 200) {
											if (this.parentId == 'none') {
												this.comments.push({
													avatarUrl: this.isAnonymous == 0 ? uni
														.getStorageSync('userInfo')
														.avatarUrl : null,
													content: this.message,
													createTime: "刚刚",
													hasHeart: 0,
													heartNum: 0,
													id: res.data.data,
													parentId: this.parentId,
													userId: uni.getStorageSync('userInfo')
														.id,
													username: this.isAnonymous == 0 ? uni
														.getStorageSync('userInfo')
														.username : null,
													child: [],
													mouTX: false
												})
											} else {
												for (let i = 0; i < this.comments.length; i++) {
													if (this.comments[i].id == this.parentId) {
														this.comments[i].child.push({
															avatarUrl: this.isAnonymous ==
																0 ? uni.getStorageSync(
																	'userInfo')
																.avatarUrl : null,
															content: this.message,
															createTime: "刚刚",
															hasHeart: 0,
															heartNum: 0,
															id: res.data.data,
															parentId: this.parentId,
															userId: uni.getStorageSync(
																'userInfo').id,
															username: this.isAnonymous ==
																0 ? uni.getStorageSync(
																	'userInfo')
																.username : null,
															mouTX: false
														})
														break
													}
												}
											}
											this.MessageNum++
											uni.$emit("updateCommentNumById", [this.data.id, this
												.MessageNum
											]);
											uni.$emit("updateCommentNumById1", [this.data.id, this
												.MessageNum
											]);

											if (this.data.userId != uni.getStorageSync('userInfo')
												.id) {
												uni.sendSocketMessage({
													data: JSON.stringify({
														task: 1,
														avatarUrl: this
															.isAnonymous ?
															'../../static/none3.png' :
															uni
															.getStorageSync(
																'userInfo')
															.avatarUrl,
														content: this.message,
														createTime: Date.now(),
														isChecked: 0,
														username: this
															.isAnonymous ? '某同学' :
															uni
															.getStorageSync(
																'userInfo')
															.username,
														getCommunityListVo: {
															avatarUrl: this.data
																.avatarUrl,
															commentNum: this
																.MessageNum,
															content: this.data
																.content,
															createTime: this.data
																.createTime,
															hasHeart: this.islike,
															heartNum: this.like,
															id: this.data.id,
															isAnonymous: this.data
																.isAnonymous,
															userId: this.data
																.userId,
															username: this.data
																.username,
															imgNumber: this.data
																.imgNumber,
															itsTags: this.data
																.itsTags
														}
													})
												});
											}

											this.message = ''
											uni.hideKeyboard()
											this.lock3 = false;
											this.$refs.loading.hideLoading();
											clearTimeout(this.func)
											this.$nextTick(function() {
												uni.pageScrollTo({
													scrollTop: 9999,
													duration: 0
												})
											})
										}
									}
								})
							}
						} else {
							uni.showToast({
								icon: "none",
								title: "服务器开小差了，请稍后再试",
								duration: 1500
							})
							this.lock3 = false;
							this.$refs.loading.hideLoading();
							clearTimeout(this.func)
						}
					}
				})
			},
			anonymous() {
				this.isAnonymous = 1 - this.isAnonymous
			},
			love(index) {
				if (!this.isLogin()) {
					return;
				}
				if (this.lock2) {
					return
				}
				this.lock2 = true
				this.comments[index].hasHeart = !this.comments[index].hasHeart
				if (this.comments[index].hasHeart == 1) {
					this.comments[index].heartNum++
				} else {
					this.comments[index].heartNum--
				}
				uni.request({
					url: this.$serverUrl + '/community/changeCommentHeart',
					method: "POST",
					data: {
						commentId: this.comments[index].id,
						type: this.comments[index].hasHeart ? 0 : 1,
						userId: uni.getStorageSync('userInfo').id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.lock2 = false;
						}
					},
					fail: (res) => {

					},
					complete: (res) => {
						setTimeout(() => {
							this.lock2 = false;
						}, 1000);
					}
				})
			},
			love1(index, index1) {
				if (!this.isLogin()) {
					return;
				}
				if (this.lock2) {
					return
				}
				this.lock2 = true
				this.comments[index].child[index1].hasHeart = !this.comments[index].child[index1].hasHeart
				if (this.comments[index].child[index1].hasHeart == 1) {
					this.comments[index].child[index1].heartNum++
				} else {
					this.comments[index].child[index1].heartNum--
				}
				uni.request({
					url: this.$serverUrl + '/community/changeCommentHeart',
					method: "POST",
					data: {
						commentId: this.comments[index].child[index1].id,
						type: this.comments[index].child[index1].hasHeart ? 0 : 1,
						userId: uni.getStorageSync('userInfo').id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.lock2 = false;
						}
					},
					fail: (res) => {

					},
					complete: (res) => {
						setTimeout(() => {
							this.lock2 = false;
						}, 1000);
					}
				})
			},
			getCommentList() {
				this.$refs.loading.showLoading();
				uni.request({
					url: this.$serverUrl + '/community/getCommentList',
					method: "POST",
					data: {
						communityId: this.data.id,
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.sortComments(res.data.data)
						}
						this.$refs.loading.hideLoading();
					},
					fail: (res) => {
						this.$refs.loading.hideLoading();
					}
				})
			},
			sortComments(data) {
				for (let i = 0; i < data.length; i++) {
					if (data[i].parentId == "none") {
						data[i].mouTX = false
						data[i].child = []
						for (let j = 0; j < data.length; j++) {
							if (data[j].parentId == data[i].id) {
								data[j].mouTX = false
								data[i].child.push(data[j])
							}
						}
						this.comments.push(data[i])
					}
				}
			},
			givealike() {
				if (!this.isLogin()) {
					return;
				}
				if (this.lock) {
					return
				}
				this.lock = true
				this.islike = !this.islike
				if (this.islike == 1) {
					this.like++
				} else {
					this.like--
				}
				uni.request({
					url: this.$serverUrl + '/community/changeCommunityHeart',
					method: "POST",
					data: {
						communityId: this.data.id,
						type: this.islike ? 0 : 1,
						userId: uni.getStorageSync('userInfo').id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							uni.$emit("changeHeartById", [this.data.id, this.islike, this.like]);
							uni.$emit("changeHeartById1", [this.data.id, this.islike, this.like]);
							this.lock = false;
						}
					},
					fail: (res) => {

					},
					complete: (res) => {
						setTimeout(() => {
							this.lock = false;
						}, 1000);
					}
				})
			}
		}
	}
</script>

<style scoped>
	button::after {
		border: none;
	}

	.mo {
		font-size: 24rpx;
		line-height: 34rpx;
		letter-spacing: 0.140769rpx;
		color: #FFFFFF;
		position: absolute;
		width: 73rpx;
		height: 34rpx;
		left: 195rpx;
		top: 20rpx;
		white-space: nowrap;
	}

	.send {
		font-weight: bold;
		font-size: 32rpx;
		line-height: 45rpx;
		white-space: nowrap;
		letter-spacing: 0.140769rpx;
		transition: all .3s ease-in-out;
		position: absolute;
		width: 65rpx;
		height: 45rpx;
		left: 649rpx;
		top: 98rpx;
	}

	.input {
		position: absolute;
		width: 597rpx;
		height: 72rpx;
		left: 24rpx;
		top: 82rpx;
		transition: all .3s ease-in-out;
		border: 2rpx solid rgba(0, 0, 0, 0.06);
		box-sizing: border-box;
		border-radius: 200rpx;
		padding-left: 30rpx;
		padding-right: 30rpx;
		font-size: 32rpx;
		line-height: 72rpx;
	}

	.outer {
		position: absolute;
		width: 78rpx;
		height: 42rpx;
		left: 24rpx;
		top: 16rpx;
		border-radius: 100rpx;
	}

	.comments {
		margin-top: 40rpx;
		width: 100%;
		display: flex;
		flex-direction: column;
		margin-bottom: 242rpx;
	}

	.like {
		width: 44rpx;
		height: 44rpx;
		background: url(../../static/likeit.png) center/100% 100% no-repeat;
	}

	.like.active {
		background: url(../../static/likeitred.png) center/100% 100% no-repeat;
	}

	.hex {
		position: absolute;
		width: 38rpx;
		height: 38rpx;
		left: 26rpx;
		border-radius: 50%;
		top: 18rpx;
		z-index: 3;
		background: #FFFFFF;
		transition: all .3s ease-in-out;
	}

	.hex.active {
		transform: translateX(40rpx);
		transition: all .3s ease-in-out;
	}

	.share {
		width: 32rpx;
		height: 32rpx;
		background: url(../../static/Share.png) center/60% 100% no-repeat;
		margin-left: 55.67%;
		margin-right: 0;
	}

	.content {
		display: flex;
		flex-direction: column;
		overflow-y: scroll;
		overflow-x: hidden;
	}

	.topNavigationBar {
		z-index: 5;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		text-align: center;
		height: 180rpx;
		color: #000000;
		font-size: 32rpx;
		background: #ffffff;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 3%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
		filter: brightness(0%);
	}

	.userinfo {
		height: 140rpx;
		flex-shrink: 0;
		margin-top: 180rpx;
		width: 100%;
		position: relative;
	}

	.profilePic {
		left: 5.5%;
		top: 15%;
		width: 110rpx;
		height: 110rpx;
		position: absolute;
	}

	.nameloaction {
		position: relative;
		top: 15%;
		left: 21%;
		display: flex;
		align-items: center;
		height: 50rpx;
	}

	.name {
		font-weight: bold;
		color: black;
		font-size: 28rpx;
	}

	.time {
		color: rgba(0, 0, 0, 0.4);
		position: absolute;
		left: 21%;
		top: 55%;
		font-size: 24rpx;
	}

	.line {
		width: 670rpx;
		margin-left: 40rpx;
		border: 2rpx solid rgba(0, 0, 0, 0.06);
	}

	.line1 {
		width: 600rpx;
		margin-left: 36rpx;
		border: 2rpx solid rgba(0, 0, 0, 0.06);
	}

	.tag {
		font-size: 28rpx;
		letter-spacing: 0.140769rpx;
		line-height: 33rpx;
		margin-left: 40rpx;
		margin-bottom: 20rpx;
		margin-top: 10rpx;
	}
</style>
