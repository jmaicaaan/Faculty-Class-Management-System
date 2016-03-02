(function(){
	angular.module("attendanceModule")
		.controller("recordAttendance_ListCtrl", recordAttendance_ListCtrl);

	function recordAttendance_ListCtrl(scheduleService){
		var self = this;

		self.schedule = [];
		getClass();

		function getClass(){
			if( !self.hasSchedule ){
				scheduleService.view_mySchedule().then(function(response){
					console.log(response);
					self.schedule = response;
				});
			}
		}
	}
})();