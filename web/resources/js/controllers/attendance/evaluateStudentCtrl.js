(function(){
	angular.module("attendanceModule")
		.controller("evaluateStudentCtrl", evaluateStudentCtrl);

	function evaluateStudentCtrl(evaluateStudentService){
		var self = this;
		self.paginate = {
			order: "users.idNo",
			limit: 10,
			page: 1,
			query: ""
		};
		self.hasStudents = false;
		self.studentList = [];

		viewStudents();

		function viewStudents(){
			evaluateStudentService.viewStudents().then(function(response){
				self.studentList = response.data.uList;
				self.hasStudents = true;
			});
		}
	}
})();