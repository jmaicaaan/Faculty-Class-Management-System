(function(){
	angular.module("profileModule")
		.controller("projectsCtrl", projectsCtrl);

	function projectsCtrl($mdDialog, $timeout, projectService, cfpLoadingBar){
		
		const TEMP_LOC = "resources/templates/";
		
		var self = this;
		self.hasFiles = false;
		self.displayContent = false;
		self.showUploadDialog = showUploadDialog;
		self.showDeleteDialog = showDeleteDialog;
		self.getFiles = getFiles;

		activate_get_Projects();

		function activate_get_Projects(){
			cfpLoadingBar.start();

			//Use timeout to make the transition between tabs to be fast.
			$timeout(function(){
				projectService.get_Projects().then(function(){
					cfpLoadingBar.complete();
					self.displayContent = true;
				});	
			}, 2000);
		}
		
		function getFiles(){
			var file = null;
			file = projectService.listFile;
			self.hasFiles = file.length ? true : false;
			console.log(file);
			return file;
			
		}

		function showDeleteDialog(event, project){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/global-delete-upload.html",
					controller: deleteDialogController,
					controllerAs: "deleteDialogCtrl",
					locals:{
						project: project
					}
			   });
		}
									/** DeleteDialog Controller **/
		
		function deleteDialogController($mdDialog, $mdToast, $timeout, project, projectService){
			var self = this;
			self.disableDeleteBtn = false;
			self.closeDialog = closeDialog;
			self.projectFile = project;
			self.deleteFile = deleteProject;
			
			function closeDialog(){
				$mdDialog.hide();
			}

			function deleteProject(){
				var projectFile = self.projectFile;

				projectService.delete_Project(projectFile).then(function(response){

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
					templateUrl: TEMP_LOC + "profiling/projects.uploadProject.html",
					controller: uploadDialogController,
					controllerAs: "uploadDialogCtrl"
			   });
		}
		
									/** UploadDialog Controller **/

		function uploadDialogController($mdDialog, $mdToast, projectService){
			var self = this;
			self.project = {};
			self.disableButton = false;
			self.cancelDialog = cancelDialog;
			self.uploadProject = uploadProject;
			

			function uploadProject(){
				
				/** Disable uploadButton to avoid double submission */
				self.disableButton = true;
				
				var projectObj = self.project;
				
				projectService.uploadProject(projectObj)
					.then(function(response){
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