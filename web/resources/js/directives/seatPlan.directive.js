(function(){
	angular.module("attendanceModule")
		.directive("seatPlan", seatPlan);

	function seatPlan(TEMP_LOC, seatPlanService, $filter){
		return {
			restrict: "E",
			scope: {
				classObj: "=",
				hasList: "&"
			},
			controller: seatPlanCtrl,
			controllerAs: "seatPlanCtrl",
			link: linker,
			templateUrl: TEMP_LOC.PATH + "attendance/seatPlan.template.html"
		}

		function seatPlanCtrl(seatPlanService, $mdToast){
			var self = this;
			self.saveAttendance = saveAttendance;

			function saveAttendance(studentObj){
				seatPlanService.saveAttendance(studentObj).then(function(response){
					if(response.status == 200){
						displayToast($mdToast, "You have now recorded your attendance.");
					}else{
						displayToast($mdToast, "You have encounter an error recording your attendance.");
					}
				});
			}

			function displayToast($mdToast, message){
				$mdToast.show(
					$mdToast.simple()
						.textContent(message)
						.position("top")
						.hideDelay(2000)
				);
			}

		}

		function linker(scope, elem, attrs){
			var classList = [];
			var emptySeats = [];
			var schedObj = {
				"schedObj": {
					"section": scope.classObj.section,
					"subjects": {
						"courseCode": scope.classObj.subject
					}
				}
			};
			var seatMap = angular.element(elem[0].childNodes[0].childNodes[1].childNodes[1]);
			var classSubject = angular.element(elem[0].childNodes[0].childNodes[1].childNodes[1].childNodes[0]);
			var saveAttendanceButton = angular.element(elem[0].childNodes[0].childNodes[1].childNodes[3].childNodes[7]);

			seatPlanService.viewClassList(schedObj).then(function(response){
				classList = response.data.classList;
				if(classList.length > 0){
					var counter = 0;
					var SCsettings = {
					    map: [
					    	"aaaaa_aaaaa",
					    	"aaaaa_aaaaa",
					    	"aaaaa_aaaaa",
					    	"aaaaa_aaaaa"
					    	],
					    seats: {},
					    naming: {
					        top: true,
					        columns: ["A", "B", "C", "D", "E", "", "F", "G", "H", "I", "J"],
					        getId: function(character, row, column) {
					            var pair = row + "_" + column;
					            if(classList[counter] !== undefined){
					        		pair = classList[counter].users.idNo;
					        	}
					            return pair;
					        },
					        getLabel: function(character, row, column){
					        	var pair = row + "_" + column;
					        	if(classList[counter] !== undefined){
					        		pair = classList[counter].users.lastName + ", " + classList[counter].users.firstName;
					        	}else{
					        		emptySeats.push(pair);	
					        	}
					        	counter++;
					        	return pair;
					        }
					    },
					    click: function(){
					        if (this.status() == "available") {
					            return "absent";
					        } else if (this.status() == "absent") {
					            return "late";
					        } else if (this.status() == "late") {
					            return "available";
					        } else {
					            return this.style();
					        }
					    },
					    focus: function(){
					        $("#student_id").text(this.settings.id);
					        $("#student_name").text(this.settings.label);
					        return this.style();
					    },
					    blur: function() {
					        $("#student_id").empty();
					        $("#student_name").empty();
					        return this.style();
					    }
					};

					seatMap = seatMap.seatCharts(SCsettings);
					seatMap.status(emptySeats, 'unavailable');
					$("#title").text(schedObj.schedObj.subjects.courseCode + " " + schedObj.schedObj.section);
				}
			});

			saveAttendanceButton.on("click", function(event){
				var absentObj = seatMap.find("absent").seatIds,
					lateObj = seatMap.find("late").seatIds;
				var obj = {
					absentList: [],
					lateList: []
				};
				angular.forEach(absentObj, function(index){
					var user = {
						"idNo" : index
					};
					obj.absentList.push(user);
				});

				angular.forEach(lateObj, function(index){
					var user = {
						"idNo" : index
					};
					obj.lateList.push(user);
				});

				seatMap.find("absent").status("available");
				seatMap.find("late").status("available");
				scope.seatPlanCtrl.saveAttendance(obj);
			});
		}
	}
})();