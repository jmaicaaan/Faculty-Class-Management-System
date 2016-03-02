(function(){
	angular.module("facultyApp")
		.controller("genSetCtrl", genSetCtrl);

	function genSetCtrl($mdDialog, $scope, userService, authService, subjectService, genSetService){

		const TEMP_LOC = "resources/templates/";
		var self = this;
		self.listOfSubjects = [];
		self.selectedSubjects = [];
		self.showDialogPassword = showDialogPassword;
		self.saveChanges = saveChanges;
		self.loadSubjects = loadSubjects;
		self.getExpertise = getExpertise;
		self.delete_subject = delete_subject;
		self.validateChips = validateChips;
		self.user = userService.userInfo;
		self.getAccountType = userService.getAccountType;
		self.isNotAuthorized = false;
		//Init 
		init();
		function init(){

			var userAccountType = userService.getAccountType().toLowerCase();

			if(userAccountType == 'student' || userAccountType == 'secretary' || userAccountType == 'developer'){
				self.isNotAuthorized = true;
			}else{
				loadSubjects();
				getExpertise();
			}
		}

		function loadSubjects(){
			subjectService.loadSubjects().then(function(response){
				self.listOfSubjects = response.subjects;
			});
		}
		function getExpertise(){
			genSetService.get_Expertise().then(function(){
				self.selectedSubjects = genSetService.expertiseList;
			});
		}
		

		function saveChanges(){
			var userObj = self.user,
				subjectObj = self.selectedSubjects;

			genSetService.updateUserProfile(userObj, subjectObj).then(function(response){
				authService.updateSession();
				self.getExpertise();
			});
		}

		function delete_subject(subjectChip){
			console.log(subjectChip);
			if(subjectChip["flag"] === undefined){
				genSetService.delete_subject(subjectChip).then(function(resp){
					console.log(resp);
				});
			}
		}

		function validateChips(selectedChip){
			
			selectedChip.flag = true;	
			//Add a flag which means it is not yet added in the database.
			var len = self.selectedSubjects.length;
			
			console.log(selectedChip);
			if(len > 3){
				self.selectedSubjects[len - 1] = selectedChip;
			}
			console.log(self.selectedSubjects);
		}

		function showDialogPassword(event){
			$mdDialog.show({
				parent: angular.element(document.body),
				targetEvent: event,
				templateUrl: TEMP_LOC + "settings/settings.general.password.html",
				clickOutsideToClose: true,
				controller: dialogPasswordCtrl,
				controllerAs: "dialogPasswordCtrl"
			});
		}

		function dialogPasswordCtrl($mdDialog){
			var self = this;
			self.updateUserPassword = updateUserPassword;
			self.closeDialog = closeDialog;

			function updateUserPassword(){
				var userObj = {
					"pModel": self.pass
				};
				genSetService.updateUserPassword(userObj).then(function(response){
					self.closeDialog();
				});
			}

			function closeDialog(){
				$mdDialog.hide();
			}

		}
	}
}());