(function(){
	angular.module("attendanceModule")
		.service("evaluateStudentService", evaluateStudentService);

	function evaluateStudentService($http){
		var self = this;

		self.viewStudents = viewStudents;

		function viewStudents(){
			var request = {
				url: "View_Students.action",
				method: "post",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){

					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});	
		}
	}
})();