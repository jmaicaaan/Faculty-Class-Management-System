(function(){
	angular.module("developerApp")
		.controller("developerCtrl", developerCtrl);

	function developerCtrl(developerService, $timeout){
		var self = this;
		self.uploadProfessors = uploadProfessors;
		self.loadProfessors = loadProfessors;
		self.updateAccountType = updateAccountType;
		self.selectedUsers = {};
		
		self.displaySubjectsTable = false;
		self.displayUsersTable = false;

		function uploadProfessors(){
			developerService.uploadProfessors().then(function(response){
				self.displayUsersTable = true;
				console.log(response);
			});
		}

		function loadProfessors(){
			//Database Users

			developerService.loadProfessors().then(function(response){
				
				self.displaySubjectsTable = false;
				self.displayUsersTable = true;
				
				self.list = response.data.users;
				console.log(response);
				
			});
		}
		

		function updateAccountType(){
			
			var usersObjList = self.selectedUsers;
			developerService.addAccountType(usersObjList).then(function(response){
				console.log(response);
				self.selectedUsers = {};
				self.loadProfessors();
			});
		}
	}
}());