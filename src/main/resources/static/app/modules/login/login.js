'use strict';

angular.module('myApp.login', ['ui.router', 'ngMaterial', 'myApp.common'])

//ROUTER
    .config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('login', {
            url: "/login",
            templateUrl: 'modules/login/view/login.html',
            controller: 'loginCtrl',
            controllerAs: 'ctrl'
        });
    }])

    //CONTROLLER
    .controller('loginCtrl', ['loginService','$state', 'userService', 'notificationService', 'credentialsService', function(service, $state, userService, notificationService) {

        this.username = "";
        this.password = "";
        this.$state = $state;
        this.userService = userService;
        this.notificationService = notificationService;

        //Functions
        /**
         * Authenticate the user and redirect to landing page on sucessful login
         * as well as adds authentication key to storage.
         *
         * Notifies user of unsuccessful login attempts
         */
        this.login = function() {
            service.authenticate(this.username, this.password)
                .then(function successCallback(response){

                    //Todo add statement to tests.
                    userService.setUserRoles(response.data.roles);
                    userService.setUsername(response.data.username);
                    notificationService.notify('authentication successful');

                    $state.go('app.home');
                }, function errorCallback(error) {

                    //Todo coordinate with casey's notifications to show proper message from server
                    notificationService.notify('authentication failed');
                });
        }
    }])
    .service('loginService', ['$http', 'RestUtil', '$window', 'credentialsService',  function($http, restUtil, $window, credentialsService) {

        this.$window = $window;
        this.$http = $http;

        /**
         * Post request to server in order to authenticate the user
         *
         * @param username - the username
         * @param password - the password
         * @returns {HttpPromise} - Promise that resolves to http response
         */
        this.authenticate = function(username, password) {


            credentialsService.setCredentials(username,password);

            var url = restUtil.URL + '/authenticate';

            return this.$http(
                {
                    url: url,
                    method: "POST",
                    withCredentials: true
                }
            );
        };

        this.isAuthenticated = function() {
            // angular.isDefined($window.localStorage.getItem('x-auth-token'));
        }
    }]);
