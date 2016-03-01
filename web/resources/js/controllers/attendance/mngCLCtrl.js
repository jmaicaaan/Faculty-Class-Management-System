(function(){
	angular.module("attendanceModule")
		.controller("mngCLCtrl", mngCLCtrl);

	function mngCLCtrl($stateParams, $scope, mngCLService, $mdDialog){

		const TEMP_LOC = "resources/templates/";

		var self = this;
		self.filedata = {};
		self.data = [];
		self.classList = [];
		self.requestObj = [$stateParams.c, $stateParams.s].join();
		self.viewClassList = viewClassList;
		self.showDeleteDialog = showDeleteDialog;
		self.addStudentDialog = addStudentDialog;
		self.hasClasslist = false;
		self.class = {
			"subject": $stateParams.c,
			"section": $stateParams.s
		};
		self.paginate = {
			order: "users.idNo",
			limit: 10,
			page: 1
		};

		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			if(newValue.name){
				viewClassList();	
			}
		});

		function viewClassList(){
			var schedObj = {
				"schedObj": {
					"section": self.class.section,
					"subjects":{
						"courseCode": self.class.subject
					}
				}
			};
			mngCLService.viewClassList(schedObj).then(function(response){
				self.classList = response.data.classList;
				self.hasClasslist = true;
			});
		}

		function showDeleteDialog(event, studentObj){
			$mdDialog.show({
					parent: angular.element(document.body),
					targetEvent: event,
					templateUrl: TEMP_LOC + "profiling/global-delete-upload.html",
					controller: deleteDialogController,
					controllerAs: "deleteDialogCtrl",
					locals:{
						studentObj: studentObj
					}
			   });
		}

		function deleteDialogController(studentObj, mngCLService, $mdToast, $mdDialog, $filter){
			var self = this;
			self.student = studentObj;

			/** **/
			self.displayFile = [studentObj.users.idNo, 
							$filter("nameFormat")(studentObj.users.firstName), 
							$filter("nameFormat")(studentObj.users.lastName)].join(" ");
			self.deleteFile = deleteStudent;
			self.closeDialog = closeDialog;

			function deleteStudent(){
				mngCLService.deleteStudent(self.student).then(function(response){
					if(response.status == 200){
						viewClassList();
						closeDialog();	
					}else{
						displayToast($mdToast);
					}
					
				});
			}

			function closeDialog(){
				$mdDialog.hide();
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

		function addStudentDialog(event){
			$mdDialog.show({
				parent: angular.element(document.body),
				targetEvent: event,
				templateUrl: TEMP_LOC + "attendance/manageClasslist.addStudentDialog.html",
				controller: addStudentDialogController,
				controllerAs: "addStudentDialogCtrl"

			});
		}

		function addStudentDialogController($mdDialog, mngCLService, $stateParams, $mdToast){
			var self = this;
			self.student = {};
			self.disableButton = false;
			self.closeDialog = closeDialog;
			self.addStudent = addStudent;
			self.class = {
				"subject": $stateParams.c,
				"section": $stateParams.s
			};
			
			function addStudent(){
				mngCLService.addStudent(self.student, self.class).then(function(response){
					if(response.status == 200){
						viewClassList();
						closeDialog();	
					}else{
						displayToast($mdToast);
					}
					
				});
			}

			function closeDialog(){
				$mdDialog.hide();
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
	}
})();