(function(){
	angular.module("profileModule")
		.controller("achievementsCtrl", achievementsCtrl);

	function achievementsCtrl($mdDialog, achievementService, $timeout, cfpLoadingBar){
		
		const TEMP_LOC = "resources/templates/";

		var self = this;
		self.hasFiles = false;
		self.displayContent = false;		
		self.showUploadDialog = showUploadDialog;
		self.showDeleteDialog = showDeleteDialog;
		self.getFiles = getFiles;
			
		activate_get_Achievements_Certifications();

		function activate_get_Achievements_Certifications(){
			cfpLoadingBar.start();

			//Use timeout to make the transition between tabs to be fast.
			$timeout(function(){
				achievementService.get_Achievements_Certifications().then(function(){
					cfpLoadingBar.complete();
					self.displayContent = true;
				});	
			}, 2000);
		}
			
		function getFiles(){
			var file = null;

			file = achievementService.listFile;
			self.hasFiles = file.length ? true : false;
			return file;
		}

		function showDeleteDialog(event, achievement){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/global-delete-upload.html",
					controller: deleteDialogController,
					controllerAs: "deleteDialogCtrl",
					locals:{
						achievement: achievement
					}
			   });
		}
											/** DeleteDialog Controller **/
		
		function deleteDialogController($mdDialog, $mdToast, $timeout, achievement, achievementService){
			var self = this;
			self.disableDeleteBtn = false;	
			self.closeDialog = closeDialog;
			self.achievementFile = achievement;

				/** Watch out on this naming. Since we're using a global template in the delete **/
			self.deleteFile = deleteAchievement;
			

			function closeDialog(){
				$mdDialog.hide();
			}

			function deleteAchievement(){
				var achievementFile = self.achievementFile;
				achievementService.delete_Achievements_Certifications(achievementFile)
					.then(function(response){

						self.disableDeleteBtn = true;

						if(response.status == 200){
							self.closeDialog();
						}else{
							displayToast($mdToast);
							$timeout(function(){
								self.disableDeleteBtn = false;	
							}, 1500);
						}
					});
			}

			function displayToast($mdToast){
				$mdToast.show(
					$mdToast.simple()
						.textContent("An error has occured. Please try again.")
						.position("top")
						.hideDelay(2000)
				);
			}
		}
											/** UploadDialog Controller **/
		
		function showUploadDialog(event){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/achievements.uploadDialog.html",
					controller: dialogController,
					controllerAs: "uploadDialogCtrl"
			   });
		}
				
		function dialogController($mdDialog, $mdToast, $timeout, achievementService){
			
			//DIALOG CONTROLLER - MODAL

			var self = this;
			self.achievementFileData = {};
			self.disableButton = false;
			self.cancelDialog = cancelDialog;
			self.upload_Achievement_Certificate = upload_Achievement_Certificate;

			function upload_Achievement_Certificate(){
				self.disableButton = true;
				var achievementObj = self.achievementFileData;
				
				achievementService.uploadFile(achievementObj)
					.then(function(response){
						
						if(response.status == 200){
							self.cancelDialog();
						}else{
							displayToast($mdToast);
							$timeout(function(){
								self.disableButton = false;	
							}, 1500);
						}
					});				
			}

			function displayToast($mdToast){
				$mdToast.show(
					$mdToast.simple()
						.textContent("An error has occured. Please try again.")
						.position("top")
						.hideDelay(2000)
				);
			}


			function cancelDialog(){
				$mdDialog.hide();
			}
		}
	}
}());