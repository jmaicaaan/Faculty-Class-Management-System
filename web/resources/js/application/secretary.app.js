(function(){
	angular.module("secretaryApp", []);
})();

(function(){
	angular.module("secretaryApp")
		.config(secretaryAppConfig);

	function secretaryAppConfig($stateProvider, $urlRouterProvider){
		const TEMP_LOC = "resources/templates/";

		$urlRouterProvider
			.otherwise("/dashboard");

		$stateProvider
			.state("dashboard.downloadProfiles", {
				url: "/downloadProfiles",
				templateUrl: TEMP_LOC + "secretary/downloadProfiles.html",
				controller: "downloadProfilesCtrl",
				controllerAs: "dp"
			});
	}
}());