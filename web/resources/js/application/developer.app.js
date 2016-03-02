(function(){
	angular.module("developerApp", ["events"]);
}());

(function(){
	angular.module("developerApp")
		.config(developerAppConfig)

	function developerAppConfig($stateProvider, $urlRouterProvider, USER_ROLES){

		const TEMP_LOC = "resources/templates/";

		$urlRouterProvider
			.otherwise("/dashboard");
		
		$stateProvider
			.state("dashboard.userManagement", {
				url: "/users_manage",
				data:{
					authorizedRoles: [USER_ROLES.developer]
				},
				templateUrl: TEMP_LOC + "developer/users_manage.html",
				controller: "usersManageCtrl",
				controllerAs: "um"
			})
			.state("dashboard.subjManagement", {
				url: "/subj_manage",
				data:{
					authorizedRoles: [USER_ROLES.developer]
				},
				templateUrl: TEMP_LOC + "developer/subj_manage.html",
				controller: "subjManageCtrl",
				controllerAs: "sm"
			});
	}	
}());