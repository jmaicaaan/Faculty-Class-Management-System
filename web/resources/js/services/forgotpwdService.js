(function(){
	angular.module("facultyApp")
		.service("forgotpwdService", forgotpwdService);

	function forgotpwdService($http){
		var self = this;
		self.changePassword = changePassword;

		function changePassword(username){
			var obj = {
				"uObj": {
					"username": username
				}
			};
			var request = {
				url: "Edit_Password.action",
				method: "post",
				data: obj,
				headers:{
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){
					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});
		}
	}
})();