<template>
	<view class="content">
		<uni-nav-bar class="navBar" :fixed="true" :status-bar="true">
			<view v-if="titleBar" class="bar" :style="{top:titleBar.top+'px',height:titleBar.height+'px'}">
				<view class="logo" :style="{height:titleBar.height+'px', width:titleBar.height+'px'}" @click="back()">
				</view>
				<view class="searchIcon" :style="{height:(titleBar.height-2)+'px'}"></view>
				<input type="text" class="navBarSearch" placeholder="搜索" confirm-type='search'
					:style="{height:(titleBar.height-2)+'px', width:(titleBar.left-titleBar.height*1.5-45)+'px'}"
					v-model="content" @confirm='search' maxlength="30">
				<view class="cancenl" :style="{left:(titleBar.left-titleBar.height*1.4)+'px'}" @click="cancel" v-if="content!=''"></view>
			</view>
		</uni-nav-bar>
		<view class="history">
			<view
				style="margin-left: 32rpx ; font-size: 28rpx; font-weight: bold; display: flex; justify-content: space-between;">
				历史搜索
				<view class="bin" @click="deleteHistory()"></view>
			</view>
			<view class="Label" v-if="historyList.length>0">
				<view v-for="(item,index) in historyList" class="Label-box" :key="index" @click="select(index)">
					<view class="Label-font">{{chan(item)}}</view>
				</view>
			</view>
			<view v-if="historyList.length==0"
				style="margin-left: 32rpx; color: rgba(0, 0, 0, 0.6); font-size: 24rpx; margin-top: 30rpx;">
				暂无搜索记录
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content: '',
				titleBar: null,
				historyList: []
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBar = uni.getMenuButtonBoundingClientRect();
			}, 0);
		},
		onLoad() {
			this.historyList = uni.getStorageSync('historySearch')
		},

		methods: {
			back() {
				uni.navigateBack({})
			},
			deleteHistory() {
				uni.setStorageSync('historySearch', [])
				this.historyList = []
			},
			cancel() {
				this.content = ''
			},
			chan(value) {
				if (value.length < 25)
					return value
				return value.substr(0, 23) + "..."
			},
			search() {
				uni.navigateTo({
					url: '/pages/searchResult/searchResult?content=' + this.content
				});
				let a = uni.getStorageSync('historySearch')
				if (a == null || a == '') {
					a = []
				}
				let repeat = 0;
				for (let i = 0; i <= a.length; i++) {
					if (this.content == a[i]) {
						repeat = 1;
						break
					}
				}
				if (!repeat && this.content != '') {
					a.unshift(this.content)
					if (a.length >= 15) {
						a.splice(15, 1)
					}
					uni.setStorageSync('historySearch', a)
				}
				this.historyList = a
			},
			select(index) {
				this.content = this.historyList[index]
				this.search()
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

	.Label {
		margin: 30rpx;
		display: flex;
		flex-wrap: wrap;
	}

	.Label-font {
		font-size: 24rpx;
		margin-left: 30rpx;
		margin-right: 30rpx;
	}

	.history {
		width: 100%;
		margin-top: 30rpx;
		display: flex;
		flex-direction: column;
	}

	.Label-box {
		border: 1rpx solid rgba(0, 0, 0, 0.12);
		height: 50rpx;
		margin-right: 20rpx;
		margin-bottom: 40rpx;
		display: flex;
		align-items: center;
		border-radius: 200rpx;
	}

	.cancenl {
		background: url(../../static/cancel.png) center/100% 100% no-repeat;
		width: 35rpx;
		height: 35rpx;
		position: absolute;
		z-index: 99;
	}

	.bin {
		background: url(../../static/bin.png) center/100% 100% no-repeat;
		width: 26rpx;
		height: 26rpx;
		margin-right: 30rpx;
	}
</style>
