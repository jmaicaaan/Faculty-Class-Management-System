(function(){
	angular.module("schedulingModule")
		.controller("assignFacultyCtrl", assignFacultyCtrl);

	function assignFacultyCtrl(uploadSubjectsService, $scope, $q, $mdToast, $state){
		var self = this;
		self.filedata = {};
		self.data = [];
		self.hasList = false;
		self.saveChanges = saveChanges;
		self.generate_assignedFaculty = generate_assignedFaculty;
		self.hasConflict = false;

		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			self.data = [];
			uploadSubjectsService.parseList(newValue.response).then(function(response){
				if(response.length > 0){
					self.data = response;
					self.hasList = true;
				}
			});
		});

		function saveChanges(){
			
			var selectedList = self.selected;
			var dataList = self.data;

			uploadSubjectsService.saveChanges(selectedList, dataList).then(function(response){
				self.hasList = false;
				displayToast($mdToast, "You have successfully assigned faculties. Download the PDF now containing your assigned faculties.");
			});
		}

		function generate_assignedFaculty(){
			uploadSubjectsService.generate_assignedFaculty().then(function(response){
				console.log(response);
			});
		}

		function displayToast($mdToast, message){

			var toast = $mdToast.simple()
				.textContent(message)
				.position("top")
				.action("OK")
				.highlightAction(false);

			$mdToast.show(toast).then(function(response){
				if(response == 'ok'){
					$state.go("dashboard.settings.miscellaneous");
				} 
			});
		}
	}
}());