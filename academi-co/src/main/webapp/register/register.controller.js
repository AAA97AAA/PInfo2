(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', '$rootScope', 'FlashService'];
    function RegisterController(UserService, $location, $rootScope, FlashService) {
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if (response) {
                    	console.log("shit works !!!!!")
                    	console.log(vm.user)
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    } else {
                    	console.log("shit doesnt work bro")
                    	console.log(vm.user)
                        FlashService.Error(response);
                        vm.dataLoading = false;
                    }
                });
        }
    }

})();
