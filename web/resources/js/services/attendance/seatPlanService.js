(function(){
	angular.module("attendanceModule")
		.service("seatPlanService", seatPlanService);

	function seatPlanService($http){
		var self = this;
		self.viewClassList = viewClassList;
		self.saveAttendance = saveAttendance;
		self.viewAttendance = viewAttendance;
		self.getDate_Helper = getDate_Helper;
		self.hasScheduleToday_Helper = hasScheduleToday_Helper;
		self.classList = [];
		self.classAttendance = [];

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
					if(response.status == 200 && response.data.classList.length > 0){
						self.classList.push(response.data.classList);
					}
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
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function viewAttendance(schedObj){
			var request = {
				url: "viewAttendance.action",
				method: "post",
				data: schedObj,
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){
					//Initialize it, so it can be watched rather than pushing elements!
					self.classAttendance = response.data.aList;
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function getDate_Helper(){
			var d = new Date();

			return {
				getDate: getDateFormat,
				getDay: getDayFormat,
				formatDate: formatDate
			};

			function getDateFormat(){
				return [d.getMonth() + 1, 
						d.getDate().length > 1? d.getDate() : '0' + d.getDate(), 
						d.getFullYear()].join("/");
			}
			function getDayFormat(){
				//0 = Sunday 
				return d.getDay();
			}
			function formatDate(date){
				return [date.getMonth() + 1, 
						date.getDate().length > 1? date.getDate() : '0' + date.getDate(), 
						date.getFullYear()].join("/");
			}
		}

		function hasScheduleToday_Helper(schedObj){
			var classDay = schedObj.day;
			if(classDay == "MW"){
				return (1 == getDate_Helper().getDay || 	
					3 == getDate_Helper().getDay);
			}else if(classDay == "TTH"){
				return (2 == getDate_Helper().getDay ||
					4 == getDate_Helper().getDay);
			}else if(classDay == "F"){
				return (5 == getDate_Helper().getDay);
			}else if(classDay == "S"){
				return (6 == getDate_Helper().getDay);
			}else{
				return false;
			}
		}
	}
})();