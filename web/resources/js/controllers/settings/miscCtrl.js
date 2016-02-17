(function(){
	angular.module("facultyApp")
		.controller("miscCtrl", miscCtrl);

	function miscCtrl(genSetService, $window){
		var self = this;
		self.pdf = pdf;

		function pdf(){
			genSetService.generatePDF().then(function(response){
				console.log(response);
				// $window.location.href = response.data;
				$window.open(response.data, "_blank");
			});
		}
	}
}());