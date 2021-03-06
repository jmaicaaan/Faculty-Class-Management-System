(function(){
	angular.module("attendanceModule", [
		"events", 
		"highcharts-ng"
	]);
}());

(function(){
	angular.module("attendanceModule")
		.config(config);

	function config($stateProvider, $urlRouterProvider, TEMP_LOC, USER_ROLES){
		$urlRouterProvider
			.when("/manageClasslist", "/manageClasslist/list")
			.otherwise("/dashboard/");

		$stateProvider
			.state("dashboard.manageClasslist", {
				url: "/manageClasslist",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Manage Classlist"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/menu.html",
				controller: function($state, $timeout){
					$timeout(function(){
						$state.go("dashboard.manageClasslist.list");
					});
				}
			})
			.state("dashboard.manageClasslist.list", {
				url: "/list",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Manage Classlist"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.list.html",
				controller: "mngCLCtrlList",
				controllerAs: "mngCLCtrlList"
			})
			.state("dashboard.manageClasslist.class",{
				url: "/?c&s",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Manage Classlist"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.html",
				controller: "mngCLCtrl",
				controllerAs: "mngCLCtrl"
			})
			.state("dashboard.recordAttendance", {
				url: "/recordAttendance",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Record Attendance"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/menu.html",
				controller: function($state){
					$state.go("dashboard.recordAttendance.list");
				}
			})
			.state("dashboard.recordAttendance.list", {
				url: "/recordAttendanceList",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Record Attendance"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/recordAttendance_List.html",
				controller: "recordAttendance_ListCtrl",
				controllerAs: "recordAttendance_ListCtrl"
			})
			.state("dashboard.recordAttendance.class", {
				url: "/?c&s&d",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Record Attendance"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/seatPlan.html",
				controller: "seatPlanCtrl",
				controllerAs: "seatPlanCtrl"
			})
			.state("dashboard.attendanceReports", {
				url: "/attendanceReports",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Attendance Reports"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/menu.html",
				controller: function($state){
					$state.go("dashboard.attendanceReports.list");
				}
			})
			.state("dashboard.attendanceReports.list", {
				url: "/attendanceReportsList",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Attendance Reports"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/attendanceReports_List.html",
				controller: "attendanceRptList_Ctrl",
				controllerAs: "attendanceRptList_Ctrl"
			})
			.state("dashboard.attendanceReports.class", {
				url: "/?c&s",
				data:{
				    authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson],
				    stateName: "Attendance Reports"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/attendanceReports.html",
				controller: "attendanceRptCtrl",
				controllerAs: "attendanceRptCtrl"
			})
			.state("dashboard.studentAttendanceReports", {
				url: "/attendanceReportsStudent",
				data:{
				    authorizedRoles: [USER_ROLES.student],
				    stateName: "Attendance Reports"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/studentAttendanceReports.html",
				controller: "attendanceRptStudCtrl",
				controllerAs: "attendanceRptStudCtrl"
			})
			.state("dashboard.evaluateStudent", {
				url: "/evaluateStudent",
				data:{ 
				    authorizedRoles: [USER_ROLES.acadAdviser],
				    stateName: "Evaluate Student"
				},
				templateUrl: TEMP_LOC.PATH + "attendance/evaluateStudent.html",
				controller: "evaluateStudentCtrl",
				controllerAs: "evaluateStudentCtrl"
			});
	    }
}());