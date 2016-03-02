(function(){
	angular.module("attendanceModule", ["events"]);
}());

(function(){
	angular.module("attendanceModule")
		.config(config)
		.constant("TEMP_LOC", {
			"PATH": "resources/templates/"
		});

	function config($stateProvider, $urlRouterProvider, TEMP_LOC, USER_ROLES){

		$urlRouterProvider
			.when("/manageClasslist", "/manageClasslist/list")
			.otherwise("/dashboard/");

		$stateProvider
			.state("dashboard.manageClasslist", {
				url: "/manageClasslist",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist_Menu.html",
				controller: function($state, $timeout){
					$timeout(function(){
						$state.go("dashboard.manageClasslist.list");
					});
				}
			})
			.state("dashboard.manageClasslist.list", {
				url: "/list",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.list.html",
				controller: "mngCLCtrlList",
				controllerAs: "mngCLCtrlList"
			})
			.state("dashboard.manageClasslist.class",{
				url: "/?c&s",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.html",
				controller: "mngCLCtrl",
				controllerAs: "mngCLCtrl"
			})
			.state("dashboard.recordAttendance", {
				url: "/recordAttendance",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/recordAttendance_Menu.html",
				controller: function($state){
					$state.go("dashboard.recordAttendance.list");
				}
			})
			.state("dashboard.recordAttendance.list", {
				url: "/recordAttendanceList",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/recordAttendance_List.html",
				controller: "recordAttendance_ListCtrl",
				controllerAs: "recordAttendance_ListCtrl"
			})
			.state("dashboard.recordAttendance.class", {
				url: "/?c&s",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "attendance/seatPlan.html",
				controller: "seatPlanCtrl",
				controllerAs: "seatPlanCtrl"
			});
	}
}());