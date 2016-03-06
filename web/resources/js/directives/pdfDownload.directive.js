(function(){
	angular.module("secretaryApp")
		.directive("pdfDownload", pdfDownload);

	function pdfDownload(){
		return {
			restrict: "EA",
			scope: {
				link: "@",
				selected: "="
			},
			link: linker
		};

		function linker(scope, elem, attrs){
			var button = elem;

			button.on("click", function(event){
				console.log(scope.selected);
				var selected = scope.selected;
				var link = "";
				scope.link = "view.action?";
				console.log(scope.link);
			});
		}
	}
})();