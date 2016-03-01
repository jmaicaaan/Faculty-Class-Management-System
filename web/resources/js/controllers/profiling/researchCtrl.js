(function(){
	angular.module("profileModule")
		.controller("researchCtrl", researchCtrl);

	function researchCtrl($mdDialog, $timeout, researchService, cfpLoadingBar){
		
		const TEMP_LOC = "resources/templates/";
		var self = this;
		self.hasFiles = false;
		self.displayContent = false;
		self.showUploadDialog = showUploadDialog;
		self.showDeleteDialog = showDeleteDialog;
		
		self.getFiles = getFiles;

		activate_get_Researches();

		function activate_get_Researches(){
			cfpLoadingBar.start();

			//Use timeout to make the transition between tabs to be fast.
			$timeout(function(){
				researchService.get_Researches().then(function(){
					cfpLoadingBar.complete();
					self.displayContent = true;
				});	
			}, 2000);
		}
		

		function getFiles(){
			var file = researchService.listFile;

			self.hasFiles = file.length ? true : false;
			return file;
		}

		function showDeleteDialog(event, research){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/global-delete-upload.html",
					controller: deleteDialogController,
					controllerAs: "deleteDialogCtrl",
					locals:{
						research: research
					}					
			   });
		}

									/** DeleteDialog Controller **/

		function deleteDialogController($mdDialog, $mdToast, $timeout, research, researchService){
			var self = this;
			self.disableDeleteBtn = false;
			self.closeDialog = closeDialog;
			self.researchFile = research;

			self.displayFile = research.researchName;
			self.deleteFile = deleteResearch;
			
			function closeDialog(){
				$mdDialog.hide();
			}

			function deleteResearch(){
				var researchFile = self.researchFile;
				researchService.delete_Research(researchFile).then(function(response){

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

									/** UploadDialog Controller **/

		function showUploadDialog(event){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/research.uploadResearch.html",
					controller: uploadDialogController,
					controllerAs: "uploadDialogCtrl"
			   });
		}

		function uploadDialogController($mdDialog, $mdToast, $timeout, researchService){
			var self = this;
			self.research = {};
			self.cancelDialog = cancelDialog;
			self.uploadResearch = uploadResearch;
			self.disableButton = false;

			
			function uploadResearch(){
				
				/** Disable uploadButton to avoid double submission */
				self.disableButton = true;
				
				var researchObj = self.research;
				
				researchService.uploadResearch(researchObj)
					.then(function(response){
						console.log(response);
						//200 status is OK!
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