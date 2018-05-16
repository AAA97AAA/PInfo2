var app = angular.module("academi-co",[]);

app.controller("lang",function($scope, $http){
	$http.get("http://localhost:8080/academi-co/resources/tags/languages/0").then(function(response)){
		$scope.las = response.name;
	});
	
});