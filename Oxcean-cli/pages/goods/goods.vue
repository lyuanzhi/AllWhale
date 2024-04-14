<template>
	<view class="content" @click="login">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height*1.5+'px'}"></view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" placeholder="搜索"
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-65)+'px'}"
					disabled @click="toSearch"></input>
			</view>
		</uni-nav-bar>
		<!-- <custom-swiper class="swiper" style="width: 100%;  margin-top: 25rpx; z-index: 0;"></custom-swiper> -->
		<view class="second-place" :style="{position: 'fixed',top: navHeight+'px'}">
			<!-- :style="{position: touchTop==1?'fixed':'absolute',top: touchTop==1?navHeight+'px':swiperBottom+'px'}"> -->
			<view
				style="box-shadow: 0px 2px 0px rgba(24, 48, 51, 0.1);width: 100%; display: flex; align-items: center; justify-content: space-around; height: 85rpx; z-index: 2; background-color: #ffffff;">
				<view class="second-place-text" :class="{'active':choose==0}" @click="selectGoods(0)">
					<view class="hex" v-if="choose==0"></view>闲置
				</view>
				<view class="second-place-text" :class="{'active':choose==1}" @click="selectGoods(1)">
					<view class="hex" v-if="choose==1"></view>租房
				</view>
			</view>
			<view class="selectionBar" :class="{'active':isOpen}">
				<view class="filter" @click="openFilter">
					<view :class="{'beBlue':isOpen}">{{filterTxt}}</view>
					<view class="recimg" :class="{'active':isOpen}"></view>
				</view>
				<view class="selectionSecond" v-show="isOpen" :class="{'active':openani}">
					<view class="labelbox" v-for="(item,index) in goodsTitleArray[choose]" :key="index">
						<view class="labelbox-in">
							<view v-if="item.length<=2"
								style="font-size: 24rpx;white-space: nowrap;margin-left: 32rpx;margin-top: 16rpx;">
								{{item}}
							</view>
							<view v-if="item.length>2"
								style="font-size: 24rpx;margin-left: 32rpx;margin-top: 16rpx;white-space: nowrap;">
								价格
								<br>
								(/周)
							</view>
							<view class="scroll">
								<block v-for="(item2,index2) in secondGoodsArray[choose][index]" :key='index2'>
									<view class="smallbox"
										:class="{'select':choose?(selectedSecond2_[index]==index2):(selectedSecond_[index]==index2)}"
										@click="changeSelectedSecond(index,index2)">
										<view class="border"
											:style="{fontSize:item2=='Summertown'||item2=='Headington'?'20rpx':'28rpx'}">
											{{item2}}
										</view>
									</view>
								</block>
							</view>
						</view>
					</view>
				</view>
				<view v-if="openani" style="display: flex;width: 100%;margin-top: 24rpx;">
					<view class="reset" @click="reset">重置</view>
					<view class="confirm" @click="confirm">确定</view>
				</view>
			</view>
		</view>
		<view class="thrid-place">
			<view class="thrid-place-half" v-if="detailArray[0].length+detailArray[1].length>0">
				<detail-box v-for="(item,index) in detailArray[0]" :key="index" :array="item" style="width: 100%;">
				</detail-box>
			</view>
			<view class="thrid-place-half" v-if="detailArray[0].length+detailArray[1].length>0">
				<detail-box v-for="(item,index) in detailArray[1]" :key="index" :array="item" style="width: 100%;">
				</detail-box>
			</view>
			<view v-if="detailArray[0].length+detailArray[1].length==0"
				style="width: 100%;margin-top: 200rpx;margin-bottom: 300rpx;">
				<image src="../../static/none.png" mode="aspectFit" style="width: 100%;height:240rpx;"></image>
				<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">
					{{choose?"暂无房源":"暂无商品"}}
				</view>
			</view>
		</view>
		<view style="width: 100%;background: #FAFAFA;" :style="{height:forthPlaceHeight+'px'}"></view>
		<!-- <view class="circles"></view> -->
		<custom-tab-bar currentPage="goods" style="z-index: 100;"></custom-tab-bar>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	import customSwiper from '@/components/custom-swiper/custom-swiper.vue'
	import detailBox from '@/components/detail-box/detail-box.vue'
	export default {
		data() {
			return {
				choose: 0,
				touchTop: 0,
				navHeight: 0,
				swiperBottom: 0,
				forthPlaceHeight: 0,
				modelHeight: 0,
				titleBar: null,
				detailArray: [
					[],
					[]
				],
				goodsTitleArray: [
					['类型', '新旧', '取货'],
					['位置', '房型', '卫浴', '价格(/周)', '租期']
				],
				secondGoodsArray: [
					[
						['全部', '美妆', '日用', '厨具', '电子', '其他'],
						['全部', '全新', '几乎全新', '略微使用', '日常折旧'],
						['全部', '自取', '送货上门']
					],
					[
						['全部', '市中心', 'Cowley', 'Botley', 'Hinksey', 'Summertown', 'Headington', '其他'],
						['全部', 'Flat', 'Studio', 'House'],
						['全部', '独卫', '非独卫'],
						['全部', '<£150', '£150-200', '£200-250', '>£250'],
						['全部', '<7天', '7-30天', '1-3月', '>3月']
					]
				],
				isOpen: 0,
				openani: 0,
				timeoutFunc3: null,
				selectedSecond: [0, 0, 0],
				selectedSecond2: [0, 0, 0, 0, 0],
				selectedSecond_: [0, 0, 0],
				selectedSecond2_: [0, 0, 0, 0, 0],
				filterTxt: "高级筛选",
				page: 1,
				page2: 1,
				lock: false,
				func: null
			}
		},
		components: {
			customSwiper,
			detailBox
		},
		onReady() {
			let that = this;
			const query = uni.createSelectorQuery()
			setTimeout(() => {
				query.select('.navBar').boundingClientRect(function(data) {
					that.navHeight = data.height
				}).exec();
				// query.select('.swiper').boundingClientRect(function(data) {
				// 	that.swiperBottom = data.bottom
				// }).exec();
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		// onPageScroll(e) {
		// 	this.touchTop = e.scrollTop >= this.swiperBottom - this.navHeight ? 1 : 0
		// },
		onPullDownRefresh() {
			uni.reLaunch({
				url: '../goods/goods',
				success() {
					uni.hideTabBar()
				}
			});
		},
		onLoad() {
			this.modelHeight = uni.getSystemInfoSync().screenHeight
			uni.removeStorageSync('detailArrayHouse')
			uni.removeStorageSync('detailArrayGoods')
			this.getGoodsList(this.choose, 1)
			this.getShowRed()
			uni.$on('flushGoodsPage', (choose) => {
				if (choose == 0) {
					this.page = 1
				} else {
					this.page2 = 1
				}
				this.getGoodsList(choose, 1)
			})
			uni.$on('openSocket', () => {
				this.init()
			})
			if (this.isLogin()) {
				this.init()
			}
		},
		onReachBottom() {
			if (!this.isLogin()) {
				return;
			}
			if (this.lock) {
				return;
			}
			this.lock = true;
			if (this.choose == 0) {
				this.page++
			} else {
				this.page2++
			}
			this.getGoodsList(this.choose, 0)
		},
		methods: {
			toSearch() {
				uni.navigateTo({
					url: '/pages/search/search'
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
			login() {
				if (!this.isLogin()) {
					uni.navigateTo({
						url: '../../pages/login/login'
					})
				}
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
			arraySeparate(array) {
				let that = this
				for (let i = 0; i < array.length; i++) {
					this.detailArray[i % 2].push(array[i])
				}
				this.$nextTick(function() {
					uni.createSelectorQuery().select('.thrid-place').boundingClientRect(function(data) {
						if (data != null) {
							that.forthPlaceHeight = that.modelHeight < data.bottom ? 0 : that.modelHeight -
								data.bottom
						}
					}).exec();
				})
			},
			getGoodsList(choose, ifClear) {
				let data = {
					"ensuiteType": choose ? this.secondGoodsArray[choose][2][this.selectedSecond2[2]] : null,
					"goodsType": choose ? null : this.secondGoodsArray[choose][0][this.selectedSecond[0]],
					"houseType": choose ? this.secondGoodsArray[choose][1][this.selectedSecond2[1]] : null,
					"howNewType": choose ? null : this.secondGoodsArray[choose][1][this.selectedSecond[1]],
					"locationType": choose ? this.secondGoodsArray[choose][0][this.selectedSecond2[0]] : null,
					"pickUpType": choose ? null : this.secondGoodsArray[choose][2][this.selectedSecond[2]],
					"priceType": choose ? this.secondGoodsArray[choose][3][this.selectedSecond2[3]] : null,
					"secondHandType": choose ? '租房' : '闲置',
					"tenancyType": choose ? this.secondGoodsArray[choose][4][this.selectedSecond2[4]] : null,
					"page": choose ? this.page2 : this.page
				}
				if (ifClear == 1) {
					this.$refs.loading.showLoading();
				}
				uni.request({
					url: this.$serverUrl + '/goods/getGoodsList',
					method: 'POST',
					data: data,
					header: {
						"Content-Type": "application/json"
					},
					success: (res) => {
						if (res.data.code == 200) {
							if (ifClear == 0) {
								if ((choose ? this.page2 : this.page) > res.data.data.total) {
									if (choose == 0) {
										this.page = res.data.data.total
									} else {
										this.page2 = res.data.data.total
									}
									return
								}
							}
							if (choose == this.choose) {
								if (ifClear == 1) {
									this.detailArray = [
										[],
										[]
									]
								}
								this.arraySeparate(res.data.data.rows)
								uni.setStorageSync(choose ? 'detailArrayHouse' : 'detailArrayGoods', this
									.detailArray)
							} else {
								let a = [
									[],
									[]
								]
								for (let i = 0; i < res.data.data.rows.length; i++) {
									a[i % 2].push(res.data.data.rows[i])
								}
								uni.setStorageSync(choose ? 'detailArrayHouse' : 'detailArrayGoods', a)
							}
							if (ifClear == 0) {
								clearTimeout(this.func)
								this.lock = false
							}
						}
						this.$refs.loading.hideLoading();
					},
					complete: (res) => {
						if (ifClear == 0) {
							this.func = setTimeout(() => {
								this.lock = false;
							}, 2000);
						}
						this.$refs.loading.hideLoading();
					}
				});
			},
			selectGoods(index) {
				if (!this.isLogin()) {
					return;
				}
				if (this.choose != index) {
					this.choose = index
					this.changeStr()
					let dArray = uni.getStorageSync(this.choose ? 'detailArrayHouse' : 'detailArrayGoods')
					if (dArray != "" && dArray != null) {
						this.detailArray = dArray
						let that = this
						this.$nextTick(function() {
							uni.createSelectorQuery().select('.thrid-place').boundingClientRect(function(data) {
								if (data != null) {
									that.forthPlaceHeight = that.modelHeight < data.bottom ? 0 : that
										.modelHeight - data.bottom
								}
							}).exec();
						})
						return
					}
					if (this.choose == 0) {
						this.page = 1
					} else {
						this.page2 = 1
					}
					this.getGoodsList(this.choose, 1)
				}
			},
			changeSelectedSecond(index, index2) {
				if (!this.isLogin()) {
					return;
				}
				if (this.choose) {
					if (this.selectedSecond2_[index] == index2) {
						return;
					}
					this.$set(this.selectedSecond2_, index, index2)
				} else {
					if (this.selectedSecond_[index] == index2) {
						return;
					}
					this.$set(this.selectedSecond_, index, index2)
				}
			},
			openFilter() {
				if (!this.isLogin()) {
					return;
				}
				this.isOpen = 1 - this.isOpen
				clearTimeout(this.timeoutFunc3)
				if (this.isOpen == 1) {
					this.timeoutFunc3 = setTimeout(() => {
						this.openani = 1
					}, 100)
				} else {
					this.openani = 0
				}
				this.selectedSecond2_ = this.selectedSecond2.slice(0)
				this.selectedSecond_ = this.selectedSecond.slice(0)
			},
			reset() {
				if (!this.isLogin()) {
					return;
				}
				if (this.choose) {
					this.selectedSecond2_ = [0, 0, 0, 0, 0]
				} else {
					this.selectedSecond_ = [0, 0, 0]
				}
			},
			confirm() {
				if (!this.isLogin()) {
					return;
				}
				this.selectedSecond = this.selectedSecond_.slice(0)
				this.selectedSecond2 = this.selectedSecond2_.slice(0)
				this.isOpen = 0
				this.openani = 0
				this.changeStr()
				if (this.choose == 0) {
					this.page = 1
				} else {
					this.page2 = 1
				}
				this.getGoodsList(this.choose, 1)
			},
			changeStr() {
				let str = ""
				if (this.choose) {
					for (let i = 0; i < this.selectedSecond2.length; ++i) {
						if (this.selectedSecond2[i] != 0) {
							str = str + this.secondGoodsArray[this.choose][i][this.selectedSecond2[i]] + "、"
						}
					}
				} else {
					for (let i = 0; i < this.selectedSecond.length; ++i) {
						if (this.selectedSecond[i] != 0) {
							str = str + this.secondGoodsArray[this.choose][i][this.selectedSecond[i]] + "、"
						}
					}
				}
				if (str.length > 4) {
					str = str.slice(0, 4) + "..."
				}
				if (str[str.length - 1] == "、") {
					str = str.slice(0, str.length - 1)
				}
				if (str == "") {
					str = "高级筛选"
				}
				this.filterTxt = str
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

	.second-place {
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: flex-end;
		z-index: 5;
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

	.thrid-place {
		box-sizing: border-box;
		padding-bottom: 170rpx;
		padding-top: 110rpx;
		display: flex;
		flex-direction: row;
		width: 100%;
		align-content: flex-start;
		justify-content: space-evenly;
		background-color: #FAFAFA;
	}

	.thrid-place-half {
		width: 45%;
		display: flex;
		flex-direction: column;
	}

	.selectionBar {
		position: relative;
		height: 76rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 188rpx;
		border-radius: 0 0 16px 16px;
		background-color: #FFFFFF;
		transition: all 0.2s ease-in-out;
		box-shadow: 0px 1px 2px rgba(24, 48, 51, 0.1);
	}

	.selectionBar.active {
		height: 844rpx;
		width: 100%;
		transition: all 0.2s ease-in-out;
	}

	.recimg {
		display: inline-block;
		margin-left: 10rpx;
		width: 16rpx;
		height: 16rpx;
		background: url(../../static/rec.png) center/80% 80% no-repeat;
		transition: all 0.5s ease-in-out;
	}

	.recimg.active {
		transform: rotate(180deg);
		transition: all 0.5s ease-in-out;
		filter: drop-shadow(0 0 0 #1F9DA6);
	}

	.selectionSecond {
		width: 100%;
		opacity: 0;
		transition: all .2s;
		margin-top: 85rpx;
		border-top: 1px solid rgba(24, 48, 51, 0.1);
		padding-top: 32rpx;
		box-sizing: border-box;
		overflow-y: scroll;
		height: 642rpx;
	}

	.selectionSecond.active {
		opacity: 1;
		transition: all .2s;
	}

	.border {
		margin-left: 25rpx;
		margin-right: 25rpx;
		writing-mode: horizontal-tb;
	}

	.filter {
		position: absolute;
		right: 0;
		top: 0;
		color: rgba(0, 0, 0, 0.6);
		padding-left: 10rpx;
		padding-right: 10rpx;
		box-sizing: border-box;
		margin: 16rpx 23rpx;
		height: 45rpx;
		font-size: 24rpx;
		white-space: nowrap;
		text-align: right;
		display: flex;
		align-items: center;
	}

	.beBlue {
		color: #499199;
	}

	.labelbox {
		display: flex;
		flex-direction: column;
	}

	.labelbox-in {
		display: flex;
		flex-direction: row;
		margin-bottom: 32rpx;
	}

	.scroll {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		margin-left: 30rpx;
		white-space: nowrap;
	}

	.smallbox {
		background: rgba(0, 0, 0, 0.06);
		border-radius: 12rpx;
		height: 64rpx;
		width: 140rpx;
		margin-right: 16rpx;
		display: flex;
		align-items: center;
		color: rgba(0, 0, 0, 0.6);
		justify-content: center;
		margin-bottom: 16rpx;
	}

	.smallbox.select {
		background-color: #DCF1F3;
		color: #499199 !important;
		font-weight: 900;
	}

	.reset {
		width: 36%;
		height: 96rpx;
		font-size: 32rpx;
		line-height: 96rpx;
		text-align: center;
		border-radius: 0px 0px 0px 16px;
		box-shadow: 0px -1px 0px rgba(0, 0, 0, 0.1);
	}

	.confirm {
		width: 64%;
		height: 96rpx;
		font-size: 32rpx;
		line-height: 96rpx;
		text-align: center;
		background: #5DB8C2;
		box-shadow: 0px -1px 0px rgba(0, 0, 0, 0.1);
		border-radius: 0px 0px 16px 0px;
		color: #FFFFFF;
		font-weight: 900;
	}

	.circles {
		position: fixed;
		width: 226px;
		height: 226px;
		left: 287px;
		z-index: 120;
		top: -67px;
		border-radius: 50%;
		background: radial-gradient(95.04% 95.04% at 50% 50%, rgba(186, 237, 242, 0.06) 0%, rgba(186, 237, 242, 0.7) 100%);
	}
</style>
