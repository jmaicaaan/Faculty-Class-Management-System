(function(){
	angular.module("schedulingModule")
		.controller("assignFacultyCtrl", assignFacultyCtrl);

	function assignFacultyCtrl(uploadSubjectsService, $scope, $q, $mdToast){
		var self = this;
		self.filedata = {};
		self.data = [];
		self.hasList = false;
		self.saveChanges = saveChanges;
		self.generate_assignedFaculty = generate_assignedFaculty;

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
				displayToast($mdToast);
			});
		}

		function generate_assignedFaculty(){
			uploadSubjectsService.generate_assignedFaculty().then(function(response){
				console.log(response);
			});
		}

		function displayToast($mdToast){
			$mdToast.show(
				$mdToast.simple()
					.textContent("You have successfully assigned faculties")
					.position("top")
					.hideDelay(2000)
			);
		}
	}
}());