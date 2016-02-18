(function(){
	angular.module("developerApp")
		.controller("subjManageCtrl", subjManageCtrl);

	function subjManageCtrl(developerService){
		var self = this;
		self.fabOpen = false;
		self.loadSubjects = loadSubjects;
		self.uploadSubjects = uploadSubjects;
		self.listOfSubjects = [];

		function loadSubjects(){
			developerService.loadSubjects().then(function(response){
				self.displaySubjectsTable = true;
				self.listOfSubjects = response.subjects;
			});
		}

		function uploadSubjects(){
			developerService.uploadSubjects().then(function(response){
				self.displaySubjectsTable = true;
				self.listOfSubjects = response.subjects;
			});
		}
	}
}());