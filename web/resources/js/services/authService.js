(function(){
	angular.module("facultyApp")
		.factory("authService", authService);

	function authService($http, userService){
		var authService = {
			login: login,
			checkOnlineUser: checkOnlineUser,
			updateSession: updateSession,
			isAuthorized: isAuthorized,
			logoutUser: logoutUser
		};

		return authService;

		function login(credentials){
			
			var userObj = {
				"usersModel":{
					username: credentials.username,
					password: [{
						password: credentials.password
					}]
				}
			};

			var request = {
				url: "userLogin.action",
				method: "post",
				data: userObj,
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
					
			};

			return $http(request)
				.then(function(response){
					var responseObj = response.data.usersModel;
					userService.createSession(responseObj);
					return response;
				})
				.catch(function(error){
					return error;
			});
		}

		function checkOnlineUser(){
			var request = {
				method: "post",
				url: "checkOnlineUser.action",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function updateSession(){

			var request = {
				url: "updateCurrentSession.action",
				method: "post",
				headers:{
					"Content-Type": "application/json",
					"datatype": "json"
				}
			};

			return $http(request)
				.then(function(response){
					var responseObj = response.data.user;
					return userService.createSession(responseObj);
				})
				.catch(function(error){
					return error;
				});
		}

		function isAuthorized(arrayOfRoles){
			var userAccountType = userService.getAccountType().trim();
			if(userAccountType){
				if(arrayOfRoles.indexOf(userAccountType.toLowerCase()) > -1){
					return true;
				}
			}
			return false;
		}

		function logoutUser(){
			var request = {
				method: "post",
				url: "logoutUser.action",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
					
			};

			return $http(request)
				.then(function(response){
					userService.destroySession();
					return response;
				})
				.catch(function(error){
					return error;
				});

		}
	}
}());