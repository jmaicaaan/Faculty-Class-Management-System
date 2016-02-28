(function(){
	angular.module("secretaryApp")
		.controller("downloadProfilesCtrl", downloadProfilesCtrl);

	function downloadProfilesCtrl(){
		var self = this;
		self.items = ['Mon Zalameda', 'Eusebio Yu', 'Eisen Sy', 'Henry Castro', 'Ernest Ocson'];
		self.selected = [];
		self.toggle = toggle;
		self.exists = exists;
		self.selectAllProfessors = selectAllProfessors;

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
		

		self.get = function(){
			console.log(self.selected);
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
	}
})();