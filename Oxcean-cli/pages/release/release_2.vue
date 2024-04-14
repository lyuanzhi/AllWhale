<template>
	<view class="background">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}">
				发布租房
			</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="inputname">
			<view class="font">房源名称</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<input type="text" style="margin-left: 10rpx; width: 70%; font-size: 28rpx;" @change="chan"
				placeholder="请输入房源名称（25字以内)" v-model="name">
		</view>
		<view class="inputdetail">
			<view class="font">补充信息</view>
			<textarea placeholder="说说物品的详细信息，例如型号、入手渠道、使用感受、转手原因" v-model="detail" maxlength="200"
				style="width: 70%; margin-left: 25rpx; margin-top: 5rpx; margin-bottom: 20rpx;" />
		</view>
		<view class="pics">
			<view v-for="(item,index) in imgArr" :key='index' style="position: relative;">
				<image style="margin-left: 40rpx; margin-bottom: 40rpx; " :src="item" mode="aspectFill">
				</image>
				<view class="image-remover" @click="removeImage(index)">
					<view class="deleteicon"></view>
				</view>
			</view>
			<view v-if="imgArr.length<=5" class="uploadiocn" @click="update">+</view>
		</view>
		<view class="line"></view>
		<view class="label" @click="expand">
			<view class="font" style="margin-left: 50rpx;">房型/卫浴/租期</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<view class="labelicon" :class="{'active':isExpend}"></view>
		</view>
		<view class="labelbox" v-if="isExpend">
			<view class="labelbox-in">
				<view style="font-size: 28rpx; font-weight: 600; white-space: nowrap; line-height: 56rpx;">
					房型
				</view>
				<view class="scroll">
					<view v-for="(items,index) in labels.roomType" :key='index' @click="changeSelect(index,0)">
						<view class="smallbox" :class="{'select':selectLabel[0]==index}">
							<view class="border">{{items}}</view>
						</view>
					</view>
				</view>
			</view>
			<view class="labelbox-in">
				<view style="font-size: 28rpx; font-weight: 600; white-space: nowrap; line-height: 56rpx">
					卫浴
				</view>
				<view class="scroll">
					<view v-for="(items,index) in labels.bathroom" :key='index' @click="changeSelect(index,1)">
						<view class="smallbox" :class="{'select':selectLabel[1]==index}">
							<view class="border">{{items}}</view>
						</view>
					</view>
				</view>
			</view>
			<view class="labelbox-in">
				<view style="font-size: 28rpx; font-weight: 600; white-space: nowrap; line-height: 56rpx">
					租期
				</view>
				<view class="scroll">
					<view v-for="(items,index) in labels.tenancy" :key='index' @click="changeSelect(index,2)">
						<view class="smallbox" :class="{'select':selectLabel[2]==index}">
							<view class="border">{{items}}</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">房租 (£/周)</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<input type="digit" cursor-spacing="13"
				style="margin-left: 4%; width: 60%; text-align: right; font-size: 32rpx; color: #ff0000;"
				placeholder="请输入" v-model="currentprice" maxlength="10" @blur="checkCurrentprice">
		</view>
		<view class="line"></view>

		<view class='inputbox' @click='selectlocation'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">房源位置</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<view style="height: 40rpx;width: 55%; text-align: right; font-size: 32rpx; color: #686868;" v-model="area"
				:style="{marginLeft:area=='请选择'?'10%':'15%'}">
				{{area}}
			</view>
			<view class="littleIcon" v-if="area=='请选择'">
			</view>
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 30rpx; white-space: nowrap;">联系方式</view>
			<view style="color:#a7a7a7; white-space: nowrap; font-size: 24rpx; ">（可对意向买家一键发送）</view>
			<input type="text" style=" width: 36%; text-align: right; font-size: 32rpx; color: #000000;"
				placeholder="请输入" v-model="wxcode" cursor-spacing="13" maxlength="30">
		</view>
		<view style="margin-bottom: 200rpx;"></view>
		<view class='bottomplace'>
			<view class="updatebotton" @click="upload()"
				:class="{'all':name!=''&&selectLabel[1]!='-1'&&selectLabel[0]!='-1'&&selectLabel[2]!='-1'&&currentprice!=''&&area!='请选择'&&imgLength!=0}">
				发布</view>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: '',
				detail: '',
				imgArr: [],
				isExpend: 1,
				labels: {
					roomType: ['Flat', 'Studio', 'House'],
					bathroom: ['独卫', '非独卫'],
					tenancy: ['<7天', '7-30天', '1-3月', '>3月']
				},
				selectLabel: ['-1', '-1', '-1'],
				currentprice: '',
				area: '请选择',
				wxcode: '',
				all: 0,
				titleBarTop: 0,
				lock: false,
				func2: null,
				num: 0
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		onLoad() {
			let wxNum = uni.getStorageSync('wxNumber')
			if (wxNum != null && wxNum != "") {
				this.wxcode = wxNum
			}
		},
		watch: {
			num() {
				if (this.num == this.imgArr.length + 1) {
					uni.$emit('flushGoodsPage', 1)
					clearTimeout(this.func2)
					this.$refs.loading.hideLoading();
					this.back()
				}
			}
		},
		computed: {
			imgLength() {
				return this.imgArr.length
			}
		},
		methods: {
			chan() {
				if (this.name.length < 26) return
				this.name = this.name.substr(0, 25)
			},
			checkCurrentprice() {
				if (!(/(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/.test(this.currentprice))) {
					this.currentprice = ""
					uni.showToast({
						icon: "none",
						title: "请正确输入价格(最多2位小数)",
						duration: 2000
					})
				}
			},
			back() {
				uni.navigateBack({})
			},
			selectlocation() {
				uni.navigateTo({
					url: '/pages/location/location?area=' + this.area
				});
			},
			changeSelect(index, e) {
				if (this.selectLabel[e] == index) {
					this.$set(this.selectLabel, e, '-1')
					return
				}
				this.$set(this.selectLabel, e, index)

			},
			expand() {
				this.isExpend = !this.isExpend
			},
			update() {
				let that = this
				if (that.imgArr.length > 5) {
					return;
				}
				uni.chooseImage({
					count: 6,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(res) {
						if (that.imgArr.length + res.tempFilePaths.length > 6) {
							uni.showToast({
								icon: "none",
								title: "只能上传6张照片",
								duration: 2000
							})
							return;
						}
						for (let i = 0; i <= res.tempFilePaths.length - 1; i++) {
							that.imgArr.push(res.tempFilePaths[i])
						}
					}
				});
			},
			removeImage(e) {
				this.imgArr.splice(e, 1)
			},
			previewImage(current) {
				uni.previewImage({
					current,
					urls: this.imgArr
				})
			},
			upload() {
				if ((this.name != '' && this.selectLabel[1] != '-1' && this.selectLabel[0] != '-1' &&
						this.selectLabel[2] != '-1' && this.currentprice != '' && this.area != '请选择') == false) {
					uni.showToast({
						icon: "none",
						title: "请把信息填写完整",
						duration: 2000
					})
					return;
				}
				if (this.imgLength == 0) {
					uni.showToast({
						icon: "none",
						title: "请至少添加一张图片",
						duration: 2000
					})
					return;
				}
				if(this.detail.length > 200){
					this.detail = this.detail.substr(0, 200)
				}
				if (this.lock) {
					return
				}
				this.lock = true
				this.$refs.loading.showLoading();
				uni.request({
					url: this.$serverUrl + '/goods/getGoodsId',
					method: "POST",
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							for (let i = 0; i < this.imgArr.length; i++) {
								uni.uploadFile({
									url: 'https://lyuanzhi.com:8080/goods/addGoodsImgs',
									filePath: this.imgArr[i],
									name: 'uploadImg',
									formData: {
										'index': i,
										'id': res.data.data
									},
									success: (res) => {
										this.num++
									},
									complete: (res) => {
										if (i == this.imgArr.length - 1) {
											this.func2 = setTimeout(() => {
												this.lock = false
												this.$refs.loading.hideLoading();
												this.num = 0
											}, 8000)
										}
									}
								})
							}

							let data = {
								"createTime": -1,
								"ensuiteType": this.labels.bathroom[this.selectLabel[1]],
								'detailedDesc': this.detail,
								// "goodsType": this.labels.category[this.selectLabel[0]],
								"houseType": this.labels.roomType[this.selectLabel[0]],
								// "howNewType": this.labels.condition[this.selectLabel[1]],
								"id": res.data.data,
								"imgNumber": this.imgArr.length,
								"isCancelled": -1,
								"locationType": this.area,
								"name": this.name,
								"price": parseInt(this.currentprice),
								"priceType": this.currentprice <= 150 ? '<£150' : this.currentprice >
									150 && this.currentprice <= 200 ? '£150-£200' : this.currentprice >
									200 && this.currentprice <= 250 ? '£200-£250' : '>£250',
								"secondHandType": "租房",
								"tenancyType": this.labels.tenancy[this.selectLabel[2]],
								"userId": uni.getStorageSync('userInfo').id,
								"wxNumber": this.wxcode,
							}
							uni.request({
								url: this.$serverUrl + '/goods/addGoods',
								method: "POST",
								data: data,
								header: {
									"Content-Type": "application/json"
								},
								success: (res) => {
									if (res.data.code == 200) {
										this.num++
										uni.setStorageSync('wxNumber', this.wxcode)
									}
								},
								fail: (res) => {

								}
							})
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
	input {
		height: 42rpx;
		caret-color: #acacac;
	}

	.pics {
		display: flex;
		flex-wrap: wrap;
		width: 100%;
	}

	textarea {
		font-size: 28rpx;
		height: 150rpx;
		caret-color: #acacac;
	}

	image {
		height: 200rpx;
		width: 200rpx;
		border-radius: 12rpx;
	}

	.inputbox {
		display: flex;
		flex-direction: row;
		align-items: center;
		height: 100rpx;
	}

	.backicon {
		position: absolute;
		width: 30rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/85% 85% no-repeat;
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

	.updatebotton.all {
		background: #48D1E0;
	}

	.scroll {
		overflow: hidden;
		overflow-x: scroll;
		display: flex;
		align-items: center;
		flex-wrap: nowrap;
		margin-left: 50rpx;
		white-space: nowrap;
	}

	.border {
		font-size: 28rpx;
		margin-left: 25rpx;
		margin-right: 25rpx;
		writing-mode: horizontal-tb;
	}

	.smallbox {
		background: rgba(0, 0, 0, 0.06);
		border-radius: 100rpx;
		margin-right: 30rpx;
		height: 50rpx;
		display: flex;
		align-items: center;
		color: rgba(0, 0, 0, 0.6);
	}

	.smallbox.select {
		background-color: #DCF1F3;
		color: #499199 !important;
	}

	.background {
		padding: 0;
		margin: 0;
		width: 100%;
		display: flex;
		flex-direction: column;
	}

	.font {
		color: #499199;
		font-size: 28rpx;
	}

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

	.selectWay {
		display: flex;
		align-items: center;
		font-size: 24rpx;
		margin-top: 50rpx;
		margin-left: 50rpx;
		width: 35%;
		height: 54rpx;
		border: 2rpx solid #48D1E0;
		box-sizing: border-box;
		border-radius: 32rpx;
		position: relative;
		color: #499199;
		margin-top: 220rpx;
	}

	.selectCover {
		left: -1%;
		position: absolute;
		background-color: #48D1E0;
		border-radius: 32rpx;
		width: 55%;
		height: 105%;
		z-index: -1;
		transition: all .4s ease-in-out;
	}

	.selectCover.active {
		left: auto;
		transform: translateX(95%);
		transition: all .4s ease-in-out;
	}

	.inputname {
		width: 80%;
		margin-left: 45rpx;
		margin-top: 250rpx;
		display: flex;
		align-items: center;
	}

	.inputdetail {
		width: 90%;
		margin-left: 45rpx;
		margin-top: 55rpx;
		display: flex;
		align-items: flex-start;
	}

	.uploadiocn {
		width: 200rpx;
		height: 200rpx;
		background: rgba(0, 0, 0, 0.06);
		display: flex;
		justify-content: center;
		font-size: 100rpx;
		align-items: center;
		margin-left: 40rpx;
		color: #C4C4C4;
		margin-bottom: 40rpx;
		border-radius: 12rpx;
	}

	.image-remover {
		position: absolute;
		background-color: #007AFF;
		width: 50rpx;
		height: 50rpx;
		top: -10rpx;
		right: -10rpx;
		background: #5DB8C2;
		border-radius: 50%;
		border: 4rpx solid #FFFFFF;
		box-sizing: border-box;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.deleteicon {
		background: url(../../static/x.png) center/50% 50% no-repeat;
		width: 100%;
		height: 100%;
	}

	.line {
		width: 90%;
		border: 3rpx solid rgba(0, 0, 0, 0.06);
		margin-left: 5%;
	}

	.label {
		height: 100rpx;
		display: flex;
		align-items: center;
	}

	.labelicon {
		width: 45rpx;
		height: 45rpx;
		background: url(../../static/labelicon.png) center/60% 60% no-repeat;
		margin-left: 53%;
		transition: all .3s ease-in-out;
	}

	.labelicon.active {
		transform: rotate(-180deg);
		transition: all .3s ease-in-out;
	}

	.labelbox {
		display: flex;
		flex-direction: column;
	}

	.labelbox-in {
		margin-bottom: 40rpx;
		margin-left: 100rpx;
		display: flex;
		flex-direction: row;
	}

	.littleIcon {
		background: url(../../static/labelicon.png) center/60% 60% no-repeat;
		transform: rotate(-90deg);
		width: 45rpx;
		height: 45rpx;
		margin-top: 4rpx;
	}
</style>
