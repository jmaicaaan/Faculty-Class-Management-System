(function(){
	angular.module("schedulingModule")
		.controller("scheduleCtrl", scheduleCtrl);

	function scheduleCtrl(scheduleService, $timeout){
		var self = this;
		self.message = "Hello";
		self.schedule = [];
		self.view_mySchedule = view_mySchedule;
		self.hasSchedule = false;


		view_mySchedule();

		function view_mySchedule(){
			scheduleService.view_mySchedule().then(function(response){
				self.schedule = response;
				self.hasSchedule = true;
			});
		}
	}
}());