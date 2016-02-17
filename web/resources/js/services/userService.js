(function(){
	angular.module("facultyApp")
		.service("userService", userService);

	function userService($http, $timeout, $rootScope, $q){
		var self = this;

		self.createSession = createSession;
		self.destroySession = destroySession;
		self.userInfo = {};

		function createSession(responseObj){

			//Too much query. Lessen the query to userID, id, username, fn, ln, ac, and pic only.

			var defer = $q.defer();

			self.userInfo.userID = responseObj.userID? responseObj.userID: "";
			self.userInfo.idNo = responseObj.idNo? responseObj.idNo : "";
			self.userInfo.username = responseObj.username? responseObj.username : "";
			self.userInfo.firstName = responseObj.firstName? responseObj.firstName : "";
			self.userInfo.lastName = responseObj.lastName? responseObj.lastName : "";

			var ac = responseObj.accountType.length == 0? "" : responseObj.accountType.length == 1? 
						[responseObj.accountType[0].accountType] : 
								[responseObj.accountType[0].accountType, responseObj.accountType[1].accountType];
			ac = ac.join(", ");
			
			self.userInfo.userRole = ac;
			self.userInfo.pictureUrl = {
				url : ""
			};
			self.userInfo.pictureUrl.url = responseObj.pictureUrl? responseObj.pictureUrl : ""; 
			
			// console.log(self.userInfo);
			defer.resolve();
			return defer.promise;
		}
		
		function destroySession(){
			self.userInfo = {};
		}

	}
}());