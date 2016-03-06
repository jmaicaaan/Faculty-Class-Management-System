(function(){
	angular.module("attendanceModule")
		.service("attendanceRptStudService", attendanceRptStudService);

	function attendanceRptStudService($http, userService){
		var self = this;
		self.view_HighCharts = view_HighCharts;

		function view_HighCharts(){
			
			var userObj =  {
				"userObj": {
					"username": userService.userInfo.username
				}
			};
			userService.userInfo;
			var request = {
				url: "View_StudentHighChart.action",
				method: "post",
				data: userObj,
				headers:{
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
	}
})();