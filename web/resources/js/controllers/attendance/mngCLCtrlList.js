(function(){
	angular.module("attendanceModule")
		.controller("mngCLCtrlList", mngCLCtrlList);

	function mngCLCtrlList(scheduleService){
		var self = this;
		self.message = "Hello";
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
}());