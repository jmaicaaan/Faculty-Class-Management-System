(function(){
	angular.module("secretaryApp")
		.service("downloadProfilesService", downloadProfilesService);

	function downloadProfilesService($http, $cookies){
		var self = this;
		self.view_Professors = view_Professors;
		self.downloadPDF = downloadPDF;

		function view_Professors(){
			var request = {
				url: "view_Professors.action",
				method: "post",
				headers: {
					"Content-Type": "application/json",
					"dataType": "json"
				}
			};

			return $http(request)
				.then(function(response){

					console.log(response);
					return response;
				})
				.catch(function(error){
					return error;
				});
		}

		function downloadPDF(){
			var selected = $cookies.get("secretaryList");
			// var request = {
			// 	url: "sec_downloadPDF.action",
			// 	method: "post",
			// 	data: selected,
			// 	headers: {
			// 		"Content-Type"
			// 	}
			// };
		}
	}
}());