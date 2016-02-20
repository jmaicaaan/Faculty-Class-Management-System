(function(){
	angular.module("profileModule")
		.directive("uploadIcon", uploadIcon);

	function uploadIcon(){
		return {
			restrict: "E",
			scope: {
				icon: "@", //Enable this to acquire it from the template. See below
				fileData: "=",
				url: "="
			},
			controller: function($scope, $timeout, uploadIconService){
				$scope.upload = upload;

				function upload(file, url){
					var formdata = new FormData();
					formdata.append("file", file);
					
					uploadIconService.upload(formdata, url).then(function(response){
						console.log(response);
						var obj = {
							name: response.data.fileFileName,
							url: response.data.url,
							response: response.data.response
						};
						
						//Best practice compared to $scope.$apply
						$timeout(function(){
							$scope.fileData = obj;
						});
					
					});
				}
			},
			link: linker,
			template: '<md-button class="md-icon-button" aria-label="icon">' +
							'<md-icon md-svg-src="{{icon}}"></md-icon>' +
							'<input type="file">' +
							'</md-button>'
	 									
		}

		function linker(scope, elem, attrs){
		
			var button = elem.find("button"),
			fileUpload = elem.find("input"),
			url = attrs.url;

			button.on("click", function(event){
				fileUpload[0].click();
			});

			fileUpload.on("change", function(event){
				var selectedFile = event.target.files[0];
				if(selectedFile){
					scope.upload(selectedFile, url);
				}
			});


		}
	}
}());