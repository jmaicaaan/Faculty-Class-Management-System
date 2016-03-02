(function(){
	angular.module("schedulingModule", [
			"events",
			"ngCookies",

		]);
}());

(function(){
	angular.module("schedulingModule")
		.config(config)
		.constant("TEMP_LOC", {
			"PATH": "resources/templates/"
		});

	function config($stateProvider, $urlRouterProvider, cfpLoadingBarProvider, TEMP_LOC, USER_ROLES){
		$stateProvider
			.state("dashboard.assignFaculty", {
				url: "/assignFaculty",
				data:{
					authorizedRoles: [USER_ROLES.chairperson]
				},
				templateUrl: TEMP_LOC.PATH + "scheduling/assignFaculty.html",
				controller: "assignFacultyCtrl",
				controllerAs: "assignFaculty"
			});

	}
}());