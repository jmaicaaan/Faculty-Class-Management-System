(function(){
	angular.module("developerApp", ["angular-loading-bar"]);
}());

(function(){
	angular.module("developerApp")
		.config(developerAppConfig)

	function developerAppConfig($stateProvider, $urlRouterProvider){

		const TEMP_LOC = "resources/templates/";

		$urlRouterProvider
			.otherwise("/dashboard");
		
		$stateProvider
			.state("dashboard.userManagement", {
				url: "/users_manage",
				templateUrl: TEMP_LOC + "developer/users_manage.html",
				controller: "usersManageCtrl",
				controllerAs: "um"
			})
			.state("dashboard.subjManagement", {
				url: "/subj_manage",
				templateUrl: TEMP_LOC + "developer/subj_manage.html",
				controller: "subjManageCtrl",
				controllerAs: "sm"
			});
	}	
}());