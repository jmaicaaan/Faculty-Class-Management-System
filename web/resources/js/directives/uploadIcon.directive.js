(function(){
	angular.module("profileModule")
		.directive("uploadIcon", uploadIcon);

	function uploadIcon(){
		return {
			restrict: "E",
			scope: {
				icon: "@", //Enable this to acquire it from the template. See below
				fileData: "=",
				url: "=",
				requestObj: "=",
				class: "@",
				tooltipMsg: "@",
				tooltipPos: "@",
				style: "@"
			},
			controller: uploadIconCtrl,
			link: linker,
			template: '<md-button class="{{class}}" aria-label="icon" style="{{style}}">' +
						'<md-tooltip md-direction="{{tooltipPos}}"> {{tooltipMsg}} </md-tooltip>' +
							'<md-icon md-svg-src="{{icon}}"></md-icon>' +
							'<input type="file">' +
						'</md-button>'
	 									
		}

		function uploadIconCtrl($scope, $timeout, uploadIconService, $mdToast){
			$scope.upload = upload;

			function upload(file, url){
				var formdata = new FormData();
				formdata.append("file", file);
				if($scope.requestObj){
					formdata.append("request", $scope.requestObj);
				}
				
				uploadIconService.upload(formdata, url).then(function(response){
					if(response.status == 200){
						var obj = {
							name: response.data.fileFileName,
							url: response.data.url,
							response: response.data.response
						};
						
						//Best practice compared to $scope.$apply
						$timeout(function(){
							$scope.fileData = obj;
							displayToast("Your file has been successfully uploaded.");
						});
					}else{
						displayToast("An error has occured. Please try again.");
					}
				});

				function displayToast(message){
					$mdToast.show(
						$mdToast.simple()
							.textContent(message)
							.position("top right")
							.hideDelay(2000)
					);
				}
			}
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