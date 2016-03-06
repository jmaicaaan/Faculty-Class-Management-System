(function(){
	angular.module("secretaryApp", 
		["events", 
		"ngCookies"]);
})();

(function(){
	angular.module("secretaryApp")
		.config(secretaryAppConfig);

	function secretaryAppConfig($stateProvider, $urlRouterProvider, USER_ROLES){
		const TEMP_LOC = "resources/templates/";

		$urlRouterProvider
			.otherwise("/dashboard");

		$stateProvider
			.state("dashboard.downloadProfiles", {
				url: "/downloadProfiles",
				data:{
					authorizedRoles: [USER_ROLES.secretary]
				},
				templateUrl: TEMP_LOC + "secretary/downloadProfiles.html",
				controller: "downloadProfilesCtrl",
				controllerAs: "dp"
			});
	}
}());