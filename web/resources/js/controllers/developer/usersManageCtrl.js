(function(){
	angular.module("developerApp")
		.controller("usersManageCtrl", usersManageCtrl);

	function usersManageCtrl(developerService, $mdToast){
		var self = this;
		self.uploadProfessors = uploadProfessors;
		self.loadProfessors = loadProfessors;
		self.updateAccountType = updateAccountType;
		self.list = [];
		self.selectedUsers = {};
		self.displayUsersTable = false;

		function uploadProfessors(){
			developerService.uploadProfessors().then(function(response){
				self.loadProfessors();
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
				if( !response.data.hasAdded){
					displayToast($mdToast);
				}
				self.selectedUsers = {};
				self.loadProfessors();
			});
		}

		function displayToast($mdToast){
			$mdToast.show(
				$mdToast.simple()
					.textContent("Maximum of 3 Academic Adviser")
					.position("top")
					.hideDelay(2000)
			);
		}
	}
}());