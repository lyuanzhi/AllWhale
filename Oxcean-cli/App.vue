<script>
	export default {
		onLaunch: function() {
			let $serverUrl = "https://lyuanzhi.com:8080"
			// let $serverUrl = "http://localhost:8080"
			uni.request({
				url: $serverUrl + '/config/getConfig',
				method: "POST",
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: (res) => {
					if (res.data.code == 200) {
						this.$store.state.config = res.data.data;
					}
				}
			})
		},
		onShow: function() {

		},
		onHide: function() {
			
		}
	}
</script>

<style>
	/*每个页面公共css */
	@import url("./common/animate.min.css");
	
	page{
		height: 100%;
		width: 100%;
	}
	
	::-webkit-scrollbar {  
	    display: none;  
	    width: 0 !important;  
	    height: 0 !important;  
	    -webkit-appearance: none;  
	    background: transparent;  
		color:transparent;
	}
</style>
