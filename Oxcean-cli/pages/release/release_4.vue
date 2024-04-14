<template>
	<view class="background">
		<view class="topNavigationBar">
			<view :style="{marginTop:(titleBarTop+6)+'px'}" style="font-weight: 900; letter-spacing: 0.02em;">
				发布活动
			</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="inputname">
			<view class="font">活动标题</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<input type="text" style="margin-left: 10rpx; width: 70%; font-size: 28rpx;" @change="chan"
				placeholder="请概括活动内容（25字以内)" v-model="name"
				placeholder-style="color:#rgba(0, 0, 0, 0.4);font-size:24rpx;letter-spacing: 0.140769rpx">
		</view>
		<view class="inputdetail">
			<view class="font">内容简述</view>
			<textarea placeholder="说说活动的详细信息，例如具体行程、时间安排、活动条件和要求" v-model="detail" maxlength="200"
				style="width: 70%; margin-left: 25rpx; margin-top: 5rpx; margin-bottom: 20rpx;"
				placeholder-style="color:#rgba(0, 0, 0, 0.4);font-size:24rpx;letter-spacing: 0.140769rpx" />
		</view>
		<view class="pics">
			<view v-for="(item,index) in imgArr" :key='index' style="position: relative;">
				<image style="margin-left: 40rpx; margin-bottom: 40rpx; " :src="item" mode="aspectFit">
				</image>
				<view class="image-remover" @click="removeImage(index)">
					<view class="deleteicon"></view>
				</view>
			</view>
			<view v-if="imgArr.length<=0" class="uploadiocn" @click="update">
				<view style="font-size: 100rpx">+</view>
				<view
					style="font-size: 20rpx;line-height: 28rpx;text-align: center;letter-spacing: 0.140769rpx;color: rgba(0, 0, 0, 0.4);">
					活动群/联系方式</view>
			</view>
		</view>
		<view class="line"></view>
		<view class="label">
			<view class="font" style="margin-left: 50rpx;">活动类别</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
		</view>
		<view class="labelbox">
			<view class="labelbox-in">
				<view class="scroll">
					<view v-for="(items,index) in labels" :key='index' @click="changeSelect(index)">
						<view class="smallbox" :class="{'select':selectLabel==index}">
							<view class="border">{{items}}</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">活动时间</view>
			<view style="color: #e20003; margin-left: 7rpx;">*</view>
			<view style="margin-left: 7%; width: 60%; text-align: right; font-size: 28rpx; color: #499199;height:45rpx;"
				@click="DatePicker">{{time==""?"选择活动时间":time}}</view>
			<mx-date-picker :show="showPicker" type="datetime" :value="value" :show-seconds="false" @confirm="ed"
				@cancel="ed" format="yyyy-mm-dd hh: ii" />
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">人数限制</view>
			<input type="digit" cursor-spacing="13"
				placeholder-style="color:#rgba(0, 0, 0, 0.4);font-size:28rpx;letter-spacing: 0.140769rpx"
				style="margin-left: 10%; width: 60%; text-align: right; font-size: 28rpx; color: #499199;"
				placeholder="请输入" v-model="number" maxlength="10">
			<view class="littleIcon" v-if="area=='请选择'">
			</view>
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">活动地址</view>
			<input type="text" cursor-spacing="13"
				placeholder-style="color:#rgba(0, 0, 0, 0.4);font-size:28rpx;letter-spacing: 0.140769rpx"
				style="margin-left: 10%; width: 60%; text-align: right; font-size: 28rpx; color: #499199;"
				placeholder="请输入" v-model="place" maxlength="40">
		</view>
		<view class="line"></view>
		<view class='inputbox'>
			<view class="font" style="margin-left: 50rpx; white-space: nowrap;">活动费用 (£)</view>
			<input type="digit" cursor-spacing="13"
				placeholder-style="color:#rgba(0, 0, 0, 0.4);font-size:28rpx;letter-spacing: 0.140769rpx"
				style="margin-left: 4%; width: 60%; text-align: right; font-size: 28rpx; color: #499199;"
				placeholder="请输入" v-model="price" maxlength="10">
		</view>
		<view style="margin-bottom: 200rpx;"></view>
		<view class='bottomplace'>
			<view class="updatebotton" @click="upload()"
				:class="{'all': name != '' && selectLabel != '-1' && time != ''}">
				发布</view>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	import MxDatePicker from "@/components/mx-datepicker/mx-datepicker.vue";
	export default {
		data() {
			return {
				name: '',
				detail: '',
				imgArr: [],
				labels: ['娱乐局', '运动局', '学习局', '拼团', '其他'],
				selectLabel: "-1",
				price: '',
				wxcode: '',
				all: 0,
				titleBarTop: 0,
				lock: false,
				func2: null,
				time: '',
				place: '',
				number: '',
				showPicker: false,
				value: ''
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
		components: {
			MxDatePicker
		},
		methods: {
			DatePicker() {
				this.showPicker = true;
				this.value = this["time"];
			},
			ed(e) {
				this.showPicker = false;
				if (e) {
					this["time"] = e.value;
				}
			},
			chan() {
				if (this.name.length < 26) return
				this.name = this.name.substr(0, 25)
			},
			back() {
				uni.navigateBack({})
			},
			changeSelect(index) {
				this.selectLabel = index
			},
			update() {
				let that = this
				if (that.imgArr.length > 0) {
					return;
				}
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(res) {
						if (that.imgArr.length + res.tempFilePaths.length > 1) {
							uni.showToast({
								icon: "none",
								title: "只能上传1张照片",
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
				if (!(/(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/.test(this.price))) {
					if (this.price != "") {
						this.price = ""
						uni.showToast({
							icon: "none",
							title: "请正确输入价格(最多2位小数)",
							duration: 2000
						})
						return
					}
				}
				if ((this.name != '' && this.selectLabel != '-1' && this.time != '') == false) {
					uni.showToast({
						icon: "none",
						title: "请把信息填写完整",
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
				this.func2 = setTimeout(() => {
					this.lock = false
					this.$refs.loading.hideLoading();
				}, 8000)
				uni.request({
					url: this.$serverUrl + '/activity/addActivity/' + uni.getStorageSync('userInfo').id,
					method: "POST",
					data: {
						activityAddr: this.place == '' ? null : this.place,
						activityFee: this.price == '' ? null : Number(this.price),
						activityTime: this.time == '' ? null : this.time,
						activityType: this.labels[this.selectLabel],
						content: this.detail == '' ? null : this.detail,
						maxPeople: this.number == '' ? null : Number(this.number),
						title: this.name == '' ? null : this.name,
						userId: uni.getStorageSync('userInfo').id,
						imgNumber: this.imgArr.length
					},
					header: {
						"Content-Type": "application/json"
					},
					success: (res) => {
						if (res.data.code == 200) {
							if (this.imgArr.length == 0) {
								uni.$emit('addActivity', this.selectLabel)
								clearTimeout(this.func2)
								this.$refs.loading.hideLoading();
								this.back()
								return
							}
							uni.uploadFile({
								url: 'https://lyuanzhi.com:8080/activity/addActivityImgs',
								filePath: this.imgArr[0],
								name: 'uploadImg',
								formData: {
									'id': res.data.data.id
								},
								success: (res) => {
									uni.$emit('addActivity', this.selectLabel)
									clearTimeout(this.func2)
									this.$refs.loading.hideLoading();
									this.back()
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

		display: flex;
		align-items: center;
		flex-wrap: wrap;
		white-space: nowrap;
	}

	.border {
		font-size: 24rpx;
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
		margin-bottom: 30rpx;
	}

	.smallbox.select {
		background-color: #DCF1F3;
		color: #499199 !important;
		font-weight: 900;
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
		font-weight: 900;
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
		justify-content: flex-start;
		align-items: center;
		margin-left: 40rpx;
		color: #C4C4C4;
		margin-bottom: 40rpx;
		border-radius: 12rpx;
		flex-direction: column;
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
		margin-bottom: 5rpx;
		margin-left: 50rpx;
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
