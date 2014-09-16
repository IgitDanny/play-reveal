angular.module('slidesApp', [])
    .controller('ReplController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

        $http.defaults.headers.common.Accept = "application/text";

        $scope.code = "// add code here";
        $scope.error = undefined;
        $scope.errorCode = undefined;

        $timeout(function () {
            console.log("timeout");
        });

        $scope.hasError = function() {
          return angular.isDefined($scope.error);
        };

        $scope.fetchCode = function(url) {
          $http.get(url)
              .success(function(data, status, headers, config) {
                $scope.code = data;
              })
              .error(function(data, status, headers, config) {
                  $scope.error = "couldn't load script '" + url + "'";
                  $scope.errorCode = status;
                  $scope.code = data;
              });
        };
    }]);