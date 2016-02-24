(function(){
	angular.module("schedulingModule")
		.controller("scheduleCtrl", scheduleCtrl);

	function scheduleCtrl(scheduleService, $timeout){
		var self = this;
		self.message = "Hello";
		self.schedule = [];
		self.view_mySchedule = view_mySchedule;
		self.hasSchedule = false;

		// var timer = $timeout(function(){
		// 	view_mySchedule();
		// }, 2000);

		view_mySchedule();

		function view_mySchedule(){

			if( !self.hasSchedule ){
				scheduleService.view_mySchedule().then(function(response){
					self.schedule = response;
					self.hasSchedule = true;
				});
			}
			
		}
	}
}());