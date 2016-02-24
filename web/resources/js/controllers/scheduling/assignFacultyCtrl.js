(function(){
	angular.module("schedulingModule")
		.controller("assignFacultyCtrl", assignFacultyCtrl);

	function assignFacultyCtrl(uploadSubjectsService, $scope, $q){
		var self = this;
		self.filedata = {};
		self.data = [];
		self.hasList = false;
		self.saveChanges = saveChanges;
		self.generate_assignedFaculty = generate_assignedFaculty;

		init();

		function init(){
			uploadSubjectsService.view_uploadedSubjects().then(function(response){
				var listData = response.response;
				uploadSubjectsService.parseList(listData).then(function(data){
					if(data.length > 0){
						self.data = data;
						self.hasList = true;		
					}
				});
			});
		}



		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			self.data = [];
			console.log(newValue);
			uploadSubjectsService.parseList(newValue.response).then(function(response){
				if(response.length > 0){
					self.data = response;
					self.hasList = true;
					console.log(response);
				}
			});
		});

		function saveChanges(){
			
			var selectedList = self.selected;
			var dataList = self.data;

			uploadSubjectsService.saveChanges(selectedList, dataList).then(function(response){
				console.log(response);
			});
		}

		function generate_assignedFaculty(){
			uploadSubjectsService.generate_assignedFaculty().then(function(response){
				console.log(response);
			});
		}
	}
}());