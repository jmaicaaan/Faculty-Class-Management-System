(function(){
	angular.module("schedulingModule")
		.service("scheduleService", scheduleService);

	function scheduleService($http){
		var self = this;
		self.view_mySchedule = view_mySchedule;
		self.schedule = [];

		function view_mySchedule(){
			var request = {
				url: "View_MySchedule.action",
				method: "post",
				headers: {
					"Content-Type": "application/json"
				}
			};

			return $http(request).then(function(response){
				var temp = [];
				for(var sched in response.data.mySchedule){
					var courseCode = response.data.mySchedule[sched].schedule.subjects.courseCode;
					var schedule = response.data.mySchedule[sched].schedule;
					var obj = {
						"courseCode": courseCode,
						"schedule": {
							"time": schedule.time,
							"day": schedule.day,
							"room": schedule.room,
							"section": schedule.section
						}
					};
					temp.push(obj);
				}
				self.schedule = temp;
				return self.schedule;
			});
		}
	}
}());