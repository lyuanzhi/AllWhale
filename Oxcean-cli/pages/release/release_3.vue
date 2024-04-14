<template>
	<view class="content" :style="{background:!stealth?'#F2F2F2;':'#45464c;'}">
		<view class="topNavigationBar" :style="{background:!stealth?'#5DB8C2;':'#45474C;'}">
			<view :style="{marginTop:(titleBarTop+6)+'px'}"
				style="font-weight: 900;font-size: 32rpx;letter-spacing: 0.02em;color: #FFFFFF;">发布话题</view>
			<view class="backicon" @click="back" :style="{top:(titleBarTop+5)+'px'}"></view>
		</view>
		<view class="box" :style="{background:!stealth?'#FFFFFF;':'rgba(255, 255, 255, 0.12)'}"
			style="margin-top: 200rpx;">
			<textarea placeholder="说点新鲜事..." v-model="content" :style="{color:!stealth?'#000000;':'#FFFFFF;'}"
				style="margin: 24rpx;caret-color:#E06653;font-size: 28rpx; height:362rpx;" type="text"
				maxlength="250" />
			<view style="width: 100%;height:160rpx;display: flex;margin-top: 40rpx;">
				<view v-for="(item,index) in imgArr" :key='index' style="position: relative;">
					<image style="margin-left: 32rpx;width:160rpx;height:160rpx;border-radius: 12rpx;" :src="item"
						mode="aspectFill">
					</image>
					<view class="image-remover" @click="removeImage(index)">
						<view class="deleteicon"></view>
					</view>
				</view>
				<view v-if="imgArr.length<=2" class="uploadiocn" @click="update"
					:style="{color:!stealth?'#CACCCC;':'#979999;', background:!stealth?'#F2F5F5;':'rgba(255, 255, 255, 0.1)'}">
					+</view>
			</view>
			<view class=" limit" style="display: flex; flex-direction: row-reverse;"
				:style="{color:!(content.length>=250)?'rgba(0, 0, 0, 0.6);':'#E06653'}">
				{{content.length}}/250
			</view>
		</view>
		<view class="box" :style="{background:!stealth?'#FFFFFF;':'rgba(255, 255, 255, 0.12)'}"
			style="margin-top: 24rpx;padding-left: 20rpx;padding-right: 20rpx;box-sizing: border-box;">
			<view style="margin-top: 24rpx;margin-left: 9rpx;font-size: 24rpx;"
				:style="{color:!stealth?'#000000;':'rgba(255, 255, 255, 0.8)'}">#话题</view>
			<view style="display: flex;flex-wrap: wrap;margin-top: 12rpx;margin-bottom: 12rpx;">
				<view class="tag" :class="{selected: item.isSelected}" v-for="(item, index) in tags" :key="index"
					@click="changeSelected(index)"
					:style="{color:!stealth?'#CACCCC;':'rgba(255, 255, 255, 0.4)', borderColor:!stealth?'#CACCCC;':'rgba(255, 255, 255, 0.4)'}">
					{{item.name}}
				</view>
			</view>
		</view>
		<view class="box" :style="{background:!stealth?'#FFFFFF;':'rgba(255, 255, 255, 0.12)'}"
			style="height: 168rpx; background-color: #ffffff; margin-top: 24rpx; display: flex; align-items: center;">
			<image :src="avatarUrl"
				style="width: 84rpx; height: 84rpx; border-radius: 50%; margin-left: 24rpx; background-color: #C4C4C4;">
			</image>
			<view style="margin-left: 24rpx; white-space:nowrap;font-size: 28rpx;"
				:style="{color:!stealth?'black':'rgba(255, 255, 255, 0.6)'}">{{name}}</view>
			<view v-if="hideStealth" class="change" @click='change()'>{{!stealth?"切换为匿名":"切换为公开"}}</view>
		</view>
		<view class="bottomplace">
			<view class="updatebotton" @click="submit"
				:style="{background:content.length?'#48D1E0':'rgba(145, 227, 236, 1.0)'}">
				{{stealth?"匿名发布":"公开发布"}}
			</view>
		</view>
		<loading ref="loading"></loading>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				stealth: 1,
				titleBarTop: 0,
				content: '',
				avatarUrl: '',
				name: '',
				lock: false,
				imgArr: [],
				tags: [],
				tagCount: 0,
				num: 0,
				func2: null,
			}
		},
		onReady() {
			let that = this;
			setTimeout(() => {
				that.titleBarTop = uni.getMenuButtonBoundingClientRect().top;
			}, 0);
		},
		onLoad() {
			if(this.$store.state.config[0].funcValue == 0){
				this.stealth = 0
				this.name = uni.getStorageSync('userInfo').username
				this.avatarUrl = uni.getStorageSync('userInfo').avatarUrl
			} else {
				this.stealth = 1
				this.name = '某同学'
				this.avatarUrl = '../../static/none3.png'
			}
			
			this.$refs.loading.showLoading();
			uni.request({
				url: this.$serverUrl + '/community/getCommunityTags',
				method: "POST",
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						for (let i = 0; i < res.data.data.length; i++) {
							this.tags.push({
								name: res.data.data[i],
								isSelected: false
							})
						}
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
		computed: {
			hideStealth() {
				return this.$store.state.config[0].funcValue;
			}
		},
		watch: {
			num() {
				if (this.num == this.imgArr.length + 1) {
					uni.$emit("addTopic")
					clearTimeout(this.func2)
					this.$refs.loading.hideLoading();
					this.back()
				}
			}
		},
		methods: {
			changeSelected(index) {
				if (this.tags[index].isSelected) {
					this.tags[index].isSelected = !this.tags[index].isSelected
					this.tagCount--
				} else {
					if (this.tagCount >= 3) {
						uni.showToast({
							icon: "none",
							title: "最多只能选择3个标签",
							duration: 2000
						})
					} else {
						this.tags[index].isSelected = !this.tags[index].isSelected
						this.tagCount++
					}
				}
			},
			update() {
				let that = this
				if (that.imgArr.length > 2) {
					return;
				}
				uni.chooseImage({
					count: 3,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(res) {
						if (that.imgArr.length + res.tempFilePaths.length > 3) {
							uni.showToast({
								icon: "none",
								title: "只能上传3张照片",
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
			change() {
				this.stealth = 1 - this.stealth
				if (this.stealth == 0) {
					this.name = uni.getStorageSync('userInfo').username
					this.avatarUrl = uni.getStorageSync('userInfo').avatarUrl
				} else {
					this.avatarUrl = '../../static/none3.png'
					this.name = '某同学'
				}
			},
			back() {
				uni.navigateBack({})
			},
			submit() {
				if (this.content.length == 0) {
					return
				}
				if (this.content.length > 250) {
					this.content = this.content.substr(0, 250)
				}
				if (this.lock) {
					return
				}
				this.lock = true
				this.$refs.loading.showLoading();
				uni.request({
					url: this.$serverUrl + '/community/getCommunityId',
					method: "POST",
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						if (res.data.code == 200) {
							for (let i = 0; i < this.imgArr.length; i++) {
								uni.uploadFile({
									url: 'https://lyuanzhi.com:8080/community/addCommunityImgs',
									filePath: this.imgArr[i],
									name: 'uploadImg',
									formData: {
										'index': i,
										'id': res.data.data
									},
									success: (res1) => {
										this.num++
									}
								})
							}
							let itsTags = ";"
							for (let i = 0; i < this.tags.length; i++) {
								if (this.tags[i].isSelected) {
									itsTags = itsTags + this.tags[i].name + ";"
								}
							}
							uni.request({
								url: this.$serverUrl + '/community/addCommunity',
								method: "POST",
								data: {
									content: this.content,
									isAnonymous: this.stealth,
									userId: uni.getStorageSync('userInfo').id,
									id: res.data.data,
									imgNumber: this.imgArr.length,
									itsTags: itsTags
								},
								header: {
									'content-type': 'application/x-www-form-urlencoded'
								},
								success: (res1) => {
									if (res1.data.code == 200) {
										this.num++
									}
								}
							})
						}
					}
				})
			}
		}
	}
</script>

<style scoped>
	textarea {
		width: 654rpx;
		height: 500rpx;
	}

	.content {
		display: flex;
		flex-direction: column;
		height: 100vh;
		transition: all .3s ease-in-out
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
		background: #5DB8C2;
		box-shadow: 0rpx -2rpx 20rpx rgba(0, 0, 0, 0.1), 0rpx -2rpx 0rpx #000000;
	}

	.change {
		width: 169rpx;
		height: 56rpx;
		background: #48D1E0;
		border-radius: 100rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #FFFFFF;
		font-weight: bold;
		font-size: 24rpx;
		line-height: 34rpx;
		left: 535rpx;
		position: absolute;
	}

	.backicon {
		position: absolute;
		width: 50rpx;
		height: 50rpx;
		left: 6%;
		background: url(../../static/back.png) center/50% 85% no-repeat;
	}

	.box {
		width: 702rpx;
		margin-left: 24rpx;
		border-radius: 6rpx;
		font-size: 28rpx;
		line-height: 48rpx;
		letter-spacing: 0.140769rpx;
	}

	.limit {
		font-size: 24rpx;
		line-height: 48rpx;
		margin-right: 20rpx;
		letter-spacing: 0.140769rpx;
		color: rgba(0, 0, 0, 0.6);
		margin-bottom: 30rpx;
		margin-top: 30rpx;
	}

	.bottomplace {
		position: fixed;
		width: 100%;
		height: 150rpx;
		bottom: 0rpx;
		left: 0rpx;
		border-radius: 32rpx 32rpx 0rpx 0rpx;
		z-index: 5;
	}

	.updatebotton {
		position: absolute;
		width: 90%;
		height: 90rpx;
		left: 5%;
		top: 15rpx;
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 34rpx;
		color: #FFFFFF;
		font-weight: bold;
	}

	.tag {
		height: 46rpx;
		margin: 12rpx 9rpx 12rpx 9rpx;
		padding: 6rpx 26rpx 6rpx 26rpx;
		box-sizing: border-box;
		border: 2rpx solid rgba(255, 255, 255, 0.4);
		border-radius: 30rpx;
		font-size: 24rpx;
		font-weight: 900;
		letter-spacing: 0.140769px;
		line-height: 34rpx;
	}

	.image-remover {
		position: absolute;
		background-color: #5DB8C2;
		width: 32rpx;
		height: 32rpx;
		top: -8rpx;
		right: -8rpx;
		border-radius: 50%;
		border: 2rpx solid #FFFFFF;
		box-sizing: border-box;
	}

	.deleteicon {
		background: url(../../static/x.png) center/50% 50% no-repeat;
		width: 100%;
		height: 100%;
	}

	.uploadiocn {
		width: 160rpx;
		height: 160rpx;
		display: flex;
		justify-content: center;
		font-size: 100rpx;
		align-items: center;
		margin-left: 32rpx;
		border-radius: 12rpx;
	}

	.selected {
		border-color: #48D1E0 !important;
		color: #48D1E0 !important;
	}
</style>
