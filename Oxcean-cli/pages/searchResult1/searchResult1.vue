<template>
	<view class="content">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height+'px'}" @click="back('')">
				</view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" confirm-type='search' placeholder="根据标签搜索"
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-45)+'px'}"
					disabled @click="back(content)" :value="content">
				</input>
			</view>
		</uni-nav-bar>
		<view v-if="dataList.length==0" :style="{top:navHeight+'px', height: 'calc(100% - '+navHeight+'px)'}"
			style="width: 100%;position: absolute;left:0;background: #F5F7F7;">
			<image src="../../static/none.png" mode="aspectFit" style="margin-top: 400rpx;width: 100%;height:240rpx;">
			</image>
			<view style="width: 100%; text-align: center;color: rgba(0, 0, 0, 0.4);font-size: 28rpx;">暂无帖子</view>
		</view>
		<view class="thrid-place" v-if="dataList.length>0"
			:style="{top:navHeight+'px', minHeight: 'calc(100% - '+navHeight+'px)'}">
			<view v-for="(item,index) in dataList" class="label" :key="index" @click="select(index)"
				style="position: relative;">
				<view style="display: flex; margin-top: 32rpx; margin-left: 32rpx; align-items: center;">
					<image :src="item.avatarUrl==null?'../../static/none3.png':item.avatarUrl"
						style="height: 56rpx; width: 56rpx; border-radius: 50%; align-items: center;"></image>
					<view
						style="margin-left: 17rpx; font-size: 24rpx; letter-spacing: 0.140769rpx; color: rgba(0, 0, 0, 0.6);">
						{{item.username==null?"某同学":item.username}}
					</view>
				</view>
				<view class="Box"
					style="margin-left: 32rpx; margin-right: 32rpx; margin-top: 28rpx;margin-bottom: 16rpx; font-size: 28rpx; line-height: 48rpx;letter-spacing: 0.140769rpx;word-wrap: break-word;word-break: break-all;">
					{{item.content}}
				</view>
				<view style="display: flex;">
					<view v-for="(item2, index2) in item.itsTags.split(';').slice(1, -1)" :key="index2" class="tag"
						@click.stop="searchByTag(item.itsTags, index2)"
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
		</view>
		<view style="width: 100%;height: 60rpx; position: fixed;bottom: 0;left: 0;background: #F5F7F7;"></view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				lock: false,
				content: '',
				titleBar: null,
				navHeight: 0,
				page: 1,
				total: 1,
				dataList: [],
				lock2: false,
				func: null,
				func2: null
			}
		},
		onReady() {
			let that = this;
			const query = uni.createSelectorQuery()
			setTimeout(() => {
				query.select('.navBar').boundingClientRect(function(data) {
					that.navHeight = data.height
				}).exec();
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		onLoad(option) {
			this.content = option.content
			this.flash()
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
		onReachBottom() {
			this.flash()
		},
		onShareAppMessage(res) {
			let textObj = JSON.stringify(this.dataList[Number(res.target.id)])
			return {
				title: uni.getStorageSync('userInfo').username + "给你分享了一个话题",
				path: "pages/detailTopic/detailTopic?info=" + textObj + '&index=0',
				imageUrl: '../../static/none2.png'
			}
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
				this.content = '#' + itsTags.split(';').slice(1, -1)[index]
				this.page = 1
				this.total = 1
				this.dataList = []
				this.flash()
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
			back(content) {
				let pages = getCurrentPages()
				let prevPage = pages[pages.length - 2];
				if (prevPage.route == 'pages/search1/search1') {
					prevPage.$vm.content = content
					uni.navigateBack({})
				} else if (prevPage.route == 'pages/community/community') {
					if (content == '') {
						uni.navigateBack({})
					} else {
						uni.redirectTo({
							url: '/pages/search1/search1?content=' + content,
						})
					}
				}
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
				this.func2 = setTimeout(() => {
					this.lock = false;
					this.$refs.loading.hideLoading();
				}, 3000);
				let searchTags = this.content.split('#').slice(1, 4)
				for (let i = 0; i < searchTags.length; i++) {
					searchTags[i] = searchTags[i].trim()
				}
				uni.request({
					url: this.$serverUrl + '/community/searchCommunity',
					method: 'POST',
					data: {
						page: this.page,
						userId: uni.getStorageSync('userInfo').id,
						searchTags: searchTags
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							this.dataList.push(...res.data.data.rows)
							this.total = res.data.data.total
							this.page++
							this.lock = false
							this.$refs.loading.hideLoading();
							clearTimeout(this.func2)
						}
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
		flex-direction: column;
		overflow-x: hidden;
		background: #F5F7F7;
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
		padding-bottom: 60rpx;
		width: 100%;
		background-color: #F5F7F7;
		position: absolute;
		left: 0;
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

	.tag {
		font-size: 28rpx;
		letter-spacing: 0.140769rpx;
		line-height: 33rpx;
		margin-left: 32rpx;
		margin-bottom: 20rpx;
		margin-top: 10rpx;
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

	.navBar {
		box-shadow: 0rpx 2rpx 12rpx rgba(0, 0, 0, 0.08);
	}
</style>
