(function(){
	angular.module("attendanceModule")
		.controller("seatPlanCtrl", seatPlanCtrl);

	function seatPlanCtrl($stateParams, seatPlanService){
		var self = this;
		self.class = {
			"subject": $stateParams.c,
			"section": $stateParams.s
		};
	}
})();