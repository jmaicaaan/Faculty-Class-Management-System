(function(){
	angular.module("facultyApp", [
			"ui.router", 
			"ngMaterial", 
			"md.data.table", 
			"angular-loading-bar",
			"events",
			"profileModule",
			"developerApp",
			"secretaryApp"
		]);
}());

(function(){

	angular.module("facultyApp")
		.config(facultyAppConfig)
		.run(function($rootScope, $state){
			$rootScope.$on("$stateChangeError", function(event, toState, toParams, fromState, fromParams, error){
				console.log("Error occured");
				$state.go("index");
			});
		});

	function facultyAppConfig($stateProvider, $urlRouterProvider, USER_ROLES){

		const TEMP_LOC = "resources/templates/";
		
		 $urlRouterProvider
		 	.when("/dashboard/", "/dashboard")
			 .otherwise("/");
		
		$stateProvider
			.state("index", {
				url: "/",
				templateUrl: TEMP_LOC + "loginPage.html",
				controller: "loginCtrl",
				controllerAs: "login"
			})
			.state("dashboard", {
				url: "/dashboard",
				templateUrl: TEMP_LOC + "dashboard.html",
				controller: "dashboardCtrl",
				controllerAs: "dash",
				resolve:{
					"userObj": function(authService, userService, $q, $state){
						var deferred = $q.defer();
						console.log(userService.userInfo.username);

						//This one solves for refreshing the page
						if(userService.userInfo.username == undefined){
							authService.checkOnlineUser().then(function(response){
								console.log(response);
								if(response.data.has_User == true){
									authService.updateSession().then(function(){
										deferred.resolve(userService.userInfo);
									});
								}else{
									deferred.reject();
								}
							});
						}else{
							deferred.resolve(userService.userInfo);
						}
						return deferred.promise;
					}
				}
			})
			.state("dashboard.settings", {
				url: "/settings",
				templateUrl: TEMP_LOC + "/settings/settings.html",
				data:{
					authorizedRoles: [USER_ROLES.professor, USER_ROLES.acadAdviser, USER_ROLES.chairperson, USER_ROLES.student]
				},
				controller: function($state){
					$state.go("dashboard.settings.general");
				}
			})
			.state("dashboard.settings.general", {
				url: "/general",
				templateUrl: TEMP_LOC + "settings/settings.general.html",
				controller: "genSetCtrl",
				controllerAs: "genSet"
			})
			.state("dashboard.settings.miscellaneous", {
				url: "/miscellaneous",
				templateUrl: TEMP_LOC + "settings/settings.miscellaneous.html",
				controller: "miscCtrl",
				controllerAs: "misc"
			})
			.state("forgotPwd", {
				url: "/forgotPwd",
				templateUrl: TEMP_LOC + "forgotpwd.html",
				controller: "forgotpwdCtrl",
				controller: "forgotpwdCtrl"
			})
			.state("logout",{
				url: "/",
				controller: function(authService){
					authService.logoutUser();
				}
			});
	}
}());