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

		function seatPlanCtrl($scope, seatPlanService, $mdToast, $timeout){
			var self = this;
			self.date; //for datepicker
			self.saveAttendance = saveAttendance;
			self.addSelectedSeat = addSelectedSeat;
			self.deleteSelectedSeat = deleteSelectedSeat;
			self.hasScheduleToday = hasScheduleToday;
			self.selectedSeats = [];
			self.classObjAdapted = $scope.classObj; //directive scope

			function saveAttendance(studentObj){
				seatPlanService.saveAttendance(studentObj).then(function(response){
					emptySelectedSeats();
					if(response.status == 200){
						displayToast($mdToast, "You have now recorded your attendance.");
					}else{
						displayToast($mdToast, "You have encounter an error recording your attendance.");
					}
				});
			}

			$scope.$watch(function(){
				return seatPlanService.date;
			}, function(newValue){
				if(typeof newValue === "string"){
					var d = new Date(newValue);
					self.date = d;
				}
			});

			function addSelectedSeat(studentObj){
				//Let's sync it to the view! 
				$timeout(function(){
					self.selectedSeats.push(studentObj);	
				});
			}

			function deleteSelectedSeat(studentObj){
				$timeout(function(){
					var index = self.selectedSeats.indexOf(studentObj);
					self.selectedSeats.splice(index, 1);
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
			function hasScheduleToday(){
				var schedObj = self.classObjAdapted.schedObj;
				return seatPlanService.hasScheduleToday_Helper(schedObj);
			}

			function emptySelectedSeats(){
				self.selectedSeats = [];
			}
		}

		function linker(scope, elem, attrs){
			var classList = [];
			var emptySeats = [];
			var schedObj = {
				"schedObj": {
					"section": scope.classObj.schedObj.section,
					"subjects": {
						"courseCode": scope.classObj.schedObj.subjects.courseCode
					}
				}
			};
			var seatMap = angular.element(elem[0].childNodes[0].childNodes[1].childNodes[1]);
			var classSubject = angular.element(elem[0].childNodes[0].childNodes[1].childNodes[1].childNodes[0]);
			var saveAttendanceButton = angular.element(elem[0].querySelector(".md-raised"));
			var totalCount = angular.element(elem[0].querySelector("#total_count"));
			var dateCtrl = scope.seatPlanCtrl.date;
			var attendance = [];

			seatPlanService.viewClassList(schedObj).then(function(response){
				classList = response.data.classList;
				setClasslistCount(classList);
				
				if(classList.length > 0){
					var counter = 0;
					var SCsettings = {
					    map: [
					    	"aaaaa_aaaaa",
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
					        		pair = $filter("nameFormat")(classList[counter].users.lastName + 
					        			", " + classList[counter].users.firstName)
					        	}else{
					        		emptySeats.push(pair);
					        	}
					        	counter++;
					        	return pair;
					        }
					    },
					    click: function(){
					        if (this.status() == "available") {
					        	scope.seatPlanCtrl.addSelectedSeat(this.settings);
					            return "absent";
					        } else if (this.status() == "absent") {
					            return "late";
					        } else if (this.status() == "late") {
					        	scope.seatPlanCtrl.deleteSelectedSeat(this.settings);
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

					seatMap = seatMap.seatCharts(SCsettings); //sets the seatMap object
					setUnavailableSeats(seatMap, emptySeats);
					setSeatChartTitle(schedObj);
				}
			});

			scope.$watch(function(){
				return seatPlanService.classAttendance;
			}, function(newValue){
				if(newValue){
					attendance = newValue;
					loadAttendance(attendance);
					setClasslistCount(attendance);
					getAllSelectedSeats(seatMap);
				}
			});

			saveAttendanceButton.on("click", function(event){
				saveAttendance();
			});

			function setUnavailableSeats(seatMap, emptySeats){
				seatMap.status(emptySeats, "unavailable");
			}

			function setSeatChartTitle(schedObj){
				$("#title").text(schedObj.schedObj.subjects.courseCode + " " + schedObj.schedObj.section);
			}

			function setClasslistCount(classList){
				totalCount.html(classList.length);
			}

			function getAllSelectedSeats(seatMap){
				var absentSeats = seatMap.find("absent"),
					lateSeats = seatMap.find("late");
				if(absentSeats.length > 0 || lateSeats.length > 0){
					console.log(absentSeats);
					for(var i = 0; i <= absentSeats.seats.length - 1; i++){
						scope.seatPlanCtrl.addSelectedSeat(absentSeats.seats[i].settings);
					}
					for(var i = 0; i <= absentSeats.seats.length - 1; i++){
						scope.seatPlanCtrl.addSelectedSeat(lateSeats.seats[i].settings);
					}
				}
			}

			function emptyAllSeats(seatMap){
				//Based from the length of classlist
				seatMap.find("absent").status("available");
				seatMap.find("late").status("available");
			}

			function loadAttendance(attendanceArray){
				
				var len = attendanceArray.length;

				seatMap.find("a").each(function(seatID){

					var idNumber = this.settings.id;
					
					for(var i = 0; i <= len - 1; i++){
						if(idNumber == attendanceArray[i].classlist.users.idNo){
							var aStatus = "";
							switch(attendanceArray[i].attendance){
								case "P":
									aStatus = "available";
									break;
								case "L":
									aStatus = "late";
									break;
								case "A": 
									aStatus = "absent";
									break;
								default: 
									aStatus = "unavailable";
							}
							seatMap.status(idNumber, aStatus);
							break;
						}
					}
				});
			}

			function saveAttendance(){
				var absentObj = seatMap.find("absent").seatIds,
					lateObj = seatMap.find("late").seatIds,
					presentObj = seatMap.find("available").seatIds,
					date = dateCtrl;
					// date = seatPlanService.getDate_Helper().formatDate(dateCtrl);

				var attendanceObj = {
						schedObj: schedObj.schedObj,
						date: date,
						absentList: [],
						lateList: [],
						presentList: []
				};

				angular.forEach(absentObj, function(index){
					var user = {
						"idNo" : index
					};
					attendanceObj.absentList.push(user);
				});

				angular.forEach(lateObj, function(index){
					var user = {
						"idNo" : index
					};
					attendanceObj.lateList.push(user);
				});

				angular.forEach(presentObj, function(index){
					var user = {
						"idNo" : index
					};
					attendanceObj.presentList.push(user);
				});

				emptyAllSeats(seatMap);
				scope.seatPlanCtrl.saveAttendance(attendanceObj);
			}
		}
	}
})();