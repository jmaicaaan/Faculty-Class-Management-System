(function(){
	angular.module("facultyApp")
		.controller("dashboardCtrl", dashboardCtrl);

	function dashboardCtrl($state, $scope, userObj, userService){
		var self = this;
		self.user = userObj;
		
		self.userRole = userService.userInfo.userRole;
		getAccountType();

		function getAccountType(){
			var ac = self.userRole;
			self.isDeveloper = ac.trim() == 'developer' ? true : false;
			
		}
	}
}());