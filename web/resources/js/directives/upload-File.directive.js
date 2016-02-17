(function(){
	angular.module("profileModule")
		.directive("uploadFile", uploadFile);
	
	function uploadFile($timeout){
		return {
			restrict: "E",
			scope: {
				icon: "@", //Enable this to acquire it from the template. See below
				fileData: "="
			},
			link: linker,
			template: '<md-button class="md-icon-button" aria-label="icon">' +
							'<md-icon md-svg-src="{{icon}}"></md-icon>' +
							'<input type="file">' +
							'</md-button>'
	 									
		}

		function linker(scope, elem, attrs){
		
			var button = elem.find("button"),
			fileUpload = elem.find("input");

			button.on("click", function(event){
				fileUpload[0].click();
			});

			fileUpload.on("change", function(event){
				var selectedFile = event.target.files[0];
				$timeout(function(){
					console.log(selectedFile);
					scope.fileData = selectedFile;	
				});
			});


		}
	}
}());