(function(){
	angular.module("developerApp")
		.controller("usersManageCtrl", usersManageCtrl);

	function usersManageCtrl(developerService){
		var self = this;
		self.uploadProfessors = uploadProfessors;
		self.loadProfessors = loadProfessors;
		self.updateAccountType = updateAccountType;
		self.list = [];
		self.selectedUsers = {};
		self.displayUsersTable = false;

		function uploadProfessors(){
			developerService.uploadProfessors().then(function(response){
				self.displayUsersTable = true;
			});
		}

		function loadProfessors(){
			//Database Users

			developerService.loadProfessors().then(function(response){
				
				self.displaySubjectsTable = false;
				self.displayUsersTable = true;
				self.list = response.data.users;
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