(function(){
	angular.module("attendanceModule")
		.controller("uploadClasslistCtrl", uploadClasslistCtrl);

	function uploadClasslistCtrl($stateParams, $scope){
		var self = this;
		self.filedata = {};
		self.data = [];
		self.subject = [$stateParams.c, $stateParams.s].join();
		
		console.log($stateParams);
		console.log(self.subject);

		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			console.log(newValue);
		});

		
	}
})();