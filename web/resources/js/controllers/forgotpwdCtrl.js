(function(){
	angular.module("facultyApp")
		.controller("forgotpwdCtrl", forgotpwdCtrl);

	function forgotpwdCtrl(forgotpwdService){
		var self = this;
		self.changePassword = changePassword;

		function changePassword(username){
			console.log(username);
			forgotpwdService.changePassword(username).then(function(response){
				console.log(response);
			});
		}
	}
})();