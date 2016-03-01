(function(){
	angular.module("attendanceModule", []);
}());

(function(){
	angular.module("attendanceModule")
		.config(config)
		.constant("TEMP_LOC", {
			"PATH": "resources/templates/"
		});

	function config($stateProvider, $urlRouterProvider, TEMP_LOC){

		$urlRouterProvider
			.when("/manageClasslist", "/manageClasslist/list")
			.otherwise("/dashboard/");

		$stateProvider
			.state("dashboard.manageClasslist", {
				url: "/manageClasslist",
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist_Menu.html",
				controller: function($state, $timeout){
					$timeout(function(){
						$state.go("dashboard.manageClasslist.list");
					});
				}
			})
			.state("dashboard.manageClasslist.list", {
				url: "/list",
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.list.html",
				controller: "mngCLCtrlList",
				controllerAs: "mngCLCtrlList"
			})
			.state("dashboard.manageClasslist.class",{
				url: "/?c&s",
				templateUrl: TEMP_LOC.PATH + "attendance/manageClasslist.html",
				controller: "mngCLCtrl",
				controllerAs: "mngCLCtrl"
			});
	}
}());