<template>
	<view class="content">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height+'px'}" @click="back">
				</view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" confirm-type='search' placeholder="根据标签搜索"
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-45)+'px'}"
					v-model="content" @confirm='search' maxlength="30">
				<view class="cancel" :style="{left:(titleBar.left-titleBar.height*1.4)+'px'}" @click="cancel"
					v-if="content!=''"></view>
			</view>
		</uni-nav-bar>
		<view class="tags">
			<view
				style="margin-left:32rpx;font-size:28rpx;font-weight:bold;letter-spacing:0.140769rpx;line-height:39rpx;">
				#话题
			</view>
			<view style="width: 100%;padding: 38rpx 21rpx;display: flex;flex-wrap: wrap;box-sizing: border-box;">
				<view class="tagDetail" v-for="(item, index) in tags" :key="index" @click="addContent(item)"
					:style="{color: 'rgb(' + $tagsColorMap.get(item) + ')', backgroundColor: 'rgba(' + $tagsColorMap.get(item) + ', 0.1)'}">
					{{item}}
				</view>
			</view>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content: '',
				titleBar: null,
				tags: []
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		onLoad(option) {
			this.content = option.content
			this.$refs.loading.showLoading();
			uni.request({
				url: this.$serverUrl + '/community/getCommunityTags',
				method: "POST",
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.tags = res.data.data
						this.$refs.loading.hideLoading();
					}
				},
				complete: (res) => {
					setTimeout(() => {
						this.$refs.loading.hideLoading();
					}, 2000)
				}
			})
		},
		methods: {
			back() {
				uni.navigateBack({})
			},
			cancel() {
				this.content = ''
			},
			search() {
				uni.navigateTo({
					url: '/pages/searchResult1/searchResult1?content=' + this.content
				});
			},
			addContent(item) {
				if (this.content.includes('#' + item)) {
					this.content = this.content.replace(' #' + item, '')
					this.content = this.content.replace('#' + item + ' ', '')
					this.content = this.content.replace('#' + item, '')
				} else {
					this.content = this.content + ' #' + item
				}
				this.content = this.content.trim()
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

	.searchIcon {
		width: 28px;
		display: flex;
		justify-content: center;
		border-top-left-radius: 50rpx;
		border-bottom-left-radius: 50rpx;
		align-items: center;
		background: #EBF2F2 url(../../static/search.png) center/60% no-repeat;
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

	.logo {
		margin-left: 10px;
		margin-right: 10px;
		background: url(../../static/back.png) center/40% 70% no-repeat;
		filter: brightness(0%);
	}

	.cancel {
		background: url(../../static/cancel.png) center/100% 100% no-repeat;
		width: 35rpx;
		height: 35rpx;
		position: absolute;
		z-index: 99;
	}

	.tags {
		width: 100%;
		margin-top: 30rpx;
		display: flex;
		flex-direction: column;
	}

	.tagDetail {
		width: 213rpx;
		height: 99rpx;
		margin-left: 11rpx;
		margin-right: 11rpx;
		line-height: 99rpx;
		text-align: center;
		font-size: 28rpx;
		letter-spacing: 0.140769rpx;
		margin-bottom: 20rpx;
	}
</style>
