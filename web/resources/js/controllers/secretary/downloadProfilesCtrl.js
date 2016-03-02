(function(){
	angular.module("secretaryApp")
		.controller("downloadProfilesCtrl", downloadProfilesCtrl);

	function downloadProfilesCtrl(downloadProfilesService, $timeout){
		var self = this;
		self.items = [];
		self.selected = [];
		self.toggle = toggle;
		self.exists = exists;
		self.selectAllProfessors = selectAllProfessors;
		self.view_Professors = view_Professors;
		self.download = download;
		self.hasItems = false;

		view_Professors();

		function view_Professors(){
			$timeout(function(){
				downloadProfilesService.view_Professors().then(function(response){
					console.log(response);
					self.items = response.data.pList;
					self.hasItems = true;
					console.log(self.items);
				});	
			}, 1000);
		}


		function toggle(item, list){
			var index = list.indexOf(item);
			if(index > -1){
				list.splice(index, 1);
			}else{
				list.push(item);
			}
		}
		function exists(item, list){
			return list.indexOf(item) > - 1;
		}
		
		function selectAllProfessors(){
			var ar = self.items;
			var list = self.selected;

			angular.forEach(ar, function(index){
				if(list.indexOf(index) > - 1){
					list.splice(index, 1);
				}else{
					self.selected.push(index);	
				}
				
			});
		}

		function download(){
			console.log(self.selected);
		}

		
	}
})();