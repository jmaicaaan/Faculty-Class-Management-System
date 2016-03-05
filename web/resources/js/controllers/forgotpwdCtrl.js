(function(){
	angular.module("facultyApp")
		.controller("forgotpwdCtrl", forgotpwdCtrl);

	function forgotpwdCtrl($mdDialog){
		var self = this;
		self.data = {};
		self.changePassword = changePassword;

		function changePassword(){
			
		}
	}
})();