(function(){
	angular.module("attendanceModule")
		.service("seatPlanService", seatPlanService);

	function seatPlanService($http){
		var self = this;
		self.viewClassList = viewClassList;
		self.saveAttendance = saveAttendance;

		function viewClassList(schedObj){
			var request = {
				url: "view_Classlist.action",
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

		function saveAttendance(studentObj){
			var request = {
				url: "saveAttendance.action",
				method: "post",
				data: studentObj,
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
					console.log(error);
					return error;
				});
		}
	}
})();