<template>
	<view>
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">
				选择区域
			</view>
			<view class="backicon" @click="back('请选择')" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="XZQY">
			<view class="xzqy">请选择你所在的区域</view>
		</view>
		<view class="Label">
			<view v-for="(item,index) in dataList" class="Label-box" :key="index" @click="select(index)" :class="{'select':index==beSelected}">
				<view class="Label-font" :class="{'select':index==beSelected}">{{item}}</view>
			</view>
		</view>
		<view class="bottomplace">
			<view class="updatebotton" :class="{'isSelected':beSelected!='-1'}" @click="back(dataList[beSelected])">确定</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				beSelected: '-1',
				dataList: ["市中心", "Cowley", "Botley", "Hinksey", "Summertown", "Headington", "其他"],
				titleBarTop: 0
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		onLoad(option) {
			for (let i = 0; i < this.dataList.length; i++) {
				if (this.dataList[i] == option.area) {
					this.beSelected = i
					return
				}
			}
		},
		methods: {
			select(e) {
				if (this.beSelected == e) {
					this.beSelected = '-1'
					return
				}
				this.beSelected = e
			},
			back(e) {
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2];
				if (e != '请选择') {
					prevPage.$vm.area = e;
				}
				uni.navigateBack({})
			}
		}
	}
</script>

<style scoped>
	.topNavigationBar {
		z-index: 5;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		text-align: center;
		height: 180rpx;
		background-color: #5DB8C2;
		color: #ffffff;
		font-size: 32rpx;
	}

	.backicon {
		position: absolute;
		width: 30rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/85% 85% no-repeat;
	}

	.XZQY {
		margin-top: 180rpx;
		width: 100%;
		height: 60rpx;
		display: flex;
		margin-bottom: 40rpx;
	}

	.xzqy {
		color: #a8a8a8;
		font-size: 25rpx;
		margin-left: 30rpx;
		margin-top: 30rpx;
	}

	.Label {
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		margin-left: 30rpx;
	}

	.Label-box {
		background-color: #f0f0f0;
		height: 60rpx;
		margin-right: 40rpx;
		margin-bottom: 40rpx;
		display: flex;
		align-items: center;
		border-radius: 10rpx;
	}

	.Label-box.select {
		background-color: #dcf1f3;
	}

	.Label-font {
		font-size: 28rpx;
		margin-left: 30rpx;
		margin-right: 30rpx;
		color: #939393;
	}

	.Label-font.select {
		font-size: 31rpx;
		color: #5b9ca3;
	}


	.updatebotton.isSelected {
		background-color: #48d1e0;
	}

	.bottomplace {
		position: fixed;
		width: 100%;
		height: 150rpx;
		bottom: 0rpx;
		left: 0rpx;
		box-shadow: 0rpx -2rpx 20rpx rgba(230, 230, 230, 0.4), 0rpx -2rpx 0rpx rgba(0, 0, 0, 0.06);
		border-radius: 32rpx 32rpx 0rpx 0rpx;
		background-color: #ffffff;
		z-index: 5;
	}

	.updatebotton {
		position: absolute;
		width: 90%;
		height: 90rpx;
		left: 5%;
		top: 15rpx;
		background: #98f0f7;
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 34rpx;
		color: #FFFFFF;
	}
</style>
