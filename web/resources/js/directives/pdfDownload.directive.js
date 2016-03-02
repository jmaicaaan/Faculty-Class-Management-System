(function(){
	angular.module("secretaryApp")
		.directive("pdfDownload", pdfDownload);

	function pdfDownload(){
		return {
			restrict: "E",
			scope: {
				link: "@",
				selected: "="
			},
			link: linker,
			template: '<md-button class="md-primary md-raised" ng-href="{{link}}">'
					+ 'Download PDF </md-button>' 
		};

		function linker(scope, elem, attrs){
			var button = elem;

			button.on("click", function(event){
				console.log(scope.selected);
				var selected = scope.selected;
				var link = "";
				angular.forEach(selected, function(index){
					
				});
				scope.link = "view.action?";
				console.log(scope.link);
			});
		}
	}
})();