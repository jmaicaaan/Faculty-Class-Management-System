(function(){
	angular.module("attendanceModule")
		.controller("attendanceRptList_Ctrl", attendanceRptList_Ctrl);

	function attendanceRptList_Ctrl(scheduleService){
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