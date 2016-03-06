(function(){
	angular.module("profileModule", [
			"lazyLoadingApp",
			"events",
			"schedulingModule",
			"attendanceModule",

		]);
}());

(function(){
	angular.module("profileModule")
		.config(config)
		.run(function($rootScope, $state, authService){
			$rootScope.$on("$stateChangeStart", function(event, next){
				if(next.hasOwnProperty("data")){
					var arrayOfRoles = next.data.authorizedRoles;
					authService.checkOnlineUser().then(function(response){
						if(response == true){
							if( !authService.isAuthorized(arrayOfRoles)){
								event.preventDefault();
							}
						}
					});
				}				
			});
		});

	function config($stateProvider, $urlRouterProvider, cfpLoadingBarProvider, USER_ROLES){
		const TEMP_LOC = "resources/templates/";
		cfpLoadingBarProvider.includeSpinner = '<md-progress-circular md-mode="indeterminate"></md-progress-circular>';

		$urlRouterProvider
			.when("/dashboard/profile", "dashboard/profile/general")
			.otherwise("/dashboard");

		$stateProvider
			.state("dashboard.profile", {
				url: "/profile",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profile.html"
			})
			.state("dashboard.profile.general", {
				url: "/general",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profiling/generalTab.html",
				controller: "generalCtrl",
				controllerAs: "general"
			})
			.state("dashboard.profile.achievements", {
				url: "/achievements",
				templateUrl: TEMP_LOC + "profiling/achievementsTab.html",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				controller: "achievementsCtrl",
				controllerAs: "achieve"
			})
			.state("dashboard.profile.projects", {
				url: "/projects",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profiling/projectsTab.html",
				controller: "projectsCtrl",
				controllerAs: "project"
			})
			.state("dashboard.profile.research", {
				url: "/researches",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profiling/researchTab.html",
				controller: "researchCtrl",
				controllerAs: "research"
			})
			.state("dashboard.profile.resume", {
				url: "/resume",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profiling/resumeTab.html",
				controller: "resumeCtrl",
				controllerAs: "resume"
			})
			.state("dashboard.profile.schedule", { /** This controller is on the scheduling **/
				url: "/schedule",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				templateUrl: TEMP_LOC + "profiling/myScheduleTab.html",
				controller: "scheduleCtrl",
				controllerAs: "sched"
			});
	}
}());