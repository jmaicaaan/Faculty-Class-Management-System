(function(){
	angular.module("profileModule", [
			"lazyLoadingApp",
			"schedulingModule"
		]);
}());

(function(){
	angular.module("profileModule")
		.config(config);

	function config($stateProvider, $urlRouterProvider, cfpLoadingBarProvider){
		const TEMP_LOC = "resources/templates/";
		cfpLoadingBarProvider.includeSpinner = '<md-progress-circular md-mode="indeterminate"></md-progress-circular>';

		$urlRouterProvider
			.when("/dashboard/profile", "dashboard/profile/general")
			.otherwise("/dashboard/profile");

		$stateProvider
			.state("dashboard.profile", {
				url: "/profile",
				templateUrl: TEMP_LOC + "profile.html"
			})
			.state("dashboard.profile.general", {
				url: "/general",
				templateUrl: TEMP_LOC + "profiling/generalTab.html",
				controller: "generalCtrl",
				controllerAs: "general"
			})
			.state("dashboard.profile.achievements", {
				url: "/achievements",
				
				templateUrl: TEMP_LOC + "profiling/achievementsTab.html",
				controller: "achievementsCtrl",
				controllerAs: "achieve"
			})
			.state("dashboard.profile.projects", {
				url: "/projects",
				templateUrl: TEMP_LOC + "profiling/projectsTab.html",
				controller: "projectsCtrl",
				controllerAs: "project"
			})
			.state("dashboard.profile.research", {
				url: "/researches",
				templateUrl: TEMP_LOC + "profiling/researchTab.html",
				controller: "researchCtrl",
				controllerAs: "research"
			})
			.state("dashboard.profile.resume", {
				url: "/resume",
				templateUrl: TEMP_LOC + "profiling/resumeTab.html",
				controller: "resumeCtrl",
				controllerAs: "resume"
			})
			.state("dashboard.profile.schedule", { /** This controller is on the scheduling **/
				url: "/schedule",
				templateUrl: TEMP_LOC + "profiling/myScheduleTab.html",
				controller: "scheduleCtrl",
				controllerAs: "sched"
			});
	}
}());