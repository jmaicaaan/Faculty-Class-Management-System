(function(){
	angular.module("attendanceModule")
		.service("attendanceRptService", attendanceRptService);

	function attendanceRptService($http){
		var self = this;
		self.view_highCharts = view_highCharts;

		function view_highCharts(schedObj){
			var request = {
				url: "View_HighChart.action",
				method: "post",
				data: schedObj,
				headers: {
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