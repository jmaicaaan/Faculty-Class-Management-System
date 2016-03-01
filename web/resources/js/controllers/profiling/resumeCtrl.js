(function(){
	angular.module("profileModule")
		.controller("resumeCtrl", resumeCtrl);

	function resumeCtrl($scope, $mdDialog, $timeout, resumeService, cfpLoadingBar){

		const TEMP_LOC = "resources/templates/";
		var self = this;
		self.hasFiles = false;
		self.displayContent = false;
		self.showUploadDialog = showUploadDialog;
		self.showDeleteDialog = showDeleteDialog;
		self.resumeFile = {};
		

		activate_get_Resume();

		function activate_get_Resume(){
			cfpLoadingBar.start();

			//Use timeout to make the transition between tabs to be fast.
			$timeout(function(){
				resumeService.get_Resume().then(function(){
					cfpLoadingBar.complete();
					self.displayContent = true;
				});	
			}, 2000);
		}

		$scope.$watch(function(){
			return resumeService.listFile;
		}, function(newValue){
			self.hasFiles = Object.keys(newValue).length? true: false;
			if(self.hasFiles){
				self.resumeFile = newValue[0];
			}
		});

		function showDeleteDialog(event, resume){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/global-delete-upload.html",
					controller: deleteDialogController,
					controllerAs: "deleteDialogCtrl",
					locals:{
						resume: resume
					}
			   });
		}

									/** DeleteDialog Controller **/

		function deleteDialogController($mdDialog, $mdToast, $timeout, resume, resumeService){
			var self = this;
			self.disableDeleteBtn = false;
			self.resumeFile = resume;
			self.closeDialog = closeDialog;

			console.log(resume);
			self.displayFile = resume.resumeUrl;
			self.deleteFile = deleteResume;

			
			function closeDialog(){
				$mdDialog.hide();
			}

			function deleteResume(){
				var resumeFile = self.resumeFile;

				resumeService.delete_Resume(resumeFile).then(function(response){

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
		}

		function showUploadDialog(event){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/resume.uploadResume.html",
					controller: uploadDialogController,
					controllerAs: "uploadDialogCtrl"
			   });
		}

		function uploadDialogController($mdDialog, resumeService, $mdToast, $timeout){
			var self = this;
			self.disableButton = false;	
			self.filedata = {};
			self.cancelDialog = cancelDialog;
			self.uploadResume = uploadResume;

			function uploadResume(){
				var resumeObj = self.filedata;
				self.disableButton = true;
				resumeService.uploadResume(resumeObj).then(function(response){
					if(response.status == 200){
						self.cancelDialog();
					}else{
						errorToast($mdToast);
						$timeout(function(){
							self.disableButton = false;	
						}, 1500);
					}
				});
			}

			function errorToast($mdToast){
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