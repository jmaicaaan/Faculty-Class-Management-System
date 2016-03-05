(function(){
	angular.module("attendanceModule")
		.controller("attendanceRptCtrl", attendanceRptCtrl);

	function attendanceRptCtrl($stateParams){
		var self = this;
		self.class = {
			"subject": $stateParams.c,
			"section": $stateParams.s
		};
		self.downloadChart = downloadChart;
		self.chartConfig = {
	        options: {
	            chart: {
	                type: 'column'
	            }
	        },
	        series: [{
	            name: "Jm Santos",
	            data: [1]
	        }, {
	        	name: "Krister Chua",
	        	data: [4]
	        }, {
	        	name: "Bryan Dizon",
	        	data: [3]
	        }, {
	        	name: "Mark Ling",
	        	data: [5]
	        }],
	        title: {
	            text: [self.class.subject, self.class.section].join(" ")
	        },
			loading: false,
		    yAxis: {
			  currentMin: 0,
			  currentMax: 5,
			}
		};

		function downloadChart(){
			var chart = this.chartConfig.getHighcharts();
			chart.print();
		}
	}
})();