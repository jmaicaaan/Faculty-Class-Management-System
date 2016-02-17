(function(){
	angular.module("schedulingModule", []);
}());

(function(){
	angular.module("schedulingModule")
		.config(config)
		.constant("TEMP_LOC", {
			"PATH": "resources/templates/"
		});

	function config($stateProvider, $urlRouterProvider, cfpLoadingBarProvider, TEMP_LOC){


		$stateProvider
			.state("dashboard.uploadSubject", {
				url: "/uploadSubject",
				templateUrl: TEMP_LOC.PATH + "scheduling/uploadSubjects.html",
				controller: "uploadSubjectsCtrl",
				controllerAs: "uploadSubj"
			});

	}
}());