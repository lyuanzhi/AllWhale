<template>
	<view class="content" :style="{background:choose?'#ffffff;':'#F2F2F2'}" @click="login">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height*1.5+'px'}"></view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" placeholder="根据标签搜索"
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-65)+'px'}"
					disabled @click="toSearch"></input>
			</view>
		</uni-nav-bar>
		<custom-swiper class="swiper" style="width: 100%;  padding-top: 25rpx;box-sizing: border-box; z-index: 0;"></custom-swiper>
		<view class="typeselect"
			:style="{position: touchTop==1?'fixed':'absolute',top: touchTop==1?navHeight+'px':swiperBottom+'px'}">
			<view style="width: 100%; display: flex; align-items: center; justify-content: space-around;">
				<view class="second-place-text" :class="{'active':choose==0}" @click="selectType(0)">
					<view class="hex" v-if="choose==0"></view>话题
				</view>
				<view class="second-place-text" :class="{'active':choose==1}" @click="selectType(1)">
					<view class="hex" v-if="choose==1"></view>活动
				</view>
			</view>
		</view>
		<view class="labelbox" v-if="dataList.length!=0">
			<view v-for="(item,index) in dataList" class="label" :key="index" @click="select(index)" v-if="choose==0"
				style="position: relative;">
				<view style="display: flex; margin-top: 32rpx; margin-left: 32rpx; align-items: center;">
					<image :src="item.avatarUrl==null?'../../static/none3.png':item.avatarUrl"
						style="height: 56rpx; width: 56rpx; border-radius: 50%; align-items: center;"></image>
					<view
						style="margin-left: 17rpx; font-size: 24rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.6);">
						{{item.username==null?"某同学":item.username}}
					</view>
				</view>
				<image v-if="item.id=='1'" src="../../static/official.png"
					style="width:80rpx;height:80rpx;position: absolute;top:0;right:0;"></image>
				<view class="Box"
					style="margin-left: 32rpx; margin-right: 32rpx; margin-top: 28rpx;margin-bottom: 16rpx; font-size: 28rpx; line-height: 48rpx;letter-spacing: 0.140769rpx;word-wrap: break-word;word-break: break-all;">
					{{item.content}}
				</view>
				<view style="display: flex;">
					<view v-for="(item2, index2) in item.itsTags.split(';').slice(1, -1)" :key="index2" class="tag"
						:style="{color: 'rgb(' + $tagsColorMap.get(item2) + ')'}"
						@click.stop="searchByTag(item.itsTags, index2)">
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
			<view
				style="height: 82rpx; display: flex; align-items: center; overflow: hidden; overflow-x: scroll; background: #F1FCFD;"
				v-if="choose==1">
				<view v-for="(item,index) in chooseList" :key="index" class="choose" :class="{'active':chosen==index}"
					@click="choosen(index)">
					{{item}}
				</view>
				<view style="width: 30rpx;"></view>
			</view>
			<view style="display: flex; flex-direction: column; width: 100%; margin-bottom: 40rpx;">
				<view v-for="(item,index) in labelList[chosen]" :key="index" class="smalllabel" v-if="choose==1"
					@click="selectAc(index)"
					:class="{'game':item.activityType=='娱乐局','sport':item.activityType=='运动局','study':item.activityType=='学习局','Spelllist':item.activityType=='拼团','else':item.activityType=='其他'}">
					<image :src="item.avatarUrl"
						style="position: absolute; height: 60rpx; width: 60rpx; left: 3.53%; border-radius: 50%; top: 13%;">
					</image>
					<view
						style="letter-spacing: 0.140769rpx; font-size: 32rpx; line-height: 45rpx; position: absolute; left: 15.53%; top:13%">
						{{item.title.length>=14?item.title.substring(0,12)+"...":item.title}}
					</view>
					<image v-if="item.currentS<currentD" src="../../static/outOfTime.png"
						style="width:60rpx;height:60rpx;position: absolute;top:0;left:0;"></image>
					<view
						style="font-size: 24rpx;line-height: 34rpx;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.6); position: absolute; left: 15.53%;top:53%">
						活动时间：{{item.activityTime}}</view>
					<view class="outer"
						:class="{'game':item.activityType=='娱乐局','sport':item.activityType=='运动局','study':item.activityType=='学习局','Spelllist':item.activityType=='拼团','else':item.activityType=='其他'}">
						<view
							style="writing-mode:vertical-rl;font-weight: bold;font-size: 32rpx;line-height: 46rpx;letter-spacing: 0.280769rpx;color: #FFFFFF;">
							{{item.activityType}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<loading ref="loading"></loading>
		<custom-tab-bar currentPage="community"></custom-tab-bar>
	</view>
</template>

<script>
	import customSwiper from '@/components/custom-swiper/custom-swiper.vue'
	export default {
		data() {
			return {
				swiperBottom: 0,
				touchTop: 0,
				lock: false,
				lock2: false,
				func: null,
				func2: null,
				firstPage: 1,
				secondPage: [1, 1, 1, 1, 1, 1, 1, 1, 1],
				firstTotal: 1,
				secondTotal: [1, 1, 1, 1, 1, 1, 1, 1, 1],
				content: '',
				typeList: ["话题", "组局"],
				choose: 0,
				titleBar: null,
				navHeight: 0,
				dataList: [],
				chooseList: ['全部', '娱乐局', '运动局', '学习局', '拼团', '其他'],
				chosen: 0,
				labelList: [
					[],
					[],
					[],
					[],
					[],
					[],
					[],
					[],
					[]
				],
				ischeck: {
					first: [false],
					second: [false, false, false, false, false, false, false, false, false, false]
				},
				currentD: Date.now()
			}
		},
		components: {
			customSwiper
		},
		onPageScroll(e) {
			this.touchTop = e.scrollTop >= this.swiperBottom - this.navHeight ? 1 : 0
		},
		onPullDownRefresh() {
			uni.reLaunch({
				url: '../community/community',
				success() {
					uni.hideTabBar()
				}
			});
		},
		onReady() {
			let that = this;
			const query = uni.createSelectorQuery()
			setTimeout(() => {
				query.select('.navBar').boundingClientRect(function(data) {
					that.navHeight = data.height
				}).exec();
				query.select('.swiper').boundingClientRect(function(data) {
					that.swiperBottom = data.bottom
				}).exec();
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		onLoad() {
			this.$refs.loading.showLoading();
			this.flash()
			this.getShowRed()
			uni.$on('addTopic', () => {
				this.dataList = []
				this.firstPage = 1
				this.firstTotal = 1
				this.choose = 0
				this.flash()
			})
			uni.$on('addActivity', (res) => {
				this.secondPage[res + 1] = 1
				this.secondTotal[res + 1] = 1
				this.secondPage[0] = 1
				this.secondTotal[0] = 1
				this.update(res + 1)
				this.update(0)
			})
			uni.$on('changeHeartById', (res) => {
				for (let i = 0; i < this.dataList.length; i++) {
					if (this.dataList[i].id == res[0]) {
						this.dataList[i].hasHeart = res[1]
						this.dataList[i].heartNum = res[2]
					}
				}
			})
			uni.$on('updateCommentNumById', (res) => {
				for (let i = 0; i < this.dataList.length; i++) {
					if (this.dataList[i].id == res[0]) {
						this.dataList[i].commentNum = res[1]
					}
				}
			})
			uni.$on('openSocket', () => {
				this.init()
			})
			if (this.isLogin()) {
				this.init()
			}
		},
		onShareAppMessage(res) {
			let textObj = JSON.stringify(this.dataList[Number(res.target.id)])
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个话题",
				path: "pages/detailTopic/detailTopic?info=" + textObj + '&index=0',
				imageUrl: '../../static/none2.png'
			}
		},
		onReachBottom() {
			if (!this.isLogin()) {
				return;
			}
			this.flash()
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
			searchByTag(itsTags, index) {
				if (!this.isLogin()) {
					return;
				}
				uni.navigateTo({
					url: '/pages/searchResult1/searchResult1?content=' + '#' + itsTags.split(';').slice(1, -1)[
						index]
				});
			},
			toSearch() {
				if (!this.isLogin()) {
					return;
				}
				uni.navigateTo({
					url: '/pages/search1/search1?content=' + ''
				});
			},
			getShowRed() {
				uni.request({
					url: this.$serverUrl + '/chat/getShowRed',
					method: 'POST',
					data: {
						userId: uni.getStorageSync('userInfo').id
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.$store.state.showRed = res.data.data
						}
					}
				});
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
			login() {
				if (!this.isLogin()) {
					uni.navigateTo({
						url: '../../pages/login/login'
					})
				}
			},
			update(e) {
				if (!this.isLogin()) {
					return;
				}
				uni.request({
					url: this.$serverUrl + '/activity/getActivity',
					method: "POST",
					data: {
						activityType: this.chooseList[e],
						page: this.secondPage[e],
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							for (let i = 0; i < res.data.data.rows.length; i++) {
								res.data.data.rows[i].currentS = new Date(res.data.data.rows[i].activityTime
									.replace(/-/g, "/")).getTime()
							}
							this.$set(this.labelList, e, res.data.data.rows)
							this.secondTotal[e] = res.data.data.total
							this.secondPage[e]++
						}
					}
				})
			},
			choosen(e) {
				if (!this.isLogin()) {
					return;
				}
				this.chosen = e
				if (this.ischeck.second[this.chosen]) {
					return
				}
				this.ischeck.second[this.chosen] = true
				uni.request({
					url: this.$serverUrl + '/activity/getActivity',
					method: "POST",
					data: {
						activityType: this.chooseList[this.chosen],
						page: this.secondPage[this.chosen],
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							for (let i = 0; i < res.data.data.rows.length; i++) {
								res.data.data.rows[i].currentS = new Date(res.data.data.rows[i].activityTime
									.replace(/-/g, "/")).getTime()
								this.labelList[this.chosen].push(res.data.data.rows[i])
							}
							this.secondTotal[this.chosen] = res.data.data.total
							this.secondPage[this.chosen]++
						}
					}
				})

			},
			selectType(e) {
				if (!this.isLogin()) {
					return;
				}
				if (this.choose == e) {
					return
				}
				this.choose = e
				this.choosen(this.chosen)
			},
			select(index) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(this.dataList[index])
				uni.navigateTo({
					url: '../../pages/detailTopic/detailTopic?info=' + textObj + '&index=' + index,
				})
			},
			selectAc(index) {
				if (!this.isLogin()) {
					return;
				}
				let textObj = JSON.stringify(this.labelList[this.chosen][index])
				uni.navigateTo({
					url: '../../pages/detialActivity/detialActivity?info=' + textObj,
				})
			},
			flash() {
				if (this.lock) {
					return
				}
				this.lock = true
				this.func2 = setTimeout(() => {
					this.lock = false;
					this.$refs.loading.hideLoading();
				}, 4000);
				if (this.choose == 0) {

					if (this.firstPage > this.firstTotal) {
						return
					}

					uni.request({
						url: this.$serverUrl + '/community/getCommunityList',
						method: "POST",
						data: {
							page: this.firstPage,
							userId: uni.getStorageSync('userInfo').id
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								uni.request({
									url: this.$serverUrl + '/community/getOfficialTopic',
									method: "POST",
									data: {
										userId: uni.getStorageSync('userInfo').id
									},
									header: {
										'content-type': 'application/x-www-form-urlencoded'
									},
									success: (res1) => {
										if (res1.data.code == 200) {
											for (let i = 0; i < res.data.data.rows.length; i++) {
												if (i == 2 && this.firstPage == 1) {
													this.dataList.push(res1.data.data)
												}
												this.dataList.push(res.data.data.rows[i])
											}
											this.firstTotal = res.data.data.total
											this.firstPage++
											this.lock = false
											clearTimeout(this.func2)
											this.$refs.loading.hideLoading();
										}
									},
									fail: (res1) => {

									}
								})
							}
						},
						fail: (res) => {

						}
					})
				} else if (this.choose == 1) {
					if (this.secondPage[this.chosen] > this.secondTotal[this.chosen]) {
						return
					}
					// if(this.ischeck.second[this.chosen]){
					// 	return
					// }
					// this.ischeck.second[this.chosen]=true
					uni.request({
						url: this.$serverUrl + '/activity/getActivity',
						method: "POST",
						data: {
							activityType: this.chooseList[this.chosen],
							page: this.secondPage[this.chosen],
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: (res) => {
							if (res.data.code == 200) {
								for (let i = 0; i < res.data.data.rows.length; i++) {
									res.data.data.rows[i].currentS = new Date(res.data.data.rows[i]
										.activityTime.replace(/-/g, "/")).getTime()
									this.labelList[this.chosen].push(res.data.data.rows[i])
								}
								this.secondTotal[this.chosen] = res.data.data.total
								this.secondPage[this.chosen]++
								this.lock = false
								clearTimeout(this.func2)
								this.$refs.loading.hideLoading();
							}
						},
						fail: (res) => {

						}
					})
				}

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
			}
		}
	}
</script>

<style scoped>
	.smalllabel {
		width: 686rpx;
		height: 167rpx;
		margin-left: 32rpx;
		box-sizing: border-box;
		box-shadow: 0rpx 2rpx 12rpx rgba(0, 0, 0, 0.03);
		border-radius: 12rpx;
		margin-top: 30rpx;
		position: relative;
	}

	.smalllabel.eat {
		background: #FFFBF7;
		border: 1rpx solid #F3D4B5;
	}

	.smalllabel.game {
		background: #FAFAFF;
		border: 1rpx solid #CBC9E8;
	}

	.smalllabel.sport {
		background: #F7FFFD;
		border: 1rpx solid #AFE8D7
	}

	.smalllabel.study {
		background: #F7FDFF;
		border: 1rpx solid #B2E0EE;
	}

	.smalllabel.friend {
		background: #F8FFF7;
		border: 1rpx solid #C2EABC;
	}

	.smalllabel.Spelllist {
		background: #FFF7FD;
		border: 1rpx solid #ECC7E4;
	}

	.smalllabel.Spellcar {
		background: #FFFAFA;
		border: 1rpx solid #ECC3C3;
	}

	.smalllabel.else {
		background: #FAFCFF;
		border: 1rpx solid #C3D3EC;
	}

	.outer {
		position: absolute;
		height: 100%;
		width: 82rpx;
		right: 0;
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


	.choose {
		height: 82rpx;
		font-size: 24rpx;
		line-height: 82rpx;
		letter-spacing: 0.140769rpx;
		color: rgba(0, 0, 0, 0.6);
		white-space: nowrap;
		flex: none;
		padding-left: 30rpx;
		padding-right: 30rpx;
	}

	.choose.active {
		font-weight: bold;
		color: #000000;
	}

	button::after {
		border: none;
	}

	.font {
		font-size: 24rpx;
		line-height: 34rpx;
		letter-spacing: 0.140769rpx;
		color: rgba(0, 0, 0, 0.4);
	}

	.share {
		height: 32rpx;
		width: 32rpx;
		background: url(../../static/Share.png) center/60% 100% no-repeat;
		margin: 0;
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

	.content {
		display: flex;
		flex-direction: column;
		overflow-x: hidden;
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

	.typeselect {
		flex-shrink: 0;
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: flex-end;
		position: relative;
		z-index: 1 !important;
		height: 80rpx;
		box-shadow: 0rpx 2rpx 12rpx rgba(0, 0, 0, 0.08);
		background-color: #ffffff;
	}

	.second-place-text {
		align-items: center;
		width: 50%;
		justify-content: center;
		display: flex;
		font-size: 34rpx;
		line-height: 50rpx;
		font-weight: 300;
	}

	.second-place-text.active {
		font-weight: 550;
	}

	.hex {
		width: 28rpx;
		height: 28rpx;
		background: #48D1E0;
		border-radius: 50%;
		margin-right: 10rpx;
	}

	.bar {
		display: flex;
		flex-direction: row;
		align-items: center;
		position: fixed;
		background-color: #FFFFFF;
	}

	.labelbox {
		display: flex;
		flex-direction: column;
		width: 100%;
		margin-bottom: 160rpx;
		margin-top: 80rpx;
	}

	.label {
		width: 100%;
		background-color: #ffffff;
		margin-bottom: 16rpx;
		display: flex;
		flex-direction: column;
	}

	.Box {
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 5;
		/* 可以显示的行数，超出部分用...表示*/
		-webkit-box-orient: vertical;
	}

	.navBarSearch {
		border-top-right-radius: 50rpx;
		border-bottom-right-radius: 50rpx;
		background-color: #EBF2F2;
		color: rgb(34, 21, 51);
		font-size: 28rpx;
	}

	.logo {
		margin-left: 16px;
		margin-right: 12px;
		background: url(../../static/logo.png) center/100% no-repeat;
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

	.tag {
		font-size: 28rpx;
		letter-spacing: 0.140769rpx;
		line-height: 33rpx;
		margin-left: 32rpx;
		margin-bottom: 20rpx;
		margin-top: 10rpx;
	}
	
	.swiper {
		background-color: #FFFFFF !important;
	}
</style>
