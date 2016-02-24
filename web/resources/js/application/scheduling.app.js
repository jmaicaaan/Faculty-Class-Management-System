(function(){
	angular.module("schedulingModule", [
			"ngCookies"
		]);
}());

(function(){
	angular.module("schedulingModule")
		.config(config)
		.constant("TEMP_LOC", {
			"PATH": "resources/templates/"
		});

	function config($stateProvider, $urlRouterProvider, cfpLoadingBarProvider, TEMP_LOC){
		$stateProvider
			.state("dashboard.assignFaculty", {
				url: "/assignFaculty",
				templateUrl: TEMP_LOC.PATH + "scheduling/assignFaculty.html",
				controller: "assignFacultyCtrl",
				controllerAs: "assignFaculty"
			});

	}
}());