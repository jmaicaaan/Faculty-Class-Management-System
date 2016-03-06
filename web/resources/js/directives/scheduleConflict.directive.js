(function(){
	angular.module("schedulingModule")
		.directive("scheduleConflict", scheduleConflict);

	function scheduleConflict(){
		return {
			restrict: "A",
			scope: {
				subjectData: "=",
				userData: "="
			},
			link: linker,
			controller: schedCtrl
		};

		function linker(scope, elem, attrs){
			var radioButton = angular.element(elem[0]);
			var hasConflict = false;

			radioButton.on("click", function(event){
				var data = {
					"hashKey": scope.subjectData.$$hashKey,
					"user": scope.userData,
					"data": {
						"cID": scope.subjectData.sched.cID,
						"day": scope.subjectData.sched.day,
						"time": scope.subjectData.sched.time,
						"section": scope.subjectData.sched.section,
						"room": scope.subjectData.sched.room,
						"courseCode": scope.subjectData.subject.courseCode
					}
				};
				scope.checkConflict(data).then(function(response){
					console.log(response);
					if(response == true){
						alert("A conflict has occurred");
					}
				});
			});
		}

		function schedCtrl($scope, scheduleConflictService, $q, $toast){
			var self = $scope;
			self.checkConflict = checkConflict;
			self.displayToast = displayToast;

			function checkConflict(data){
				var serviceContainer = scheduleConflictService.container;
				var dataObj = data;
				var hasConflict = false;
				var deferred = $q.defer();

				if(serviceContainer.length <= 0){
					serviceContainer.push(dataObj);
				}else{
					
					//Same hashKey means same table/card
					for(index in serviceContainer){
						if(dataObj.hashKey == serviceContainer[index].hashKey){

							if(scheduleConflictService.hasConflict(dataObj)){
								hasConflict = true;
							} else{
								serviceContainer[index].user = dataObj.user;
							}
							break;
						}else{

							if(scheduleConflictService.hasConflict(dataObj)){
								hasConflict = true;
							}else{
								serviceContainer.push(dataObj);	
							}
							break;
						}
					}
				}
				deferred.resolve(hasConflict);
				return deferred.promise;
			}

			function displayToast($mdToast){
				$mdToast.show(
					$mdToast.simple()
						.textContent("Conflict has occurred")
						.position("bottom")
				);
			}
		}
	}
})();

(function(){
	angular.module("schedulingModule")
		.service("scheduleConflictService", scheduleConflictService);

	function scheduleConflictService($q){
		var self = this;
		self.container = [];
		// self.checkConflict = checkConflict;
		self.hasConflict = hasConflict;

		

		function hasConflict(dataObj){
			var container = self.container;

			for(index in container){
				if (dataObj.hashKey != container[index].hashKey){
					if(dataObj.data.courseCode == container[index].data.courseCode
						&& dataObj.data.time == container[index].data.time
						&& dataObj.data.day == container[index].data.day){

						if(dataObj.user == container[index].user){
							return true;
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}

	}
})();