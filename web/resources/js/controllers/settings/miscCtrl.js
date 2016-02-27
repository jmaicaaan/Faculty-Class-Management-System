(function(){
	angular.module("facultyApp")
		.controller("miscCtrl", miscCtrl);

	function miscCtrl(userService){
		var self = this;
		self.userRole = userService.userInfo.userRole;
		getAccountType();

		function getAccountType(){
			var ac = self.userRole.split(",");
			if(ac.length > 1){
				self.isChairperson = ac[1].trim() == 'Chairperson' ? true : false;
			}else{
				self.isChairperson = ac[0].trim() == 'Chairperson' ? true : false;
			}
		}
	}
}());