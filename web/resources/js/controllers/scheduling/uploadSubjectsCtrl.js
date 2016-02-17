(function(){
	angular.module("schedulingModule")
		.controller("uploadSubjectsCtrl", uploadSubjectsCtrl);

	function uploadSubjectsCtrl(uploadSubjectsService){
		var self = this;
		self.message = "Hello!";
	}
}());