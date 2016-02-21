(function(){
	angular.module("schedulingModule")
		.controller("uploadSubjectsCtrl", uploadSubjectsCtrl);

	function uploadSubjectsCtrl(uploadSubjectsService, $scope, $q){
		var self = this;
		self.message = "Hello!";
		self.filedata = {};
		self.data = [];
		self.hasList = false;
		
		$scope.$watch(function(){
			return self.filedata;
		}, function(newValue){
			uploadSubjectsService.parseList(newValue).then(function(response){
				if(response.length > 0){
					self.data = response;
					self.hasList = true;
					console.log(response);
				}
			});
		});

		self.saveChanges = function(){
			
			var selectedList = self.selected;
			var dataList = self.data;

			uploadSubjectsService.saveChanges(selectedList, dataList).then(function(response){
				console.log(response);
			});
		}
	}
}());